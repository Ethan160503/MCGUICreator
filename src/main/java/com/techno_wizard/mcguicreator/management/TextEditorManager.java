package com.techno_wizard.mcguicreator.management;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Zombie_Striker on 4/17/2016.
 */
public class TextEditorManager {

    public static final int ITEMSTACK_LORE_EDITOR = 0;
    public static final int ITEMSTACK_NAME_EDITOR = 1;
    public static final int INVENTORY_NAME_EDITOR = 2;
    public static final int ITEMSTACK_NOTES = 3;

    private int selectedEditor = 0;

    private EditorManager em;

    public TextEditorManager(EditorManager e){
            this.em = e;
        initListener();
    }

    //TODO: Figure out an easy way to know if the person clicked another button/box. We do not want them
    //being able to add chatcolors to the Itemstack's displayname while in the enchantment menu
    private void initListener(){
        MouseListener loreListener = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {setSelectedEditor(ITEMSTACK_LORE_EDITOR);}        };
        em.getItemStackLoreEditor().addMouseListener(loreListener);

        MouseListener itemstackNameListener= new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {setSelectedEditor(ITEMSTACK_NAME_EDITOR);}        };
        em.getItemStackNameEditor().addMouseListener(itemstackNameListener);

        MouseListener inventoryNameListener = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {setSelectedEditor(INVENTORY_NAME_EDITOR);}        };
        em.getItemStackLoreEditor().addMouseListener(inventoryNameListener);

        MouseListener notesListener= new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {setSelectedEditor(ITEMSTACK_NOTES);}        };
        em.getNotes().addMouseListener(notesListener);
    }

    public void setSelectedEditor(int selectedEditor){this.selectedEditor = selectedEditor;}
    public int getSelectedEditor(){return this.selectedEditor;}

    /**
     * Adds the ChatColor 'colorCode' to the selected editor
     * @param colorCode
     */
    public void editSelectedEditor(String colorCode){
        switch (selectedEditor){
            case ITEMSTACK_LORE_EDITOR:
                em.getItemStackLoreEditor().setText(em.getItemStackLoreEditor().getText()+colorCode);
                break;
            case ITEMSTACK_NAME_EDITOR:
                em.getItemStackNameEditor().setText(em.getItemStackNameEditor().getText()+colorCode);
                break;
            case INVENTORY_NAME_EDITOR:
                em.getInventoryNameEditor().setText(em.getInventoryNameEditor().getText()+colorCode);
                break;
            case ITEMSTACK_NOTES:
                em.getNotes().setText(em.getNotes().getText()+colorCode);
                break;
        }
    }
}
