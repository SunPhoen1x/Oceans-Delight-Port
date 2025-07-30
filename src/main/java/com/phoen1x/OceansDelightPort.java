package com.phoen1x;

import com.phoen1x.blocks.entities.ODEntities;
import com.phoen1x.blocks.ODBlocks;
import com.phoen1x.items.data.ODLootTables;
import com.phoen1x.items.ODItems;
import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import eu.pb4.polymer.resourcepack.extras.api.ResourcePackExtras;
import net.fabricmc.api.ModInitializer;

import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OceansDelightPort implements ModInitializer {
	public static final String MOD_ID = "oceansdelight-port";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ODItems.registerModItems();
		ODBlocks.registerBlocks();
		ODEntities.register();
		ODLootTables.modifyLootTables();
		if (PolymerResourcePackUtils.addModAssets(MOD_ID)) {
			ResourcePackExtras.forDefault().addBridgedModelsFolder(id("block"), id("item"));
		} else {
			LOGGER.error("Failed to add mod assets for " + MOD_ID);
		}
		PolymerResourcePackUtils.markAsRequired();
	}

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}
}