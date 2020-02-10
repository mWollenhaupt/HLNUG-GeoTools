package de.hsbo.fbg.hlnug.view.tooltabs;

import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * @author Moritz Wollenhaupt <moritz.wollenhaupt@hs-bochum.de>
 */
public abstract class AbstractToolTab extends JPanel {
    
    private String title;
    private String tip;
    private Icon icon;
    
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

    public abstract void showGui();
    
}
