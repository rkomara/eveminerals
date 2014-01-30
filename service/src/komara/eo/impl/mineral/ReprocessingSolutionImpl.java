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
import komara.eo.mineral.ReprocessingSolution;

import java.util.*;

/**
 * Created by Rastislav Komara on 1/14/14.
 */
class ReprocessingSolutionImpl implements ReprocessingSolution {
    private final OreSolution solution;
    private long totalVolume = Long.MIN_VALUE;

    ReprocessingSolutionImpl(OreSolution solution) {
        this.solution = solution;
    }

    @Override
    public long[] getResultMinerals() {
        long[] result = new long[Mineral.values().length];
        if (solution != null) {
            System.arraycopy(solution.minerals, 0, result, 0, result.length);
        } else {
            Arrays.fill(result, 0);
        }
        return result;
    }

    @Override
    public Collection<Ore> getOres() {
        if (solution != null) {
            return Collections.unmodifiableSet(solution.volumes.keySet());
        } else {
            return Collections.emptySet();
        }
    }

    @Override
    public long getTotalVolume() {
        if (totalVolume == Long.MIN_VALUE) {
            if (solution != null) {
                totalVolume = 0;
                for (Long volumePerOre : solution.volumes.values()) {
                    totalVolume += volumePerOre;
                }
            } else {
                totalVolume = -1;
            }
        }
        return totalVolume;
    }

    @Override
    public long getVolume(Ore ore) {
        if (solution != null && solution.volumes.containsKey(ore)) {
            return solution.volumes.get(ore);
        }
        return -1;
    }

    @Override
    public Map<Ore, Long> getOreList() {
        if (solution != null) {
            return Collections.unmodifiableSortedMap(new TreeMap<Ore, Long>(solution.volumes));
        }
        return Collections.emptyMap();
    }

    @Override
    public boolean isValid() {
        return solution != null;
    }

    @Override
    public boolean isPartial() {
        if (solution != null) {
            for (long mineral : solution.minerals) {
                if (mineral > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int compareTo(ReprocessingSolution o) {
        return Long.compare(this.getTotalVolume(), o.getTotalVolume());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReprocessingSolutionImpl that = (ReprocessingSolutionImpl) o;

        if (solution != null ? !solution.equals(that.solution) : that.solution != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return solution != null ? solution.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ReprocessingSolutionImpl{" +
                "solution=" + solution +
                ", totalVolume=" + getTotalVolume() +
                '}';
    }
}
