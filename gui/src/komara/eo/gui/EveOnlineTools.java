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

package komara.eo.gui;

import komara.eo.gui.mineral.EveMineralsFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * Created by Rastislav Komara on 1/14/14.
 */
public class EveOnlineTools {

    public static final String KEY_FRAME_X = "frame.x";
    public static final String KEY_FRAME_Y = "frame.y";
    public static final String KEY_FRAME_WIDTH = "frame.width";
    public static final String KEY_FRAME_HEIGHT = "frame.height";

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final Preferences preferences = Preferences.userNodeForPackage(EveOnlineTools.class);
        try {
            preferences.sync();
        } catch (BackingStoreException e) {
            e.printStackTrace();
        }

        final EveMineralsFrame frame = new EveMineralsFrame(preferences);
        frame.setTitle("Eve Online Tools");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                frame.buildUI();
                frame.setLocation(preferences.getInt(KEY_FRAME_X, 0), preferences.getInt(KEY_FRAME_Y, 0));
                frame.setSize(new Dimension(preferences.getInt(KEY_FRAME_WIDTH, 800), preferences.getInt(KEY_FRAME_HEIGHT, 600)));
                frame.setVisible(true);
            }
        });

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    frame.savePreferences(preferences);
                    Point location = frame.getLocation();
                    preferences.putInt(KEY_FRAME_X, location.x);
                    preferences.putInt(KEY_FRAME_Y, location.y);
                    Dimension size = frame.getSize();
                    preferences.putInt(KEY_FRAME_WIDTH, size.width);
                    preferences.putInt(KEY_FRAME_HEIGHT, size.height);
                    preferences.flush();
                } catch (BackingStoreException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

}
