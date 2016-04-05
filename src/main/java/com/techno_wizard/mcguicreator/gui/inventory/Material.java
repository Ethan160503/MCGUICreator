package com.techno_wizard.mcguicreator.gui.inventory;

import javax.swing.*;
import java.awt.image.BufferedImage;

/**
 * Created by Ethan on 4/3/2016.
 */
public enum Material {
    AIR("Null","Air"),
    ACACIA_BOAT("/itemimg/acacia_boat.png","Acaial Boat"),
    APPLE("/itemimg/apple.png","Apple"),
    APPLE_GOLDEN("/itemimg/apple_golden.png","Golden Apple"),
    ARROW("/itemimg/arrow.png","Arrow"),
//    BANNER_BASE("Null","Banner "),
//    BANNER_OVERLAY("Null","Air"),
    BARRIER("/itemimg/barrier.png","Barrier"),
    BED("/itemimg/bed.png","Bed"),
    BEEF_COOKED("/itemimg/beef_cooked.png","Cooked Beef"),
    BEEF_RAW("/itemimg/beef_raw.png","Raw Beef"),
    BEETROOT("/itemimg/beetroot.png","BeetRoot"),
    BEETROOT_SEEDS("/itemimg/beetroot_seeds.png","Beetroot Seeds"),
    BEETROOT_SOUP("/itemimg/beetroot_soup.png","Beetroot soup"),
    BIRCH_BOAT("/itemimg/birch_boat.png","Birch Boat"),
    BLAZE_POWDER("/itemimg/blaze_powder.png","Blaze Powder"),
    BLAZE_ROD("/itemimg/blaze_rod.png","Blaze Rod"),
    BONE("/itemimg/bone.png","Bone"),
    BOOK_ENCHANTED("/itemimg/book_enchanted.png","Enchanted Book"),
    BOOK("/itemimg/book_normal.png","Book"),
    BOW("/itemimg/bow.png","Bow"),
    BOWL("/itemimg/bowl.png","Empty Bowl"),
    BREAD("/itemimg/bread.png","Bread"),
    BREWING_STAND("/itemimg/brewing_stand.png","Brewing Stand"),
    BRICK("/itemimg/brick.png","Brick"),
    BUCKET("/itemimg/bucket_empty.png","Empty Bucket"),
    BUCKET_MILK("/itemimg/bucket_milk.png","Milk Bucket"),
    BUCKET_WATER("/itemimg/bucket_water.png","Water Bucket"),
    BUCKET_LAVA("/itemimg/bucket_lava.png","Lava Bucket"),
    CAKE("/itemimg/bow.png","Bow"),
    CARROT_GOLDEN("/itemimg/bow.png","Bow"),
    CARROT_ON_STICK("/itemimg/bow.png","Bow"),
    CAULDRON("/itemimg/bow.png","Bow"),
    CHAINMAIL_BOOTS("/itemimg/bow.png","Bow"),
    CHAINMAIL_CHESTPLATE("/itemimg/bow.png","Bow"),
    CHAINMAIL_HELMET("/itemimg/bow.png","Bow"),
    CHAINMAIL_LEGGINGS("/itemimg/bow.png","Bow"),
    CHARCOAL("/itemimg/bow.png","Bow"),
    CHICKEN_COOKED("/itemimg/bow.png","Bow"),
    CHICKEN_RAW("/itemimg/bow.png","Bow"),
    CHORUS_FRUIT("/itemimg/bow.png","Bow"),
    CHORUS_FRUIT_POPPED("/itemimg/bow.png","Bow"),
    CLAY_BALL("/itemimg/bow.png","Bow"),
    CLOCK("/itemimg/bow.png","Bow"),
    COAL("/itemimg/bow.png","Bow"),
    POISONOUS_POTATO("/itemimg/potato_poisonous.png","Poisonous Potato"),
    POTATO("/itemimg/potato.png","Potato"),
    WOOD_SWORD("/itemimg/wood_sword.png","Wood sword"),

    //Dyes
    DYE_WHITE("/itemimg/dye_powder_white.png","White Dye","INK_SACK",0),
    DYE_ORANGE("/itemimg/dye_powder_orange.png","Orange Dye","INK_SACK",1),
    DYE_MAGENTA("/itemimg/dye_powder_magenta.png","Magenta Dye","INK_SACK",2),
    DYE_LIGHT_BLUE("/itemimg/dye_powder_light_blue.png","Light Blue Dye","INK_SACK",3),
    DYE_YELLOW("/itemimg/dye_powder_yellow.png","Yellow Dye","INK_SACK",4),
    DYE_LIME("/itemimg/dye_powder_lime.png","Lime Dye","INK_SACK",5),
    DYE_PINK("/itemimg/dye_powder_pink.png","Pink Dye","INK_SACK",6),
    DYE_GRAY("/itemimg/dye_powder_gray.png","Gray Dye","INK_SACK",7),
    DYE_LIGHT_GRAY("/itemimg/dye_powder_silver.png","Light Gray Dye","INK_SACK",8),
    DYE_CYAN("/itemimg/dye_powder_cyan.png","Cyan Dye","INK_SACK",9),
    DYE_PURPLE("/itemimg/dye_powder_purple.png","Purple Dye","INK_SACK",10),
    DYE_BLUE("/itemimg/dye_powder_blue.png","Blue Dye","INK_SACK",11),
    DYE_BROWN("/itemimg/dye_powder_brown.png","Brown Dye","INK_SACK",12),
    DYE_GREEN("/itemimg/dye_powder_green.png","Green Dye","INK_SACK",13),
    DYE_RED("/itemimg/dye_powder_red.png","Red Dye","INK_SACK",14),
    DYE_BLACK("/itemimg/dye_powder_black.png","Black Dye","INK_SACK",15);

    private ImageIcon image;
    private String name;
    private String materialName;
    private int durability;

    Material(String imagePath,String name){
        this.name = name;
        materialName = toString();
        durability = 0;
        if(!name.equals("Air")) {
            this.image = new ImageIcon(getClass().getResource(imagePath));
        }else{
            image = new ImageIcon(new BufferedImage(150,150,BufferedImage.TYPE_INT_ARGB));
        }
    }
    Material(String imagePath,String name,String materialName,int durability){
        this.name = name;
        this.materialName = materialName;
        this.durability=durability;
        //System.out.println(toString());
        if(!name.equals("Air")) {
            this.image = new ImageIcon(getClass().getResource(imagePath));
        }else{
            image = new ImageIcon(new BufferedImage(150,150,BufferedImage.TYPE_INT_ARGB));
        }
    }
    public int getDurability(){
        return durability;
    }
    public String getMaterialEnumName(){
        return "Material."+materialName;
    }

    /**
     * This returns the name of each materials. Use this to turn the material into a string.
     * @return
     */
    public String getName() {
        return name;
    }

    public ImageIcon getImage(){
        return image;
    }

    public Material getMaterialByName(String name){
        for(Material m: Material.values()){
            if(m.getName().equals(name))
                return m;
        }
        return null;
    }

}
