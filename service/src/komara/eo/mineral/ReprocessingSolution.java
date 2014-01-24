package komara.eo.mineral;

import java.util.Collection;
import java.util.Map;

/**
 * Created by Rastislav Komara on 1/14/14.
 */
public interface ReprocessingSolution extends Comparable<ReprocessingSolution> {

    long[] getResultMinerals();

    Collection<Ore> getOres();

    long getTotalVolume();

    long getVolume(Ore ore);

    Map<Ore, Long> getOreList();

    boolean isValid();

    boolean isPartial();
}
