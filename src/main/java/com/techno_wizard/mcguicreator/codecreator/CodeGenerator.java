package com.techno_wizard.mcguicreator.codecreator;

import com.techno_wizard.mcguicreator.MCGUICreator;
import com.techno_wizard.mcguicreator.gui.InventoryTableModel;
import com.techno_wizard.mcguicreator.gui.MainMenu;
import com.techno_wizard.mcguicreator.gui.inventory.Enchantment;
import com.techno_wizard.mcguicreator.gui.inventory.ItemStack;
import com.techno_wizard.mcguicreator.gui.inventory.Material;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A generator that takes an inventory and outputs code to make it in MC
 */
public class CodeGenerator {

    private CodeGenerator instance;
    private MainMenu mainMenu;
    private JSONObject codeTemplates;

    private JSONObject comments;
    private JSONObject inventory;
    private JSONObject javaConstructors;
    private JSONObject itemStack;
    private JSONObject lore;
    private JSONObject enchantment;
    private JSONObject extra;

    private Pattern openBracketPattern;
    private Pattern closeBracketPattern;


    public static CodeGenerator getInstance() {
        if (instance == null)
            instance = new CodeGenerator();

        return instance;
    }

    private CodeGenerator() {
        Reader reader = new InputStreamReader(getClass().getResourceAsStream("/text/code_templates.json"));
        codeTemplates = (JSONObject) JSONValue.parse(reader);
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.mainMenu = MCGUICreator.getMainMenu();

        comments = (JSONObject) codeTemplates.get("comments");
        inventory = (JSONObject) codeTemplates.get("inventory");
        javaConstructors = (JSONObject) codeTemplates.get("java constructors");
        itemStack = (JSONObject) codeTemplates.get("itemstack");
        lore = (JSONObject) codeTemplates.get("lore");
        enchantment = (JSONObject) codeTemplates.get("enchantment");
        extra = (JSONObject) codeTemplates.get("extra");

        openBracketPattern = Pattern.compile("[{]");
        closeBracketPattern = Pattern.compile("[}]");
    }

    /**
     * A wrapper for an array list ot also give indent formatting
     */
    private class CodeBuffer {
        private List<String> code = new ArrayList<>();
        private final static String INDENT = "    ";

        public void addLine(String line) {
            code.add(line);
        }

        public void addEmptyLine() {
            code.add("");
        }

        public String getFormattedOutput() {
            return format();
        }

        private String format() {
            StringBuilder sb = new StringBuilder();
            int runningIndentCount = 0;
            // for each line

            Iterator<String> iterator = code.iterator();
            while (iterator.hasNext()) {
                String next = iterator.next();;

                // find counts of open and closes.
                Matcher openMatcher = openBracketPattern.matcher(next);
                Matcher closeMatcher = closeBracketPattern.matcher(next);

                int openCount = 0, closeCount = 0;
                while (openMatcher.find())
                    openCount ++;

                while (closeMatcher.find())
                    closeCount ++;


                int lineIndentMovement = openCount - closeCount;
                String totalIndent = "";
                if (lineIndentMovement < 0) {
                    for(int i = 0; runningIndentCount - lineIndentMovement > i; i++) {
                        totalIndent += INDENT;
                    }
                    sb.append(totalIndent + next);
                } else if (lineIndentMovement > 0) {
                    for (int i = 0; runningIndentCount > i; i++) {
                        totalIndent += INDENT;
                    }
                    sb.append(totalIndent + next);
                }
                runningIndentCount += lineIndentMovement;
                if (runningIndentCount < 0)
                    runningIndentCount = 0;
            }
            return sb.toString();
        }

