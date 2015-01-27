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

import komara.eo.mineral.Mineral;
import komara.eo.mineral.Ore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by moonko on 12/22/13.
 */
class OreSolution {

    long[] minerals = new long[Mineral.values().length];
    Map<Ore, Long> volumes;
    Map<Ore, Double> units;

    OreSolution(long[] minerals) {
        System.arraycopy(minerals, 0, this.minerals, 0, minerals.length);
        volumes = new HashMap<Ore, Long>();
        units = new HashMap<Ore, Double>();
    }

    OreSolution(OreSolution oldOne) {
        System.arraycopy(oldOne.minerals, 0, this.minerals, 0, minerals.length);
        volumes = new HashMap<Ore, Long>(oldOne.volumes);
        units = new HashMap<Ore, Double>(oldOne.units);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OreSolution that = (OreSolution) o;

        if (!Arrays.equals(minerals, that.minerals)) return false;
        if (volumes != null ? !volumes.equals(that.volumes) : that.volumes != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = minerals != null ? Arrays.hashCode(minerals) : 0;
        result = 31 * result + (volumes != null ? volumes.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "OreSolution{" +
                "minerals=" + Arrays.toString(minerals) +
                ", volumes=" + volumes +
                '}';
    }
}
