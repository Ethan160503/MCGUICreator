package com.techno_wizard.mcguicreator.gui.inventory;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Ethan on 4/3/2016.
 */
public enum Material {
    //Items
    AIR("Null", "Air"),
    //ACACIA_BOAT("/itemimg/acacia_boat.png", "Acaial Boat"),
    ACACIA_STEPS("/blockimg/acacia_steps.png", "Acacia Steps", "ACACIA_STEPS", 0),
    ANVIL("/blockimg/anvil.png", "Anvil"),
    APPLE("/itemimg/apple.png", "Apple"),
    APPLE_GOLDEN("/itemimg/apple_golden.png", "Golden Apple"),
    ARROW("/itemimg/arrow.png", "Arrow"),
    //    BANNER_BASE("Null","Banner "),
//    BANNER_OVERLAY("Null","Air"),
    BARRIER("/itemimg/barrier.png", "Barrier"),
    BEACON("/blockimg/beacon.png", "Beacon"),
    BED("/itemimg/bed.png", "Bed"),
    BEDROCK("/blockimg/bedrock.png", "Bedrock"),
    BEEF_COOKED("/itemimg/beef_cooked.png", "Cooked Beef"),
    BEEF_RAW("/itemimg/beef_raw.png", "Raw Beef"),
    BEETROOT("/itemimg/beetroot.png", "BeetRoot"),
    BEETROOT_SEEDS("/itemimg/beetroot_seeds.png", "Beetroot Seeds"),
    BEETROOT_SOUP("/itemimg/beetroot_soup.png", "Beetroot soup"),
    BIRCH_BOAT("/itemimg/birch_boat.png", "Birch Boat"),
    BIRCH_WOOD_STEPS("/blockimg/birch_wood_steps.png", "Birch Stairs"),
    BLAZE_POWDER("/itemimg/blaze_powder.png", "Blaze Powder"),
    BLAZE_ROD("/itemimg/blaze_rod.png", "Blaze Rod"),
    BONE("/itemimg/bone.png", "Bone"),
    BOOK_ENCHANTED("/itemimg/book_enchanted.png", "Enchanted Book"),
    BOOK("/itemimg/book_normal.png", "Book"),
    BOOKSHELF("/blockimg/bookshelf.png", "Bookshelf"),
    BOW("/itemimg/bow.png", "Bow"),
    BOWL("/itemimg/bowl.png", "Empty Bowl"),
    BREAD("/itemimg/bread.png", "Bread"),
    BREWING_STAND("/itemimg/brewing_stand.png", "Brewing Stand"),
    BRICK("/itemimg/brick.png", "Brick"),
    BRICK_BLOCK("/blockimg/brick_block.png", "Bricks"),//may be called BRICKS
    BRICK_STEPS("/blockimg/brick_steps.png", "Brick stairs"),
    BUCKET("/itemimg/bucket_empty.png", "Empty Bucket"),
    BUCKET_MILK("/itemimg/bucket_milk.png", "Milk Bucket"),
    BUCKET_WATER("/itemimg/bucket_water.png", "Water Bucket"),
    BUCKET_LAVA("/itemimg/bucket_lava.png", "Lava Bucket"),
    CACTUS("/blockimg/cactus.png", "Cactus"),
    CAKE("/itemimg/cake.png", "Cake"),
    CARROT_GOLDEN("/itemimg/carrot_golden.png", "Golden Carrot"),
    CARROT_ON_STICK("/itemimg/carrot_on_a_stick.png", "Carrot On A Stick"),
    CAULDRON("/itemimg/cauldron.png", "Cauldron"),
    CHAINMAIL_BOOTS("/itemimg/chainmail_boots.png", "Chainmail Boots"),
    CHAINMAIL_CHESTPLATE("/itemimg/chainmail_chestplate.png", "ChainMain ChestPlate"),
    CHAINMAIL_HELMET("/itemimg/chainmail_helmet.png", "Chainmail Helmet"),
    CHAINMAIL_LEGGINGS("/itemimg/chainmail_leggings.png", "Chainmail Leggings"),
    CHARCOAL("/itemimg/charcoal.png", "Charcoal"),
    CHEST("/blockimg/chest.png", "Chest"),
    CHICKEN_COOKED("/itemimg/chicken_cooked.png", "Cooked Chicken"),
    CHICKEN_RAW("/itemimg/chicken_raw.png", "Raw Chicken"),
    CHORUS_FRUIT("/itemimg/chorus_fruit.png", "Chorus Fruit"),
    CHORUS_FRUIT_POPPED("/itemimg/chorus_fruit_popped.png", "Popped Chorus Fruit"),
    CLAY_BALL("/itemimg/clay_ball.png", "clay_ball"),
    CLAY_BLOCK("/blockimg/clay_block.png", "Clay Block"),//may be called CLAY
    CLOCK("/itemimg/clock.png", "Clock"),
    COAL("/itemimg/coal.png", "Coal"),
    COAL_BLOCK("/blockimg/coal_block.png", "Coal Block"),
    COBBLESTONE_FENCE("/blockimg/cobble_stone_fence.png", "Cobblestone fence"),//may be called something different
    COBBLESTONE("/blockimg/cobblestone.png", "Cobblestone"),
    CRAFTING_TABLE("/blockimg/crafting_table.png", "Crafting Table"),//I could not find this in the Material Enum for bukkit
    DARK_OAK_STEPS("/blockimg/dark_oak_steps.png", "Dark Oak Stairs"),
    DAYLIGHT_DETECTOR("/blockimg/daylight_detector.png", "Daylight Detector"),//may be called something different
    DIAMOND_BLOCK("/blockimg/diamond_block.png", "Diamond Block"),
    DIAMOND_ORE("/blockimg/diamond_ore.png", "Diamond Ore"),
    DIRT("/blockimg/dirt.png", "Dirt"),
    DISPENCER("/blockimg/dispencer.png", "Dispencer"),
    DRAGON_EGG("/blockimg/dragon_egg.png", "Dragon Egg"),
    DROPPER("/blockimg/dropper.png", "Dropper"),
    DYE_WHITE("/itemimg/dye_powder_white.png", "White Dye", "INK_SACK", 0),
    DYE_ORANGE("/itemimg/dye_powder_orange.png", "Orange Dye", "INK_SACK", 1),
    DYE_MAGENTA("/itemimg/dye_powder_magenta.png", "Magenta Dye", "INK_SACK", 2),
    DYE_LIGHT_BLUE("/itemimg/dye_powder_light_blue.png", "Light Blue Dye", "INK_SACK", 3),
    DYE_YELLOW("/itemimg/dye_powder_yellow.png", "Yellow Dye", "INK_SACK", 4),
    DYE_LIME("/itemimg/dye_powder_lime.png", "Lime Dye", "INK_SACK", 5),
    DYE_PINK("/itemimg/dye_powder_pink.png", "Pink Dye", "INK_SACK", 6),
    DYE_GRAY("/itemimg/dye_powder_gray.png", "Gray Dye", "INK_SACK", 7),
    DYE_LIGHT_GRAY("/itemimg/dye_powder_silver.png", "Light Gray Dye", "INK_SACK", 8),
    DYE_CYAN("/itemimg/dye_powder_cyan.png", "Cyan Dye", "INK_SACK", 9),
    DYE_PURPLE("/itemimg/dye_powder_purple.png", "Purple Dye", "INK_SACK", 10),
    DYE_BLUE("/itemimg/dye_powder_blue.png", "Blue Dye", "INK_SACK", 11),
    DYE_BROWN("/itemimg/dye_powder_brown.png", "Brown Dye", "INK_SACK", 12),
    DYE_GREEN("/itemimg/dye_powder_green.png", "Green Dye", "INK_SACK", 13),
    DYE_RED("/itemimg/dye_powder_red.png", "Red Dye", "INK_SACK", 14),
    DYE_BLACK("/itemimg/dye_powder_black.png", "Black Dye", "INK_SACK", 15),
    EMERALD_BLOCK("/blockimg/emerald_block.png", "Emerald Block"),
    EMERALD_ORE("/blockimg/emerald_ore.png", "Emerald Ore"),
    ENCHANTMENT_TABLE("/blockimg/enchantment_table.png", "Enchantment Table"),
    END_PORTAL("/blockimg/end_portal.png", "End Portal"),
    END_PORTAL_FRAME("/blockimg/end_portal_frame.png", "End Portal Frame"),//may be called something different
    END_STONE("/blockimg/end_stone.png", "End Stone"),
    WOOD_FENCE_GATE("/blockimg/fence_gate.png", "Fence Gate"),//may be called something different
    FIRE("/blockimg/fire.png", "Fire"),
    FURNACE("/blockimg/furnace.png", "Furnace"),
    GLASS("/blockimg/glass.png", "Glass"),
    GLASS_PANE("/blockimg/glass_pane.png", "Glass Pane"),
    GLOWSTONE("/blockimg/glowstone.png", "GlowStone"),
    GOLD_BLOCK("/blockimg/gold_block.png", "Gold Block"),
    GOLD_ORE("/blockimg/gold_ore.png", "Gold Ore"),
    GOLD_PRESSURE_PLATE("/blockimg/gold_pressure_plate.png", "Gold/Light Weight Pressure Plat"),
    GRASS("/blockimg/grass.png", "Grass"),
    GRAVEL("/blockimg/gravel.png", "Gravel"),
    HARDENED_CLAY("/blockimg/hardened_clay.png", "Hardened_Clay"),
    HAY_BLOCK("/blockimg/hay_block.png", "Hay_Block"),
    ICE("/blockimg/ice.png", "Ice"),
    IRON_BARS("/blockimg/iron_bars.png", "Iron Bars"),
    IRON_BLOCK("/blockimg/iron_block.png", "Iron Block"),
    IRON_ORE("/blockimg/iron_ore.png", "Iron) Ore"),
    IRON_PRESSURE_PLATE("/blockimg/iron_pressure_plate.png", "Iron/Heavy Pressure Plate"),
    JUKEBOX("/blockimg/jukebox.png", "JukeBox"),
    JUNGLE_WOOD_STEPS("/blockimg/jungle_wood_steps.png", "Jungle Wood Stairs"),//may be called something else
    LAPIS_BLOCK("/blockimg/lapis_block.png", "Lapis Block"),
    LAPIS_ORE("/blockimg/lapis_ore.png", "Lapis Ore"),//may be called something else
    LEAVES("/blockimg/leaves.png", "Leaves"),
    LOG("/blockimg/log.png", "Log"),
    MELON_BLOCK("/blockimg/melon_block.png", "Melon Block"),
    MOB_SPAWNER("/blockimg/mob_spawner.png", "Mob Spawner"),//may be called something else
    MYCELIUM("/blockimg/mycelium.png", "Mycelium"),
    NETHER_BRICK("/blockimg/nether_brick.png", "Nether Brick"),
    NETHER_BRICK_FENCE("/blockimg/nether_brick_fence.png", "Nether Brick Fence"),//may be called something else
    NETHER_BRICK_STEPS("/blockimg/nether_brick_steps.png", "Nether Brick Steps"),
    NETHERRACK("/blockimg/netherrack.png", "Netherrack"),
    NOTE_BLOCK("/blockimg/note_block.png", "Note Block"),
    OBSIDIAN("/blockimg/obsidian.png", "Obsidian"),
    PACKED_ICE("/blockimg/packed_ice.png", "Packed Ice"),
    PISTON("/blockimg/piston.png", "Piston"),
    POISONOUS_POTATO("/itemimg/potato_poisonous.png", "Poisonous Potato"),
    POTATO("/itemimg/potato.png", "Potato"),
    PRISMARINE("/blockimg/prismarine.png", "Prismarine"),
    PUMPKIN("/blockimg/pumpkin.png", "Pumpkin"),
    PUMPKIN_STEM("/blockimg/pumpkin_stem.png", "Pumpkin Stem"),//may be called something differrnt
    PURPUR_BLOCK("/blockimg/purpur_block.png", "Purpur block"),
    QUARTZ_BLOCK("/blockimg/quartz_block.png", "Quartz Block"),
    QUARTZ_ORE("/blockimg/quartz_ore.png", "Quartz ore"),
    QUARTZ_STEPS("/blockimg/quartz_steps.png", "Quartz Steps"),
    RESTONE_BLOCK("/blockimg/redstone_block.png", "Redstone Block"),
    REDSTONE_LAMP_OFF("/blockimg/redstone_lamp_off.png", "Redstone Lamp Off"),
    REDSTONE_LAMP_ON("/blockimg/redstone_lamp_on.png", "Redstone Lamp on"),
    REDSTONE_ORE("/blockimg/redstone_ore.png", "Redstone Ore"),
    SAND("/blockimg/sand.png", "Sand"),
    SANDSTONE("/blockimg/sandstone.png", "Sand Stone"),
    SANDSTONE_STEPS("/blockimg/sandstone_steps.png", "Sandstone Stairs"),
    SAPLING("/blockimg/sapling.png", "Sapling"),
    SEA_LANTERN("/blockimg/sea_lantern.png", "Sea Lantern"),
    SKULL("/blockimg/skull_block.png", "Skull"),//keeping this called "skull" even though it's a block.
    SLIME_BLOCK("/blockimg/slime_block.png", "Slime Block"),
    SNOW_BLOCK("/blockimg/snow_block.png", "Snow Block"),//may be something different
    SNOW_LAYER("/blockimg/snow_layer.png", "Snow Layer"),
    STONE_STONE("/blockimg/stone.png", "Stone", "STONE", 0),
    STONE_GRANITE("/blockimg/stone.png", "Granite", "STONE", 1),
    STONE_GRANITE_SMOOTH("/blockimg/stone.png", "Smooth Granite", "STONE", 2),
    STONE_DIORITE("/blockimg/stone.png", "Stone", "Diorite", 3),
    STONE_DIORITE_SMOOTH("/blockimg/stone.png", "Smooth Diorite", "STONE", 4),
    STONE_ANDERSITE("/blockimg/stone.png", "Stone", "Anderstite", 5),
    STONE_ANDERSITE_SMOOTH("/blockimg/stone.png", "Smooth Andersite", "STONE", 6),
    SPONGE("/blockimg/sponge.png", "Sponge"),
    SPRUCE_WOOD_STEPS("/blockimg/spruce_wood_steps.png", "Spruce Wood Stairs"),//may be something diffeent

