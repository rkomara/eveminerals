package komara.eo.gui.mineral;

import komara.eo.ReprocessingService;
import komara.eo.Sovereignty;
import komara.eo.mineral.Mineral;
import komara.eo.mineral.Ore;
import komara.eo.mineral.ReprocessingPlant;
import komara.eo.mineral.ReprocessingSolution;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.NumberFormat;
import java.util.EnumSet;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.prefs.Preferences;

/**
 * Created by Rastislav Komara on 1/14/14.
 */
class ReprocessingPlantPanel extends JPanel {

    private final MineralPanel mineralPanel;
    private final UserPlantSettings plantSettings;
    private final SovereigntyPanel sovereigntyPanel;
    private final Document resultDocument;

    ReprocessingPlantPanel(Preferences preferences) {
        super(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 0;
        c.gridx = 0;
        c.weightx = 1.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.BASELINE_LEADING;

        mineralPanel = new MineralPanel(preferences);
        mineralPanel.setBorder(BorderFactory.createTitledBorder("Minerals"));
        add(mineralPanel, c);

        c.gridx = 1;
        c.weightx = 0.0;
        c.fill = GridBagConstraints.VERTICAL;

        plantSettings = new UserPlantSettings(preferences);
        plantSettings.setBorder(BorderFactory.createTitledBorder("Facility"));
        add(plantSettings, c);
        plantSettings.setPreferredSize(new Dimension(150, 100));

        c.gridy = 1;
        c.gridx = 0;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.HORIZONTAL;

        sovereigntyPanel = new SovereigntyPanel(preferences, new AbstractAction("Find solution") {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingWorker<String, Void> worker = new SwingWorker<String, Void>() {
                    @Override
                    protected String doInBackground() throws Exception {
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                ReprocessingPlantPanel.this.setEnabled(false);
                            }
                        });
                        EnumSet<Sovereignty> sovereignty = sovereigntyPanel.getSovereignty();
                        double securityStatus = sovereigntyPanel.getMinimumSecurityStatus();

                        ReprocessingService service = ReprocessingService.Lookup.find();
                        ReprocessingPlant plant = service.getPlant(sovereignty, securityStatus);

                        double yield = plantSettings.getYield();
                        double tax = plantSettings.getTax();

                        plant.setUserStatistics(yield / 100, tax / 100);

                        ReprocessingSolution solution = plant.getMinimumOreVolume(mineralPanel.getMinerals());
                        return toFormattedString(solution, plantSettings.getCargo());
                    }

                    @Override
                    protected void done() {
                        try {
                            resultDocument.remove(0, resultDocument.getLength());
                            resultDocument.insertString(0, get(), null);
                        } catch (BadLocationException e1) {
                            e1.printStackTrace();
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        } catch (ExecutionException e1) {
                            e1.printStackTrace();
                        } finally {
                            ReprocessingPlantPanel.this.setEnabled(true);
                        }
                    }
                };

                worker.execute();
            }
        });
        Border border = BorderFactory.createTitledBorder("Sovereignty");
        sovereigntyPanel.setBorder(border);
        add(sovereigntyPanel, c);

        // result area
        c.gridy = 2;
        c.gridx = 0;
        c.gridwidth = 2;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.insets = new Insets(0, 0, 0, 0);
        c.fill = GridBagConstraints.BOTH;
        JTextArea resultArea = new JTextArea();
        resultArea.setEditable(false);

        resultDocument = resultArea.getDocument();
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Optimal ore volumes"));
        add(scrollPane, c);

    }

    private String toFormattedString(ReprocessingSolution solution, long cargoVolume) {
        if (solution == null || !solution.isValid()) {
            return "Sorry, solution not found.";
        }
        NumberFormat format = NumberFormat.getInstance();
        StringBuilder builder = new StringBuilder("Minimal ore volume solution:\n");
        Map<Ore,Long> oreList = solution.getOreList();
        for (Map.Entry<Ore, Long> entry : oreList.entrySet()) {
            builder.append("\t").append(entry.getKey().getName()).append(": ").append(format.format(entry.getValue()));
            builder.append(" (").append(Math.ceil(((double) entry.getValue())/cargoVolume)).append(" shipments)").append("\n");
        }

        builder.append("\n").append("You will get following excessive minerals:\n");
        long[] minerals = solution.getResultMinerals();
        Mineral[] values = Mineral.values();
        for (Mineral value : values) {
            long mineral = minerals[value.ordinal()];
            if (mineral < 0) {
                builder.append("\t").append(value.name()).append(": ").append(format.format(Math.abs(mineral))).append("\n");
            }
        }
        return builder.toString();
    }

    void savePreferences(Preferences preferences) {
        this.sovereigntyPanel.savePreferences(preferences);
        this.mineralPanel.savePreferences(preferences);
        this.plantSettings.savePreferences(preferences);
    }
}
