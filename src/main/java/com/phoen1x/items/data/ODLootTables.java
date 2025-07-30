package com.phoen1x.items.data;

import com.phoen1x.items.ODItems;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.RegistryKey;

public class ODLootTables {
    private static final RegistryKey<LootTable> SQUID_KEY = EntityType.SQUID.getLootTableKey().orElseThrow(() -> new IllegalStateException("Squid loot table key not found"));
    private static final RegistryKey<LootTable> GLOW_SQUID_KEY = EntityType.GLOW_SQUID.getLootTableKey().orElseThrow(() -> new IllegalStateException("Squid loot table key not found"));
    private static final RegistryKey<LootTable> ELDER_GUARDIAN_KEY = EntityType.ELDER_GUARDIAN.getLootTableKey().orElseThrow(() -> new IllegalStateException("Elder Guardian loot table key not found"));

    public static void modifyLootTables() {
        LootTableEvents.MODIFY.register((key, tableBuilder, source) -> {
            if (SQUID_KEY.equals(key)) {
                LootPool.Builder pool = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(1f))
                        .with(ItemEntry.builder(ODItems.TENTACLES))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)).build());
                tableBuilder.pool(pool);
            }
        });
        LootTableEvents.MODIFY.register((key, tableBuilder, source) -> {
            if (GLOW_SQUID_KEY.equals(key)) {
                LootPool.Builder pool = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(1f))
                        .with(ItemEntry.builder(ODItems.TENTACLES))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)).build());
                tableBuilder.pool(pool);
            }
        });
        LootTableEvents.MODIFY.register((key, tableBuilder, source) -> {
            if (ELDER_GUARDIAN_KEY.equals(key)) {
                LootPool.Builder pool = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(1f))
                        .with(ItemEntry.builder(ODItems.GUARDIAN))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)).build());
                tableBuilder.pool(pool);
            }
        });
    }
}