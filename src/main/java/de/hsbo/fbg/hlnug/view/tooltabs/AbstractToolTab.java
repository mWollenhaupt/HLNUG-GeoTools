package de.hsbo.fbg.hlnug.view.tooltabs;

import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * Abstract tool tab class. You need to extend this class to provide more tabs
 * that contain more tools
 *
 * @author Moritz Wollenhaupt <moritz.wollenhaupt@hs-bochum.de>
 */
public abstract class AbstractToolTab extends JPanel {

    private String title;
    private String tip;
    private Icon icon;

    /**
     * Every ToolTab can consist of a title and/or and icon. You can also add an
     * tooltip which is shown by hovering tab in the gui.
     *
     * @param title The tool tabs title
     * @param icon  An icon that can be shown
     * @param tip   An tooltip, shown by hovering
     */
    public AbstractToolTab(String title, Icon icon, String tip) {
        this.title = title;
        this.icon = icon;
        this.tip = tip;
        setBorder(new EmptyBorder(5, 5, 5, 5));
        this.showGui();
    }

    public String getTitle() {
        return this.title;
    }

    public String getTip() {
        return this.tip;
    }

    public Icon getIcon() {
        return this.icon;
    }

    /**
     * here you have to initialize the gui elements
     */
    public abstract void showGui();

}
