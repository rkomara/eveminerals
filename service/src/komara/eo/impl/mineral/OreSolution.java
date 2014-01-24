package komara.eo.impl.mineral;

import komara.eo.mineral.Mineral;
import komara.eo.mineral.Ore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by moonko on 12/22/13.
 */
class OreSolution {

    long[] minerals = new long[Mineral.values().length];
    Map<Ore, Long> volumes;

    OreSolution(long[] minerals) {
        System.arraycopy(minerals, 0, this.minerals, 0, minerals.length);
        volumes = new HashMap<Ore, Long>();
    }

    OreSolution(OreSolution oldOne) {
        System.arraycopy(oldOne.minerals, 0, this.minerals, 0, minerals.length);
        volumes = new HashMap<Ore, Long>(oldOne.volumes);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OreSolution that = (OreSolution) o;

        if (!Arrays.equals(minerals, that.minerals)) return false;
        if (volumes != null ? !volumes.equals(that.volumes) : that.volumes != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = minerals != null ? Arrays.hashCode(minerals) : 0;
        result = 31 * result + (volumes != null ? volumes.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "OreSolution{" +
                "minerals=" + Arrays.toString(minerals) +
                ", volumes=" + volumes +
                '}';
    }
}
