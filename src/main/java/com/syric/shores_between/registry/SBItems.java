package com.syric.shores_between.registry;

import com.syric.shores_between.item.SBChestItem;
import com.syric.shores_between.item.SBFoodItem;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.*;
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
    public static final DeferredItem<Item> CRAB = ITEMS.registerSimpleItem("crab", new Item.Properties().food(SBFoods.CRAB));
    public static final DeferredItem<Item> COOKED_CRAB = ITEMS.registerSimpleItem("cooked_crab", new Item.Properties().food(SBFoods.COOKED_CRAB));
    public static final DeferredItem<Item> LOBSTER = ITEMS.registerSimpleItem("lobster", new Item.Properties().food(SBFoods.LOBSTER));
    public static final DeferredItem<Item> COOKED_LOBSTER = ITEMS.registerSimpleItem("cooked_lobster", new Item.Properties().food(SBFoods.COOKED_LOBSTER));
    public static final DeferredItem<Item> ODD_FISH = ITEMS.registerSimpleItem("odd_fish", new Item.Properties().food(SBFoods.ODD_FISH));
    public static final DeferredItem<Item> SEAGULL_EGG = ITEMS.register("seagull_egg", () -> new SBFoodItem(new Item.Properties().food(SBFoods.SEAGULL_EGG), SoundEvents.TURTLE_EGG_BREAK, null, 8));
    public static final DeferredItem<Item> OYSTER = ITEMS.register("oyster", () -> new SBFoodItem(new Item.Properties().food(SBFoods.OYSTER), SoundEvents.HONEY_BLOCK_BREAK, null, 12));
    public static final DeferredItem<Item> COOKED_OYSTER = ITEMS.register("cooked_oyster", () -> new SBFoodItem(new Item.Properties().food(SBFoods.COOKED_OYSTER), SoundEvents.HONEY_BLOCK_BREAK, null, 12));
    public static final DeferredItem<Item> BARNACLE = ITEMS.register("barnacle", () -> new SBFoodItem(new Item.Properties().food(SBFoods.BARNACLE), SoundEvents.TURTLE_EGG_CRACK, null, null));
    public static final DeferredItem<Item> WRITHING_TENDRILS = ITEMS.register("writhing_tendrils", () -> new SBFoodItem(new Item.Properties().food(SBFoods.WRITHING_TENDRILS), SoundEvents.HONEY_BLOCK_SLIDE, UseAnim.DRINK, 12));
    public static final DeferredItem<Item> ROTTEN_FISH = ITEMS.register("rotten_fish", () -> new SBFoodItem(new Item.Properties().food(SBFoods.ROTTEN_FISH), SoundEvents.MUD_BREAK, null, null));
    public static final DeferredItem<Item> STRANGE_MEAT = ITEMS.register("strange_meat", () -> new SBFoodItem(new Item.Properties().food(SBFoods.STRANGE_MEAT), SoundEvents.HONEY_BLOCK_BREAK, null, null));


    //Crafting materials
    public static final DeferredItem<Item> IRON_SCRAP = ITEMS.registerSimpleItem("iron_scrap", new Item.Properties());
    public static final DeferredItem<Item> RUSTY_SCRAP = ITEMS.registerSimpleItem("rusty_scrap", new Item.Properties());
    public static final DeferredItem<Item> RUST = ITEMS.registerSimpleItem("rust", new Item.Properties());
    public static final DeferredItem<Item> SALT = ITEMS.registerSimpleItem("salt", new Item.Properties());
    public static final DeferredItem<Item> WHALEBONE = ITEMS.registerSimpleItem("whalebone", new Item.Properties());
    public static final DeferredItem<Item> AMBERGRIS = ITEMS.registerSimpleItem("ambergris", new Item.Properties());
    public static final DeferredItem<Item> RAW_TITAN_MARROW = ITEMS.registerSimpleItem("raw_titan_marrow", new Item.Properties().rarity(Rarity.UNCOMMON));
    public static final DeferredItem<Item> TITAN_MARROW = ITEMS.registerSimpleItem("titan_marrow", new Item.Properties().rarity(Rarity.UNCOMMON));
    public static final DeferredItem<Item> BOTTLE_OF_ICHOR = ITEMS.registerSimpleItem("bottle_of_ichor", new Item.Properties());


    //Misc
    public static final DeferredItem<Item> MESSAGE_IN_A_BOTTLE = ITEMS.registerSimpleItem("message_in_a_bottle", new Item.Properties().stacksTo(1));
    public static final DeferredItem<Item> TOTEM_OF_WARNING = ITEMS.registerSimpleItem("totem_of_warning", new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(1));
    public static final DeferredItem<Item> TOTEM_OF_REVEALING = ITEMS.registerSimpleItem("totem_of_revealing", new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(1));


    //Treasure
    public static final DeferredItem<Item> STRAND_ARMOR_TRIM_SMITHING_TEMPLATE = ITEMS.registerSimpleItem("strand_armor_trim_smithing_template", new Item.Properties());
    
    
    //Signs
    public static final DeferredItem<Item> DRIFTWOOD_SIGN = ITEMS.register("driftwood_sign",
            () -> new SignItem( new Item.Properties().stacksTo(16), SBBlocks.DRIFTWOOD_SIGN.get(), SBBlocks.DRIFTWOOD_WALL_SIGN.get()));
    public static final DeferredItem<Item> DRIFTWOOD_HANGING_SIGN = ITEMS.register("driftwood_hanging_sign",
            () -> new HangingSignItem( SBBlocks.DRIFTWOOD_HANGING_SIGN.get(), SBBlocks.DRIFTWOOD_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));
    public static final DeferredItem<Item> MISTWOOD_SIGN = ITEMS.register("mistwood_sign",
            () -> new SignItem( new Item.Properties().stacksTo(16), SBBlocks.MISTWOOD_SIGN.get(), SBBlocks.MISTWOOD_WALL_SIGN.get()));
    public static final DeferredItem<Item> MISTWOOD_HANGING_SIGN = ITEMS.register("mistwood_hanging_sign",
            () -> new HangingSignItem( SBBlocks.MISTWOOD_HANGING_SIGN.get(), SBBlocks.MISTWOOD_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));


    //Chests
    public static final DeferredItem<Item> DRIFTWOOD_CHEST = ITEMS.register("driftwood_chest",
            () -> new SBChestItem(SBBlocks.DRIFTWOOD_CHEST.get(), new Item.Properties()));
    public static final DeferredItem<Item> MISTWOOD_CHEST = ITEMS.register("mistwood_chest",
            () -> new SBChestItem(SBBlocks.MISTWOOD_CHEST.get(), new Item.Properties()));
    public static final DeferredItem<Item> ANCIENT_CHEST = ITEMS.register("ancient_chest",
            () -> new SBChestItem(SBBlocks.ANCIENT_CHEST.get(), new Item.Properties()));




}
