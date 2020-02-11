package de.hsbo.fbg.hlnug.controller;

import de.hsbo.fbg.hlnug.model.GeoFileExtensions;
import de.hsbo.fbg.hlnug.model.GeoFileObject;
import de.hsbo.fbg.hlnug.model.GeoFileReader;
import de.hsbo.fbg.hlnug.view.FeedbackFrame;
import de.hsbo.fbg.hlnug.view.MainWindow;
import de.hsbo.fbg.hlnug.view.tooltabs.ClarNotationToolTab;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.n52.v3d.triturus.core.T3dException;
import org.n52.v3d.triturus.core.T3dNotYetImplException;
import org.n52.v3d.triturus.geologic.exporters.IoShapeWriter;
import org.n52.v3d.triturus.geologic.exporters.util.ClarNotationShapeFileAttribute;
import org.n52.v3d.triturus.geologic.exporters.util.ShapeFileAttribute;
import org.n52.v3d.triturus.geologic.importers.IoGocadTSurfReader;
import org.n52.v3d.triturus.gisimplm.GmSimpleTINFeature;
import org.opengis.referencing.FactoryException;

/**
 * @author Moritz Wollenhaupt <moritz.wollenhaupt@hs-bochum.de>
 */
public class ClarNotationTabController {

    private MainWindow mainWindow;
    private ClarNotationToolTab cnTab;

    public ClarNotationTabController(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        this.cnTab = mainWindow.getClarNotationTab();
        initController();
    }

    private void initController() {
        cnTab.getBtnRun().addActionListener(e -> exec());

    }

    private void exec() {

        // run execution in thread for non blocking gui
        Thread thread;
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // get selected row indices
                int[] rows = mainWindow.getFileTable().getSelectedRows();
                // do some input checks
                if (rows.length != 1) {
                    JOptionPane.showMessageDialog(mainWindow.getMainFrame(),
                            "Die Ausführung dieses Tools erfordert die Selektion genau eines TSURF-Objektes.",
                            "Selektion ungültig",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                GeoFileObject selection = (GeoFileObject) mainWindow.getFileTable().getValueAt(rows[0], 0);
                if (!(selection.getType() == GeoFileReader.TSURF)) {
                    JOptionPane.showMessageDialog(mainWindow.getMainFrame(),
                            "Bitte wähle ein Objekt vom Typ TSURF.",
                            "Selektion ungültig",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                System.out.println(selection + " " + selection.getIdx());
                // choose new file path
                JFileChooser fileChooser = GeoFileChooserFactory.getSaveFileDialog(
                        "Bitte Speicherpfad auswählen",
                        new String[]{GeoFileExtensions.SHP},
                        selection);
                // if new file accepted
                if (fileChooser.showSaveDialog(mainWindow.getMainFrame()) == JFileChooser.APPROVE_OPTION) {
                    String fileToSave = fileChooser.getSelectedFile().getAbsolutePath();
                    // check for file extension
                    if (!fileToSave.endsWith(".shp")) {
                        fileToSave += ".shp";
                    }
                    String fileToRead = selection.getPath();
                    // query user selections 
                    boolean dip = cnTab.getDip().isSelected();
                    boolean dipDir = cnTab.getDipDir().isSelected();
                    boolean strike = cnTab.getStrike().isSelected();
                    boolean compassDir = cnTab.getCompassDir().isSelected();
                    //create feedbackframe for user response
                    FeedbackFrame fb = new FeedbackFrame(mainWindow.getMainFrame());
                    fb.start();
                    // start reading TSURF data
                    IoGocadTSurfReader reader = new IoGocadTSurfReader();
                    GmSimpleTINFeature surf = reader.read(fileToRead).get(selection.getIdx());
                    IoShapeWriter shpWriter = new IoShapeWriter();
                    ClarNotationShapeFileAttribute attribute = new ClarNotationShapeFileAttribute(dip, dipDir, strike, compassDir);
                    List<ShapeFileAttribute> attributes = new ArrayList<>();
                    attributes.add(attribute);
                    try {
                        shpWriter.initFeatureType(IoShapeWriter.MULTI_POLYGON, "31467", attributes);
                        shpWriter.buildFeatureType();
                        shpWriter.createPolygonZFeatures(surf);
                        shpWriter.writeShapeFile(fileToSave);
                        fb.stopSuccessful();
                    } catch (IOException | T3dNotYetImplException | T3dException | FactoryException ex) {
                        Logger.getLogger(ClarNotationTabController.class.getName()).log(Level.SEVERE, null, ex);
                        fb.stopUnuccessful();
                    }

                }

            }
        });
        thread.setDaemon(true);
        thread.start();

    }

}
