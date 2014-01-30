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
