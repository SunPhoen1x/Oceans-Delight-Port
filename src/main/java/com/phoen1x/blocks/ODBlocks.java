package com.phoen1x.blocks;

import com.phoen1x.OceansDelightPort;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class ODBlocks {

    public static final Block GUARDIAN_SOUP = registerBlock("guardian_soup", GuardianSoupBlock::new, Block.Settings.copy(Blocks.BARREL));
    public static final BlockItem GUARDIAN_SOUP_ITEM = registerBlockItem("guardian_soup", settings -> new TexturedPolyBlockItem(GUARDIAN_SOUP , settings), new Item.Settings());

    public static void registerBlocks() {
    }

    public static Block registerBlock(String name, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings) {
        var key = RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(OceansDelightPort.MOD_ID, name));
        Block block = factory.apply(settings.registryKey(key));
        return Registry.register(Registries.BLOCK, key, block);
    }

    public static BlockItem registerBlockItem(String name, Function<Item.Settings, BlockItem> factory, Item.Settings settings) {
        var key = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(OceansDelightPort.MOD_ID, name));
        BlockItem item = factory.apply(settings.registryKey(key).useBlockPrefixedTranslationKey());
        return Registry.register(Registries.ITEM, key, item);
    }
}
