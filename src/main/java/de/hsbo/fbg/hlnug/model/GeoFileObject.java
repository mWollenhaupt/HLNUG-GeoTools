package de.hsbo.fbg.hlnug.model;

/**
 * @author Moritz Wollenhaupt <moritz.wollenhaupt@hs-bochum.de>
 */
public class GeoFileObject {
    
    private final String type;
    private final String path;
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
        return "GeoFileObject[type="+getType()+", path="+getPath()+", name="+getName()+"]";
    }
    
    
    
    
    
    
    
}
