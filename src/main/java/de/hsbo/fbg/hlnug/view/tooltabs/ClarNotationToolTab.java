package de.hsbo.fbg.hlnug.view.tooltabs;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;

/**
 * @author Moritz Wollenhaupt <moritz.wollenhaupt@hs-bochum.de>
 */
public class ClarNotationToolTab extends AbstractToolTab {
    
    public ClarNotationToolTab(String title, Icon icon, String tip) {
        super(title, icon, tip);
    }

    @Override
    public void showGui() {
        // init this tab with GridBafLayout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        // init label
        JLabel info = new JLabel("Was soll berechnet werden?");
        // init checkboxes
        JCheckBox dip = new JCheckBox("DIP");
        JCheckBox dipDir = new JCheckBox("DIP DIRECTION");
        JCheckBox strike = new JCheckBox("STRIKE");
        JCheckBox compassDir = new JCheckBox("HIMMELSRICHTUNG");
        //set preselections
        dip.setSelected(true);
        dipDir.setSelected(true);
        strike.setSelected(true);
        //init button
        JButton btnRun = new JButton("OK..");
        
        // arrange components
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipadx = 40;
        gbc.weighty = 0.0;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(info, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(dip, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(dipDir, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(strike, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(compassDir, gbc);
        
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.LAST_LINE_END;
        gbc.gridx = 1;
        gbc.gridy = 5;
        add(btnRun, gbc);
        
        
    }
    
    
    
}
