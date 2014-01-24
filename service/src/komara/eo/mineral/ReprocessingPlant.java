package komara.eo.mineral;

import java.util.Collection;

/**
 * Created by Rastislav Komara on 12/15/13.
 */
public interface ReprocessingPlant {

    void setUserStatistics(double yield, double tax);

    /**
     *
     *
     * @param minerals array of mineral quantities indexed by order number of mineral in {@link Mineral}
     * @return solution with lowest volume of ore.
     */
    ReprocessingSolution getMinimumOreVolume(long[] minerals);

    Collection<ReprocessingSolution> getAllSolutions(long[] minerals);
}
