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
