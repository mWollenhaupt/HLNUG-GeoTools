package de.hsbo.fbg.hlnug;

import de.hsbo.fbg.hlnug.controller.ClarNotationTabController;
import de.hsbo.fbg.hlnug.controller.FileSelectionController;
import de.hsbo.fbg.hlnug.controller.VirtualRedrillTabController;
import de.hsbo.fbg.hlnug.view.MainWindow;
import javax.swing.SwingUtilities;

/**
 * Main class for running Geologic Tools with HLNUG-GUI. The MVC pattern was
 * used for structuring this application. For further tools the view abstract
 * class AbstractToolTab (view.tooltabs package) must be derived. An Instance of
 * this derived class must be given to the ToolTabPane instance in the
 * MainWindow class (view package). For handling events of your new tool tab you
 * have to write an own controller, see existing controllers for best practice
 * (controller package).
 *
 *
 * @author Moritz Wollenhaupt <moritz.wollenhaupt@hs-bochum.de>
 */
public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainWindow mw = new MainWindow();
                FileSelectionController selectionController = new FileSelectionController(mw);
                ClarNotationTabController clarNotationController = new ClarNotationTabController(mw);
                VirtualRedrillTabController virtualRedrillController = new VirtualRedrillTabController(mw);
            }
        });
    }
}
