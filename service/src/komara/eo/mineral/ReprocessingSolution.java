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

    Map<Ore, Double> getOreUnitsList();

    boolean isValid();

    boolean isPartial();
}
