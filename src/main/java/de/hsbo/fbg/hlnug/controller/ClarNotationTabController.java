package de.hsbo.fbg.hlnug.controller;

import de.hsbo.fbg.hlnug.model.GeoFileExtensions;
import de.hsbo.fbg.hlnug.model.GeoFileObject;
import de.hsbo.fbg.hlnug.model.GeoFileReader;
import de.hsbo.fbg.hlnug.view.LoggingPanel;
import de.hsbo.fbg.hlnug.view.MainWindow;
import de.hsbo.fbg.hlnug.view.tooltabs.ClarNotationToolTab;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import org.n52.v3d.triturus.core.T3dException;
import org.n52.v3d.triturus.core.T3dNotYetImplException;
import org.n52.v3d.triturus.geologic.exporters.IoShapeWriter;
import org.n52.v3d.triturus.geologic.exporters.util.ClarNotationShapeFileAttribute;
import org.n52.v3d.triturus.geologic.exporters.util.ShapeFileAttribute;
import org.n52.v3d.triturus.geologic.importers.IoGocadTSurfReader;
import org.n52.v3d.triturus.geologic.util.CRSRecommender;
import org.n52.v3d.triturus.gisimplm.GmSimpleTINFeature;
import org.n52.v3d.triturus.gisimplm.GmSimpleTINGeometry;
import org.opengis.referencing.FactoryException;

/**
 * Controler class for ClarNotationToolTab that handels user input.
 *
 * @author Moritz Wollenhaupt <moritz.wollenhaupt@hs-bochum.de>
 */
public class ClarNotationTabController {

    private MainWindow mainWindow;           // reference to mainWindow
    private LoggingPanel logPanel;           // reference to mainWindow's logging area
    private ClarNotationToolTab cnTab;      // referecne to mainWindow'S ClarNotationToolTab

    public ClarNotationTabController(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        this.logPanel = mainWindow.getLogPanel();
        this.cnTab = mainWindow.getClarNotationTab();
        initController();
    }

    /**
     * here: initialize all Listeners do not forget to execute long term
     * calculations in an own thread, otherwise the GUI will block any following
     * input
     */
    private void initController() {
        cnTab.getBtnRun().addActionListener(e -> exec());

    }

    /**
     * starts calculating the clar notation values, writes the selected geometry
     * in a new shape file and adds the calculated values as attributes
     */
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
                    logPanel.errorLog("Die Ausführung dieses Tools erfordert die Selektion genau eines TSURF-Objektes!");
                    return;
                }
                GeoFileObject selection = (GeoFileObject) mainWindow.getFileTable().getValueAt(rows[0], 0);
                if (!(selection.getType() == GeoFileReader.TSURF)) {
                    logPanel.errorLog("Bitte wähle ein Objekt vom Typ TSURF!");
                    return;
                }
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
                    // create feedbackframe for user response
                    // start reading TSURF data
                    IoGocadTSurfReader reader = new IoGocadTSurfReader();
                    GmSimpleTINFeature surf = reader.read(fileToRead).get(selection.getIdx());
                    int numTriangles = ((GmSimpleTINGeometry) surf.getGeometry()).numberOfTriangles();
                    logPanel.appendLogString("Anzahl zu verarbeitender Dreiecke: " + numTriangles);
                    if (numTriangles > 300000) {
                        logPanel.appendLogString("WARNUNG! \tDie Berechnung von mehr als 300000 Dreiecken ist zeit- und speicherintensiv!");
                    }
                    logPanel.startCalculationFeedback("Clarwertberechnung angestoßen..");
                    // auto match the CRS by extent of given geometry
                    String epsg = CRSRecommender.recommendEPSG(surf.envelope());
                    // initalize a new shape writer
                    IoShapeWriter shpWriter = new IoShapeWriter();
                    // initialize the attributes of the shape file. 
                    // you have to write your own ShapeFileAttributeClass, derived from Triturus' abstract ShapeFileAttribute class.
                    ClarNotationShapeFileAttribute attribute = new ClarNotationShapeFileAttribute(dip, dipDir, strike, compassDir);
                    // Add them to a collection (more than one specific attribute type is possible in this way)
                    List<ShapeFileAttribute> attributes = new ArrayList<>();
                    attributes.add(attribute);
                    try {
                        // initialize and build the FeatureType you want to use by giving the Geom-Type, the CRS as an EPSG-String and the attribute(columns)
                        shpWriter.initFeatureType(IoShapeWriter.MULTI_POLYGON, epsg, attributes);
                        shpWriter.buildFeatureType();
                        // create file's geometrie and calc attributes
                        shpWriter.createPolygonZFeatures(surf);
                        // open a new datastore and write all geometries and their attributes to an file
                        shpWriter.writeShapeFile(fileToSave);
                        logPanel.stopCalculationFeedback("Clarwertberechnung erfolgreich!");
                    } catch (IOException | T3dNotYetImplException | T3dException | FactoryException ex) {
                        Logger.getLogger(ClarNotationTabController.class.getName()).log(Level.SEVERE, null, ex);
                        logPanel.stopCalculationFeedback("Bei der Clarwertberechnung ist ein Fehler aufgetreten!");
                    }

                }

            }
        });
        thread.setDaemon(true);
        thread.start();

    }

}
