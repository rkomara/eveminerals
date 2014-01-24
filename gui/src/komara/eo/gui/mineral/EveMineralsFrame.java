package komara.eo.gui.mineral;

import javax.swing.*;
import java.awt.*;
import java.util.prefs.Preferences;

/**
 * Created by Rastislav Komara on 1/14/14.
 */
public class EveMineralsFrame extends JFrame {
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
    }

    public void savePreferences(Preferences preferences) {
        reprocessingPlantPanel.savePreferences(preferences);
    }



}
