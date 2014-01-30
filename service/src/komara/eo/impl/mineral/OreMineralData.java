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

/**
 * Created by moonko on 12/22/13.
 */
class OreMineralData {
    private Mineral mineral;
    private double unitsPerCubicMeter;
    private int unitsPerBatch;

    OreMineralData(Mineral mineral, double unitsPerCubicMeter, int unitsPerBatch) {
        this.mineral = mineral;
        this.unitsPerCubicMeter = unitsPerCubicMeter;
        this.unitsPerBatch = unitsPerBatch;
    }

    OreMineralImpl toOreMineral(Ore ore) {
        return new OreMineralImpl(mineral, ore, unitsPerCubicMeter, unitsPerBatch);
    }

    Mineral getMineral() {
        return mineral;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OreMineralData that = (OreMineralData) o;

        if (unitsPerBatch != that.unitsPerBatch) return false;
        if (Double.compare(that.unitsPerCubicMeter, unitsPerCubicMeter) != 0) return false;
        if (mineral != that.mineral) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = mineral.hashCode();
        temp = Double.doubleToLongBits(unitsPerCubicMeter);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + unitsPerBatch;
        return result;
    }

    @Override
    public String toString() {
        return "OreMineralData{" +
                "mineral=" + mineral +
                ", unitsPerCubicMeter=" + unitsPerCubicMeter +
                ", unitsPerBatch=" + unitsPerBatch +
                '}';
    }
}
