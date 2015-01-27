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

package komara.eo.mineral;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: moonko
 * Date: 12/15/13
 * Time: 6:12 PM
 * To change this template use File | Settings | File Templates.
 */
public enum Mineral {
    Tritanium, Pyerite, Mexallon, Isogen, Nocxium, Megacyte, Zydrine, Morphite;

    public static Mineral getMostRare() {
        return Morphite;
    }

    public static long[] toArray(Map<Mineral, Long> minerals) {
        long[] result = new long[Mineral.values().length];
        for (Map.Entry<Mineral, Long> entry : minerals.entrySet()) {
            result[entry.getKey().ordinal()] = entry.getValue();
        }
        return result;
    }

    public static Mineral find(int remoteId) {
        /*
            34          Tritanium
            35          Pyerite
            36          Mexallon
            37          Isogen
            38          Nocxium
            39          Zydrine
            40          Megacyte
            11399       Morphite
         */
        switch (remoteId) {
            case 34: return Tritanium;
            case 35: return Pyerite;
            case 36: return Mexallon;
            case 37: return Isogen;
            case 38: return Nocxium;
            case 39: return Zydrine;
            case 40: return Megacyte;
            case 11399: return Morphite;
            default: {
                throw new RuntimeException("Id does not represent mineral");
            }
        }
    }
}