    STAINED_CLAY("/blockimg/stained_clay.png", "Stained Clay"),
    STAINED_CLAY_WHITE("/blockimg/stained_clay.png", "Stained Clay White","STAINED_CLAY",0),
    STAINED_CLAY_ORANGE("/blockimg/stained_clay.png", "Stained Clay Orange","STAINED_CLAY",1,new Color(224,140,84)),
    STAINED_CLAY_MAGENTA("/blockimg/stained_clay.png", "Stained Clay Magenta","STAINED_CLAY",2,new Color(140,75,200)),
    STAINED_CLAY_LIGHT_BLUE("/blockimg/stained_clay.png", "Stained Clay Light Blue","STAINED_CLAY",3,new Color(100,130,200)),
    STAINED_CLAY_YELLOW("/blockimg/stained_clay.png", "Stained Clay Yellow","STAINED_CLAY",4,new Color(180,170,41)),
    STAINED_CLAY_LIGHT_GREEN("/blockimg/stained_clay.png", "Stained Clay Lime","STAINED_CLAY",5,new Color(66,180,58)),
    STAINED_CLAY_PINK("/blockimg/stained_clay.png", "Stained Clay Pink","STAINED_CLAY",6,new Color(216,154,170)),
    STAINED_CLAY_GRAY("/blockimg/stained_clay.png", "Stained Clay Light Gray","STAINED_CLAY",7,new Color(70,70,70)),
    STAINED_CLAY_LIGHT_GRAY("/blockimg/stained_clay.png", "Stained Clay Gray","STAINED_CLAY",8,new Color(170,180,180)),
    STAINED_CLAY_CYAN("/blockimg/stained_clay.png", "Stained Clay Cyan","WOOL",9,new Color(44,100,130)),
    STAINED_CLAY_LIGHT_PURPLE("/blockimg/stained_clay.png", "Stained Clay Light Purple","STAINED_CLAY",10,new Color(200,100,200)),
    STAINED_CLAY_BLUE("/blockimg/stained_clay.png", "Stained Clay Blue","STAINED_CLAY",11,new Color(45,55,141)),
    STAINED_CLAY_BROWN("/blockimg/stained_clay.png", "Stained Clay Brown","STAINED_CLAY",12,new Color(80,50,30)),
    STAINED_CLAY_GREEN("/blockimg/stained_clay.png", "Stained Clay Green","STAINED_CLAY",13,new Color(0,200,0)),
    STAINED_CLAY_RED("/blockimg/stained_clay.png", "Stained Clay Red","STAINED_CLAY",14,new Color(156,50,50)),
    STAINED_CLAY_BLACK("/blockimg/stained_clay.png", "Stained Clay Black","STAINED_CLAY",15,new Color(20,20,20)),

