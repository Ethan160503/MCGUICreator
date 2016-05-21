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
 * A generator that takes an inventoryStringMap and outputs code to make it in MC
 */
public class CodeGenerator {

    private static CodeGenerator instance;
    private MainMenu mainMenu;
    private JSONObject codeTemplates;

    private JSONObject commentsStringMap;
    private JSONObject inventoryStringMap;
    private JSONObject javaConstructorsStringMap;
    private JSONObject itemStackStringMap;
    private JSONObject loreStringMap;
    private JSONObject enchantmentStringMap;
    private JSONObject extraStringMap;

    private Pattern openBracketPattern;
    private Pattern closeBracketPattern;
    private Pattern colorCodePattern;

    //instance needs to be static in order to use this
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

        commentsStringMap = (JSONObject) codeTemplates.get("comments");
        inventoryStringMap = (JSONObject) codeTemplates.get("inventory");
        javaConstructorsStringMap = (JSONObject) codeTemplates.get("java constructors");
        itemStackStringMap = (JSONObject) codeTemplates.get("itemstack");
        loreStringMap = (JSONObject) codeTemplates.get("lore");
        enchantmentStringMap = (JSONObject) codeTemplates.get("enchantment");
        extraStringMap = (JSONObject) codeTemplates.get("extra");

