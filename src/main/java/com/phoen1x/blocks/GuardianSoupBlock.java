package com.phoen1x.blocks;

import com.mojang.serialization.MapCodec;
import com.phoen1x.items.ODItems;
import eu.pb4.factorytools.api.block.FactoryBlock;
import eu.pb4.factorytools.api.virtualentity.BlockModel;
import eu.pb4.factorytools.api.virtualentity.ItemDisplayElementUtil;
import eu.pb4.polymer.virtualentity.api.ElementHolder;
import eu.pb4.polymer.virtualentity.api.attachment.BlockBoundAttachment;
import eu.pb4.polymer.virtualentity.api.attachment.HolderAttachment;
import eu.pb4.polymer.virtualentity.api.elements.ItemDisplayElement;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import xyz.nucleoid.packettweaker.PacketContext;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a Guardian Soup Block that changes its visual model based on servings.
 * This block uses a BlockEntity to store its state and a virtual entity for rendering.
 */
public class GuardianSoupBlock extends BlockWithEntity implements FactoryBlock {
    public static final int MAX_SERVINGS = 6; // Moved before SERVINGS
    public static final IntProperty SERVINGS = IntProperty.of("servings", 0, MAX_SERVINGS - 1);
    public static final MapCodec<GuardianSoupBlock> CODEC = createCodec(GuardianSoupBlock::new);
    private Model model;

    /**
     * Constructor for the GuardianSoupBlock.
     * Sets up block properties like strength, occlusion, and sounds.
     * Updated to accept BlockBehaviour.Properties as required by BlockWithEntity's codec.
     */
    public GuardianSoupBlock(Settings settings) {
        super(settings.nonOpaque());
        this.setDefaultState(this.stateManager.getDefaultState().with(SERVINGS, 0));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(SERVINGS);
    }

