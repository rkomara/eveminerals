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

package komara.eo.gui.mineral;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.util.prefs.Preferences;

/**
 * Created by Rastislav Komara on 1/14/14.
 */
public class EveMineralsFrame extends JFrame {
    public static final String KEY_COPY_FROM_CLIPBOARD = "COPY_FROM_CLIPBOARD";
    private JTabbedPane tabs;
    private final JPanel frameBase;
    private final Preferences preferences;
    private ReprocessingPlantPanel reprocessingPlantPanel;

    public EveMineralsFrame(Preferences preferences) throws HeadlessException {
        super();
        this.preferences = preferences;
        Container contentPane = getContentPane();
        frameBase = new JPanel();
//        contentPane.setLayout(new BorderLayout());
        contentPane.add(frameBase/*, BorderLayout.CENTER*/);
        }

    public void buildUI() {
        frameBase.setLayout(new BorderLayout());


        tabs = new JTabbedPane();
        frameBase.add(tabs, BorderLayout.CENTER);

        reprocessingPlantPanel = new ReprocessingPlantPanel(preferences);
        tabs.add("Ore volumes", reprocessingPlantPanel);

        pack();

        // actions
        JComponent component = (JComponent) getContentPane();
        component.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke('V', InputEvent.CTRL_DOWN_MASK), KEY_COPY_FROM_CLIPBOARD);
        component.getActionMap().put(KEY_COPY_FROM_CLIPBOARD, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("CTRL + V works!");
            }
        });

    }

    public void savePreferences(Preferences preferences) {
        reprocessingPlantPanel.savePreferences(preferences);
    }



}
