package de.hsbo.fbg.hlnug.view.tooltabs;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;

/**
 * Concrete implementation of a ToolTab. This tab contains the ClarNotation tool
 * 
 * @author Moritz Wollenhaupt <moritz.wollenhaupt@hs-bochum.de>
 */
public class ClarNotationToolTab extends AbstractToolTab {
    
    private JCheckBox dip, dipDir, strike, compassDir;
    private JButton btnRun;
    
    public ClarNotationToolTab(String title, Icon icon, String tip) {
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
        // init label
        JLabel info = new JLabel("Was soll berechnet werden?");
        // init checkboxes
        dip = new JCheckBox("Dip");
        dipDir = new JCheckBox("Dip Direction");
        strike = new JCheckBox("Strike");
        compassDir = new JCheckBox("Himmelsrichtung");
        //set preselections
        dip.setSelected(true);
        dipDir.setSelected(true);
        strike.setSelected(true);
        //init button
        btnRun = new JButton("OK..");
        
        JLabel placeholder = new JLabel("");
        
        // arrange components
//        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
//        gbc.anchor = GridBagConstraints.LINE_START;
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        gbc.ipadx = 5;
//        gbc.weighty = 0.0;
        gbc.insets = new Insets(2, 5, 0, 5);
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 0.0;
        gbc.weightx = 0.0;
        gbc.gridwidth = 5;
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(info, gbc);
        gbc.gridx = 5;
        gbc.gridy = 0;
        add(placeholder, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(dip, gbc);
        gbc.gridx = 5;
        gbc.gridy = 1;
        add(placeholder, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(dipDir, gbc);
        gbc.gridx = 5;
        gbc.gridy = 2;
        add(placeholder, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(strike, gbc);
        gbc.gridx = 5;
        gbc.gridy = 3;
        add(placeholder, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(compassDir, gbc);
        gbc.gridx = 5;
        gbc.gridy = 4;
        add(placeholder, gbc);
        
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.REMAINDER;
        gbc.weighty = 1.0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.LAST_LINE_END;
        gbc.gridx = 9;
        gbc.gridy = 5;
        add(btnRun, gbc);
    }

    public JCheckBox getDip() {
        return dip;
    }

    public JCheckBox getDipDir() {
        return dipDir;
    }

    public JCheckBox getStrike() {
        return strike;
    }

    public JCheckBox getCompassDir() {
        return compassDir;
    }

    public JButton getBtnRun() {
        return btnRun;
    }
    
    
    
}
