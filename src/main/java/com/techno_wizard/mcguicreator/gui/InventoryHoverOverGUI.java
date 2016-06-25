package com.techno_wizard.mcguicreator.gui;

import com.techno_wizard.mcguicreator.gui.inventory.Enchantment;
import com.techno_wizard.mcguicreator.gui.inventory.ItemStack;
import com.techno_wizard.mcguicreator.gui.inventory.ItemUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * Created by Zombie_Striker on 4/18/2016.
 */
public class InventoryHoverOverGUI {

    private JPanel panel;
    private InventoryTableModel model;

    //This represents the top left corner of the gui
    private int x;
    private int y;
    private int xP;
    private int yP;

    private boolean shouldDraw = true;

    private Font font;

    public InventoryHoverOverGUI(JTable j, JPanel panel, InventoryTableModel model) {
        JTable inv = j;
        this.panel = panel;
        this.model = model;
        // this.font = ItemUtil.getMCFont(true);

        MouseMotionListener mouseListener = getMouseListener();
        inv.addMouseMotionListener(mouseListener);

        MouseListener exitListener = new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                panel.updateUI();
                shouldDraw = false;
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                shouldDraw = true;
            }
        };
        inv.addMouseListener(exitListener);

        createMenu();

    }

    public void createMenu() {
        Runnable r = () -> {
            while (true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (shouldDraw) {
                    Graphics g = panel.getGraphics();
                    if (g != null) {
                        //Create the background
                        if (ItemUtil.getColumnAt(xP, 9) > 8 || ItemUtil.getRowAt(yP, 3) > 2)
                            continue;

                        //Get the itemstack
                        ItemStack is = model.getItemStackAt(ItemUtil.getColumnAt(xP, 9), ItemUtil.getRowAt(yP, 3));
                        int yOffset = 0;
                        if (is == null)
                            continue;
                        List<String> lore = is.getLoreAsList();
                        List<Enchantment> ench = is.getEnchantments();

                        int width = 80;
                        width = widthFinder(is.getName(), width);
                        for (String loreL : lore)
                            width = widthFinder(loreL, width);
                        for (Enchantment e : ench)
                            width = widthFinder(e.getDisplay(), width);

                        //Set the font
                        if (font == null)
                            font = ItemUtil.getMCFont(g.getFont());
                        g.setFont(font);

                        //Draw it
                        g.setColor(Color.black);
                        g.fillRect(xP, yP, width, 16);
                        addChatColor(g, is.getName(), 16);
                        //Make sure there is a space between the name and lore
                        yOffset++;
                        g.setColor(Color.black);
                        g.fillRect(xP, yP + (yOffset * 16), width, 16);

                        for (int loreLine = 0; loreLine < lore.size(); loreLine++) {
                            g.setColor(Color.black);
                            yOffset++;
                            g.fillRect(xP, yP + (yOffset * 16), width, 16);
                            addChatColor(g, lore.get(loreLine), 16 + (yOffset * 16));
                        }
                        for (int enchLine = 0; enchLine < ench.size(); enchLine++) {
                            g.setColor(Color.black);
                            yOffset++;
                            g.fillRect(xP, yP + (yOffset * 16), width, 16);
                            addChatColor(g, ench.get(enchLine).getDisplay(), 16 + (yOffset * 16));
                        }

                        g.setColor(new Color(102, 0, 204));
                        g.drawRect(xP + 1, yP + 1, width - 2, 16 + (yOffset * 16) - 2);
                        g.dispose();
                        try {
                            panel.update(g);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };

        Thread thread = new Thread(r);
        thread.setDaemon(true);
        thread.start();
    }

    public MouseMotionListener getMouseListener() {
        return new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);

                //Make sure the person at least moved the mouse. Don't want to create unnecessary lag.
                int tempx = ItemUtil.getColumnAt(e.getX(), model.getColumnCount());
                int tempy = ItemUtil.getRowAt(e.getY(), model.getRowCount());
                if (tempx != x || tempy != y) {
                    x = tempx;
                    y = tempy;
                    xP = ItemUtil.getMaxXAt(x, model.getColumnCount()) + (600 / model.getColumnCount()) - 1;
                    yP = ItemUtil.getMaxYAt(y, model.getRowCount());

                    panel.updateUI();
                }
            }
        };
    }

    public void addChatColor(Graphics g, String message, int yoffset) {
        String[] text = message.split(ChatColor.COLOR_CHAR + "");
        int offset = 0;
        for (int i = 0; i < text.length; i++) {
            if (i == 0) {
                g.setColor(hex2Rgb(ChatColor.RESET.getHex()));
                text[i] = "X" + text[i];
            } else {
                ChatColor color = ChatColor.getChatColor(text[i].charAt(0));
                if (!color.isAFormat())
                    g.setColor(hex2Rgb(color.getHex()));
            }
            if (text[i].length() >= 2) {
                g.drawString(text[i].substring(1), xP + 4 + offset, yP - 2 + yoffset);
                for (int charAt = 1; charAt < text[i].length(); charAt++)
                    offset += ItemUtil.getCharSize(text[i].charAt(charAt));
            }
        }
    }

    //TODO: Possibly move this to the ChatColor class
    public static Color hex2Rgb(String colorStr) {
        return new Color(
                Integer.valueOf(colorStr.substring(0, 2), 16),
                Integer.valueOf(colorStr.substring(2, 4), 16),
                Integer.valueOf(colorStr.substring(4, 6), 16));
    }

    private int widthFinder(String s, int base) {
        int offset = 0;
        for (int charAt = 1; charAt < s.length(); charAt++)
            offset += ItemUtil.getCharSize(s.charAt(charAt));
        return offset > base ? offset : base;
    }
}
