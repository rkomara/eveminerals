package komara.eo.gui.mineral;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * Created by Rastislav Komara on 1/15/14.
 */
class MineralVolumeDocument extends PlainDocument {

    @Override
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        char[] chars = str.toCharArray();
        for (char aChar : chars) {
            if (!Character.isDigit(aChar)) {
                return;
            }
        }
        super.insertString(offs, str, a);
    }
}
