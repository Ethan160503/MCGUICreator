package com.techno_wizard.mcguicreator.codecreator;

import com.techno_wizard.mcguicreator.MCGUICreator;
import com.techno_wizard.mcguicreator.gui.InventoryTableModel;
import com.techno_wizard.mcguicreator.gui.MainMenu;
import com.techno_wizard.mcguicreator.gui.inventory.Enchantment;
import com.techno_wizard.mcguicreator.gui.inventory.ItemStack;
import com.techno_wizard.mcguicreator.gui.inventory.Material;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * A generator that takes an inventory and outputs code to make it in MC
 */
public class CodeGenerator {

    private static CodeGenerator instance;
    private static MainMenu mainMenu;
    private JSONObject codeTemplates;



    private static JSONObject comments;
    private static JSONObject inventory;
    private static JSONObject javaConstructors;
    private static JSONObject itemStack;
    private static JSONObject lore;
    private static JSONObject enchantment;
    private static JSONObject extrra;

    public static CodeGenerator getInstance() {
        if (instance == null)
            instance = new CodeGenerator();

        return instance;
    }

    private CodeGenerator() {
        Reader reader = new InputStreamReader(getClass().getResourceAsStream("/text/code_templates.json"));
        codeTemplates = (JSONObject) JSONValue.parse(reader);
        this.mainMenu = MCGUICreator.getMainMenu();



        comments = (JSONObject) codeTemplates.get("comments");
        inventory = (JSONObject) codeTemplates.get("inventory");
        javaConstructors = (JSONObject) codeTemplates.get("java constructors");
        itemStack = (JSONObject) codeTemplates.get("itemstack");
        lore = (JSONObject) codeTemplates.get("lore");
        enchantment = (JSONObject) codeTemplates.get("enchantment");
        extrra = (JSONObject) codeTemplates.get("extra");
    }

    /**
     * A wrapper for an array list ot also give indent formatting
     */
    private class CodeBuffer {
        private List<String> code = new ArrayList<>();

        public void addLine(String line) {
            code.add(line);
        }

        public String getFormattedOutput() {
            return format();
        }

        private String format() {
            StringBuilder sb = new StringBuilder();
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

                sb.append(indents+ code.get(i));
            }
            return sb.toString();
        }
    }

    /**
     * Writes the java code that constructs the inventory table model in bukkit
     * @return
     */
    public String writeRepresentingJava(InventoryTableModel invModel){
        CodeBuffer buffer = new CodeBuffer();
        StringBuilder builder = new StringBuilder();

        builder.append("\n"+(String)comments.get("init comment"));
        builder.append("\n"+((String)extrra.get("class constructor"))
                .replace("$count$",mainMenu.getInventoryTableModel().getRowCount()*9+"")
                .replace("$name$",mainMenu.getInventoryTableModel().getInventoryName())
                .replace("$inv$","inventoryInstance")
                .replace("$classname$",mainMenu.getInventoryTableModel().getInventoryName().replace(" ","")+"Inventory")
        );
        builder.append("\n"+(String)extrra.get("init inv"));
        for(int slot = 0; slot < mainMenu.getInventoryTableModel().getRowCount()*9;slot++){
            if(mainMenu.getInventoryTableModel().getItemStackAt(slot%9,slot/9).getMaterial() != Material.AIR){
                addItemstackToInv(builder,slot);
            }
        }

        //Formatting
        String[] lines = builder.toString().split("\n");
        for(int i = 0; i < lines.length;i++)
            buffer.addLine(lines[i]);

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

    public static void main(String[] args) {
        //todo test for file reading
        CodeGenerator gen = CodeGenerator.getInstance();
        System.out.println("outut: " + gen.getJavaMemeberConstructor("uhh", "var"));

    }


    private static void addItemstackToInv(StringBuilder builder,int slot){
        ItemStack itemStack1 = mainMenu.getInventoryTableModel().getItemStackAt(slot%9,slot/9);

        String shouldHaveData = itemStack1.getMaterial().getDurability() == 0? "create no data" :"create with data";
        builder.append("\n"+((String)itemStack.get(shouldHaveData))
                .replace("$material$",itemStack1.getMaterial().getMaterialEnumName())
                .replace("$count$",itemStack1.getAmount()+"")
                .replace("$data$",itemStack1.getMaterial().getDurability()+"")
                .replace("$stack$","itemStack"+slot)
        );

        builder.append("\n"+((String)lore.get("lore buffer"))
                .replace("$id$",slot+"")
        );
        for(String lore_line : itemStack1.getLoreAsList())
            builder.append("\n"+((String)lore.get("add item to lore buffer"))
                    .replace("$id$",slot+"")
                    .replace("$lore_line$",lore_line)
            );
        builder.append("\n"+((String)lore.get("ench buffer"))
                .replace("$id$",slot+"")
        );
        for(Enchantment ench: itemStack1.getEnchantments())
            builder.append("\n"+((String)lore.get("add item to ench buffer"))
                    .replace("$id$",slot+"")
                    .replace("$ench$",ench.getBukkitName())
                    .replace("$power$",ench.getPowerLavel()+"")
            );
        builder.append("\n"+((String)itemStack.get("call formatter"))
                .replace("$stack$","itemStack"+slot)
                .replace("$name$",itemStack1.getName())
                .replace("$loreBuffer$","loreBuffer"+slot)
                .replace("$enchantments$","enchBuffer"+slot)

        );
    }
}
