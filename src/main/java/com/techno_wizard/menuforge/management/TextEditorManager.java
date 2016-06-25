package com.techno_wizard.menuforge.management;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import static com.techno_wizard.menuforge.management.TextEditorManager.ColorEditor.*;

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

    public TextEditorManager(EditorManager e) {
        this.em = e;
        initListener();
    }

    //todo add tab change listener to deactivate focus. Cannot use lose focus because the buttons fire it
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

    /**
     * Adds the ChatColor 'colorCode' to the selected editor
     *
     * @param colorCode
     */
    public void editSelectedEditor(String colorCode) {
        // check is nothing is selected
        if (selectedEditor != null) {

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