    STAINED_GLASS_WHITE("/blockimg/stained_glass.png", "Stained Glass White","STAINED_GLASS",0),
    STAINED_GLASS_ORANGE("/blockimg/stained_glass.png", "Stained Glass Orange","STAINED_GLASS",1,new Color(224,140,84)),
    STAINED_GLASS_MAGENTA("/blockimg/stained_glass.png", "Stained Glass Magenta","STAINED_GLASS",2,new Color(140,75,200)),
    STAINED_GLASS_LIGHT_BLUE("/blockimg/stained_glass.png", "Stained Glass Light Blue","STAINED_GLASS",3,new Color(100,130,200)),
    STAINED_GLASS_YELLOW("/blockimg/stained_glass.png", "Stained Glass Yellow","STAINED_GLASS",4,new Color(180,170,41)),
    STAINED_GLASS_LIGHT_GREEN("/blockimg/stained_glass.png", "Stained Glass  Lime","STAINED_GLASS",5,new Color(66,180,58)),
    STAINED_GLASS_PINK("/blockimg/stained_glass.png", "Stained Glass Pink","STAINED_GLASS",6,new Color(216,154,170)),
    STAINED_GLASS_GRAY("/blockimg/stained_glass.png", "Stained Glass Light Gray","STAINED_GLASS",7,new Color(70,70,70)),
    STAINED_GLASS_LIGHT_GRAY("/blockimg/stained_glass.png", "Stained Glass  Gray","STAINED_GLASS",8,new Color(170,180,180)),
    STAINED_GLASS_CYAN("/blockimg/stained_glass.png", "Stained Glass Cyan","STAINED_GLASS",9,new Color(44,100,130)),
    STAINED_GLASS_LIGHT_PURPLE("/blockimg/stained_glass.png", "Stained Glass  Light Purple","STAINED_GLASS",10,new Color(200,100,200)),
    STAINED_GLASS_BLUE("/blockimg/stained_glass.png", "Stained Glass Blue","STAINED_GLASS",11,new Color(45,55,141)),
    STAINED_GLASS_BROWN("/blockimg/stained_glass.png","Stained Glass Brown","STAINED_GLASS",12,new Color(80,50,30)),
    STAINED_GLASS_GREEN("/blockimg/stained_glass.png", "Stained Glass Green","STAINED_GLASS",13,new Color(0,200,0)),
    STAINED_GLASS_RED("/blockimg/stained_glass.png", "Stained Glass Red","STAINED_GLASS",14,new Color(156,50,50)),
    STAINED_GLASS_BLACK("/blockimg/stained_glass.png", "Stained Glass Black","STAINED_GLASS",15,new Color(20,20,20)),

