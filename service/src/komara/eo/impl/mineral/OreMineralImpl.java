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
