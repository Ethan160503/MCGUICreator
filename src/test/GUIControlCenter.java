import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ethan on 5/19/16.
 */
public class GUIControlCenter implements Listener {
    private JavaPlugin plugin;

    public GUIControlCenter(JavaPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    /*public Inventory getInventory() {
        //Inventory inv = Bukkit.createInventory(null, $size$, "$name$");

        return
    }*/

    public Inventory generateInventory() {
        Inventory inv = Bukkit.createInventory(null, 9, "This is the title");

        List<String> loreBuffer = new ArrayList<String>();
        ItemStack stack = new ItemStack(Material.OBSIDIAN);

        inv.setItem(0, stack);
        return inv;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        Inventory inv = e.getInventory();
        String invName = inv.getName();
        boolean didMatchInv = false;

        if (invName.equals("This is the title")) {
            didMatchInv = true;
            ItemStack clicked = e.getCurrentItem();
            if (clicked != null) {
                /*
            there are a few things here. I will only use one inventory, but the same will be applied.
            Check each stack name that has a secondary listener, find it's name, an set a if for it.
            Then fill the if with the template.
             */
                String name = clicked.getItemMeta().getDisplayName();
                // items will be identified by their name (At least for now)

                if (name.equals("Obsidian")) {
                    //TODO auto-generated listener stub. Changes will be needed.
                    /*
                    All templates:
                    GIVE_ITEM: player.getInventory().addItem();
                    OPEN_OTHER_INVENTORY: Bukkit.getScheduler().runTaskLater(plugin, () -> player.openInventory(), 2);
                    SEND_MESSAGE: player.sendMessage();
                     */

                    // if the click of this item closes the inventory
                    Bukkit.getScheduler().runTask(plugin, () -> player.closeInventory());
                }
            }
        }
        if (didMatchInv)
            e.setCancelled(true);
    }

    public Inventory cloneInventory(Inventory inv) {
        Inventory clone = Bukkit.createInventory(inv.getHolder(), inv.getSize(), inv.getName());
        clone.setContents(inv.getContents().clone());
        return clone;
    }
}
