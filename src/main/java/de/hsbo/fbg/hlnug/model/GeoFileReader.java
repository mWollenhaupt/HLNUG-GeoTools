package de.hsbo.fbg.hlnug.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.n52.v3d.triturus.geologic.importers.GocadDataInfo;
import org.n52.v3d.triturus.geologic.importers.IoGocadTSurfReader;

/**
 * @author Moritz Wollenhaupt <moritz.wollenhaupt@hs-bochum.de>
 */
public class GeoFileReader {

    public static final String VWELL = "VIRTUAL WELL";
    public static final String RWELL = "REAL WELL";
    public static final String RAWWELL = "RAW WELL";
    public static final String TSURF = "TSURF";
    public static final String SHP = "SHAPE";

    private List<GeoFileObject> objects;

    public GeoFileReader(String path) {
        objects = new ArrayList<>();
        read(path);
    }

    private void read(String path) {
        Optional<String> extensionCheck = getFileExtension(path);
        if (!extensionCheck.isPresent()) {
            return; // TODO: Throw exception
        }
        String ext = extensionCheck.get();
        
        switch(ext.toLowerCase()) {
            case GeoFileExtensions.TS:
                List<GocadDataInfo> info = new IoGocadTSurfReader().getInfo(path);
                if (info != null) {
                    for(int i = 0; i < info.size(); i++) {
                        GeoFileObject obj = new GeoFileObject(TSURF, path, info.get(i).getObjectName(), i);
                        objects.add(obj);
                    }
                }
                break;
            case GeoFileExtensions.WL:
                break;
            case GeoFileExtensions.SHP:
                GeoFileObject shp = new GeoFileObject(SHP, path, new File(path).getName(), 0);
                objects.add(shp);
                break;
            case GeoFileExtensions.XLSX:
                GeoFileObject xlsx = new GeoFileObject(RAWWELL, path, new File(path).getName(), 0);
                objects.add(xlsx);
                break;
            default:
                return; // TODO: Throw exception
        }
    }

    public Optional<String> getFileExtension(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }

    public List<GeoFileObject> getObjects() {
        return objects;
    }

    
    
}
