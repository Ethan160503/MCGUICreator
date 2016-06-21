import com.techno_wizard.mcguicreator.gui.ChatColor;
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
}