    STAINED_GLASS_PANE_WHITE("/blockimg/stained_glass_pane.png", "Stained Glass Pane White","STAINED_GLASS_PANE",0),
    STAINED_GLASS_PANE_ORANGE("/blockimg/stained_glass_pane.png", "Stained Glass Pane Orange","STAINED_GLASS_PANE",1,new Color(224,140,84)),
    STAINED_GLASS_PANE_MAGENTA("/blockimg/stained_glass_pane.png", "Stained Glass Pane Magenta","STAINED_GLASS_PANE",2,new Color(140,75,200)),
    STAINED_GLASS_PANE_LIGHT_BLUE("/blockimg/stained_glass_pane.png", "Stained Glass Pane Light Blue","STAINED_GLASS_PANE",3,new Color(100,130,200)),
    STAINED_GLASS_PANE_YELLOW("/blockimg/stained_glass_pane.png", "Stained Glass Pane Yellow","STAINED_GLASS_PANE",4,new Color(180,170,41)),
    STAINED_GLASS_PANE_LIGHT_GREEN("/blockimg/stained_glass_pane.png", "Stained Glass Pane Lime","STAINED_GLASS_PANE",5,new Color(66,180,58)),
    STAINED_GLASS_PANE_PINK("/blockimg/stained_glass_pane.png", "Stained Glass Pane Pink","STAINED_GLASS_PANE",6,new Color(216,154,170)),
    STAINED_GLASS_PANE_GRAY("/blockimg/stained_glass_pane.png", "Stained Glass Pane Light Gray","STAINED_GLASS_PANE",7,new Color(70,70,70)),
    STAINED_GLASS_PANE_LIGHT_GRAY("/blockimg/stained_glass_pane.png", "Stained Glass Pane Gray","STAINED_GLASS_PANE",8,new Color(170,180,180)),
    STAINED_GLASS_PANE_CYAN("/blockimg/stained_glass_pane.png", "Stained Glass Pane Cyan","STAINED_GLASS_PANE",9,new Color(44,100,130)),
    STAINED_GLASS_PANE_LIGHT_PURPLE("/blockimg/stained_glass_pane.png", "Stained Glass Pane Light Purple","STAINED_GLASS_PANE",10,new Color(200,100,200)),
    STAINED_GLASS_PANE_BLUE("/blockimg/stained_glass_pane.png", "Stained Glass Pane Blue","STAINED_GLASS_PANE",11,new Color(45,55,141)),
    STAINED_GLASS_PANE_BROWN("/blockimg/stained_glass_pane.png","Stained Glass Pane Brown","STAINED_GLASS_PANE",12,new Color(80,50,30)),
    STAINED_GLASS_PANE_GREEN("/blockimg/stained_glass_pane.png", "Stained Glass Pane Green","STAINED_GLASS_PANE",13,new Color(0,200,0)),
    STAINED_GLASS_PANE_RED("/blockimg/stained_glass_pane.png", "Stained Glass Pane Red","STAINED_GLASS_PANE",14,new Color(156,50,50)),
    STAINED_GLASS_PANE_BLACK("/blockimg/stained_glass_pane.png", "Stained Glass Pane Black","STAINED_GLASS_PANE",15,new Color(20,20,20)),

