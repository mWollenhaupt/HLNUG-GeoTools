package de.hsbo.fbg.hlnug.view.tooltabs;

import javax.swing.JTabbedPane;

/**
 * Simple tab-wrapper class for an own Pane that stores the ToolTabs
 * 
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
