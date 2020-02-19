package de.hsbo.fbg.hlnug.controller;

import de.hsbo.fbg.hlnug.model.GeoFileExtensions;
import de.hsbo.fbg.hlnug.model.GeoFileObject;
import de.hsbo.fbg.hlnug.model.GeoFileReader;
import de.hsbo.fbg.hlnug.view.LoggingPanel;
import de.hsbo.fbg.hlnug.view.MainWindow;
import java.io.File;
import javax.swing.JFileChooser;

/**
 * @author Moritz Wollenhaupt <moritz.wollenhaupt@hs-bochum.de>
 */
public class FileSelectionController {

    private MainWindow mainWindow;
    private LoggingPanel logPanel;

    public FileSelectionController(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        logPanel = mainWindow.getLogPanel();
        initController();
    }

    private void initController() {
        mainWindow.getBtnAdd().addActionListener(e -> add());
        mainWindow.getBtnClear().addActionListener(e -> clear());
        mainWindow.getBtnRemove().addActionListener(e -> remove());
    }

    private void add() {
        JFileChooser fileChooser = GeoFileChooserFactory.getLoadFileDialog("Wähle einzulesende Daten aus!",
                new String[]{GeoFileExtensions.TS,
                    GeoFileExtensions.WL,
                    GeoFileExtensions.SHP,
                    GeoFileExtensions.XLSX});
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            logPanel.appendLogString("Ausgewählte Dateien werden eingelesen..");
            Thread readingThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    File[] selectedFiles = fileChooser.getSelectedFiles();
                    for (int i = 0; i < selectedFiles.length; i++) {
                        GeoFileReader reader = new GeoFileReader(selectedFiles[i].getAbsolutePath());
                        for (GeoFileObject obj : reader.getObjects()) {
                            mainWindow.getTableModel().addRow(obj);
                        }
                    }
                    mainWindow.getFileTable().updateUI();
                    logPanel.appendLogString("Dateien erfolgreich eingelesen!");
                }
            });
            readingThread.setDaemon(true);
            readingThread.start();
        }

    }

    private void clear() {
        mainWindow.getTableModel().removeAllElements();
        mainWindow.getFileTable().updateUI();
        mainWindow.getFileTable().clearSelection();
        logPanel.clearLog();
    }

    private void remove() {
        int[] selectedRows = mainWindow.getFileTable().getSelectedRows();
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