    /**
     * Returns the MapCodec for this block.
     */
    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    /**
     * Creates a new BlockEntity for this block.
     * @param pos The position of the block.
     * @param state The block state.
     * @return A new GuardianSoupBlockEntity instance.
     */
    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new GuardianSoupBlockEntity(pos, state);
    }

    /**
     * Provides the BlockEntityTicker for this block, allowing the BlockEntity to be ticked.
     * @param world The world.
     * @param state The block state.
     * @param type The BlockEntityType.
     * @return A BlockEntityTicker for GuardianSoupBlockEntity.
     */
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return type == ODEntities.GUARDIAN_SOUP ? (world1, pos, state1, entity) -> GuardianSoupBlockEntity.tick(world1, pos, state1, (GuardianSoupBlockEntity) entity) : null;
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient()) {
            if (world.getBlockEntity(pos) instanceof GuardianSoupBlockEntity entity) {
                ItemStack playerStack = player.getMainHandStack();
                int currentServings = entity.getServings();

                // Scenario 1: Player is holding a bowl and there is soup to take (not completely empty).
                if (playerStack.isOf(Items.BOWL) && currentServings < MAX_SERVINGS - 1) {
                    playerStack.decrement(1);
                    if (!player.getInventory().insertStack(new ItemStack(ODItems.BOWL_OF_GUARDIAN_SOUP))) {
                        player.dropItem(new ItemStack(ODItems.BOWL_OF_GUARDIAN_SOUP), false, true);
                    }
                    entity.setServings(currentServings + 1);
                    world.setBlockState(pos, state.with(SERVINGS, currentServings + 1)); // Update block state
                    world.playSound(null, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    return ActionResult.SUCCESS;
                }
                // Scenario 2: Player is holding a Bowl of Guardian Soup and the block is not full.
                else if (playerStack.isOf(ODItems.BOWL_OF_GUARDIAN_SOUP) && currentServings > 0) {
                    playerStack.decrement(1);
                    if (!player.getInventory().insertStack(new ItemStack(Items.BOWL))) {
                        player.dropItem(new ItemStack(Items.BOWL), false, true);
                    }
                    entity.setServings(currentServings - 1);
                    world.setBlockState(pos, state.with(SERVINGS, currentServings - 1)); // Update block state
                    world.playSound(null, pos, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    return ActionResult.SUCCESS;
                }
            }
        }
        return super.onUse(state, world, pos, player, hit);
    }

    /**
     * Specifies the Polymer block state to send to clients.
     * Returning Blocks.BARRIER hides the actual block, allowing the virtual entity to render.
     * @param state The current block state.
     * @param context The packet context.
     * @return The BlockState to send to clients.
     */
    @Override
    public BlockState getPolymerBlockState(BlockState state, PacketContext context) {
        return Blocks.BARRIER.getDefaultState();
    }

    /**
     * Creates an ElementHolder for the virtual entity rendering.
     * This method is called when the block is placed or loaded.
     * @param world The server world.
     * @param pos The position of the block.
     * @param initialBlockState The initial block state.
     * @return A new Model instance for this block.
     */
    @Override
    public @Nullable ElementHolder createElementHolder(ServerWorld world, BlockPos pos, BlockState initialBlockState) {
        Identifier blockId = Registries.BLOCK.getId(this);
        model = new Model(initialBlockState, world, pos, blockId);
        return model;
    }

    /**
     * Ticks the ElementHolder (virtual entity model) for this block.
     * Ensures the model stays updated with the BlockEntity's state.
     * @param world The server world.
     * @param pos The position of the block.
     * @param initialBlockState The initial block state.
     * @return True if the ElementHolder should continue ticking.
     */
    @Override
    public boolean tickElementHolder(ServerWorld world, BlockPos pos, BlockState initialBlockState) {
        // Retrieve the BlockEntity to get the current servings.
        if (world.getBlockEntity(pos) instanceof GuardianSoupBlockEntity entity) {
            if (model != null) {
                // Update the model based on the BlockEntity's servings.
                model.updateSoupModel(entity.getServings());
            }
        }
        return true; // Continue ticking the element holder.
    }

    /**
     * Inner class representing the virtual model for the Guardian Soup Block.
     * This class handles loading and displaying the different stages of the soup model.
     */
    public static class Model extends BlockModel {
        // Map to store pre-loaded ItemStacks for each serving stage (0 to MAX_SERVINGS-1).
        private final Map<Integer, ItemStack> servingModels = new HashMap<>();
        // The ItemDisplayElement used to render the current soup model.
        private final ItemDisplayElement soupDisplayElement;
        private final ServerWorld world;
        private final BlockPos pos;

        /**
         * Constructor for the Model.
         * Loads all the different stage models and initializes the display element.
         * @param state The initial block state.
         * @param world The server world.
         * @param pos The position of the block.
         * @param blockId The Identifier of the Guardian Soup Block.
         */
        public Model(BlockState state, ServerWorld world, BlockPos pos, Identifier blockId) {
            this.world = world;
            this.pos = pos;

            // Load all serving models from stage0.json to stage(MAX_SERVINGS-1).json.
            // These JSON files define the visual appearance for each stage.
            for (int i = 0; i < MAX_SERVINGS; i++) {
                // Constructs the Identifier for each model file.
                // Example: "oceansdelight:block/guardian_soup_stage0"
                Identifier modelId = Identifier.of(blockId.getNamespace(), "block/guardian_soup_stage" + i);
                servingModels.put(i, ItemDisplayElementUtil.getModel(modelId));
            }

            // Determine the initial servings from the BlockEntity.
            int initialServings = 0; // Default to full (stage 0) if entity not yet available.
            if (world.getBlockEntity(pos) instanceof GuardianSoupBlockEntity entity) {
                initialServings = entity.getServings();
            }

            // Create the initial ItemDisplayElement with the model corresponding to the initial servings.
            // Uses getOrDefault to provide a fallback (stage0) if the key is not found, though it should be.
            this.soupDisplayElement = ItemDisplayElementUtil.createSimple(servingModels.getOrDefault(initialServings, servingModels.get(0)));
            // Set the scale of the virtual model (adjust as needed for proper size).
            this.soupDisplayElement.setScale(new Vector3f(1.0f));
            // Add the display element to this model holder.
            this.addElement(soupDisplayElement);
        }

        /**
         * Updates the displayed soup model based on the provided servings count.
         * This method dynamically changes the visual representation of the block.
         * @param servings The current servings count from the BlockEntity.
         */
        public void updateSoupModel(int servings) {
            // Map servings (0=full, 5=empty) to models (stage0=full, leftover=empty)
            int modelIndex = GuardianSoupBlock.MAX_SERVINGS - 1 - servings; // Invert: 0->5, 1->4, 2->3, 3->2, 4->1, 5->0
            ItemStack newModel;
            if (modelIndex == 0) {
                // Use the leftover model for empty state (servings=5)
                newModel = servingModels.getOrDefault(modelIndex, ItemDisplayElementUtil.getModel(Identifier.of("oceansdelight-port", "block/guardian_soup_leftover")));
            } else {
                // Use stage models for other states
                newModel = servingModels.getOrDefault(modelIndex, servingModels.get(0));
            }
            this.soupDisplayElement.setItem(newModel);
            this.soupDisplayElement.tick();
        }

        /**
         * Called when the attached block's state is updated.
         * This ensures the virtual model reflects any changes in the block entity's state.
         * @param updateType The type of update that occurred.
         */
        @Override
        public void notifyUpdate(HolderAttachment.UpdateType updateType) {
            if (updateType == BlockBoundAttachment.BLOCK_STATE_UPDATE) {
                if (world.getBlockEntity(pos) instanceof GuardianSoupBlockEntity entity) {
                    updateSoupModel(entity.getServings());
                }
            }
            super.notifyUpdate(updateType);
        }

        /**
         * Called every tick for the model.
         * Currently, no continuous animation or logic is needed here.
         */
        @Override
        protected void onTick() {
            // No continuous animation needed for the model itself, but could be added here
            // for effects like subtle bobbing or glowing.
        }
    }
}