        openBracketPattern = Pattern.compile("[{]");
        closeBracketPattern = Pattern.compile("[}]");
        colorCodePattern = Pattern.compile("[§.]");
    }

    /**
     * A wrapper for an array list ot also give indent formatting
     */
    private class CodeBuffer {
        private List<String> code = new ArrayList<>();
        private final static String INDENT = "    ";

        public void addLine(String line) {
            String[] sections = line.split("\\n");
            for (String section :
                    sections) {
                code.add(section);
            }
        }

        public void addEmptyLine() {
            code.add("");
        }

        public String getFormattedOutput() {
            StringBuilder sb = new StringBuilder();
            int runningIndentCount = 0;
            // for each line

            Iterator<String> iterator = code.iterator();
            while (iterator.hasNext()) {
                String next = iterator.next();

                // find counts of open and closes.
                Matcher openMatcher = openBracketPattern.matcher(next);
                Matcher closeMatcher = closeBracketPattern.matcher(next);

                int openCount = 0, closeCount = 0;
                while (openMatcher.find())
                    openCount ++;

                while (closeMatcher.find())
                    closeCount ++;


                int lineIndentMovement = openCount - closeCount;
                String shownIndent = "";
                // init the indent
                for (int i = 0; i < runningIndentCount; i++) {
                    shownIndent += INDENT;
                }

                // edit it for the current line
                if (lineIndentMovement < 0) {
                    // it's losing indents on this line
                     if(runningIndentCount + lineIndentMovement <= 0) {
                         // everything was cleared
                         shownIndent = "";
                     } else {
                         // not everything was cleared
                         /*
                         Take the first index, then go to the last index (total char) and add the
                         negative line indent of the line multiplied by 4
                          */
                         shownIndent = shownIndent.substring(0, (runningIndentCount * 4) + (lineIndentMovement * 4));
                     }
                }
                sb.append(shownIndent + next + "\n");
                runningIndentCount += lineIndentMovement;
                if (runningIndentCount < 0)
                    runningIndentCount = 0;
            }
            return sb.toString();
        }
    }
    /**
     * Writes the java code that constructs the inventoryStringMap table model in bukkit
     * @return
     */
    public String writeRepresentingJava(InventoryTableModel invModel){
        CodeBuffer buffer = new CodeBuffer();

        // print the initial notes
        buffer.addLine((String) commentsStringMap.get("init comment"));
        buffer.addEmptyLine();
        buffer.addEmptyLine();



        buffer.addLine(((String) extraStringMap.get("class constructor"))
                .replace("$count$", invModel.getRowCount()*9+"")
                .replace("$name$", invModel.getInventoryName())
                .replace("$inv$","inventoryInstance")
                .replace("$classname$", invModel.getInventoryName().replace(" ","")+"Inventory")
        );

        buffer.addLine((String) extraStringMap.get("init inv"));
        // set itemstack var
        buffer.addLine((String) itemStackStringMap.get("create stack"));
        // set lore buffer
        buffer.addLine((String) loreStringMap.get("create lore buffer"));
        // set enchant buffer
        buffer.addLine((String) enchantmentStringMap.get("create ench buffer"));
        for(int slot = 0; slot < invModel.getRowCount()*9;slot++){
            ItemStack toWrite = invModel.getItemStackAt(slot%9,slot/9);
            if(toWrite.getMaterial() != Material.AIR){
                addItemstackToInv(toWrite, buffer,slot);
                buffer.addEmptyLine();
            }
        }

        return buffer.getFormattedOutput();
    }

    private void addItemstackToInv(ItemStack itemStack, CodeBuffer buffer, int slot) {

        // does the item require durability to create?
        String shouldHaveData = itemStack.getMaterial().getDurability() == 0 ? "create no data" : "create with data";
        buffer.addLine(replaceFields((String) itemStackStringMap.get(shouldHaveData),
                "material:" + itemStack.getMaterial().getMaterialEnumName(),
                "count:" + itemStack.getAmount(),
                "data:" + itemStack.getMaterial().getDurability(),
                "stack:" + "itemStackStringMap" + slot));

        /*buffer.addLine(((String) itemStackStringMap.get(shouldHaveData))
                .replace("$material$", itemStack.getMaterial().getMaterialEnumName()
                .replace("$count$", itemStack.getAmount() + "")
                .replace("$data$", itemStack.getMaterial().getDurability() + "")
                .replace("$stack$", "itemStackStringMap" + slot)
        ));*/

        // set lore buffer
        buffer.addLine(((String) loreStringMap.get("create lore buffer"))
                .replace("$id$", slot + "")
        );

        // set lore lines
        for (String lore_line : itemStack.getLoreAsList())
            buffer.addLine(((String) loreStringMap.get("add item to lore buffer"))
                    .replace("$id$", slot + "")
                    .replace("$lore_line$", lore_line)
            );

        // create enchantment buffer
        buffer.addLine(((String) enchantmentStringMap.get("create ench buffer"))
                .replace("$id$", slot + "")
        );

        // add enchantments
        for (Enchantment ench : itemStack.getEnchantments())
            buffer.addLine(((String) enchantmentStringMap.get("add item to ench buffer"))
                    .replace("$id$", slot + "")
                    .replace("$ench$", ench.getBukkitName())
                    .replace("$power$", ench.getPowerLavel() + "")
            );

        // call metadata formatter
        buffer.addLine(((String) itemStackStringMap.get("call formatter"))
                .replace("$stack$", "itemStackStringMap" + slot)
                .replace("$name$", itemStack.getName())
                .replace("$loreBuffer$", "loreBuffer" + slot)
                .replace("$enchantments$", "enchBuffer" + slot)

        );

        // clear buffers
        buffer.addLine((String) loreStringMap.get("clear lore buffer"));
        buffer.addLine((String) enchantmentStringMap.get("clear"));
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


    /**
     * Replaces the given fields in a template with the following syntax for field replacement:
     *<p>"fieldToReplace:toReplaceWith"</p>
     * @param template The string template
     * @param args The list of fields to replace with the former syntax
     * @return The final output
     */
    private String replaceFields(String template, String... args) {
        for (String arg :
                args) {
            String[] split = arg.split(":");
            assert split.length == 2 : "Warning: Invalid replace syntax";
            template.replace("$" + split[0] + "$", split[1]);
        }
        return template;
    }

    public static void main(String[] args) {
        InventoryTableModel model = new InventoryTableModel();

        ItemStack stack = new ItemStack(Material.DYE_GREEN);
        ItemStack another = new ItemStack(Material.APPLE);
        another.setLore("Such lore. wow");
        another.setName("uuhh");

        stack.setLore("This is lore\nSuch lore meta");
        System.out.println("LOADING");

        model.setValueAt(stack, 0, 0);
        model.setValueAt(another, 0, 1);
        System.out.println(getInstance().writeRepresentingJava(model));
    }
}