package com.phoen1x.items;

import com.phoen1x.OceansDelightPort;
import eu.pb4.polymer.core.api.item.PolymerItemGroupUtils;
import eu.pb4.polymer.core.api.item.SimplePolymerItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

import static com.phoen1x.blocks.ODBlocks.GUARDIAN_SOUP_ITEM;

public class ODItems {
    public static final Logger LOGGER = LoggerFactory.getLogger("oceansdelight");

    public static final Item TENTACLES = registerItem("tentacles", SimplePolymerItem::new, new Item.Settings().food(ODFoodComponents.TENTACLES));
    public static final Item CUT_TENTACLES = registerItem("cut_tentacles", SimplePolymerItem::new, new Item.Settings().food(ODFoodComponents.CUT_TENTACLES));
    public static final Item SQUID_RINGS = registerItem("squid_rings", SimplePolymerItem::new, new Item.Settings().food(ODFoodComponents.SQUID_RINGS));
    public static final Item TENTACLE_ON_A_STICK = registerItem("tentacle_on_a_stick", SimplePolymerItem::new, new Item.Settings().food(ODFoodComponents.TENTACLE_ON_A_STICK));
    public static final Item BAKED_TENTACLE_ON_A_STICK = registerItem("baked_tentacle_on_a_stick", SimplePolymerItem::new, new Item.Settings().food(ODFoodComponents.BAKED_TENTACLE_ON_A_STICK));
    public static final Item GUARDIAN = registerItem("guardian", SimplePolymerItem::new, new Item.Settings());
    public static final Item BOWL_OF_GUARDIAN_SOUP = registerItem("bowl_of_guardian_soup", SimplePolymerItem::new, new Item.Settings().food(ODFoodComponents.BOWL_OF_GUARDIAN_SOUP).maxCount(16).recipeRemainder(Items.BOWL));
    public static final Item GUARDIAN_TAIL = registerItem("guardian_tail", SimplePolymerItem::new, new Item.Settings().food(ODFoodComponents.GUARDIAN_TAIL));
    public static final Item COOKED_GUARDIAN_TAIL = registerItem("cooked_guardian_tail", SimplePolymerItem::new, new Item.Settings().food(ODFoodComponents.COOKED_GUARDIAN_TAIL));
    public static final Item ELDER_GUARDIAN_SLAB = registerItem("elder_guardian_slab", SimplePolymerItem::new, new Item.Settings().food(ODFoodComponents.ELDER_GUARDIAN_SLAB));
    public static final Item ELDER_GUARDIAN_SLICE = registerItem("elder_guardian_slice", SimplePolymerItem::new, new Item.Settings().food(ODFoodComponents.ELDER_GUARDIAN_SLICE));
    public static final Item COOKED_ELDER_GUARDIAN_SLICE = registerItem("cooked_elder_guardian_slice", SimplePolymerItem::new, new Item.Settings().food(ODFoodComponents.COOKED_ELDER_GUARDIAN_SLICE));
    public static final Item ELDER_GUARDIAN_ROLL = registerItem("elder_guardian_roll", SimplePolymerItem::new, new Item.Settings().food(ODFoodComponents.ELDER_GUARDIAN_ROLL));
    public static final Item CABBAGE_WRAPPED_ELDER_GUARDIAN = registerItem("cabbage_wrapped_elder_guardian", SimplePolymerItem::new, new Item.Settings().food(ODFoodComponents.CABBAGE_WRAPPED_ELDER_GUARDIAN));
    public static final Item FUGU_SLICE = registerItem("fugu_slice", SimplePolymerItem::new, new Item.Settings().food(ODFoodComponents.FUGU_SLICE));
    public static final Item FUGU_ROLL = registerItem("fugu_roll", SimplePolymerItem::new, new Item.Settings().food(ODFoodComponents.FUGU_ROLL));
    public static final Item BRAISED_SEA_PICKLE = registerItem("braised_sea_pickle", SimplePolymerItem::new, new Item.Settings().food(ODFoodComponents.BRAISED_SEA_PICKLE));
    public static final Item STUFFED_COD = registerItem("stuffed_cod", SimplePolymerItem::new, new Item.Settings().food(ODFoodComponents.STUFFED_COD));
    public static final Item COOKED_STUFFED_COD = registerItem("cooked_stuffed_cod", SimplePolymerItem::new, new Item.Settings().food(ODFoodComponents.COOKED_STUFFED_COD));
    public static final Item HONEY_FRIED_KELP = registerItem("honey_fried_kelp", SimplePolymerItem::new, new Item.Settings().food(ODFoodComponents.HONEY_FRIED_KELP));
    public static final Item SEAGRASS_SALAD = registerItem("seagrass_salad", SimplePolymerItem::new, new Item.Settings().food(ODFoodComponents.SEAGRASS_SALAD));

    private static Item registerItem(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        var key = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(OceansDelightPort.MOD_ID, name));
        Item item = factory.apply(settings.registryKey(key));
        return Registry.register(Registries.ITEM, key, item);
    }

    public static void registerModItems() {
        ItemGroup.Builder builder = PolymerItemGroupUtils.builder();
        builder.icon(() -> new ItemStack(ODItems.CABBAGE_WRAPPED_ELDER_GUARDIAN, 1));
        builder.displayName(Text.translatable("itemGroup.oceansdelight"));

        builder.entries((displayContext, entries) -> {
            entries.add(TENTACLES);
            entries.add(CUT_TENTACLES);
            entries.add(SQUID_RINGS);
            entries.add(TENTACLE_ON_A_STICK);
            entries.add(BAKED_TENTACLE_ON_A_STICK);
            entries.add(GUARDIAN);
            entries.add(BOWL_OF_GUARDIAN_SOUP);
            entries.add(GUARDIAN_TAIL);
            entries.add(COOKED_GUARDIAN_TAIL);
            entries.add(ELDER_GUARDIAN_SLAB);
            entries.add(ELDER_GUARDIAN_SLICE);
            entries.add(GUARDIAN_SOUP_ITEM);
            entries.add(COOKED_ELDER_GUARDIAN_SLICE);
            entries.add(ELDER_GUARDIAN_ROLL);
            entries.add(CABBAGE_WRAPPED_ELDER_GUARDIAN);
            entries.add(FUGU_SLICE);
            entries.add(FUGU_ROLL);
            entries.add(BRAISED_SEA_PICKLE);
            entries.add(STUFFED_COD);
            entries.add(COOKED_STUFFED_COD);
            entries.add(HONEY_FRIED_KELP);
            entries.add(SEAGRASS_SALAD);
        });

        ItemGroup polymerGroup = builder.build();
        PolymerItemGroupUtils.registerPolymerItemGroup(Identifier.of(OceansDelightPort.MOD_ID, "oceansdelight_itemgroup"), polymerGroup);
        LOGGER.info("OceansDelight Items Registered");
    }
}