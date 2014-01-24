package komara.eo.mineral;

/**
 * Created with IntelliJ IDEA.
 * User: moonko
 * Date: 12/15/13
 * Time: 6:18 PM
 * To change this template use File | Settings | File Templates.
 */
public interface OreMineral {
    Mineral getMineral();

    Ore getOre();

    double getUnitsPerCubicMeter();

    int getUnitsPerBatch();
}
