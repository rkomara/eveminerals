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

package komara.eo.gui.input;

import komara.eo.mineral.Mineral;

import java.util.Arrays;

/**
 * Created by Rastislav Komara on 2/2/14.
 */
class MineralInputImpl implements MineralInput {
    private long[] minerals = new long[Mineral.values().length];

    MineralInputImpl() {
        Arrays.fill(minerals, 0);
    }

    MineralInputImpl(long[] minerals) {
        this.minerals = Arrays.copyOf(minerals, minerals.length);
    }

    @Override
    public long getQuantity(Mineral mineral) {
        return minerals[mineral.ordinal()];
    }

    void setQuantity(Mineral mineral, long quantity) {
        minerals[mineral.ordinal()] = quantity;
    }

    @Override
    public String toString() {
        return "MineralInputImpl{" +
                "minerals=" + Arrays.toString(minerals) +
                '}';
    }
}
