package de.hsbo.fbg.hlnug.view.tooltabs;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.Icon;
import javax.swing.JButton;

/**
 * @author Moritz Wollenhaupt <moritz.wollenhaupt@hs-bochum.de>
 */

public class VirtualRedrillTab extends AbstractToolTab {
    
    private JButton btnRun;

    public VirtualRedrillTab(String title, Icon icon, String tip) {
        super(title, icon, tip);
    }

    /**
     * here you have to initialize the gui elements
     */
    @Override
    public void showGui() {
        // init this tab with GridBagLayout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        // arrange components
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipadx = 40;
        gbc.weighty = 0.0;
        gbc.gridx = 0;
        gbc.gridy = 0;
        
        
        
        this.btnRun = new JButton("OK..");
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.LAST_LINE_END;
        gbc.gridx = 1;
        gbc.gridy = 5;
        add(btnRun, gbc);
        
    }

    public JButton getBtnRun() {
        return btnRun;
    }

    
    
}
