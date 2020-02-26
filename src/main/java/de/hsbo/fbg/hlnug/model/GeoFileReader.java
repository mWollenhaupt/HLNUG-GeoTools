package de.hsbo.fbg.hlnug.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.n52.v3d.triturus.geologic.importers.GocadDataInfo;
import org.n52.v3d.triturus.geologic.importers.IoGocadTSurfReader;

/**
 * Simple file reader that stores all read files in GeoFileObject instances
 * Important: GeoFileObjects do not store any geometries. They are only read in
 * during processing
 *
 * @author Moritz Wollenhaupt <moritz.wollenhaupt@hs-bochum.de>
 */
public class GeoFileReader {

    /* All read file (GeoFile)Objects will be stored in a collection. 
    The reason for this is, that GOCAD files for example can store multiple 
    geometries in a single file. Each geometrie object is stored as 
    an own GeoFileObject*/
    private List<GeoFileObject> objects;

    /**
     * Constructor that takes the file's path to be read
     *
     * @param path The file's path to be read
     */
    public GeoFileReader(String path) {
        objects = new ArrayList<>();
        read(path);
    }

    /**
     * Reads an given file object and stores the containend geometrie object in
     * a GeoFileObject instance
     *
     * @param path The file's path to be read
     */
    private void read(String path) {
        // first: check for correct file extension
        Optional<String> extensionCheck = getFileExtension(path);
        if (!extensionCheck.isPresent()) {
            return; // TODO: Throw exception
        }
        String ext = extensionCheck.get();

        // check for what kind of file type you have to read
        if (ext.toLowerCase().equals(GeoFileExtensions.get(GeoFileExtensions.SKUA_GOCAD_TSURF))) {
            List<GocadDataInfo> info = new IoGocadTSurfReader().getInfo(path);
            if (info != null) {
                for (int i = 0; i < info.size(); i++) {
                    GeoFileObject obj = new GeoFileObject(GeoFileExtensions.get(GeoFileExtensions.SKUA_GOCAD_TSURF), path, info.get(i).getObjectName(), i);
                    objects.add(obj);
                }
            }
        } else if (ext.toLowerCase().equals(GeoFileExtensions.get(GeoFileExtensions.CSV))) {
            GeoFileObject csv = new GeoFileObject(GeoFileExtensions.get(GeoFileExtensions.CSV), path, new File(path).getName().replaceAll(".csv", ""), 0);
            objects.add(csv);
        } else {
            // todo: Throw exception
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
