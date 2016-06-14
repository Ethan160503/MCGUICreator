package com.techno_wizard.mcguicreator.gui.events;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zombie_Striker on 4/13/2016.
 */
public enum AutoGenerateType {
    NONE(generateEmpty(), "Do not generate any"),
    EMPTY(AutoGenerateType.generateEmpty(), "Empty (Default)"),
    GIVE_ITEM(AutoGenerateType.generateGiveItem(), "Give Itemstack"),
    SEND_MESSAGE(AutoGenerateType.generateSendMessage(), "Send Message"),
    OPEN_OTHER_INVENTORY(AutoGenerateType.generateOpenOtherInventory(), "Open an other inventory");

    private List<String> code;
    private String name;

    AutoGenerateType(List<String> code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public List<String> getCode() {
        return code;
    }

    public String getCodeAsString() {
        StringBuilder sb = new StringBuilder();
        for (String s : code)
            sb.append(s + "\n");
        return sb.toString();
    }

    public static AutoGenerateType getTypeByName(String name) {
        for (AutoGenerateType a : AutoGenerateType.values()) {
            if (a.getName().equals(name))
                return a;
        }
        return null;

    }


    private static List<String> generateEmpty() {
        ArrayList<String> code = new ArrayList<>();
        code.add("");
        return code;
    }

    private static List<String> generateGiveItem() {
        ArrayList<String> code = new ArrayList<>();
        code.add("((Player)e.getWhoClicked()).getInventory().addItem(/*INSERT ITEMSTACK HERE*/);");
        return code;
    }

    private static List<String> generateSendMessage() {
        ArrayList<String> code = new ArrayList<>();
        code.add("e.getWhoClicked().sendMessage(\"Testing\");");
        return code;
    }

    private static List<String> generateOpenOtherInventory() {
        ArrayList<String> code = new ArrayList<>();
        code.add("e.getWhoClicked().closeInventory();");
        code.add("e.getWhoClicked().openInventory(/*INSERT INVENTORY HERE*/)");
        return code;
    }

}
