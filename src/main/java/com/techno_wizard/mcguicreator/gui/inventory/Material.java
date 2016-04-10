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
    //ACACIA_STEPS("blockimg/acacia_steps.png", "Acacia Steps", "ACACIA_STEPS", 0),
    APPLE("/itemimg/apple.png", "Apple"),
    APPLE_GOLDEN("/itemimg/apple_golden.png", "Golden Apple"),
    ARROW("/itemimg/arrow.png", "Arrow"),
    //    BANNER_BASE("Null","Banner "),
//    BANNER_OVERLAY("Null","Air"),
    BARRIER("/itemimg/barrier.png", "Barrier"),
    BED("/itemimg/bed.png", "Bed"),
    BEEF_COOKED("/itemimg/beef_cooked.png", "Cooked Beef"),
    BEEF_RAW("/itemimg/beef_raw.png", "Raw Beef"),
    BEETROOT("/itemimg/beetroot.png", "BeetRoot"),
    BEETROOT_SEEDS("/itemimg/beetroot_seeds.png", "Beetroot Seeds"),
    BEETROOT_SOUP("/itemimg/beetroot_soup.png", "Beetroot soup"),
    BIRCH_BOAT("/itemimg/birch_boat.png", "Birch Boat"),
    BLAZE_POWDER("/itemimg/blaze_powder.png", "Blaze Powder"),
    BLAZE_ROD("/itemimg/blaze_rod.png", "Blaze Rod"),
    BONE("/itemimg/bone.png", "Bone"),
    BOOK_ENCHANTED("/itemimg/book_enchanted.png", "Enchanted Book"),
    BOOK("/itemimg/book_normal.png", "Book"),
    BOW("/itemimg/bow.png", "Bow"),
    BOWL("/itemimg/bowl.png", "Empty Bowl"),
    BREAD("/itemimg/bread.png", "Bread"),
    BREWING_STAND("/itemimg/brewing_stand.png", "Brewing Stand"),
    BRICK("/itemimg/brick.png", "Brick"),
    BUCKET("/itemimg/bucket_empty.png", "Empty Bucket"),
    BUCKET_MILK("/itemimg/bucket_milk.png", "Milk Bucket"),
    BUCKET_WATER("/itemimg/bucket_water.png", "Water Bucket"),
    BUCKET_LAVA("/itemimg/bucket_lava.png", "Lava Bucket"),
    CAKE("/itemimg/bow.png", "Bow"),
    CARROT_GOLDEN("/itemimg/bow.png", "Bow"),
    CARROT_ON_STICK("/itemimg/bow.png", "Bow"),
    CAULDRON("/itemimg/bow.png", "Bow"),
    CHAINMAIL_BOOTS("/itemimg/bow.png", "Bow"),
    CHAINMAIL_CHESTPLATE("/itemimg/bow.png", "Bow"),
    CHAINMAIL_HELMET("/itemimg/bow.png", "Bow"),
    CHAINMAIL_LEGGINGS("/itemimg/bow.png", "Bow"),
    CHARCOAL("/itemimg/bow.png", "Bow"),
    CHICKEN_COOKED("/itemimg/bow.png", "Bow"),
    CHICKEN_RAW("/itemimg/bow.png", "Bow"),
    CHORUS_FRUIT("/itemimg/bow.png", "Bow"),
    CHORUS_FRUIT_POPPED("/itemimg/bow.png", "Bow"),
    CLAY_BALL("/itemimg/bow.png", "Bow"),
    CLOCK("/itemimg/bow.png", "Bow"),
    COAL("/itemimg/bow.png", "Bow"),
    POISONOUS_POTATO("/itemimg/potato_poisonous.png", "Poisonous Potato"),
    POTATO("/itemimg/potato.png", "Potato"),/*
    STONE_STONE("blockimg/stone.png", "Stone", "STONE", 0),
    STONE_GRANITE("blockimg/stone.png", "Granite", "STONE", 1),
    STONE_GRANITE_SMOOTH("blockimg/stone.png", "Smooth Granite", "STONE", 2),
    STONE_DIORITE("blockimg/stone.png", "Stone", "Diorite", 3),
    STONE_DIORITE_SMOOTH("blockimg/stone.png", "Smooth Diorite", "STONE", 4),
    STONE_ANDERSITE("blockimg/stone.png", "Stone", "Anderstite", 5),
    STONE_ANDERSITE_SMOOTH("blockimg/stone.png", "Smooth Andersite", "STONE", 6),*/
    WOOD_SWORD("/itemimg/wood_sword.png", "Wood sword"),

    //Dyes
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
    DYE_BLACK("/itemimg/dye_powder_black.png", "Black Dye", "INK_SACK", 15);

    //TODO: Put the following blocks in alphabetical order back in the section above
    //TODO: Figure out why the iamges that have the correct path are null
    /*ANVIL("blockimg/anvil.png", "Anvil"),
    BEACON("blockimg/beacon.png", "Beacon"),
    BEDROCK("blockimg/bedrock.png", "Bedrock"),
    BIRCH_WOOD_STEPS("blockimg/birch_wood_steps.png", "Birch Stairs"),
    BOOKSHELF("blockimg/bookshelf.png", "Booshelf"),
    BRICK_BLOCK("blockimg/brick_block.png", "Bricks"),//may be called BRICKS
    BRICK_STEPS("blockimg/brick_steps.png", "Brick stairs"),
    CACTUS("blockimg/cactus.png", "Cactus"),
    CHEST("blockimg/chest.png", "Chest"),
    CLAY_BLOCK("blockimg/clay_block.png", "Clay Block"),//may be called CLAY
    COAL_BLOCK("blockimg/coal_block.png", "Coal Block"),
    COBBLESTONE_FENCE("blockimg/cobble_stone_fence.png", "Cobblestone fence"),//may be called something different
    COBBLESTONE("blockimg/cobblestone.png", "Cobblestone"),
    CRAFTING_TABLE("blockimg/crafting_table.png", "Crafting Table"),//I could not find this in the Material Enum for bukkit
    DARK_OAK_STEPS("blockimg/stone.png", "Dark Oak Stairs"),
    DAYLIGHT_DETECTOR("blockimg/stone.png", "Daylight Detector"),//may be called something different
    DIAMOND_BLOCK("blockimg/stone.png", "Diamond Block"),
    DIAMOND_ORE("blockimg/stone.png", "Diamond Ore"),
    DIRT("blockimg/stone.png", "Dirt"),
    DISPENCER("blockimg/stone.png", "Dispencer"),
    DRAGON_EGG("blockimg/stone.png", "Dragon Egg"),
    DROPPER("blockimg/stone.png", "Dropper"),
    EMERALD_BLOCK("blockimg/stone.png", "Emerald Block"),
    EMERALD_ORE("blockimg/stone.png", "Emerald Ore"),
    ENCHANTMENT_TABLE("blockimg/stone.png", "Enchantment Table"),
    END_PORTAL("blockimg/stone.png", "End Portal"),
    END_PORTAL_FRAME("blockimg/stone.png", "End Portal Frame"),//may be called something different
    END_STONE("blockimg/stone.png", "End Stone"),
    WOOD_FENCE_GATE("blockimg/stone.png", "Fence Gate"),//may be called something different
    FIRE("blockimg/stone.png", "Fire"),
    FURNACE("blockimg/stone.png", "Furnace"),
    GLASS("blockimg/stone.png", "Glass"),
    GLASS_PANE("blockimg/stone.png", "Glass Pane"),
    GLOWSTONE("blockimg/stone.png", "GlowStone"),
    GOLD_BLOCK("blockimg/stone.png", "Gold Block"),
    GOLD_ORE("blockimg/stone.png", "Gold Ore"),
    GOLD_PRESSURE_PLATE("blockimg/stone.png", "Gold/Light Weight Pressure Plat"),
    GRASS("blockimg/stone.png", "Grass"),
    GRAVEL("blockimg/stone.png", "Gravel"),
    HARDENED_CLAY("blockimg/stone.png", "Hardened_Clay"),
    HAY_BLOCK("blockimg/stone.png", "Hay_Block"),
    ICE("blockimg/stone.png", "Ice"),
    IRON_BARS("blockimg/stone.png", "Iron Bars"),
    IRON_BLOCK("blockimg/stone.png", "Iron Block"),
    IRON_ORE("blockimg/stone.png", "Iron) Ore"),
    IRON_PRESSURE_PLATE("blockimg/stone.png", "Iron/Heavy Pressure Plate"),
    JUKEBOX("blockimg/stone.png", "JukeBox"),
    JUNGLE_WOOD_STEPS("blockimg/stone.png", "Jungle Wood Stairs"),//may be called something else
    LAPIS_BLOCK("blockimg/stone.png", "Lapis Block"),
    LAPIS_ORE("blockimg/stone.png", "Lapis Ore"),//may be called something else
    LEAVES("blockimg/stone.png", "Leaves"),
    LOG("blockimg/stone.png", "Log"),
    MELON_BLOCK("blockimg/stone.png", "Melon Block"),
    MOB_SPAWNER("blockimg/stone.png", "Mob Spawner"),//may be called something else
    MYCELIUM("blockimg/stone.png", "Mycelium"),
    NETHER_BRICK("blockimg/stone.png", "Nether Brick"),
    NETHER_BRICK_FENCE("blockimg/stone.png", "Nether Brick Fence"),//may be called something else
    NETHER_BRICK_STEPS("blockimg/stone.png", "Nether Brick Steps"),
    NETHERRACK("blockimg/stone.png", "Netherrack"),
    NOTE_BLOCK("blockimg/stone.png", "Note Block"),
    OBSIDIAN("blockimg/stone.png", "Obsidian"),
    PACKED_ICE("blockimg/stone.png", "Packed Ice"),
    PISTON("blockimg/stone.png", "Piston"),
    PRISMARINE("blockimg/stone.png", "Prismarine"),
    PUMPKIN("blockimg/stone.png", "Pumpkin"),
    PUMPKIN_STEM("blockimg/stone.png", "Pumpkin Stem"),//may be called something differrnt
    PURPUR_BLOCK("blockimg/stone.png", "Purpur block"),
    QUARTZ_BLOCK("blockimg/stone.png", "Quartz Block"),
    QUARTZ_ORE("blockimg/stone.png", "Quartz ore"),
    QUARTZ_STEPS("blockimg/stone.png", "Quartz Steps"),
    RESTONE_BLOCK("blockimg/stone.png", "Redstone Block"),
    REDSTONE_LAMP_OFF("blockimg/stone.png", "Redstone Lamp Off"),
    REDSTONE_LAMP_ON("blockimg/stone.png", "Redstone Lamp on"),
    REDSTONE_ORE("blockimg/stone.png", "Redstone Ore"),
    SAND("blockimg/stone.png", "Sand"),
    SANDSTONE("blockimg/stone.png", "Sand Stone"),
    SANDSTONE_STEPS("blockimg/stone.png", "Sandstone Stairs"),
    SAPLING("blockimg/stone.png", "Sapling"),
    SEA_LANTERN("blockimg/stone.png", "Sea Lantern"),
    SKULL("blockimg/stone.png", "Skull"),//keeping this called "skull" even though it's a block.
    SLIME_BLOCK("blockimg/stone.png", "Slime Block"),
    SNOW_BLOCK("blockimg/stone.png", "Snow Block"),//may be something different
    SNOW_LAYER("blockimg/stone.png", "Snow Layer"),
    SPONGE("blockimg/stone.png", "Sponge"),
    SPRUCE_WOOD_STEPS("blockimg/stone.png", "Spruce Wood Stairs"),//may be something diffeent
    STAINED_CLAY("blockimg/stone.png", "Stained Clay"),
    STAINED_GLASS("blockimg/stone.png", "Stained Glass"),
    STAINED_GLASS_PANE("blockimg/stone.png", "Stained Glass Pane"),
    STICKY_PISTON("blockimg/stone.png", "Sticky Piston"),
    STONE_BRICK("blockimg/stone.png", "Stone Brick"),//may be called something different
    STONE_BRICK_STEPS("blockimg/stone.png", "Stone Brick Stairs"),
    STONE_PRESSURE_PLATE("blockimg/stone.png", "Stone Pressure Plate"),
    STONE_SLAB("blockimg/stone.png", "Stone Slab"),
    TNT("blockimg/stone.png", "TNT"),
    TRAPPED_CHEST("blockimg/stone.png", "Trapped Chest"),
    VINE("blockimg/stone.png", "Vine"),
    WATERLILLY("blockimg/stone.png", "Water Lilly"),//may be called something else
    WOOD("blockimg/stone.png", "Wood"),
    WOOD_FENCE("blockimg/stone.png", "Wood Fence"),
    WOOD_SLAB("blockimg/stone.png", "Wood Slab"),
    WOOD_STEPS("blockimg/stone.png", "Wood Stairs"),
    WOOL_CARPET_WHITE("blockimg/stone.png", "Wool Carpet White"),
    WOOL_WHITE("blockimg/stone.png", "Wool White");*/

    private ImageIcon image;
    private String name;
    private String materialName;
    private int durability;

    Material(String imagePath, String name) {
        this.name = name;
        materialName = toString();
        durability = 0;
        if (!name.equals("Air")) {
            try {
                this.image = new ImageIcon(getClass().getResource(imagePath));
            }catch(Exception e){
                System.out.println("THE ERROR WAS CAUSED BY MATERIAL "+name);
                e.printStackTrace();
            }
        } else {
            image = new ImageIcon(new BufferedImage(150, 150, BufferedImage.TYPE_INT_ARGB));
        }
    }

    Material(String imagePath, String name, String materialName, int durability) {
        this.name = name;
        this.materialName = materialName;
        this.durability = durability;
        //System.out.println(toString());
        if (!name.equals("Air")) {
            try {
                this.image = new ImageIcon(getClass().getResource(imagePath));
            }catch(Exception e){
                System.out.println("THE ERROR WAS CAUSED BY MATERIAL "+name);
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

    public Material getMaterialByName(String name) {
        for (Material m : Material.values()) {
            if (m.getDisplayName().equals(name))
                return m;
        }
        return null;
    }

}
