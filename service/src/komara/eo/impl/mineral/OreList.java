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

import java.util.*;

/**
 * Created by moonko on 12/15/13.
 */
class OreList {
    private static final OreList instance = new OreList();

    private final List<Ore> ores = new LinkedList<Ore>();

    private OreList() {
        ores.add(new OreImpl("Veldspar", 333, .1, Sovereignty.empires(), -1.0, 1.0,
                new OreMineralData(Mineral.Tritanium, 30.03, 1000)));
        ores.add(new OreImpl("Scordite", 333, 0.15,Sovereignty.empires(), -1.0, 1.0,
                new OreMineralData(Mineral.Tritanium, 16.67, 833),
                new OreMineralData(Mineral.Pyerite, 8.32, 416)));
        ores.add(new OreImpl("Pyroxeres", 333, .3, EnumSet.of(Sovereignty.Caldari, Sovereignty.Amarr), -1.0, .8,
                new OreMineralData(Mineral.Tritanium, 8.44, 844),
                new OreMineralData(Mineral.Pyerite, 0.59, 59),
                new OreMineralData(Mineral.Mexallon, 1.2, 120),
                new OreMineralData(Mineral.Nocxium, .11, 11)));
        ores.add(new OreImpl("Plagioclase", 333, .35, EnumSet.of(Sovereignty.Gallente, Sovereignty.Minmatar), -1.0, 0.8,
                new OreMineralData(Mineral.Tritanium, 2.19, 256),
                new OreMineralData(Mineral.Pyerite, 4.39, 512),
                new OreMineralData(Mineral.Mexallon, 2.19, 256)));
        ores.add(new OreImpl("Plagioclase", 333, .35, EnumSet.of(Sovereignty.Caldari), -1.0, 0.7,
                new OreMineralData(Mineral.Tritanium, 2.19, 256),
                new OreMineralData(Mineral.Pyerite, 4.39, 512),
                new OreMineralData(Mineral.Mexallon, 2.19, 256)));
        ores.add(new OreImpl("Kernite", 400, 1.2,  EnumSet.of(Sovereignty.Amarr), -1.0, 0.7,
                new OreMineralData(Mineral.Tritanium, 0.8, 386),
                new OreMineralData(Mineral.Mexallon, 1.61, 773),
                new OreMineralData(Mineral.Isogen, 0.8, 386)));
        ores.add(new OreImpl("Kernite", 400, 1.2,  EnumSet.of(Sovereignty.Caldari, Sovereignty.Minmatar), -1.0, 0.6,
                new OreMineralData(Mineral.Tritanium, 0.8, 386),
                new OreMineralData(Mineral.Mexallon, 1.61, 773),
                new OreMineralData(Mineral.Isogen, 0.8, 386)));
        ores.add(new OreImpl("Omber", 500, .6, EnumSet.of(Sovereignty.Gallente, Sovereignty.Minmatar), -1.0, 0.6,
                new OreMineralData(Mineral.Tritanium, 1.02, 307),
                new OreMineralData(Mineral.Pyerite, .41, 123),
                new OreMineralData(Mineral.Isogen, 1.02, 307)));
        ores.add(new OreImpl("Jaspet", 500, 2.0, EnumSet.of(Sovereignty.Amarr, Sovereignty.Gallente), -1.0, 0.4,
                new OreMineralData(Mineral.Tritanium, 0.259, 259),
                new OreMineralData(Mineral.Pyerite, 0.259, 259),
                new OreMineralData(Mineral.Mexallon, 0.518, 518),
                new OreMineralData(Mineral.Nocxium, .259, 259),
                new OreMineralData(Mineral.Zydrine, .008, 8)));
        ores.add(new OreImpl("Hemorphite", 500, 3.0, EnumSet.of(Sovereignty.Amarr, Sovereignty.Gallente), -1.0, 0.2,
                new OreMineralData(Mineral.Tritanium, .141, 212),
                new OreMineralData(Mineral.Isogen, .141, 212),
                new OreMineralData(Mineral.Nocxium, .282, 424),
                new OreMineralData(Mineral.Zydrine, .018, 28)));

        ores.add(new OreImpl("Hedbergite", 500, 3.0, EnumSet.of(Sovereignty.Caldari, Sovereignty.Minmatar), -1.0, 0.2,
                new OreMineralData(Mineral.Isogen, .472, 708),
                new OreMineralData(Mineral.Nocxium, .236, 354),
                new OreMineralData(Mineral.Zydrine, .021, 32)));

    }

    static OreList get() {
        return instance;
    }

    Collection<Ore> getOres(EnumSet<Sovereignty> sovereignties, double securityLevel) {
        Collection<Ore> result = new HashSet<Ore>();
        for (Ore ore : ores) {
            if (sovereigntiesMatch(sovereignties, ore.getSovereignty()) && ore.getMaxSecLevel() >= securityLevel) {
                result.add(ore);
            }
        }
        return Collections.unmodifiableCollection(result);
    }

    Collection<Ore> getOres(double securityLevel) {
        Collection<Ore> result = new HashSet<Ore>();
        for (Ore ore : ores) {
            if (ore.getMaxSecLevel() <= securityLevel) {
                ores.add(ore);
            }
        }
        return Collections.unmodifiableCollection(result);
    }

    private boolean sovereigntiesMatch(EnumSet<Sovereignty> required, EnumSet<Sovereignty> available) {
        EnumSet<Sovereignty> toCheck = EnumSet.copyOf(required);
        Iterator<Sovereignty> iterator = toCheck.iterator();
        while (iterator.hasNext()) {
            Sovereignty next = iterator.next();
            if (available.contains(next)) {
                return true;
            }
        }
        return false;
    }

    Ore getOreByName(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        } else {
            for (Ore ore : ores) {
                if (name.equals(ore.getName())) {
                    return ore;
                }
            }
            return  null;
        }
    }
}
