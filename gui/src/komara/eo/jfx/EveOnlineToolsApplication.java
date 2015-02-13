/*
 * EveMinerals
 * Copyright (C) 2015  Rastislav Komara
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

package komara.eo.jfx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.LinkedList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.prefs.Preferences;

/**
 * Created by Rastislav Komara on 2/12/2015.
 */
public class EveOnlineToolsApplication extends Application {
    private final Preferences preferences = Preferences.userNodeForPackage(EveOnlineToolsApplication.class);
    private List<ApplicationModule> modules = new LinkedList<>();

    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane root = new AnchorPane();
        TabPane tabs = new TabPane();

        ServiceLoader<ApplicationModule> serviceLoader = ServiceLoader.load(ApplicationModule.class);
        serviceLoader.forEach((module) -> {
            Tab moduleTab = module.createModule(preferences.node(module.getModuleId()));
            moduleTab.setClosable(false);
            tabs.getTabs().add(moduleTab);
            modules.add(module);
        });

        AnchorPane.setTopAnchor(tabs, 0.0);
        AnchorPane.setBottomAnchor(tabs, 0.0);
        AnchorPane.setLeftAnchor(tabs, 0.0);
        AnchorPane.setRightAnchor(tabs, 0.0);

        root.getChildren().add(tabs);

        Scene scene = new Scene(root, 800, 640);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setOnCloseRequest(
                e -> modules.forEach(
                        module -> module.savePreferences(preferences.node(module.getModuleId()))
                )
        );
    }

    public static void main(String[] args) {
        EveOnlineToolsApplication.launch(args);
    }
}
