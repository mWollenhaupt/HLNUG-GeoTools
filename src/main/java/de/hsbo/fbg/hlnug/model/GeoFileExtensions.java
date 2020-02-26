package de.hsbo.fbg.hlnug.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Moritz Wollenhaupt <moritz.wollenhaupt@hs-bochum.de>
 */
public class GeoFileExtensions {

    /**
     * Codelist class with HLNUG-Geologic-Tools valid file extensions
     */
    public static final String SKUA_GOCAD_TSURF = "SKUA-GOCAD TSurf";
    public static final String SKUA_GOCAD_WELL = "SKUA-GOCAD Well";
    public static final String CSV = "CSV";
    public static final String ESRI_SHAPEFILE = "ESRI Shapefile";
    public static final String ARC_INFO_ASCII_GRID = "Arc/Info ASCII Grid";    
    public static final String VTK_DATASET_FILE = "VTK Dataset File";       
    
    // Map for mapping JComboBox-Items to their extensions -> Grab all Extensions via get() method
    private static final Map<String, String> extensions;
    static {
        extensions = new HashMap<>();
        extensions.put(SKUA_GOCAD_TSURF, "ts");
        extensions.put(SKUA_GOCAD_WELL, "wl");
        extensions.put(ESRI_SHAPEFILE, "shp");
        extensions.put(CSV, "csv");
        extensions.put(ARC_INFO_ASCII_GRID, "");  // EXTENSION?
        extensions.put(VTK_DATASET_FILE, "");     // EXTENSION?
    }
    
    public static String get(String extension) {
        return extensions.get(extension);
    }
    
    

}
