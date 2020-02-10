
package de.hsbo.fbg.hlnug.controller;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

/**
 * @author Moritz Wollenhaupt <moritz.wollenhaupt@hs-bochum.de>
 */
public class GeoFileChooserFactory {
    
    
    
    private GeoFileChooserFactory() {
    }
    
    public static JFileChooser getLoadFileDialog(String title, String[] extensions) {
        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        fileChooser.setDialogTitle(title);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setMultiSelectionEnabled(true);
        String ext = "";
        for(String e : extensions) {
            ext += "*."+e+";";
        }
        FileNameExtensionFilter filter = new FileNameExtensionFilter(ext, extensions);
        fileChooser.setFileFilter(filter);
        
        
        return fileChooser;
    }
    
}
