package de.hsbo.fbg.hlnug;

import de.hsbo.fbg.hlnug.controller.FileSelectionController;
import de.hsbo.fbg.hlnug.view.MainWindow;
import javax.swing.SwingUtilities;

/**
 * @author Moritz Wollenhaupt <moritz.wollenhaupt@hs-bochum.de>
 */
public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainWindow mw = new MainWindow();
                FileSelectionController selectionController = new FileSelectionController(mw);
            }
        });
    }
}
