package de.hsbo.fbg.hlnug.controller;

import de.hsbo.fbg.hlnug.model.GeoFileExtensions;
import de.hsbo.fbg.hlnug.model.GeoFileObject;
import de.hsbo.fbg.hlnug.model.GeoFileReader;
import de.hsbo.fbg.hlnug.view.MainWindow;
import java.io.File;
import javax.swing.JFileChooser;

/**
 * @author Moritz Wollenhaupt <moritz.wollenhaupt@hs-bochum.de>
 */
public class FileSelectionController {

    private MainWindow mainWindow;

    public FileSelectionController(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        initController();
    }

    private void initController() {
        mainWindow.getBtnAdd().addActionListener(e -> add());
        mainWindow.getBtnClear().addActionListener(e -> clear());
    }

    private void add() {
        JFileChooser fileChooser = GeoFileChooserFactory.getLoadFileDialog("Select your data!",
                new String[]{GeoFileExtensions.TS,
                    GeoFileExtensions.WL,
                    GeoFileExtensions.SHP,
                    GeoFileExtensions.XLSX});
        if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File[] selectedFiles = fileChooser.getSelectedFiles();
            for (int i = 0; i < selectedFiles.length; i++) {
                GeoFileReader reader = new GeoFileReader(selectedFiles[i].getAbsolutePath());
                for(GeoFileObject obj : reader.getObjects()) {
                    mainWindow.getTableModel().addRow(obj);
                }
                mainWindow.getFileTable().updateUI();
            }
        }
        
    }

    private void clear() {
        mainWindow.getTableModel().removeAllElements();
        mainWindow.getFileTable().updateUI();
        mainWindow.getFileTable().clearSelection();
    }

}
