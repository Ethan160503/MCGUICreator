package com.techno_wizard.mcguicreator.management;

import java.awt.*;
import java.awt.event.*;

import static com.techno_wizard.mcguicreator.management.TextEditorManager.ColorEditor.*;

/**
 * Created by Zombie_Striker on 4/17/2016.
 */
public class TextEditorManager {

    public enum ColorEditor {
        ITEMSTACK_LORE,
        ITEMSTACK_NAME,
        ITEMSTACK_NOTES,
        INVENTORY_NAME
    }

    private ColorEditor selectedEditor = null;

    private EditorManager em;

    public TextEditorManager(EditorManager e){
        this.em = e;
        initListener();
    }

    //TODO: Figure out an easy way to know if the person clicked another button/box. We do not want them
    //being able to add chatcolors to the Itemstack's displayname while in the enchantment menu
    private void initListener() {

        em.getItemStackLoreEditor().addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                setSelectedEditor(ITEMSTACK_LORE);
            }
        });

        em.getInventoryNameEditor().addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                setSelectedEditor(INVENTORY_NAME);
            }
        });

        em.getItemStackNameEditor().addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                setSelectedEditor(ITEMSTACK_NAME);
            }
        });

        em.getNotes().addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                setSelectedEditor(ITEMSTACK_NOTES);
            }
        });
    }

    public void setSelectedEditor(ColorEditor colorEditor) {
        this.selectedEditor = colorEditor;
    }

    public ColorEditor getSelectedEditor() {
        return this.selectedEditor;
    }

    /**
     * Adds the ChatColor 'colorCode' to the selected editor
     * @param colorCode
     */
    public void editSelectedEditor(String colorCode){
        // check is nothing is selected
        if(selectedEditor != null) {
            switch (selectedEditor) {
                case ITEMSTACK_LORE:
                    em.getItemStackLoreEditor().setText(em.getItemStackLoreEditor().getText() + colorCode);
                    break;
                case ITEMSTACK_NAME:
                    em.getItemStackNameEditor().setText(em.getItemStackNameEditor().getText() + colorCode);
                    break;
                case INVENTORY_NAME:
                    em.getInventoryNameEditor().setText(em.getInventoryNameEditor().getText() + colorCode);
                    break;
                case ITEMSTACK_NOTES:
                    em.getNotes().setText(em.getNotes().getText() + colorCode);
                    break;
            }
        }
    }


}
