package de.hsbo.fbg.hlnug.view.tooltabs;

import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JTabbedPane;

/**
 * @author Moritz Wollenhaupt <moritz.wollenhaupt@hs-bochum.de>
 */
public class ToolTabPane extends JTabbedPane {
    
    public ToolTabPane() {
        super();
    }
    
    public void addTab(AbstractToolTab toolTab) {
        super.addTab(toolTab.getTitle(), toolTab.getIcon(), toolTab, toolTab.getTip());
    }
    
}
