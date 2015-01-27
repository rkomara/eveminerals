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

package komara.eo.gui.input;

import komara.eo.mineral.Mineral;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.text.NumberFormat;

/**
 * Created by Rastislav Komara on 2/2/14.
 */
public class EveIPHDualInput implements InputDataParser {

    private MineralInputImpl mineralInput;

    @Override
    public boolean parse(Transferable input) {
        if (canAccept(input)) {
            BufferedReader br = null;
            try {
                Reader reader = DataFlavor.getTextPlainUnicodeFlavor().getReaderForText(input);
                br = new BufferedReader(reader);
                String line;
                mineralInput = null;
                while ((line = br.readLine()) != null) {
                    if (line.startsWith("Material - Quantity")) {
                        while ((line = br.readLine()) != null) {
                            String[] split = line.split(" ");
                            if (split.length == 3) {
                                if (mineralInput == null) {
                                    mineralInput = new MineralInputImpl();
                                }
                                Mineral mineral = Mineral.valueOf(split[0]);
                                Number quantity = NumberFormat.getInstance().parse(split[2]);
                                mineralInput.setQuantity(mineral, quantity.longValue());
                            } else {
                                return mineralInput != null;
                            }

                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean canAccept(Transferable input) {
        return input.isDataFlavorSupported(DataFlavor.getTextPlainUnicodeFlavor());
    }

    @Override
    public MineralInput getMineralInput() {
        return mineralInput;
    }
}