    STICKY_PISTON("/blockimg/sticky_piston.png", "Sticky Piston"),
    STONE_BRICK("/blockimg/stone_brick.png", "Stone Brick"),//may be called something different
    STONE_BRICK_STEPS("/blockimg/stone_brick_steps.png", "Stone Brick Stairs"),
    STONE_PRESSURE_PLATE("/blockimg/stone_pressure_plate.png", "Stone Pressure Plate"),
    STONE_SLAB("/blockimg/stone_slab.png", "Stone Slab"),
    TNT("/blockimg/tnt.png", "TNT"),
    TRAPPED_CHEST("/blockimg/trapped_chest.png", "Trapped Chest"),
    VINE("/blockimg/vine.png", "Vine"),
    WATERLILLY("/blockimg/waterlilly.png", "Water Lilly"),//may be called something else
    WOOD_SWORD("/itemimg/wood_sword.png", "Wood sword"),
    WOOD("/blockimg/wood.png", "Wood"),
    WOOD_FENCE("/blockimg/wood_fence.png", "Wood Fence"),
    WOOD_SLAB("/blockimg/wood_slab.png", "Wood Slab"),
    WOOD_STEPS("/blockimg/wood_steps.png", "Wood Stairs"),
    WOOL_CARPET_WHITE("/blockimg/wool_carpet.png", "Wool Carpet White","WOOL_CARPET",0),
    WOOL_CARPET_ORANGE("/blockimg/wool_carpet.png", "Wool Carpet Orange","WOOL_CARPET",1,new Color(224,140,84)),
    WOOL_CARPET_MAGENTA("/blockimg/wool_carpet.png", "Wool Carpet Magenta","WOOL_CARPET",2,new Color(140,75,200)),
    WOOL_CARPET_LIGHT_BLUE("/blockimg/wool_carpet.png", "Wool Carpet Light Blue","WOOL_CARPET",3,new Color(100,130,200)),
    WOOL_CARPET_YELLOW("/blockimg/wool_carpet.png", "Wool Carpet Yellow","WOOL_CARPET",4,new Color(180,170,41)),
    WOOL_CARPET_LIGHT_GREEN("/blockimg/wool_carpet.png", "Wool Carpet Lime","WOOL_CARPET",5,new Color(66,180,58)),
    WOOL_CARPET_PINK("/blockimg/wool_carpet.png", "Wool Carpet Pink","WOOL_CARPET",6,new Color(216,154,170)),
    WOOL_CARPET_GRAY("/blockimg/wool_carpet.png", "Wool Carpet Light Gray","WOOL_CARPET",7,new Color(70,70,70)),
    WOOL_CARPET_LIGHT_GRAY("/blockimg/wool_carpet.png", "Wool Carpet Gray","WOOL_CARPET",8,new Color(170,180,180)),
    WOOL_CARPET_CYAN("/blockimg/wool_carpet.png", "Wool Carpet Cyan","WOOL_CARPET",9,new Color(44,100,130)),
    WOOL_CARPET_LIGHT_PURPLE("/blockimg/wool_carpet.png", "Wool Carpet Light Purple","WOOL_CARPET",10,new Color(200,100,200)),
    WOOL_CARPET_BLUE("/blockimg/wool_carpet.png", "Wool Carpet Blue","WOOL_CARPET",11,new Color(45,55,141)),
    WOOL_CARPET_BROWN("/blockimg/wool_carpet.png", "Wool Carpet Brown","WOOL_CARPET",12,new Color(80,50,30)),
    WOOL_CARPET_GREEN("/blockimg/wool_carpet.png", "Wool Carpet Green","WOOL_CARPET",13,new Color(0,200,0)),
    WOOL_CARPET_RED("/blockimg/wool_carpet.png", "Wool Carpet Red","WOOL_CARPET",14,new Color(156,50,50)),
    WOOL_CARPET_BLACK("/blockimg/wool_carpet.png", "Wool Carpet Black","WOOL_CARPET",15,new Color(20,20,20)),

