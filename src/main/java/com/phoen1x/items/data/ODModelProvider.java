package com.phoen1x.items.data;

import com.phoen1x.blocks.ODBlocks;
import com.phoen1x.items.ODItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.Models;

public class ODModelProvider extends FabricModelProvider {
    public ODModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) { }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ODItems.TENTACLES, Models.GENERATED);
        itemModelGenerator.register(ODItems.CUT_TENTACLES, Models.GENERATED);
        itemModelGenerator.register(ODItems.SQUID_RINGS, Models.GENERATED);
        itemModelGenerator.register(ODItems.TENTACLE_ON_A_STICK, Models.GENERATED);
        itemModelGenerator.register(ODItems.BAKED_TENTACLE_ON_A_STICK, Models.GENERATED);
        itemModelGenerator.register(ODItems.GUARDIAN, Models.GENERATED);
        itemModelGenerator.register(ODBlocks.GUARDIAN_SOUP_ITEM, Models.GENERATED);
        itemModelGenerator.register(ODItems.BOWL_OF_GUARDIAN_SOUP, Models.GENERATED);
        itemModelGenerator.register(ODItems.GUARDIAN_TAIL, Models.GENERATED);
        itemModelGenerator.register(ODItems.COOKED_GUARDIAN_TAIL, Models.GENERATED);
        itemModelGenerator.register(ODItems.ELDER_GUARDIAN_SLAB, Models.GENERATED);
        itemModelGenerator.register(ODItems.ELDER_GUARDIAN_SLICE, Models.GENERATED);
        itemModelGenerator.register(ODItems.COOKED_ELDER_GUARDIAN_SLICE, Models.GENERATED);
        itemModelGenerator.register(ODItems.ELDER_GUARDIAN_ROLL, Models.GENERATED);
        itemModelGenerator.register(ODItems.CABBAGE_WRAPPED_ELDER_GUARDIAN, Models.GENERATED);
        itemModelGenerator.register(ODItems.FUGU_SLICE, Models.GENERATED);
        itemModelGenerator.register(ODItems.FUGU_ROLL, Models.GENERATED);
        itemModelGenerator.register(ODItems.BRAISED_SEA_PICKLE, Models.GENERATED);
        itemModelGenerator.register(ODItems.STUFFED_COD, Models.GENERATED);
        itemModelGenerator.register(ODItems.COOKED_STUFFED_COD, Models.GENERATED);
        itemModelGenerator.register(ODItems.HONEY_FRIED_KELP, Models.GENERATED);
        itemModelGenerator.register(ODItems.SEAGRASS_SALAD, Models.GENERATED);
    }
}