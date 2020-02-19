package de.hsbo.fbg.hlnug.controller;

import de.hsbo.fbg.hlnug.model.GeoFileObject;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

/**
 * Helper class for creating correct file choosing dialogs
 *
 * @author Moritz Wollenhaupt <moritz.wollenhaupt@hs-bochum.de>
 */
public class GeoFileChooserFactory {

    private GeoFileChooserFactory() {
    }

    /**
     *
     * @param title The dialogs title
     * @param extensions An array that contains the holds the file extensions to
     * filter on
     * @return The file chooser dialog for reading a new file
     */
    public static JFileChooser getLoadFileDialog(String title, String[] extensions) {
        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        fileChooser.setDialogTitle(title);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setMultiSelectionEnabled(true);
        String ext = "";
        for (String e : extensions) {
            ext += "*." + e + ";";
        }
        FileNameExtensionFilter filter = new FileNameExtensionFilter(ext, extensions);
        fileChooser.setFileFilter(filter);

        return fileChooser;
    }

    /**
     *
     * @param title The dialogs title
     * @param extensions An array that contains the holds the file extensions to
     * filter on
     * @param selection The currenctly selected Object. Used for preferring new save path.
     * @return The file chooser dialog for saving a new file
     */
    public static JFileChooser getSaveFileDialog(String title, String[] extensions, GeoFileObject selection) {
        JFileChooser fileChooser = new JFileChooser();
        String path = selection.getPath();
        int endIdx = path.lastIndexOf("\\");
        path = path.substring(0, endIdx);
        fileChooser.setSelectedFile(new File(path + "/" + selection.getName() + "." + extensions[0]));
        fileChooser.setDialogTitle(title);
        String ext = "";
        for (String e : extensions) {
            ext += "*." + e + ";";
        }
        FileNameExtensionFilter filter = new FileNameExtensionFilter(ext, extensions);
        fileChooser.setFileFilter(filter);

        return fileChooser;
    }

}