    WOOL_WHITE("/blockimg/wool.png", "Wool White","WOOL",0),
    WOOL_ORANGE("/blockimg/wool.png", "Wool Orange","WOOL",1,new Color(224,140,84)),
    WOOL_MAGENTA("/blockimg/wool.png", "Wool Magenta","WOOL",2,new Color(140,75,200)),
    WOOL_LIGHT_BLUE("/blockimg/wool.png", "Wool Light Blue","WOOL",3,new Color(100,130,200)),
    WOOL_YELLOW("/blockimg/wool.png", "Wool Yellow","WOOL",4,new Color(180,170,41)),
    WOOL_LIGHT_GREEN("/blockimg/wool.png", "Wool Lime","WOOL",5,new Color(66,180,58)),
    WOOL_PINK("/blockimg/wool.png", "Wool Pink","WOOL",6,new Color(216,154,170)),
    WOOL_GRAY("/blockimg/wool.png", "Wool Light Gray","WOOL",7,new Color(70,70,70)),
    WOOL_LIGHT_GRAY("/blockimg/wool.png", "Wool Gray","WOOL",8,new Color(170,180,180)),
    WOOL_CYAN("/blockimg/wool.png", "Wool Cyan","WOOL",9,new Color(44,100,130)),
    WOOL_LIGHT_PURPLE("/blockimg/wool.png", "Wool Light Purple","WOOL",10,new Color(200,100,200)),
    WOOL_BLUE("/blockimg/wool.png", "Wool Blue","WOOL",11,new Color(45,55,141)),
    WOOL_BROWN("/blockimg/wool.png", "Wool Brown","WOOL",12,new Color(80,50,30)),
    WOOL_GREEN("/blockimg/wool.png", "Wool Green","WOOL",13,new Color(0,200,0)),
    WOOL_RED("/blockimg/wool.png", "Wool Red","WOOL",14,new Color(156,50,50)),
    WOOL_BLACK("/blockimg/wool.png", "Wool Black","WOOL",15,new Color(20,20,20));

