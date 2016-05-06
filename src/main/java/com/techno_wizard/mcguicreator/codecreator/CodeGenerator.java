package com.techno_wizard.mcguicreator.codecreator;

import com.techno_wizard.mcguicreator.gui.InventoryTableModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A generator that takes an inventory and outputs code to make it in MC
 */
public class CodeGenerator {

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
    public static String writeRepresentingJava(InventoryTableModel invModel) {

        //Adding this so the program can run
        return "";
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
    private static String getJavaMemeberConstructor(Object wrapperValue) {
        if(wrapperValue.getClass().isAssignableFrom(Integer.class)) {
            Integer integer = (Integer) wrapperValue;
            //todo add java constructors to code generation template file
        }
        //Adding this so the program can run
        return "";
    }
}
