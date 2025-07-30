package com.phoen1x.blocks;

import eu.pb4.factorytools.api.block.entity.LockableBlockEntity; // Changed base class
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.storage.ReadView; // Added import
import net.minecraft.storage.WriteView; // Added import
import net.minecraft.text.Text; // Added import for getContainerName

/**
 * BlockEntity for the Guardian Soup Block.
 * This entity stores the current number of servings remaining in the block.
 * Servings range from 0 (full) to GuardianSoupBlock.MAX_SERVINGS - 1 (empty).
 */
public class GuardianSoupBlockEntity extends LockableBlockEntity { // Changed to extend LockableBlockEntity
    // NBT key for storing the servings count
    private static final String SERVINGS_KEY = "Servings";
    // Current number of servings consumed (0 = full, MAX_SERVINGS-1 = empty)
    private int servings;

    /**
     * Constructor for the GuardianSoupBlockEntity.
     * @param pos The position of the block entity.
     * @param state The block state associated with this entity.
     */
    public GuardianSoupBlockEntity(BlockPos pos, BlockState state) {
        // Initialize with the registered BlockEntityType for Guardian Soup
        // Assumes BlockEntityTypesRegistry.GUARDIAN_SOUP is correctly registered.
        super(ODEntities.GUARDIAN_SOUP, pos, state);
        // Initialize servings to 0, which represents a full block (stage0 model).
        this.servings = 0;
    }

    /**
     * Writes the block entity's data to a WriteView.
     * This method is used for saving custom data with LockableBlockEntity.
     * @param view The WriteView to write data to.
     */
    @Override
    protected void writeData(WriteView view) {
        super.writeData(view); // Call super to save data from LockableBlockEntity
        view.putInt(SERVINGS_KEY, this.servings); // Save the servings count
    }

    /**
     * Reads the block entity's data from a ReadView.
     * This method is used for loading custom data with LockableBlockEntity.
     * @param view The ReadView to read data from.
     */
    @Override
    public void readData(ReadView view) {
        super.readData(view); // Call super to load data from LockableBlockEntity
        // Load the 'Servings' value, defaulting to 0 if not present.
        this.servings = view.getInt(SERVINGS_KEY, 0);
    }

    /**
     * Gets the current number of servings consumed.
     * @return The current servings count.
     */
    public int getServings() {
        return this.servings;
    }

    /**
     * Sets the number of servings consumed and triggers an update.
     * @param newServings The new servings count.
     */
    public void setServings(int newServings) {
        int oldServings = this.servings;
        // Ensure servings stay within valid bounds (0 to MAX_SERVINGS - 1).
        this.servings = Math.max(0, Math.min(newServings, GuardianSoupBlock.MAX_SERVINGS - 1));
        // Mark the block entity as dirty so its changes are saved.
        this.markDirty();

        // If the servings count has changed, notify the world to update listeners.
        // This is crucial for the virtual entity model on the client to refresh.
        if (this.world != null && !this.world.isClient() && oldServings != this.servings) {
            this.world.updateListeners(this.pos, this.getCachedState(), this.getCachedState(), 3);
        }
    }

    /**
     * Tick method for the BlockEntity.
     * This method is called every game tick if a ticker is registered for this BlockEntity type.
     * @param world The world the block entity is in.
     * @param pos The position of the block entity.
     * @param state The block state.
     * @param t The block entity instance.
     */
    public static <T extends BlockEntity> void tick(World world, BlockPos pos, BlockState state, T t) {
        if (t instanceof GuardianSoupBlockEntity entity) {
            // Currently, no continuous logic is needed for the Guardian Soup Block Entity.
            // This method can be used for animations, decay, or other periodic updates if required in the future.
        }
    }

    /**
     * Provides the container name for the block entity.
     * Required by LockableBlockEntity.
     * @return The translatable text for the container name.
     */
    @Override
    protected Text getContainerName() {
        return Text.translatable("container.oceansdelight.guardian_soup"); // Example translatable key
    }
}
