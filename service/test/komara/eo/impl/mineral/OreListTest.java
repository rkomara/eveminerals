/*
 * EveMinerals
 * Copyright (C) 2014  Rastislav Komara
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

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
