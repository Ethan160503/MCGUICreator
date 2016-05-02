package com.techno_wizard.mcguicreator.codecreator;

import com.techno_wizard.mcguicreator.gui.InventoryTableModel;
import com.techno_wizard.mcguicreator.gui.MainMenu;
import com.techno_wizard.mcguicreator.gui.events.AutoGenerateType;
import com.techno_wizard.mcguicreator.gui.inventory.Enchantment;
import com.techno_wizard.mcguicreator.gui.inventory.ItemStack;
import com.techno_wizard.mcguicreator.gui.inventory.Material;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zombie_Striker on 4/5/2016.
 */
public class CodeCreator {

    public static List<String> writecode(MainMenu mainMenu) {
        List<String> code = new ArrayList<String>(300);
        InventoryTableModel model = mainMenu.getInventoryTableModel();

        //Add comments saying the code was generated by this program
        code.add("/*");
        code.add("Code for inventory generated by [official name of our project here].");
        //TODO: Should we add our names to the auto-generated code?
        code.add("Created by Zombie_Striker and [the name you wish to go by]");
        code.add("*/");
        code.add("");
        code.add("/** HOW TO ADD THIS TO YOUR CODE");
        code.add("1. Add the first code section to the top of your class. This is the inventory field");
        code.add("--Note: Each code section is separated by a line. ");
        code.add("2. Add the second section to the onEnable or the constructor of the class. This calls for the inventory to be created.");
        code.add("3. Add the third section to the bottom of the class. These are the methods that create and set the inventory");
        code.add("4. Add the fourth section to the bottom of the class. This is for events (I.e when a player clicks the inventory)");
        code.add("IMPORTANT NOTE: You NEED to make sure");
        code.add("**/");
        code.add("");
        code.add("");

        String inventoryName = mainMenu.getInventoryTableModel().getInventoryName();

        //Creating the inventory
        code.add("//------- SECTION 1");
        code.add("");
        code.add("private Inventory " + inventoryName + ";");
        code.add("");
        code.add("//------- SECTION 2");
        code.add("");
        code.add("initInventory();");
        code.add("");
        code.add("//------- SECTION 3");
        code.add("");
        code.add("public void initInventory(){");
        code.add(inventoryName + " = Bukkit.createInventory(null," + (model.getRowCount() * 9) + "," + inventoryName + ");");

        //Setting the slots
        for (int row = 0; row < model.getRowCount(); row++) {
            for (int col = 0; col < model.getColumnCount(); col++) {
                ItemStack is = model.getItemStackAt(col, row);

                //Make sure all itemstacks are real.
                if (is.getMaterial() == Material.AIR) continue;

                //TODO:Add better way to write code for lores of itemstacks. Should setting the itemstacks be moved to a new method?
                //TODO: Ethan- probably. It would make a lot more sense that way and the output would be more readable. Btw, added a buffer to list for speed
                String loreName = "itemlore" + ((row * 9) + col);
                boolean hasLore = createStringList(code, is.getLoreAsList(), loreName);
                code.add("Itemstack itemstack"+((row * 9) + col)+" = createItemstack("
                                + is.getMaterial().getMaterialEnumName() + ","
                                + is.getAmount() + ","
                                + "\"" + is.getName()
                                + "\"," + (hasLore ? loreName : "null")
                                + ", (short)" + is.getMaterial().getDurability()
                                + ");");
                for(int i = 0; i < is.getEnchantments().size();i++){
                    Enchantment en=is.getEnchantments().get(i);
                    code.add("itemstack"+((row * 9) + col)+".add"+(en.isUnsafe()?"Unsafe":"")+"Enchantment(Enchantment."+en.getBukkitName()+","+en.getPowerLavel()+");");
                }
                code.add(inventoryName + ".setItem(" + ((row * 9) + col) + ",itemstack"+((row*9)+col)+");");
            }
        }
        code.add("}");
        code.add("");

        code.add("");
        code.add("//------- SECTION 4");
        code.add("");
        createEvents(code,mainMenu);

        //Create the createItemstack method
        createCreateItemstackMethod(code);

        //Format the code
        code = format(code);

        return code;
    }

