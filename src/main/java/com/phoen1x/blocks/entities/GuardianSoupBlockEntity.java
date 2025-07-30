package com.phoen1x.blocks.entities;

import eu.pb4.factorytools.api.block.entity.LockableBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.BlockPos;
import net.minecraft.text.Text;

/**
 * BlockEntity for the Guardian Soup Block.
 * This entity is now minimal and does not store state, as the 'servings'
 * are handled by the BlockState.
 */
public class GuardianSoupBlockEntity extends LockableBlockEntity {

    public GuardianSoupBlockEntity(BlockPos pos, BlockState state) {
        super(ODEntities.GUARDIAN_SOUP, pos, state);
    }

    // Немає потреби зберігати 'servings' в NBT, оскільки це робить BlockState.
    @Override
    protected void writeData(WriteView view) {
        super.writeData(view);
    }

    @Override
    public void readData(ReadView view) {
        super.readData(view);
    }

    @Override
    protected Text getContainerName() {
        return Text.translatable("container.oceansdelight.guardian_soup");
    }
}