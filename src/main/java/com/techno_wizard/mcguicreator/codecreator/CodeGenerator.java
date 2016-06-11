package com.techno_wizard.mcguicreator.codecreator;

import com.techno_wizard.mcguicreator.MCGUICreator;
import com.techno_wizard.mcguicreator.gui.InventoryTableModel;
import com.techno_wizard.mcguicreator.gui.MainMenu;
import com.techno_wizard.mcguicreator.gui.events.AutoGenerateType;
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
            for (String section :sections) {
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

        buffer.addLine(((String)extraStringMap.get("imports")));
        // print the initial notes
        buffer.addLine((String) commentsStringMap.get("init comment"));
        buffer.addEmptyLine();
        buffer.addEmptyLine();

        buffer.addLine(((String) extraStringMap.get("class constructor"))
                .replace("$count$", invModel.getRowCount()*9+"")
                .replace("$classname$", invModel.getInventoryName().replace(" ", ""))
                .replace("$inv$","inventoryInstance")
                .replace("$size$",(mainMenu.getInvManager().getInventoryTableModel().getRowCount()*9)+"")
        );

        buffer.addLine((String) extraStringMap.get("init inv"));
        // set lore buffer
        buffer.addLine((String) loreStringMap.get("create lore buffer"));
        // set enchant buffer
        buffer.addLine((String) enchantmentStringMap.get("create ench buffer"));
        // add space before creating stacks
        buffer.addEmptyLine();

        for(int slot = 0; slot < invModel.getRowCount()*9;slot++){
            ItemStack toWrite = invModel.getItemStackAt(slot%9,slot/9);
            if(toWrite.getMaterial() != Material.AIR){
                addItemstackToInv(toWrite, buffer,slot);
                buffer.addEmptyLine();
            }
        }
        buffer.addLine("}");//Closing bracket for the intiInv method

        buffer.addLine(((String)extraStringMap.get("listener top"))
                .replace("$inv$","inventoryInstance")
        );
        buffer.addLine("switch(e.getSlot()){");
        for(int slot = 0; slot < invModel.getRowCount()*9;slot++){
            ItemStack toWrite = invModel.getItemStackAt(slot%9,slot/9);
            if(toWrite.getMaterial() != Material.AIR){
                addItemstackToListener(toWrite, buffer,slot);
                buffer.addEmptyLine();
            }
        }

        // close out brackets. 5 needed.
        for (int i = 0; i < 5; i++)
            buffer.addLine("}");

        return buffer.getFormattedOutput();
    }


    private void addItemstackToListener(ItemStack itemstack, CodeBuffer codeBuffer, int slot) {
        if (itemstack.getAutoGenerateType() != AutoGenerateType.EMPTY) {
            codeBuffer.addLine(String.format("case %d: // stack: %s -> %s", slot, itemstack.getName(), itemstack.getNotes()));
            codeBuffer.addLine(itemstack.getAutoGenerateType().getCodeAsString());
            codeBuffer.addLine("break;");
        }
    }

    private void addItemstackToInv(ItemStack itemStack, CodeBuffer buffer, int slot) {
        // set itemstack var
        buffer.addLine(((String) itemStackStringMap.get("create stack")).replace("$stack$", "stack" + slot));
        // does the item require durability to create?
        String shouldHaveData = itemStack.getMaterial().getDurability() == 0 ? "create no data" : "create with data";
        buffer.addLine(((String) itemStackStringMap.get(shouldHaveData))
                .replace("$material$", itemStack.getMaterial().getMaterialEnumName())
                .replace("$count$", itemStack.getAmount() + "")
                .replace("$data$", itemStack.getMaterial().getDurability() + "")
                .replace("$stack$", "stack" + slot)
        );


        // set lore lines
        for (String lore_line : itemStack.getLoreAsList())
            buffer.addLine(((String) loreStringMap.get("add item to lore buffer"))
                    .replace("$id$", slot + "")
                    .replace("$lore_line$", lore_line)
            );

        // add enchantments
        for (Enchantment ench : itemStack.getEnchantments())
            buffer.addLine(((String) enchantmentStringMap.get("add item to ench buffer"))
                    .replace("$id$", slot + "")
                    .replace("$ench$", ench.getBukkitName())
                    .replace("$power$", ench.getPowerLevel() + "")
            );

        // call metadata formatter
        buffer.addLine(((String) itemStackStringMap.get("call formatter"))
                .replace("$stack$", "stack" + slot)
                .replace("$name$", itemStack.getName())
                .replace("$loreBuffer$", "loreBuffer")
                .replace("$enchantments$", "enchBuffer")

        );

        // clear buffers
        buffer.addLine((String) loreStringMap.get("clear lore buffer"));
        buffer.addLine((String) enchantmentStringMap.get("clear"));

        buffer.addLine(((String) inventoryStringMap.get("add stack to slot"))
                .replace("$stack$", "stack" + slot)
                .replace("$slot$", slot+"")
                .replace("$inv$", "inventoryInstance" )
        );

        buffer.addLine("//end stack");
        buffer.addEmptyLine();
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