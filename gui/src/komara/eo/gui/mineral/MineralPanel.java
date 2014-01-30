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

import komara.eo.mineral.Mineral;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.prefs.Preferences;

/**
 * Created by Rastislav Komara on 1/14/14.
 */
class MineralPanel extends JPanel {

//    private final JTextField tritaniumText;
//    private final JTextField pyeriteText;
//    private final JTextField mexallonText;
//    private final JTextField isogenText;
//    private final JTextField nocxiumText;
//    private final JTextField megacyteText;
//    private final JTextField zydrineText;
//    private final JTextField morphiteText;

    private final JTextField[] mineralTextFields = new JTextField[Mineral.values().length];

    MineralPanel(Preferences preferences) {
        super(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        //Tritanium
        c.anchor = GridBagConstraints.BASELINE_LEADING;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0, 0, 0, 0);
        c.weightx = 0.0;

        JLabel label = new JLabel(Mineral.Tritanium.name() + ":");

        this.add(label, c);

        c.anchor = GridBagConstraints.BASELINE_LEADING;
        c.gridx = 1;
        c.insets = new Insets(0, 5, 0, 20);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;

        int i = Mineral.Tritanium.ordinal();
        mineralTextFields[i] = createTextField();
        label.setLabelFor(mineralTextFields[i]);
        this.add(mineralTextFields[i], c);

        //Pyerite
        c.anchor = GridBagConstraints.BASELINE_LEADING;
        c.gridx = 2;
        c.insets = new Insets(0, 0, 0, 0);
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0.0;

        label = new JLabel(Mineral.Pyerite.name() + ":");

        this.add(label, c);

        c.anchor = GridBagConstraints.BASELINE_LEADING;
        c.gridx = 3;
        c.insets = new Insets(0, 5, 0, 0);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;

        i = Mineral.Pyerite.ordinal();
        mineralTextFields[i] = createTextField();
        label.setLabelFor(mineralTextFields[i]);
        this.add(mineralTextFields[i], c);

        //Mexallon
        c.anchor = GridBagConstraints.BASELINE_LEADING;
        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(0, 0, 0, 0);
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0.0;

        label = new JLabel(Mineral.Mexallon.name() + ":");

        this.add(label, c);

        c.anchor = GridBagConstraints.BASELINE_LEADING;
        c.gridx = 1;
        c.insets = new Insets(5, 5, 0, 20);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;

        i = Mineral.Mexallon.ordinal();
        mineralTextFields[i] = createTextField();
        label.setLabelFor(mineralTextFields[i]);
        this.add(mineralTextFields[i], c);

        //Isogen
        c.anchor = GridBagConstraints.BASELINE_LEADING;
        c.gridx = 2;
        c.insets = new Insets(0, 0, 0, 0);
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0.0;

        label = new JLabel(Mineral.Isogen.name() + ":");

        this.add(label, c);

        c.anchor = GridBagConstraints.BASELINE_LEADING;
        c.gridx = 3;
        c.insets = new Insets(5, 5, 0, 0);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;

        i = Mineral.Isogen.ordinal();
        mineralTextFields[i] = createTextField();
        label.setLabelFor(mineralTextFields[i]);
        this.add(mineralTextFields[i], c);


        //Nocxium
        c.anchor = GridBagConstraints.BASELINE_LEADING;
        c.gridx = 0;
        c.gridy = 2;
        c.insets = new Insets(0, 0, 0, 0);
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0.0;

        label = new JLabel(Mineral.Nocxium.name() + ":");

        this.add(label, c);

        c.anchor = GridBagConstraints.BASELINE_LEADING;
        c.gridx = 1;
        c.insets = new Insets(5, 5, 0, 20);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;

        i = Mineral.Nocxium.ordinal();
        mineralTextFields[i] = createTextField();
        label.setLabelFor(mineralTextFields[i]);
        this.add(mineralTextFields[i], c);

        //Megacyte
        c.anchor = GridBagConstraints.BASELINE_LEADING;
        c.gridx = 2;
        c.insets = new Insets(0, 0, 0, 0);
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0.0;

        label = new JLabel(Mineral.Megacyte.name() + ":");

        this.add(label, c);

        c.anchor = GridBagConstraints.BASELINE_LEADING;
        c.gridx = 3;
        c.insets = new Insets(5, 5, 0, 0);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;

        i = Mineral.Megacyte.ordinal();
        mineralTextFields[i] = createTextField();
        label.setLabelFor(mineralTextFields[i]);
        this.add(mineralTextFields[i], c);

        //Zydrine
        c.anchor = GridBagConstraints.BASELINE_LEADING;
        c.gridx = 0;
        c.gridy = 3;
        c.insets = new Insets(0, 0, 0, 0);
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0.0;

        label = new JLabel(Mineral.Zydrine.name() + ":");

        this.add(label, c);

        c.anchor = GridBagConstraints.BASELINE_LEADING;
        c.gridx = 1;
        c.insets = new Insets(5, 5, 0, 20);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;

        i = Mineral.Zydrine.ordinal();
        mineralTextFields[i] = createTextField();
        label.setLabelFor(mineralTextFields[i]);
        this.add(mineralTextFields[i], c);

        //Morphite
        c.anchor = GridBagConstraints.BASELINE_LEADING;
        c.gridx = 2;
        c.insets = new Insets(0, 0, 0, 0);
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0.0;

        label = new JLabel(Mineral.Morphite.name() + ":");

        this.add(label, c);

        c.anchor = GridBagConstraints.BASELINE_LEADING;
        c.gridx = 3;
        c.insets = new Insets(5, 5, 0, 0);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;

        i = Mineral.Morphite.ordinal();
        mineralTextFields[i] = createTextField();
        label.setLabelFor(mineralTextFields[i]);
        this.add(mineralTextFields[i], c);
    }

    private JTextField createTextField() {
        //TODO: Reconsider...
        final JFormattedTextField textField = new JFormattedTextField();
        textField.setColumns(12);
        textField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(NumberFormat.getInstance())));
        textField.setHorizontalAlignment(JTextField.TRAILING);
        textField.setFocusLostBehavior(JFormattedTextField.COMMIT_OR_REVERT);
        textField.setValue(0L);
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        textField.selectAll();
                    }
                });
            }
        });
        return textField;
    }

    long[] getMinerals() {
        long[] result = new long[mineralTextFields.length];
        NumberFormat numberFormat = NumberFormat.getInstance();
        for (int i = 0; i < mineralTextFields.length; i++) {
            JTextField textField = mineralTextFields[i];
            String value = textField.getText();
            try {
                Number number = numberFormat.parse(value);
                result[i] = number.longValue();
            } catch (ParseException e) {
                e.printStackTrace();
                result[i] = 0;
            }
        }
        return result;
    }

    public void savePreferences(Preferences preferences) {

    }
}
