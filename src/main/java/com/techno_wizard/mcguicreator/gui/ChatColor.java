package com.techno_wizard.mcguicreator.gui;

public enum ChatColor {
    BLACK('0', "000000"),
    DARK_BLUE('1', "0000AA"),
    DARK_GREEN('2', "00AA00"),
    DARK_AQUA('3', "00AAAA"),
    DARK_RED('4', "AA0000"),
    DARK_PURPLE('5', "AA00AA"),
    GOLD('6', "FFAA00"),
    GRAY('7', "AAAAAA"),
    DARK_GRAY('8', "555555"),
    BLUE('9', "5555FF"),
    GREEN('a', "55FF55"),
    AQUA('b', "55FFFF"),
    RED('c', "FF5555"),
    LIGHT_PURPLE('d', "FF55FF"),
    YELLOW('e', "FFFF55"),
    WHITE('f', "FFFFFF"),
    MAGIC('k', null, "[Magic]"),
    BOLD('l', "<b>"),
    STRIKETHROUGH('m', null, "<s>"),
    UNDERLINE('n', null, "<u>"),
    ITALIC('o', null, "<i>"),
    RESET('r', "FFFFFF");

    public static final char COLOR_CHAR = '§';
    private final String HEX;
    private final String HTML_OPEN_TAG;
    private final String HTML_CLOSE_TAG;
    private final char code;
    private final String toString;

    ChatColor(char code, String hex) {
        this(code, hex, null);
    }

    ChatColor(char code, String hex, String htmlOpen) {
        this.code = code;
        this.HEX = hex;
        if(htmlOpen != null) {
            HTML_OPEN_TAG = htmlOpen;
            HTML_CLOSE_TAG = new StringBuilder(htmlOpen).insert(1, "/").toString();
        } else {
            HTML_CLOSE_TAG = null;
            HTML_OPEN_TAG = null;
        }
        this.toString = new String(new char[]{'§', code});
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
     * @return hex color code
     */
    public String getHex() {
        return HEX;
    }

    /**
     * gets the html open tag. may be null
     * @return the html open tag
     */
    public String getHTMLOpenTag() {
        return HTML_OPEN_TAG;
    }

    /**
     * Gets the html close tag. Return may be null
     * @return html close tag. may be null
     */
    public String getHTMLCloseTag() {
        return HTML_CLOSE_TAG;
    }

    public String toString() {
        return this.toString;
    }
}