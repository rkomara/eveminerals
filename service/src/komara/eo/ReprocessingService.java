package komara.eo;

import komara.eo.impl.mineral.ReprocessingPlantImpl;
import komara.eo.mineral.ReprocessingPlant;

import java.util.EnumSet;

/**
 * Created by Rastislav Komara on 1/14/14.
 */
public interface ReprocessingService {

    static public class Lookup {
        private static ReprocessingService service = new ReprocessingService() {
            @Override
            public ReprocessingPlant getPlant(EnumSet<Sovereignty> sovereignty, double securityStatus) {
                return new ReprocessingPlantImpl(sovereignty, securityStatus);
            }

            @Override
            public ReprocessingPlant getIdealPlant() {
                return new ReprocessingPlantImpl();
            }
        };
        static public ReprocessingService find() {
            return service;
        }
    }

    ReprocessingPlant getPlant(EnumSet<Sovereignty> sovereignty, double securityStatus);

    ReprocessingPlant getIdealPlant();
}