    //Dyes

    private ImageIcon image;
    private String name;
    private String materialName;
    private int durability;

    Material(String imagePath, String name) {
        this.name = name;
        materialName = convertNameToEnum(toString());
        durability = 0;
        if (!name.equals("Air")) {
            try {
                this.image = new ImageIcon(getClass().getResource(imagePath));
            }catch(Exception e){
                System.out.println("THE ERROR WAS CAUSED BY THE IMAGE FROM MATERIAL "+name);
                e.printStackTrace();
            }
        } else {
            image = new ImageIcon(new BufferedImage(150, 150, BufferedImage.TYPE_INT_ARGB));
        }
    }

    Material(String imagePath, String name, String materialName, int durability) {
        this.name = name;
        this.materialName = convertNameToEnum(materialName);
        this.durability = durability;
        //System.out.println(toString());
        if (!name.equals("Air")) {
            try {
                this.image = new ImageIcon(getClass().getResource(imagePath));
            }catch(Exception e){
                System.out.println("THE ERROR WAS CAUSED BY THE IMAGE FROM MATERIAL "+name);
                e.printStackTrace();
            }
        } else {
            image = new ImageIcon(new BufferedImage(150, 150, BufferedImage.TYPE_INT_ARGB));
        }
    }
    Material(String imagePath, String name, String materialName, int durability, Color tint) {
        this.name = name;
        this.materialName = convertNameToEnum(materialName);
        this.durability = durability;
        //System.out.println(toString());
        if (!name.equals("Air")) {
            try {
                this.image = ItemUtil.tintImage(new ImageIcon(getClass().getResource(imagePath)), tint);
            }catch(Exception e){
                System.out.println("THE ERROR WAS CAUSED BY THE IMAGE FROM MATERIAL "+name);
                e.printStackTrace();
            }
        } else {
            image = new ImageIcon(new BufferedImage(150, 150, BufferedImage.TYPE_INT_ARGB));
        }
    }

    public int getDurability() {
        return durability;
    }

    public String getMaterialEnumName() {
        return "Material." + materialName;
    }

    public String getDisplayName() {
        return name;
    }

    /**
     * This returns the name of each materials. Use this to turn the material into a string.
     *
     * @return
     */
    public String toString() {
        return name;
    }

    public ImageIcon getImage() {
        return image;
    }

    public static Material getMaterialByName(String name) {
        for (Material m : Material.values()) {
            if (m.getDisplayName().equals(name))
                return m;
        }
        return null;
    }

    private String convertNameToEnum(String string){
        return string.replace(" ","_").toUpperCase();
    }

}
