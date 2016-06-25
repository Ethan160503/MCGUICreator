package com.techno_wizard.mcguicreator.gui;

import javax.swing.text.html.HTML;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ChatColor {
    BLACK("Black", '0', "000000"),
    DARK_BLUE("Dark Blue", '1', "0000AA"),
    DARK_GREEN("Dark Green", '2', "00AA00"),
    DARK_AQUA("Cyan", '3', "00AAAA"),
    DARK_RED("Dark Red", '4', "AA0000"),
    DARK_PURPLE("Dark Purple", '5', "AA00AA"),
    GOLD("Gold", '6', "FFAA00"),
    GRAY("Gray", '7', "AAAAAA"),
    DARK_GRAY("Dark Gray", '8', "555555"),
    BLUE("Blue", '9', "5555FF"),
    GREEN("Green", 'a', "55FF55"),
    AQUA("Aqua", 'b', "55FFFF"),
    RED("Red", 'c', "FF5555"),
    LIGHT_PURPLE("Light Purple", 'd', "FF55FF"),
    YELLOW("Yellow", 'e', "FFFF55"),
    WHITE("White", 'f', "FFFFFF"),
    MAGIC("Magic", 'k', null, "[Magic]"),
    BOLD("Bold", 'l', null, "<b>"),
    STRIKETHROUGH("StrikeThrough", 'm', null, "<s>"),
    UNDERLINE("Underline", 'n', null, "<u>"),
    ITALIC("Italic", 'o', null, "<i>"),
    RESET("Reset", 'r', "FFFFFF");

    public static final char COLOR_CHAR = '§';
    private final String HEX;
    private final String HTML_OPEN_TAG;
    private final String HTML_CLOSE_TAG;
    private final char code;
    private final String name;
    private final String toString;
    private static Pattern colorCode = Pattern.compile("§.");

    ChatColor(String name, char code, String hex) {
        this(name, code, hex, "<font color=#" + hex + "\">");
    }

    ChatColor(String name, char code, String hex, String htmlOpen) {
        this.code = code;
        this.HEX = hex;
        this.name = name;
        if (htmlOpen != null) {
            if (name.equals("Reset")) {
                HTML_OPEN_TAG = "</i></b></u></s>" + htmlOpen;
            } else {
                HTML_OPEN_TAG = htmlOpen;
            }
            HTML_CLOSE_TAG = new StringBuilder(htmlOpen).insert(1, "/").toString();
        } else {
            HTML_CLOSE_TAG = null;
            HTML_OPEN_TAG = null;
        }
        this.toString = new String(new char[]{'§', code});
    }

    public static ChatColor getChatColor(char c) {
        for (ChatColor color : ChatColor.values())
            if (color.getChar() == c)
                return color;
        return ChatColor.RESET;
    }

    public String getName() {
        return this.name;
    }

    public char getChar() {
        return this.code;
    }

    public boolean isFormattingCode() {
        return HTML_CLOSE_TAG != null;
    }

    public String getColorCode() {
        return COLOR_CHAR + "" + this.code;
    }

    /**
     * gets code's HEX color code
     *
     * @return hex color code
     */
    public String getHex() {
        return HEX;
    }

    /**
     * gets the html open tag. may be null
     *
     * @return the html open tag
     */
    public String getHTMLOpenTag() {
        return HTML_OPEN_TAG;
    }

    /**
     * Gets the html close tag. Return may be null
     *
     * @return html close tag. may be null
     */
    public String getHTMLCloseTag() {
        return HTML_CLOSE_TAG;
    }

    public String toString() {
        return this.toString;
    }

    public static String getFormattedColorText(String coded) {
        String output = new String(coded);
        Matcher matcher = colorCode.matcher(coded);
        while (matcher.find()) {
            String sub = coded.substring(matcher.start(), matcher.end());

            for (ChatColor color: ChatColor.values()) {
                if (color.getChar() == sub.charAt(1)) {
                    output = output.replace(sub, color.getHTMLOpenTag());
                    break;
                }
            }
        }
        return output;
    }

    /**
     * Returns whether or not the ChatColor is a format, not a color.
     * For example, RED is a color, but BOLD is a format.
     * @return
     */
    public boolean isAFormat() {
        return HEX == null;
    }
}