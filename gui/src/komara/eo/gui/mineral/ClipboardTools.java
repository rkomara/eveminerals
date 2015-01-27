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

import komara.eo.gui.input.EveIPHDualInput;
import komara.eo.gui.input.MineralInput;

import javax.swing.*;
import javax.swing.event.EventListenerList;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.ActionEvent;
import java.util.EventListener;

/**
 * Created by Rastislav Komara on 2/2/14.
 */
public class ClipboardTools implements ClipboardOwner, FlavorListener {
    private static ClipboardTools instance = new ClipboardTools();
    public static final String KEY_PASTE_FROM_CLIPBOARD = "paste-from-clipboard";
    public static final KeyStroke KEYSTROKE_CONTROL_V = KeyStroke.getKeyStroke("ctrl pressed V");

    public static void installClipboardSupport(JComponent target) {
        Object oldActionBinding = target.getInputMap().get(KEYSTROKE_CONTROL_V);
        final Action oldAction = target.getActionMap().get(oldActionBinding);
        target.getInputMap().put(KEYSTROKE_CONTROL_V, KEY_PASTE_FROM_CLIPBOARD);
        target.getActionMap().put(KEY_PASTE_FROM_CLIPBOARD, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Transferable contents = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(this);
                if (contents != null) {
                    EveIPHDualInput input = new EveIPHDualInput();
                    if (input.canAccept(contents)) {
                        if (input.parse(contents)) {
                            MineralInput mineralInput = input.getMineralInput();
                            System.out.println(mineralInput.toString());

                            MineralClipboardInputListener[] listeners = getDefault().listenerList.getListeners(MineralClipboardInputListener.class);
                            for (MineralClipboardInputListener listener : listeners) {
                                listener.mineralInput(mineralInput);
                            }

                            return;
                        }
                    }
                }
                if (oldAction != null) {
                    System.out.println("Executing old action");
                    oldAction.actionPerformed(e);
                }
            }
        });
    }

    private EventListenerList listenerList = new EventListenerList();

    public static ClipboardTools getDefault() {
        return instance;
    }

    private ClipboardTools() {
    }

    @Override
    public void lostOwnership(Clipboard clipboard, Transferable contents) {

    }

    @Override
    public void flavorsChanged(FlavorEvent e) {

    }

    public <T extends EventListener> void addListener(Class<T> clazz, T listener) {
        listenerList.add(clazz, listener);
    }

    public <T extends EventListener> void removeListener(Class<T> clazz, T listener) {
        listenerList.remove(clazz, listener);
    }

}
