package komara.eo.gui.mineral;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.NumberFormat;
import java.util.prefs.Preferences;

/**
 * Created by Rastislav Komara on 1/16/14.
 */
class UserPlantSettings extends JPanel {

    public static final String KEY_PLANT_USER_YIELD = "plant.user.yield";
    public static final String KEY_PLANT_USER_TAX = "plant.user.tax";
    public static final String KEY_GENERAL_USER_MINING_CARGO = "general.user.mining.cargo";
    private final JSpinner yieldText;
    private final JSpinner taxText;
    private final SpinnerNumberModel yieldNumberModel;
    private final SpinnerNumberModel taxNumberModel;
    private final JFormattedTextField cargoVolume;

    UserPlantSettings(final Preferences preferences) {
        super(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        c.anchor = GridBagConstraints.BASELINE_LEADING;
        // Yield
        c.gridy = 0;
        c.gridx = 0;
        c.weightx = 0.0;
        c.weighty = 0.0;
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(0, 0, 0, 5);

        JLabel label = new JLabel("Yield:");
        add(label, c);

        yieldNumberModel = new SpinnerNumberModel(preferences.getDouble(KEY_PLANT_USER_YIELD, 100.0), 0.0, 100.0, 0.1);
        yieldText = createTextField(yieldNumberModel);
        label.setLabelFor(yieldText);

        c.gridx = 1;
        c.weightx = 1.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 0, 0, 0);

        add(yieldText, c);

        // Tax
        c.gridy = 1;
        c.gridx = 0;
        c.weightx = 0.0;
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(5, 0, 0, 5);

        label = new JLabel("Tax:");
        add(label, c);

        taxNumberModel = new SpinnerNumberModel(preferences.getDouble(KEY_PLANT_USER_TAX, 0.0), 0.00, 100.00, 0.01);
        taxText = createTextField(taxNumberModel);
        label.setLabelFor(taxText);

        c.gridx = 1;
        c.weightx = 1.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 0, 0, 0);

        add(taxText, c);

        // Tax
        c.gridy++;
        c.gridx = 0;
        c.weightx = 0.0;
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(5, 0, 0, 5);

        label = new JLabel("Cargo:");
        add(label, c);

        cargoVolume = new JFormattedTextField();
        cargoVolume.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(NumberFormat.getInstance())));
        cargoVolume.setHorizontalAlignment(JTextField.TRAILING);
        cargoVolume.setFocusLostBehavior(JFormattedTextField.COMMIT_OR_REVERT);
        cargoVolume.setValue(preferences.getLong(KEY_GENERAL_USER_MINING_CARGO, 27500L));
        cargoVolume.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        cargoVolume.selectAll();
                    }
                });
            }
        });
        label.setLabelFor(cargoVolume);

        c.gridx = 1;
        c.weightx = 1.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 0, 0, 0);

        add(cargoVolume, c);


        JPanel fill = new JPanel();
        c.gridy = c.gridy + 1;
        c.gridx = 0;
        c.weighty = 1.0;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.VERTICAL;
        c.insets = new Insets(0, 0, 0, 0);
        add(fill, c);

    }

    private JSpinner createTextField(SpinnerNumberModel model) {
        final JSpinner spinner = new JSpinner();
        spinner.setModel(model);
        FocusAdapter focusAdapter = new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                final JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) spinner.getEditor();
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        editor.getTextField().selectAll();
                    }
                });

            }
        };
        ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField()
                .addFocusListener(focusAdapter);
        return spinner;
    }

    double getYield() {
        return yieldNumberModel.getNumber().doubleValue();
    }

    double getTax() {
        return taxNumberModel.getNumber().doubleValue();
    }

    long getCargo() {
        return ((Number)cargoVolume.getValue()).longValue();
    }

    public void savePreferences(Preferences preferences) {
        preferences.putDouble(KEY_PLANT_USER_YIELD, getYield());
        preferences.putDouble(KEY_PLANT_USER_TAX, getTax());
        preferences.putLong(KEY_GENERAL_USER_MINING_CARGO, getCargo());
    }
}
