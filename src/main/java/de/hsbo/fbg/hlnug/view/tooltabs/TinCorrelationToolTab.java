package de.hsbo.fbg.hlnug.view.tooltabs;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.HashMap;
import java.util.Map;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import org.n52.v3d.triturus.geologic.analysis.FltTINCorrelation;

/**
 * @author Moritz Wollenhaupt <moritz.wollenhaupt@hs-bochum.de>
 */
public class TinCorrelationToolTab extends AbstractToolTab {

    private JButton btnRun;
    private JComboBox mvnWinSize, gridWidth, zConflictHandler, outputFormat;
    private FltTINCorrelation t;

    public TinCorrelationToolTab(String title, Icon icon, String tip) {
        super(title, icon, tip);
    }

    @Override
    public void showGui() {
        this.t = new FltTINCorrelation();
        // init this tab with GridBagLayout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        JLabel mvnWinSizeLabel = new JLabel("Größe des gleitenden Fensters");
        String[] mvnWinSizeVals = t.UIDialog_MovingWindowSizeOptions();
        this.mvnWinSize = new JComboBox(mvnWinSizeVals);

        JLabel gridWidthLabel = new JLabel("Rasterweite");
        String[] gridWidthVals = t.UIDialog_CellSizeOptions();
        this.gridWidth = new JComboBox(gridWidthVals);

        JLabel zConflictHandlerLabel = new JLabel("Z-Konflikt-Handler");
        String[] zConflictHandlerVals = t.UIDialog_ZConflictOptions();
        this.zConflictHandler = new JComboBox(zConflictHandlerVals);

        JLabel outputFormatLabel = new JLabel("Ausgabeformat");
        String outputFormatVals[] = t.UIDialog_OutputFormatOptions();
        this.outputFormat = new JComboBox(outputFormatVals);

        this.btnRun = new JButton("OK..");

        // arrange components
        gbc.insets = new Insets(5, 5, 0, 5);
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 0.0;
        gbc.weightx = 0.0;
        gbc.gridwidth = 5;
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(mvnWinSizeLabel, gbc);
        gbc.gridx = 5;
        gbc.gridy = 0;
        add(mvnWinSize, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(gridWidthLabel, gbc);
        gbc.gridx = 5;
        gbc.gridy = 1;
        add(gridWidth, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(zConflictHandlerLabel, gbc);
        gbc.gridx = 5;
        gbc.gridy = 2;
        add(zConflictHandler, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(outputFormatLabel, gbc);
        gbc.gridx = 5;
        gbc.gridy = 3;
        add(outputFormat, gbc);

        
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.REMAINDER;
        gbc.weighty = 1.0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.LAST_LINE_END;
        gbc.gridx = 9;
        gbc.gridy = 4;
        add(btnRun, gbc);

    }

    public JButton getBtnRun() {
        return btnRun;
    }

    public JComboBox getMvnWinSize() {
        return mvnWinSize;
    }

    public JComboBox getGridWidth() {
        return gridWidth;
    }

    public JComboBox getzConflictHandler() {
        return zConflictHandler;
    }

    public JComboBox getOutputFormat() {
        return outputFormat;
    }

}
