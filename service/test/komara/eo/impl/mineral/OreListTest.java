package komara.eo.impl.mineral;

import junit.framework.Assert;
import komara.eo.Sovereignty;
import komara.eo.mineral.Ore;
import org.junit.Test;

import java.util.Collection;
import java.util.EnumSet;

/**
 * Created by Rastislav Komara on 1/9/14.
 */
public class OreListTest {
    @Test
    public void testGetOres() throws Exception {
        OreList list = OreList.get();
        Collection<Ore> ores = list.getOres(EnumSet.of(Sovereignty.Caldari), 1.0);
        Assert.assertNotNull(ores);
        Assert.assertFalse(ores.isEmpty());
        Assert.assertEquals(2, ores.size());
        Assert.assertTrue(ores.contains(list.getOreByName("Veldspar")));
        Assert.assertTrue(ores.contains(list.getOreByName("Scordite")));
    }
}
