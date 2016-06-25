import com.techno_wizard.menuforge.gui.ChatColor;
import com.techno_wizard.menuforge.gui.InventoryTableModel;
import com.techno_wizard.menuforge.gui.inventory.ItemStack;
import com.techno_wizard.menuforge.gui.inventory.Material;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by ethan on 6/20/16.
 */
public class EditorTests {

    @Test
    public void test() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        System.out.println(ChatColor.getFormattedColorText("§0"));
        System.out.println(ChatColor.getFormattedColorText("§b"));
        System.out.println(ChatColor.getFormattedColorText("§l"));
        assert ChatColor.getFormattedColorText("§1").length() > 2;
    }

    @Test
    public void codeOutput() {
        InventoryTableModel model = new InventoryTableModel();

        ItemStack stack = new ItemStack(Material.DYE_GREEN);
        ItemStack another = new ItemStack(Material.APPLE);
        another.setLore("Such lore. wow");
        another.setName("uuhh");

        stack.setLore("This is lore\nSuch lore meta");
        System.out.println("LOADING");

        model.setValueAt(stack, 0, 0);
        model.setValueAt(another, 0, 1);
        //System.out.println(getInstance().writeRepresentingJava(model));
    }
}

