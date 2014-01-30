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

import komara.eo.Sovereignty;
import komara.eo.mineral.Mineral;
import komara.eo.mineral.Ore;
import komara.eo.mineral.OreMineral;
import komara.eo.mineral.ReprocessingSolution;
import org.junit.Assert;
import org.junit.Test;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;

/**
 * Created by Rastislav Komara on 1/9/14.
 */
public class ReprocessingPlantImplTest {
    @Test
    public void testSetSovereignty() throws Exception {
        ReprocessingPlantImpl rp = new ReprocessingPlantImpl();
        rp.setSovereignty(Sovereignty.empires());
        Assert.assertEquals(Sovereignty.empires(), rp.getSovereignty());
    }

    @Test
    public void testSetSecurityStatus() throws Exception {
        double security = 0.314;
        ReprocessingPlantImpl rp = new ReprocessingPlantImpl();
        rp.setSecurityStatus(security);
        Assert.assertEquals(security, rp.getSecurityStatus(), 0.001);
        try {
            rp.setSecurityStatus(5.0);
            Assert.fail("Exception expected but not thrown");
        } catch (IllegalArgumentException e) {
            ;
        }

        try {
            rp.setSecurityStatus(-1.1);
            Assert.fail("Exception expected but not thrown");
        } catch (IllegalArgumentException e) {
            ;
        }
    }

    @Test
    public void testSetUserStatistics() throws Exception {
        ReprocessingPlantImpl rp = new ReprocessingPlantImpl();
        rp.setUserStatistics(1.00, 0.05);
        Assert.assertEquals(rp.getYield(), 1.00, 0.001);
        Assert.assertEquals(rp.getTax(), 0.05, 0.001);

        try {
            rp.setUserStatistics(2.0, 2.0);
            Assert.fail("Exception expected but not thrown");
        } catch (IllegalArgumentException e) {

        }

        try {
            rp.setUserStatistics(0.95, 2.0);
            Assert.fail("Exception expected but not thrown");
        } catch (IllegalArgumentException e) {

        }

        try {
            rp.setUserStatistics(.5, .8);
            Assert.fail("Exception expected but not thrown");
        } catch (IllegalArgumentException e) {

        }

    }

    @Test
    public void testGetMinimumOreVolume() throws Exception {
        System.out.println("--- testGetMinimumOreVolume()");
        ReprocessingPlantImpl rp = new ReprocessingPlantImpl();
        rp.setSecurityStatus(1.0);
        rp.setSovereignty(EnumSet.of(Sovereignty.Caldari));
        rp.setUserStatistics(1.0, 0.00);
        long[] minerals = {1000, 0, 0, 0, 0, 0, 0, 0};
        ReprocessingSolution solution = rp.getMinimumOreVolume(minerals);
        for (Map.Entry<Ore, Long> entry : solution.getOreList().entrySet()) {
            System.out.printf("%s - %d m^3%n", entry.getKey().getName(), entry.getValue());
        }
        Assert.assertNotNull("Solution was not found", solution);
        Assert.assertTrue(solution.getOres().contains(OreList.get().getOreByName("Veldspar")));
    }

    @Test
    public void testGetMinimumOreVolumeScordite() throws Exception {
        System.out.println("--- testGetMinimumOreVolumeScordite()");
        ReprocessingPlantImpl rp = new ReprocessingPlantImpl();
        rp.setSecurityStatus(1.0);
        rp.setSovereignty(EnumSet.of(Sovereignty.Caldari));
        rp.setUserStatistics(1.0, 0.00);
        long[] minerals = {833, 416, 0, 0, 0, 0, 0, 0};
        ReprocessingSolution solution = rp.getMinimumOreVolume(minerals);
        Assert.assertNotNull("Solution was not found", solution);
        for (Map.Entry<Ore, Long> entry : solution.getOreList().entrySet()) {
            System.out.printf("%s - %d m^3%n", entry.getKey().getName(), entry.getValue());
        }
        Assert.assertTrue(solution.getOres().contains(OreList.get().getOreByName("Scordite")));
    }

