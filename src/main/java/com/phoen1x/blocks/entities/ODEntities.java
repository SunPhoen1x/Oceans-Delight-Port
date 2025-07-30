package com.phoen1x.blocks.entities;

import com.phoen1x.OceansDelightPort;
import com.phoen1x.blocks.ODBlocks;
import eu.pb4.polymer.core.api.block.PolymerBlockUtils;
import eu.pb4.polymer.core.api.entity.PolymerEntityUtils;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ODEntities {
    public static final BlockEntityType<GuardianSoupBlockEntity> GUARDIAN_SOUP = register("guardian_soup", FabricBlockEntityTypeBuilder.create(GuardianSoupBlockEntity::new, ODBlocks.GUARDIAN_SOUP));

    public static void register() {}

    public static <T extends BlockEntity> BlockEntityType<T> register(String path, FabricBlockEntityTypeBuilder<T> factory) {
        BlockEntityType<T> blockEntityType = Registry.register(Registries.BLOCK_ENTITY_TYPE,Identifier.of(OceansDelightPort.MOD_ID, path), factory.build());
        PolymerBlockUtils.registerBlockEntity(blockEntityType);

        return blockEntityType;
    }
    public static <T extends Entity> EntityType<T> register(String path, EntityType.Builder<T> item) {
        var x = Registry.register(Registries.ENTITY_TYPE, Identifier.of(OceansDelightPort.MOD_ID, path), item.build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(OceansDelightPort.MOD_ID, path))));
        PolymerEntityUtils.registerType(x);
        return x;
    }
}