        /**
         * Writes the java code that constructs the inventory table model in bukkit
         * @return
         */
        public String writeRepresentingJava(InventoryTableModel invModel){
            CodeBuffer buffer = new CodeBuffer();

            buffer.addLine((String) comments.get("init comment"));
            buffer.addLine(((String) extra.get("class constructor"))
                    .replace("$count$",mainMenu.getInventoryTableModel().getRowCount()*9+"")
                    .replace("$name$",mainMenu.getInventoryTableModel().getInventoryName())
                    .replace("$inv$","inventoryInstance")
                    .replace("$classname$",mainMenu.getInventoryTableModel().getInventoryName().replace(" ","")+"Inventory")
            );

            buffer.addLine((String) extra.get("init inv"));
            for(int slot = 0; slot < mainMenu.getInventoryTableModel().getRowCount()*9;slot++){
                if(mainMenu.getInventoryTableModel().getItemStackAt(slot%9,slot/9).getMaterial() != Material.AIR){
                    addItemstackToInv(buffer,slot);
                }
            }

            return buffer.getFormattedOutput();
        }

        /**
         * Gets java data members' constructors. The following are supported<br>
         * int, double, short, float, String, char
         *
         * @param wrapperValue the value to get its constructor of.
         *
         * @return java of data member constructor
         *
         */
        private String getJavaMemeberConstructor(Object wrapperValue, String varName) {
            JSONObject constructors = (JSONObject) codeTemplates.get("java constructors");
            String template;
            if(wrapperValue instanceof Integer) {
                Integer integer = (Integer) wrapperValue;
                template = (String) constructors.get("int");
                template.replace("$name$", varName);
                template.replace("$val$", integer.toString());
            } else if (wrapperValue instanceof Double) {
                Double dub = (Double) wrapperValue;
                template = (String) constructors.get("double");
                template.replace("$name$", varName);
                template.replace("$val$", dub.toString());
            } else if (wrapperValue instanceof Short) {
                Short shrt = (Short) wrapperValue;
                template = (String) constructors.get("short");
                template.replace("$name$", varName);
                template.replace("$val$", shrt.toString());
            } else if (wrapperValue instanceof Character) {
                Character ctr = (Character) wrapperValue;
                template = (String) constructors.get("char");
                template.replace("$name$", varName);
                template.replace("$val$", ctr.toString());
            } else if (wrapperValue instanceof Float) {
                Float flot = (Float) wrapperValue;
                template = (String) constructors.get("float");
                template.replace("$name$", varName);
                template.replace("$val$", flot.toString());
            } else if (wrapperValue instanceof String) {
                String string = (String) wrapperValue;
                template = (String) constructors.get("String");
                template.replace("$name$", varName);
                template.replace("$val$", string);
            } else {
                System.out.println("Error: could not determine java class type");
                template = "Error";
            }
            return template;
        }


        private void addItemstackToInv(ItemStack itemstack, CodeBuffer buffer){

            String shouldHaveData = itemStack..getDurability() == 0 ? "create no data" : "create with data";
            buffer.addLine(((String)itemStack.get(shouldHaveData))
                    .replace("$material$",itemStack1.getMaterial().getMaterialEnumName())
                    .replace("$count$",itemStack1.getAmount()+"")
                    .replace("$data$",itemStack1.getMaterial().getDurability()+"")
                    .replace("$stack$","itemStack"+slot)
            );

            buffer.addLine(((String)lore.get("lore buffer"))
                    .replace("$id$",slot+"")
            );
            for(String lore_line : itemStack1.getLoreAsList())
                buffer.addLine(((String)lore.get("add item to lore buffer"))
                        .replace("$id$",slot+"")
                        .replace("$lore_line$",lore_line)
                );
            buffer.addLine(((String)lore.get("ench buffer"))
                    .replace("$id$",slot+"")
            );
            for(Enchantment ench: itemStack1.getEnchantments())
                buffer.addLine(((String)lore.get("add item to ench buffer"))
                        .replace("$id$",slot+"")
                        .replace("$ench$",ench.getBukkitName())
                        .replace("$power$",ench.getPowerLavel()+"")
                );
            buffer.addLine(((String)itemStack.get("call formatter"))
                    .replace("$stack$","itemStack"+slot)
                    .replace("$name$",itemStack1.getName())
                    .replace("$loreBuffer$","loreBuffer"+slot)
                    .replace("$enchantments$","enchBuffer"+slot)

            );
        }
    }
