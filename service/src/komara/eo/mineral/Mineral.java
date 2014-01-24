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
}
