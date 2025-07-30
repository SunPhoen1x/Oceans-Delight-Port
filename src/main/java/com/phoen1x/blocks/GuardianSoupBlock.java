package com.phoen1x.blocks;

import com.mojang.serialization.MapCodec;
import com.phoen1x.blocks.entities.GuardianSoupBlockEntity;
import com.phoen1x.items.ODItems;
import eu.pb4.factorytools.api.block.FactoryBlock;
import eu.pb4.factorytools.api.virtualentity.BlockModel;
import eu.pb4.factorytools.api.virtualentity.ItemDisplayElementUtil;
import eu.pb4.polymer.virtualentity.api.ElementHolder;
import eu.pb4.polymer.virtualentity.api.attachment.BlockBoundAttachment;
import eu.pb4.polymer.virtualentity.api.attachment.HolderAttachment;
import eu.pb4.polymer.virtualentity.api.elements.ItemDisplayElement;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import xyz.nucleoid.packettweaker.PacketContext;

import java.util.HashMap;
import java.util.Map;

public class GuardianSoupBlock extends BlockWithEntity implements FactoryBlock {
    public static final int MAX_SERVINGS = 4;
    public static final IntProperty SERVINGS = IntProperty.of("servings", 0, MAX_SERVINGS);
    public static final EnumProperty<Direction> FACING = Properties.HORIZONTAL_FACING;
    public static final MapCodec<GuardianSoupBlock> CODEC = createCodec(GuardianSoupBlock::new);

    public GuardianSoupBlock(Settings settings) {
        super(settings.nonOpaque());
        this.setDefaultState(this.stateManager.getDefaultState().with(SERVINGS, 0).with(FACING, Direction.NORTH));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(SERVINGS, FACING);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new GuardianSoupBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return null;
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (world.isClient()) {
            return ActionResult.SUCCESS;
        }

        ItemStack playerStack = player.getMainHandStack();
        int currentServings = state.get(SERVINGS);

        if (playerStack.isOf(Items.BOWL) && currentServings < MAX_SERVINGS) {
            playerStack.decrement(1);
            if (!player.getInventory().insertStack(new ItemStack(ODItems.BOWL_OF_GUARDIAN_SOUP))) {
                player.dropItem(new ItemStack(ODItems.BOWL_OF_GUARDIAN_SOUP), false, true);
            }
            world.setBlockState(pos, state.with(SERVINGS, currentServings + 1));
            world.playSound(null, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
            return ActionResult.SUCCESS;
        } else if (playerStack.isOf(ODItems.BOWL_OF_GUARDIAN_SOUP) && currentServings > 0) {
            playerStack.decrement(1);
            if (!player.getInventory().insertStack(new ItemStack(Items.BOWL))) {
                player.dropItem(new ItemStack(Items.BOWL), false, true);
            }
            world.setBlockState(pos, state.with(SERVINGS, currentServings - 1));
            world.playSound(null, pos, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
            return ActionResult.SUCCESS;
        } else if (currentServings == MAX_SERVINGS) {
            world.breakBlock(pos, true);
            world.playSound(null, pos, SoundEvents.BLOCK_STONE_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);
            return ActionResult.SUCCESS;
        } else {
            player.sendMessage(Text.translatable("farmersdelight.block.feast.use_container", Items.BOWL.getName()), true);
            return ActionResult.FAIL;
        }
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite()).with(SERVINGS, 0);
    }

    @Override
    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    public BlockState getPolymerBlockState(BlockState state, PacketContext context) {
        return Blocks.BARRIER.getDefaultState();
    }

    @Override
    public BlockState getPolymerBreakEventBlockState(BlockState state, PacketContext context) {
        return Blocks.LANTERN.getDefaultState();
    }

    @Override
    public @Nullable ElementHolder createElementHolder(ServerWorld world, BlockPos pos, BlockState initialBlockState) {
        Identifier blockId = Registries.BLOCK.getId(this);
        return new Model(initialBlockState, blockId);
    }

    @Override
    public boolean tickElementHolder(ServerWorld world, BlockPos pos, BlockState state) {
        return true;
    }

    public static class Model extends BlockModel {
        private final Map<Integer, ItemStack> servingModels = new HashMap<>();
        private final ItemDisplayElement soupDisplayElement;

        public Model(BlockState state, Identifier blockId) {
            for (int i = 0; i < MAX_SERVINGS; i++) {
                Identifier modelId = Identifier.of(blockId.getNamespace(), "block/guardian_soup_stage" + i);
                servingModels.put(i, ItemDisplayElementUtil.getModel(modelId));
            }
            servingModels.put(MAX_SERVINGS, ItemDisplayElementUtil.getModel(Identifier.of(blockId.getNamespace(), "block/guardian_soup_leftover")));

            this.soupDisplayElement = ItemDisplayElementUtil.createSimple(
                    servingModels.getOrDefault(state.get(SERVINGS), servingModels.get(0))
            );
            this.soupDisplayElement.setScale(new Vector3f(1.0f));
            this.updateStatePos(state);
            this.addElement(soupDisplayElement);
        }

        private void updateStatePos(BlockState state) {
            var direction = state.get(FACING);
            var yaw = direction.getPositiveHorizontalDegrees();
            this.soupDisplayElement.setYaw(yaw);
        }

        public void updateSoupModel(BlockState state) {
            int servings = state.get(SERVINGS);
            ItemStack newModel = servingModels.getOrDefault(servings, servingModels.get(0));
            this.soupDisplayElement.setItem(newModel);
            this.updateStatePos(state);
            this.soupDisplayElement.tick();
        }

        @Override
        public void notifyUpdate(HolderAttachment.UpdateType updateType) {
            if (updateType == BlockBoundAttachment.BLOCK_STATE_UPDATE) {
                if (this.getAttachment() instanceof BlockBoundAttachment attachment) {
                    this.updateSoupModel(attachment.getBlockState());
                }
            }
            super.notifyUpdate(updateType);
        }
    }
}