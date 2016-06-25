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

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A generator that takes an inventoryStringMap and outputs code to make it in MC
 */
public class CodeGenerator {

    private static CodeGenerator instance;
    private MainMenu mainMenu;

    private JSONObject commentsStringMap;
    private JSONObject inventoryStringMap;
    private JSONObject itemStackStringMap;
    private JSONObject loreStringMap;
    private JSONObject enchantmentStringMap;
    private JSONObject extraStringMap;

    private Pattern openBracketPattern;
    private Pattern closeBracketPattern;
    private Pattern switchCasePattern;

    //instance needs to be static in order to use this
    public static CodeGenerator getInstance() {
        if (instance == null)
            instance = new CodeGenerator();

        return instance;
    }

    private CodeGenerator() {
        Reader reader = new InputStreamReader(getClass().getResourceAsStream("/text/code_templates.json"));
        JSONObject codeTemplates = (JSONObject) JSONValue.parse(reader);
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.mainMenu = MCGUICreator.getMainMenu();

        commentsStringMap = (JSONObject) codeTemplates.get("comments");
        inventoryStringMap = (JSONObject) codeTemplates.get("inventory");
        itemStackStringMap = (JSONObject) codeTemplates.get("itemstack");
        loreStringMap = (JSONObject) codeTemplates.get("lore");
        enchantmentStringMap = (JSONObject) codeTemplates.get("enchantment");
        extraStringMap = (JSONObject) codeTemplates.get("extra");

        openBracketPattern = Pattern.compile("[{]");
        closeBracketPattern = Pattern.compile("[}]");
        switchCasePattern = Pattern.compile("case .*:");
    }

    /**
     * A wrapper for an array list ot also give indent formatting
     */
    private class CodeBuffer {
        private List<String> code = new ArrayList<>();
        private final static String INDENT = "    ";

        void addLine(String line) {
            String[] sections = line.split("\\n");
            Collections.addAll(code, sections);
        }

        void addEmptyLine() {
            code.add("");
        }

        String getFormattedOutput() {
            StringBuilder sb = new StringBuilder();
            int runningIndentCount = 0;
            boolean isOnSwitchCase = false;
            boolean applySwitchClose = false;
            // for each line

            for (String next : code) {
                // find counts of open and closes.
                Matcher openMatcher = openBracketPattern.matcher(next);
                Matcher closeMatcher = closeBracketPattern.matcher(next);

                int openCount = 0, closeCount = 0;
                while (openMatcher.find())
                    openCount++;

                if (!isOnSwitchCase && switchCasePattern.matcher(next).find()) {
                    // its a case statement
                    openCount++;
                    isOnSwitchCase = true;
                }


                while (closeMatcher.find())
                    closeCount++;

                // the last line applied a switch case close. Unindent.
                if (applySwitchClose) {
                    closeCount++;
                    applySwitchClose = false;
                }

                if (isOnSwitchCase && next.contains("break;")) {
                    // case ended
                    isOnSwitchCase = false;
                    applySwitchClose = true;
                }


                int lineIndentMovement = openCount - closeCount;
                String shownIndent = "";
                // init the indent
                for (int i = 0; i < runningIndentCount; i++) {
                    shownIndent += INDENT;
                }

                // edit it for the current line
                if (lineIndentMovement < 0) {
                    // it's losing indents on this line
                    if (runningIndentCount + lineIndentMovement <= 0) {
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
                sb.append(shownIndent).append(next).append("\n");
                runningIndentCount += lineIndentMovement;
                if (runningIndentCount < 0)
                    runningIndentCount = 0;
            }
            return sb.toString();
        }
    }

    /**
     * Writes the java code that constructs the inventoryStringMap table model in bukkit
     *
     * @return representing java
     */
    public String writeRepresentingJava(InventoryTableModel invModel) {
        CodeBuffer buffer = new CodeBuffer();
        String invName = invModel.getInventoryName().replace(" ", "").toLowerCase();

        buffer.addLine(((String) extraStringMap.get("imports")));
        // print the initial notes
        buffer.addLine((String) commentsStringMap.get("init comment"));
        buffer.addEmptyLine();
        buffer.addEmptyLine();

        buffer.addLine(((String) extraStringMap.get("class constructor"))
                .replace("$count$", invModel.getRowCount() * 9 + "")
                .replace("$classname$", invModel.getInventoryName().replace(" ", ""))
                .replace("$name$", invModel.getInventoryName())
                .replace("$inv$", invName)
                .replace("$size$", (mainMenu.getInvManager().getInventoryTableModel().getRowCount() * 9) + "")
        );

        buffer.addLine((String) extraStringMap.get("init inv"));
        // set lore buffer
        buffer.addLine((String) loreStringMap.get("create lore buffer"));
        // set enchant buffer
        buffer.addLine((String) enchantmentStringMap.get("create ench buffer"));
        // add space before creating stacks
        buffer.addEmptyLine();

        // create itemstacks
        for (int slot = 0; slot < invModel.getRowCount() * 9; slot++) {
            ItemStack toWrite = invModel.getItemStackAt(slot % 9, slot / 9);
            if (toWrite.getMaterial() != Material.AIR) {
                addItemstackToInv(toWrite, buffer, slot, invName);
                buffer.addEmptyLine();
            }
        }
        buffer.addLine("}");//Closing bracket for the intiInv method

        buffer.addLine(((String) extraStringMap.get("listener top"))
                .replace("$inv$", invName)
        );
        buffer.addLine("switch(e.getSlot()){");
        for (int slot = 0; slot < invModel.getRowCount() * 9; slot++) {
            ItemStack toWrite = invModel.getItemStackAt(slot % 9, slot / 9);
            if (toWrite.getMaterial() != Material.AIR) {
                addItemstackToListener(toWrite, buffer, slot);
            }
        }

        // close out brackets back to class body. 4 needed.
        for (int i = 0; i < 4; i++)
            buffer.addLine("}");

        buffer.addLine((String) extraStringMap.get("clone method"));
        buffer.addEmptyLine();
        buffer.addLine((String) itemStackStringMap.get("meta formatter"));

        buffer.addLine("}"); // add class closing bracket

        return buffer.getFormattedOutput();
    }

    private void addItemstackToListener(ItemStack itemstack, CodeBuffer codeBuffer, int slot) {
        if (itemstack.getAutoGenerateType() != AutoGenerateType.NONE) {
            codeBuffer.addLine(String.format("case %d: // stack: %s -> %s", slot, itemstack.getName(), itemstack.getNotes()));
            codeBuffer.addLine(itemstack.getAutoGenerateType().getCodeAsString());
            codeBuffer.addLine("break;");
            codeBuffer.addEmptyLine();
        }
    }

    private void addItemstackToInv(ItemStack itemStack, CodeBuffer buffer, int slot, String invName) {
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
                .replace("$slot$", slot + "")
                .replace("$inv$", invName)
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