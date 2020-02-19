package de.hsbo.fbg.hlnug.model;

/**
 * Class that stores the file tables data model. It's a wrapper for many
 * possible file types, used by the HLNUG
 * 
 * !!! GeoFileObjects ARE ONLY REFERENCES TO FILES !!!
 * !!! GEOMETRIES ARE ONLY READ WHILE PROCESSING !!!
 *
 * @author Moritz Wollenhaupt <moritz.wollenhaupt@hs-bochum.de>
 */
public class GeoFileObject {

    private final String type;          // Identifier for the file's type, e.g. "Shape" or "TSurf"
    private final String path;          // A string referencing the file's path
    
    // The internal objects name. GOCAD files for example can store multiple geometries in a single file. 
    // You can access these internal objects by their name and index
    private final String name;          
    private final int idx;

    public GeoFileObject(String type, String path, String name, int idx) {
        this.type = type;
        this.path = path;
        this.name = name;
        this.idx = idx;
    }

    public String getType() {
        return type;
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    public int getIdx() {
        return idx;
    }

    @Override
    public String toString() {
        return "GeoFileObject[type=" + getType() + ", path=" + getPath() + ", name=" + getName() + "]";
    }

}
