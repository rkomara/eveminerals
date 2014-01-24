package komara.eo.mineral;

import komara.eo.Sovereignty;

import java.util.EnumSet;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: moonko
 * Date: 12/15/13
 * Time: 6:12 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Ore extends Comparable<Ore>{
    String getName();

    int getUnitsToRefine();

    double getVolume();

    List<OreMineral> getBatchResult();

    OreMineral getExtraction(Mineral mineral);

    boolean hasMineral(Mineral mineral);

    EnumSet<Sovereignty> getSovereignty();

    double getMinSecLevel();
    double getMaxSecLevel();
}
