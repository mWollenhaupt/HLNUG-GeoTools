package de.hsbo.fbg.hlnug.controller;

import de.hsbo.fbg.hlnug.model.GeoFileExtensions;
import de.hsbo.fbg.hlnug.model.GeoFileObject;
import de.hsbo.fbg.hlnug.model.GeoFileReader;
import de.hsbo.fbg.hlnug.util.ToolExecutionThreadPool;
import de.hsbo.fbg.hlnug.view.LoggingPanel;
import de.hsbo.fbg.hlnug.view.MainWindow;
import java.io.File;
import javax.swing.JFileChooser;

/**
 * @author Moritz Wollenhaupt <moritz.wollenhaupt@hs-bochum.de>
 */
public class FileSelectionController {

    private MainWindow mainWindow;              // reference to mainWindow
    private LoggingPanel logPanel;              // reference to mainWindow's logging area
    private ToolExecutionThreadPool threadPool;

    public FileSelectionController(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        this.threadPool = new ToolExecutionThreadPool();
        logPanel = mainWindow.getLogPanel();
        initController();
    }

    /**
     * here: initialize all Listeners do not forget to execute long term
     * calculations in an own thread, otherwise the GUI will block any following
     * input
     */
    private void initController() {
        mainWindow.getBtnAdd().addActionListener(e -> add());
        mainWindow.getBtnClear().addActionListener(e -> clear());
        mainWindow.getBtnRemove().addActionListener(e -> remove());
    }

    /**
     * handels clickling the "hinzufügen" buttons action
     */
    private void add() {
        // create new fileChooser with filter for preferred file extensions
        JFileChooser fileChooser = GeoFileChooserFactory.getLoadFileDialog("Wähle einzulesende Daten aus!",
                new String[]{GeoFileExtensions.TS,
//                    GeoFileExtensions.WL,
//                    GeoFileExtensions.SHP,
                    GeoFileExtensions.CSV});
        // open dialog and check for correct input
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            logPanel.appendLogString("Ausgewählte Dateien werden eingelesen..");
            // read all selected files. Since it's possible to select many files at once, 
            // this may take a while. So run it in an own thread
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    File[] selectedFiles = fileChooser.getSelectedFiles();
                for (int i = 0; i < selectedFiles.length; i++) {
                    GeoFileReader reader = new GeoFileReader(selectedFiles[i].getAbsolutePath());
                    // Gocad files for example can handle multiple geometry objects in one file, so iterate over them
                    for (GeoFileObject obj : reader.getObjects()) {
                        mainWindow.getTableModel().addRow(obj);
                    }
                }
                mainWindow.getFileTable().updateUI();
                logPanel.appendLogString("Dateien erfolgreich eingelesen!");
                }
            });
        }

    }

    /**
     * clear file table
     */
    private void clear() {
        mainWindow.getTableModel().removeAllElements();
        mainWindow.getFileTable().updateUI();
        mainWindow.getFileTable().clearSelection();
        logPanel.clearLog();
    }

    /**
     * removes the selected file from file table
     */
    private void remove() {
        int[] selectedRows = mainWindow.getFileTable().getSelectedRows();
        if(selectedRows.length == 0) {
            return;
        }
        if (selectedRows.length > 0) {
            for (int i = selectedRows.length - 1; i >= 0; i--) {
                mainWindow.getTableModel().removeRow(selectedRows[i]);
            }
        }
        mainWindow.getFileTable().updateUI();
        mainWindow.getFileTable().clearSelection();
        if (mainWindow.getTableModel().getRowCount() == 0) {
            logPanel.clearLog();
        } else {
            logPanel.appendLogString("Selektierte Datei(en) wurden entfernt!");
        }

    }

}
