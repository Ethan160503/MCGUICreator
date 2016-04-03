package com.techno_wizard.mcguicreator.gui;

public enum ChatColor {
    BLACK('0'),
    DARK_BLUE('1'),
    DARK_GREEN('2'),
    DARK_AQUA('3'),
    DARK_RED('4'),
    DARK_PURPLE('5'),
    GOLD('6'),
    GRAY('7'),
    DARK_GRAY('8'),
    BLUE('9'),
    GREEN('a'),
    AQUA('b'),
    RED('c'),
    LIGHT_PURPLE('d'),
    YELLOW('e'),
    WHITE('f'),
    MAGIC('k'),
    BOLD('l'),
    STRIKETHROUGH('m'),
    UNDERLINE('n'),
    ITALIC('o'),
    RESET('r');

    public static final char COLOR_CHAR = '§';
    private final char code;
    private final String toString;

    private ChatColor(char code) {
        this.code = code;
        this.toString = new String(new char[]{'§', code});
    }

    public char getChar() {
        return this.code;
    }

    public String getColorCode() {
        return COLOR_CHAR + "" + this.code;
    }

    public String toString() {
        return this.toString;
    }
}