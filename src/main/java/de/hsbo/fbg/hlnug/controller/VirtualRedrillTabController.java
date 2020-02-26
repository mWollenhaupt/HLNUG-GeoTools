package de.hsbo.fbg.hlnug.controller;

import de.hsbo.fbg.hlnug.model.GeoFileExtensions;
import de.hsbo.fbg.hlnug.model.GeoFileObject;
import de.hsbo.fbg.hlnug.model.GeoFileReader;
import de.hsbo.fbg.hlnug.util.ToolExecutionThreadPool;
import de.hsbo.fbg.hlnug.view.LoggingPanel;
import de.hsbo.fbg.hlnug.view.MainWindow;
import de.hsbo.fbg.hlnug.view.tooltabs.VirtualRedrillToolTab;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;

/**
 * @author Moritz Wollenhaupt <moritz.wollenhaupt@hs-bochum.de>
 */
public class VirtualRedrillTabController {

    private MainWindow mainWindow;           // reference to mainWindow
    private LoggingPanel logPanel;           // reference to mainWindow's logging area
    private VirtualRedrillToolTab vrTab;         // referecne to mainWindow's VirtualRedrillTab

    private ToolExecutionThreadPool threadPool;

    public VirtualRedrillTabController(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        this.logPanel = mainWindow.getLogPanel();
        this.vrTab = mainWindow.getVirtualRedrillTab();
        this.threadPool = new ToolExecutionThreadPool();
        initController();
    }

    /**
     * here: initialize all Listeners do not forget to execute long term
     * calculations in an own thread, otherwise the GUI will block any following
     * input
     */
    private void initController() {
        vrTab.getBtnRun().addActionListener(e -> exec());
    }

    private void exec() {
        threadPool.execute(() -> {
            int[] rows = mainWindow.getFileTable().getSelectedRows();
            // do some input checks
            if (rows.length < 1) {
                logPanel.errorLog("Bitte die zu vergleichenden CSV-Dateien selektieren!");
                return;
            }
            List<GeoFileObject> objects = new ArrayList<>();
            for (int row : rows) {
                GeoFileObject selection = (GeoFileObject) mainWindow.getFileTable().getValueAt(row, 0);
                if (!(selection.getType().equals(GeoFileExtensions.CSV))) {
                    logPanel.errorLog("Bitte nur CSV-Dateien angeben!");
                    return;
                } else {
                    objects.add(selection);
                }
            }
            
            // choose new file path
            JFileChooser fileChooser = GeoFileChooserFactory.getSaveFileDialog(
                    "Bitte Speicherpfad auswählen",
                    new String[]{GeoFileExtensions.CSV},
                    objects.get(0));
            System.out.println(objects.get(0));
            if (fileChooser.showSaveDialog(mainWindow.getMainFrame()) == JFileChooser.APPROVE_OPTION) {
                
            }
            
            // 2. Alle selektierten Dateien in WellRepo einlesen
            // 3. Berechnung durchführen
            // 4. In Datei schreiben
        });

    }

}