    public static boolean createStringList(List<String> code, List<String> stringList, String name) {
        if (stringList == null)
            return false;

        List<String> accepctableLines = new ArrayList<>();

        for (int loreLine = 0; loreLine < stringList.size(); loreLine++) {
            String line = stringList.get(loreLine);
            while (line.startsWith(" "))
                line = line.substring(1);
            stringList.set(loreLine, line);
            if (line.length() > 1) {
                /*Since I can't remove a String by it's index because it throws An UnsupportedOpterationEception,
                 we're going to use a new arraylist to store all the accepted lore lines
                */
                accepctableLines.add(line);
            }
        }

        if (accepctableLines.isEmpty())
            return false;

        code.add(" List<String> " + name + " = new ArrayList<String>()");
        for (String s : accepctableLines) {
            code.add(" " + name + ".add(\"" + s + "\");");
        }
        return true;
    }
    public static void createEvents(List<String> code,MainMenu mainMenu){
        code.add("@EventHandler");
        code.add("public void onCustomInventoryClick(InventoryClickEvent e){");
        code.add("if(e.getInventory().getTitle().equals(\""+"TEMP NAME"+"\"){");
        code.add("if(e.getCurrentItem()!=null){");
        code.add("e.setCanceled(true);");
        int slots = ((mainMenu.getInventoryTableModel().getRowCount()-1)*9)+mainMenu.getInventoryTableModel().getColumnCount()-1;
        boolean isFirst = true;
        for(int i = 0; i < slots; i++){
            int row = (i)/9;
            int col = i%9;
            ItemStack is = mainMenu.getInvManager().getInventoryTableModel().getItemStackAt(col,row);
            if(is.getAutoGenerateType() != AutoGenerateType.NONE) {
                if(isFirst){
                    code.add("if(e.getSlot() == "+((row*9)+col)+"){");
                    isFirst = false;
                }else
                    code.add("else if(e.getSlot() == "+((row*9)+col)+"){");
                code.add("//This is the "+is.getMaterial().getDisplayName()+(is.getName().length()>1?(" called "+is.getName()+"."):"."));
                    for (String s : is.getAutoGenerateType().getCode()) {
                        code.add(s);
                    }
                code.add("}");
            }
        }
        code.add("}");
        code.add("}");
    }

    public static void createCreateItemstackMethod(List<String> code) {
        code.add("private Itemstack createItemstack(Material m,int amount, String displayname, List<String> lore,short data){");
        code.add("Itemstack is = new Itemstack(m);");
        code.add("is.setAmount(amount);");
        code.add("ItemMeta im = is.getItemMeta();");
        code.add("im.setDisplayName(displayname);");
        code.add("if(lore!=null)");
        code.add("im.setLore(lore);");
        code.add("is.setItemMeta(im);");
        code.add("is.setDurability(data)");
        code.add("return is;");
        code.add("}");
    }
    public static List<String> format(List<String> code){
        List<String> formattedCode = new ArrayList<>();
        String indent = "   ";
        int indentCount = 1;
        // for each line
        for(int i = 0; i < code.size();i++){
            int prevIndents = indentCount;
            boolean hasCloseBrace = false;
            if(code.get(i).contains("}") || code.get(i).contains("{")) {
                for (Character c : code.get(i).toCharArray()) {
                    if (c.equals('{'))
                        indentCount++;
                    if (c.equals('}')) {
                        indentCount--;
                        hasCloseBrace = true;
                    }
                }
            }
            //Get the correct amount of indents and removes one if there is a close brace
            String indents="";
            int iterCount = prevIndents;
            if (hasCloseBrace) iterCount--;

            for(int j = 0; j < iterCount; j++){
                indents+=indent;
            }

            formattedCode.add(indents+ code.get(i));
        }
        return formattedCode;
    }
}
