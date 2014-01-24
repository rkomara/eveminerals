package komara.eo.gui.mineral;

import komara.eo.Sovereignty;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.prefs.Preferences;

/**
 * Created by Rastislav Komara on 1/16/14.
 */
class SovereigntyPanel extends JPanel{
    private static final byte[] DEFAULT_SOV_SELECTION = new byte[Sovereignty.values().length];
    static {
        Arrays.fill(DEFAULT_SOV_SELECTION, (byte) 0);
    }

    public static final String KEY_SOVEREIGNTY_SECURITY_LEVEL = "sovereignty.securityLevel";
    public static final String KEY_SOVEREIGNTY_SELECTION = "sovereignty.selection";

    private final JCheckBox[] sovereigntyCheckBoxes = new JCheckBox[Sovereignty.values().length];
    private final JSpinner securityStatus;
    private final SpinnerNumberModel securityStatusModel;

    SovereigntyPanel(Preferences preferences, Action action) {
        super(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.BASELINE_LEADING;
        c.insets = new Insets(0, 0, 0, 20);
        c.gridx = -1;

        Sovereignty[] values = Sovereignty.values();
        byte[] sovSelection = preferences.getByteArray(KEY_SOVEREIGNTY_SELECTION, DEFAULT_SOV_SELECTION);
        for (Sovereignty value : values) {
            c.gridx = c.gridx + 1;
            JCheckBox checkBox = new JCheckBox(value.name());
            sovereigntyCheckBoxes[value.ordinal()] = checkBox;
            checkBox.setSelected(sovSelection[value.ordinal()] == 1);
            add(checkBox, c);
        }

        c.gridx = c.gridx + 1;
        c.insets = new Insets(0, 0, 0, 5);
        JLabel label = new JLabel("Min security:");
        add(label, c);

        securityStatusModel = new SpinnerNumberModel(preferences.getDouble(KEY_SOVEREIGNTY_SECURITY_LEVEL, 0.5), -1.0, 1.0, 0.1);
        securityStatus = new JSpinner(securityStatusModel);
        c.gridx = c.gridx + 1;
        c.insets = new Insets(0, 0, 0, 0);

        label.setLabelFor(securityStatus);
        add(securityStatus, c);

        c.gridx = c.gridx + 1;
        c.weightx = 1.0;
        add(new JPanel(), c);

        c.gridx = c.gridx + 1;
        c.weightx = 0.0;
        c.insets = new Insets(0, 0, 0, 20);
        add(new JButton(action), c);
    }


    EnumSet<Sovereignty> getSovereignty() {
        EnumSet<Sovereignty> result = EnumSet.noneOf(Sovereignty.class);
        Sovereignty[] sovereigntyArray = Sovereignty.values();
        for(int i = 0; i < sovereigntyCheckBoxes.length; i++) {
            JCheckBox checkBox = sovereigntyCheckBoxes[i];
            if (checkBox.isSelected()) {
                result.add(sovereigntyArray[i]);
            }
        }
        return result;
    }

    double getMinimumSecurityStatus() {
        Number number = securityStatusModel.getNumber();
        if (number != null) {
            return number.doubleValue();
        } else {
            return 0.5;
        }
    }


    public void savePreferences(Preferences preferences) {
        byte[] sovConfig = new byte[Sovereignty.values().length];
        for(int i = 0; i < sovereigntyCheckBoxes.length; i++) {
            sovConfig[i] = (byte) (sovereigntyCheckBoxes[i].isSelected() ? 1 : 0);
        }
        preferences.putByteArray(KEY_SOVEREIGNTY_SELECTION, sovConfig);
        preferences.putDouble(KEY_SOVEREIGNTY_SECURITY_LEVEL, getMinimumSecurityStatus());
    }
}
