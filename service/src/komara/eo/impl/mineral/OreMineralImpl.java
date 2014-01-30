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
import komara.eo.mineral.OreMineral;

/**
 * Created by moonko on 12/15/13.
 */
class OreMineralImpl implements OreMineral {
    private Mineral mineral;
    private Ore ore;
    private double unitsPerCubicMeter;
    private int unitsPerBatch;

    OreMineralImpl(Mineral mineral, Ore ore, double unitsPerCubicMeter, int unitsPerBatch) {
        this.mineral = mineral;
        this.ore = ore;
        this.unitsPerCubicMeter = unitsPerCubicMeter;
        this.unitsPerBatch = unitsPerBatch;
    }

    @Override
    public Mineral getMineral() {
        return mineral;
    }

    @Override
    public Ore getOre() {
        return ore;
    }

    @Override
    public double getUnitsPerCubicMeter() {
        return unitsPerCubicMeter;
    }

    @Override
    public int getUnitsPerBatch() {
        return unitsPerBatch;
    }

    @Override
    public String toString() {
        return "OreMineralImpl{" +
                "mineral=" + mineral +
                ", ore=" + ore +
                ", unitsPerCubicMeter=" + unitsPerCubicMeter +
                ", unitsPerBatch=" + unitsPerBatch +
                '}';
    }
}
