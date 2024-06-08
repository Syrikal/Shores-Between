package com.syric.shores_between.registry;

import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.syric.shores_between.ShoresBetween.MODID;

public class SBItems {

    // Create a Deferred Register to hold Items which will all be registered under the "examplemod" namespace
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }

    //Food items
    public static final DeferredItem<Item> CRAB = ITEMS.registerSimpleItem("crab", new Item.Properties());
    public static final DeferredItem<Item> COOKED_CRAB = ITEMS.registerSimpleItem("cooked_crab", new Item.Properties());
    public static final DeferredItem<Item> LOBSTER = ITEMS.registerSimpleItem("lobster", new Item.Properties());
    public static final DeferredItem<Item> COOKED_LOBSTER = ITEMS.registerSimpleItem("cooked_lobster", new Item.Properties());
    public static final DeferredItem<Item> ODD_FISH = ITEMS.registerSimpleItem("odd_fish", new Item.Properties());
    public static final DeferredItem<Item> SEAGULL_EGG = ITEMS.registerSimpleItem("seagull_egg", new Item.Properties());
    public static final DeferredItem<Item> OYSTER = ITEMS.registerSimpleItem("oyster", new Item.Properties());
    public static final DeferredItem<Item> COOKED_OYSTER = ITEMS.registerSimpleItem("cooked_oyster", new Item.Properties());
    public static final DeferredItem<Item> WRITHING_TENDRILS = ITEMS.registerSimpleItem("writhing_tendrils", new Item.Properties());
    public static final DeferredItem<Item> ROTTEN_FISH = ITEMS.registerSimpleItem("rotten_fish", new Item.Properties());
    public static final DeferredItem<Item> STRANGE_MEAT = ITEMS.registerSimpleItem("strange_meat", new Item.Properties());


    //Crafting materials
    public static final DeferredItem<Item> IRON_SCRAP = ITEMS.registerSimpleItem("iron_scrap", new Item.Properties());
    public static final DeferredItem<Item> RUSTY_SCRAP = ITEMS.registerSimpleItem("rusty_scrap", new Item.Properties());
    public static final DeferredItem<Item> SALT = ITEMS.registerSimpleItem("salt", new Item.Properties());
    public static final DeferredItem<Item> WHALEBONE = ITEMS.registerSimpleItem("whalebone", new Item.Properties());
    public static final DeferredItem<Item> AMBERGRIS = ITEMS.registerSimpleItem("ambergris", new Item.Properties());
    public static final DeferredItem<Item> RAW_TITAN_MARROW = ITEMS.registerSimpleItem("raw_titan_marrow", new Item.Properties());
    public static final DeferredItem<Item> TITAN_MARROW = ITEMS.registerSimpleItem("titan_marrow", new Item.Properties());
    public static final DeferredItem<Item> BOTTLE_OF_ICHOR = ITEMS.registerSimpleItem("bottle_of_ichor", new Item.Properties());


    //Misc
    public static final DeferredItem<Item> MESSAGE_IN_A_BOTTLE = ITEMS.registerSimpleItem("message_in_a_bottle", new Item.Properties());
    public static final DeferredItem<Item> TOTEM_OF_WARNING = ITEMS.registerSimpleItem("totem_of_warning", new Item.Properties());
    public static final DeferredItem<Item> TOTEM_OF_WARNING_ACTIVE = ITEMS.registerSimpleItem("totem_of_warning_active", new Item.Properties());
    public static final DeferredItem<Item> TOTEM_OF_REVEALING = ITEMS.registerSimpleItem("totem_of_revealing", new Item.Properties());


    //Treasure
    public static final DeferredItem<Item> STRAND_ARMOR_TRIM_SMITHING_TEMPLATE = ITEMS.registerSimpleItem("strand_armor_trim_smithing_template", new Item.Properties());




}
