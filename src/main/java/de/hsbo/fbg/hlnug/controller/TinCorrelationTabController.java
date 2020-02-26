package de.hsbo.fbg.hlnug.controller;

import de.hsbo.fbg.hlnug.model.GeoFileExtensions;
import de.hsbo.fbg.hlnug.model.GeoFileObject;
import de.hsbo.fbg.hlnug.util.ToolExecutionThreadPool;
import de.hsbo.fbg.hlnug.view.LoggingPanel;
import de.hsbo.fbg.hlnug.view.MainWindow;
import de.hsbo.fbg.hlnug.view.tooltabs.TinCorrelationToolTab;
import org.n52.v3d.triturus.geologic.analysis.FltTINCorrelation;
import org.n52.v3d.triturus.geologic.importers.IoGocadTSurfReader;
import org.n52.v3d.triturus.gisimplm.GmSimpleElevationGrid;
import org.n52.v3d.triturus.gisimplm.GmSimpleTINFeature;

/**
 * @author Moritz Wollenhaupt <moritz.wollenhaupt@hs-bochum.de>
 */
public class TinCorrelationTabController {

    private MainWindow mainWindow;           // reference to mainWindow
    private LoggingPanel logPanel;           // reference to mainWindow's logging area
    private TinCorrelationToolTab tinTab;    // referecne to mainWindow's VirtualRedrillTab

    private ToolExecutionThreadPool threadPool;
    private FltTINCorrelation fltTinCorrelation;

    public TinCorrelationTabController(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        this.logPanel = mainWindow.getLogPanel();
        this.tinTab = mainWindow.getTinCorrelationTab();
        this.threadPool = new ToolExecutionThreadPool();
        this.fltTinCorrelation = new FltTINCorrelation();
        initController();
    }

    private void initController() {
        tinTab.getBtnRun().addActionListener(e -> execute());
    }

    private void execute() {
        threadPool.execute(() -> {
            // get selected row indices
            int[] rows = mainWindow.getFileTable().getSelectedRows();
            // do some input checks
            if (rows.length != 2) {
                logPanel.errorLog("Bitte selektiere (genau) zwei 'ts' (Gocad-TSurf) Objekte");
                return;
            }
            GeoFileObject[] geoObjects = new GeoFileObject[2];
            for (int i = 0; i < rows.length; i++) {
                GeoFileObject selection = (GeoFileObject) mainWindow.getFileTable().getValueAt(rows[i], 0);
                if (!(selection.getType().equals(GeoFileExtensions.get(GeoFileExtensions.SKUA_GOCAD_TSURF)))) {
                    logPanel.errorLog("Bitte nur Objekte vom Typ 'ts' (Gocad-TSurf) angeben!");
                    return;
                } else {
                    geoObjects[i] = selection;
                }
            }
            // collect user selection
            String mvnWinSize = (String) tinTab.getMvnWinSize().getSelectedItem();
            String gridWidth = (String) tinTab.getGridWidth().getSelectedItem();
            String zConflictHandler = (String) tinTab.getzConflictHandler().getSelectedItem();
            String outputFormat = (String) tinTab.getOutputFormat().getSelectedItem();
            // read selected tins
            IoGocadTSurfReader reader = new IoGocadTSurfReader();
            GmSimpleTINFeature tin1 = reader.read(geoObjects[0].getPath()).get(geoObjects[0].getIdx());
            GmSimpleTINFeature tin2 = reader.read(geoObjects[1].getPath()).get(geoObjects[1].getIdx());
            
            // set up correlation calculation attributes
            fltTinCorrelation.setWindowSize(Short.parseShort(mvnWinSize.split("x")[0]));
            fltTinCorrelation.setTin1(tin1);
            fltTinCorrelation.setTin2(tin2);
            // do calc
            GmSimpleElevationGrid grid = fltTinCorrelation.transform(tin1, tin2); // Setter? transform?
            
            switch (outputFormat) {
                case GeoFileExtensions.ESRI_SHAPEFILE:

                    // TODO: Write GmSimpleElevationGrid -> SHP
                    break;

                case GeoFileExtensions.ARC_INFO_ASCII_GRID:
                    throw new UnsupportedOperationException("Not yet implemented!");
                case GeoFileExtensions.VTK_DATASET_FILE:
                    throw new UnsupportedOperationException("Not yet implemented!");
                default:
                    break;
            }

        });

    }

}