    @Test
    public void testGetMinimumOreVolumeOmber() throws Exception {
        System.out.println("--- testGetMinimumOreVolumeOmber()");
        ReprocessingPlantImpl rp = new ReprocessingPlantImpl();
        rp.setSecurityStatus(.5);
        rp.setSovereignty(EnumSet.of(Sovereignty.Gallente));
        rp.setUserStatistics(1.0, 0.00);
        long[] minerals = {307, 123, 0, 307, 0, 0, 0, 0};
        ReprocessingSolution solution = rp.getMinimumOreVolume(minerals);
        Assert.assertNotNull("Solution was not found", solution);
        for (Map.Entry<Ore, Long> entry : solution.getOreList().entrySet()) {
            System.out.printf("%s - %d m^3 %n", entry.getKey().getName(), entry.getValue());
        }
        Assert.assertTrue(solution.getOres().contains(OreList.get().getOreByName("Omber")));
    }

    @Test
    public void testGetMinimumOreVolumeMultiple() throws Exception {
        System.out.println("--- testGetMinimumOreVolumeMultiple()");
        ReprocessingPlantImpl rp = new ReprocessingPlantImpl();
        rp.setSecurityStatus(.5);
        rp.setSovereignty(Sovereignty.empires());
        rp.setUserStatistics(1.0, 0.00);
        long[] minerals = {10000, 5000, 300, 1000, 80, 0, 0, 0};
        ReprocessingSolution solution = rp.getMinimumOreVolume(minerals);
        Assert.assertNotNull("Solution was not found", solution);
        for (Map.Entry<Ore, Long> entry : solution.getOreList().entrySet()) {
            System.out.printf("%s - %d m^3%n", entry.getKey().getName(), entry.getValue());
        }
        Assert.assertTrue(solution.getOres().contains(OreList.get().getOreByName("Omber")));
        Assert.assertTrue(solution.getOres().contains(OreList.get().getOreByName("Pyroxeres")));
        Assert.assertTrue(solution.getOres().contains(OreList.get().getOreByName("Scordite")));
    }

    @Test
    public void testGetMinimumOreVolumeHugeMultiple() throws Exception {
        System.out.println("--- testGetMinimumOreVolumeMultiple()");
        ReprocessingPlantImpl rp = new ReprocessingPlantImpl();
        rp.setSecurityStatus(.4);
        rp.setSovereignty(EnumSet.of(Sovereignty.Gallente));
        double yield = .974;
        double tax = 0.0221;
        rp.setUserStatistics(yield, tax);
        // Tritanium, Pyerite, Mexallon, Isogen, Nocxium, Megacyte, Zydrine, Morphite
        long[] minerals = {10966360, 5003300, 479550, 91090, 0, 0, 0, 0};
        ReprocessingSolution solution = rp.getMinimumOreVolume(minerals);
        Assert.assertNotNull("Solution was not found", solution);
        long[] extractedMinerals = new long[minerals.length];
        Arrays.fill(extractedMinerals, 0);
        for (Map.Entry<Ore, Long> entry : solution.getOreList().entrySet()) {
            System.out.printf("%s - %dm^3 (%s) %n", entry.getKey().getName(), entry.getValue(), Math.ceil(entry.getValue() / 27500));
            List<OreMineral> result = entry.getKey().getBatchResult();
            long oreVolume = entry.getValue();
            long batches = (long) (oreVolume / entry.getKey().getVolume()) / entry.getKey().getUnitsToRefine();
            for (OreMineral oreMineral : result) {
                double unitsPerBatch = oreMineral.getUnitsPerBatch();
                unitsPerBatch = unitsPerBatch * yield;
                unitsPerBatch = unitsPerBatch - unitsPerBatch * tax;
                extractedMinerals[oreMineral.getMineral().ordinal()] += unitsPerBatch * batches;
            }
        }
        NumberFormat instance = NumberFormat.getInstance();
        Mineral[] values = Mineral.values();
        for (int i = 0; i < extractedMinerals.length; i++) {
            System.out.printf("Mineral %s: %s -> %s%n", values[i].name(), instance.format(extractedMinerals[i]), instance.format(minerals[i]));
            Assert.assertTrue("Mineral " + i, extractedMinerals[i] >= minerals[i]);
        }
    }

}
