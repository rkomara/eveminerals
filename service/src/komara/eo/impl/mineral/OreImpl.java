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

import java.util.*;

/**
 * Created by moonko on 12/15/13.
 */
class OreImpl implements Ore {
    private List<OreMineralData> minerals = Collections.emptyList();
    private String name;
    private int unitsToRefine;
    private double volume;
    private EnumSet<Sovereignty> sovereignties;
    double minSecLevel;
    double maxSecLevel;

    OreImpl(String name, int unitsToRefine, double volume, EnumSet<Sovereignty> sovereignties, double minSecLevel, double maxSecLevel, OreMineralData... datas) {
        this.name = name;
        this.unitsToRefine = unitsToRefine;
        this.volume = volume;
        this.sovereignties = sovereignties;
        this.minSecLevel = minSecLevel;
        this.maxSecLevel = maxSecLevel;
        this.minerals = Arrays.asList(datas);

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getUnitsToRefine() {
        return unitsToRefine;
    }

    @Override
    public double getVolume() {
        return volume;
    }

    @Override
    public List<OreMineral> getBatchResult() {
        List<OreMineral> result = new ArrayList<OreMineral>();
        for (OreMineralData mineral : minerals) {
            result.add(mineral.toOreMineral(this));
        }
        return result;
    }

    @Override
    public OreMineral getExtraction(Mineral mineral) {
        for (OreMineralData mineralData : minerals) {
            if (mineralData.getMineral() == mineral) {
                return mineralData.toOreMineral(this);
            }
        }
        return null;
    }

    @Override
    public boolean hasMineral(Mineral mineral) {
        for (OreMineralData oreMineral : minerals) {
            if (oreMineral.getMineral() == mineral) {
                return true;
            }
        }
        return false;
    }

    @Override
    public EnumSet<Sovereignty> getSovereignty() {
        return EnumSet.copyOf(sovereignties);
    }

    @Override
    public double getMinSecLevel() {
        return minSecLevel;
    }

    @Override
    public double getMaxSecLevel() {
        return maxSecLevel;
    }

    boolean isAvailable(Sovereignty sovereignty, double securityLevel) {
        if (sovereignties.contains(sovereignty)) {
            return securityLevel >= minSecLevel && securityLevel <= maxSecLevel;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OreImpl ore = (OreImpl) o;

        if (unitsToRefine != ore.unitsToRefine) return false;
        if (Double.compare(ore.volume, volume) != 0) return false;
        if (!minerals.equals(ore.minerals)) return false;
        if (!name.equals(ore.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = minerals.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + unitsToRefine;
        temp = Double.doubleToLongBits(volume);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "OreImpl{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public int compareTo(Ore o) {
        return name.compareTo(o.getName());
    }
}
