package com.techno_wizard.mcguicreator.codecreator;

import com.techno_wizard.mcguicreator.gui.InventoryTableModel;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * A generator that takes an inventory and outputs code to make it in MC
 */
public class CodeGenerator {

    private static CodeGenerator instance;
    private JSONObject codeTemplates;

    public static CodeGenerator getInstance() {
        if (instance == null)
            instance = new CodeGenerator();

        return instance;
    }

    private CodeGenerator() {
        try {
            codeTemplates = (JSONObject) JSONValue.parse(new FileReader("/home/ethan/IdeaProjects/MCGUICreator/target/classes/code_templates.json"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Code templates file failed to load");
        }
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

        public String format() {
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
    public String writeRepresentingJava(InventoryTableModel invModel) {
        return null;
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
}
