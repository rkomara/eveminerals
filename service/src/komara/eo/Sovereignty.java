package komara.eo;

import java.util.EnumSet;

/**
 * Created by moonko on 12/15/13.
 */
public enum Sovereignty {
    Caldari, Gallente, Amarr, Minmatar, NullSec;

    public static EnumSet<Sovereignty> empires() {
        return EnumSet.of(Sovereignty.Amarr, Sovereignty.Caldari, Sovereignty.Gallente, Sovereignty.Minmatar);
    }

    public static EnumSet<Sovereignty> all() {
        return EnumSet.of(Sovereignty.Amarr, Sovereignty.Caldari, Sovereignty.Gallente, Sovereignty.Minmatar, Sovereignty.NullSec);
    }
}
