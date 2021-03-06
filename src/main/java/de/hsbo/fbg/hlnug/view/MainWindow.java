package de.hsbo.fbg.hlnug.view;

import de.hsbo.fbg.hlnug.model.GeoFileTableModel;
import de.hsbo.fbg.hlnug.view.tooltabs.ClarNotationToolTab;
import de.hsbo.fbg.hlnug.view.tooltabs.TinCorrelationToolTab;
import de.hsbo.fbg.hlnug.view.tooltabs.ToolTabPane;
import de.hsbo.fbg.hlnug.view.tooltabs.VirtualRedrillToolTab;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;

/**
 * @author Moritz Wollenhaupt <moritz.wollenhaupt@hs-bochum.de>
 */
public class MainWindow {

    private JButton btnAdd, btnClear, btnRemove;
    private GeoFileTableModel tableModel;
    private JTable fileTable;
    private JFrame mainFrame;
    private LoggingPanel logPanel;

    private ClarNotationToolTab clarNotationTab;
    private VirtualRedrillToolTab virtualRedrillTab;
    private TinCorrelationToolTab tinCorrelationTab;

    public MainWindow() {
        initMainWindow();
    }

    private void initMainWindow() {
        // init the frame
        mainFrame = new JFrame("HLNUG-GeoTools");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setPreferredSize(new Dimension(840, 530));
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
        // init first level panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        // init second level panels
        JPanel leftPanel = new JPanel(new BorderLayout());
        JPanel rightPanel = new JPanel(new BorderLayout());
        // init TableView for leftPanel
        tableModel = new GeoFileTableModel();
        fileTable = new JTable(tableModel);
        fileTable.setDefaultRenderer(Object.class, new GeoFileTableCellRenderer(fileTable));
        fileTable.getColumnModel().getColumn(0).setPreferredWidth(80);
        fileTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        fileTable.getColumnModel().getColumn(2).setPreferredWidth(200);
        // make table scrollable as needed
        JScrollPane scrollableTable = new JScrollPane(fileTable);
        scrollableTable.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollableTable.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        // init buttonbar for insert/delete/.. table data
        JPanel buttonBar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnAdd = new JButton("Hinzufügen");
        btnClear = new JButton("Alles entfernen");
        btnRemove = new JButton("Selektion entfernen");
        // init TabPane for arranging tools
        ToolTabPane tabPane = new ToolTabPane();
        // here: add tabs!
        clarNotationTab = new ClarNotationToolTab("Clarwerte", null, "");
        tabPane.addTab(clarNotationTab);
        virtualRedrillTab = new VirtualRedrillToolTab("Virtuelle Nachbohrung", null, "");
        tabPane.addTab(virtualRedrillTab);
        tinCorrelationTab = new TinCorrelationToolTab("TIN-Korrelation", null, "");
        tabPane.addTab(tinCorrelationTab);

        // init logo and info
        JLabel infoLabel = null;
        try {
            infoLabel = new JLabel("Version 0.1", new ImageIcon(ImageIO.read(getClass().getClassLoader().getResourceAsStream("icon_hlnug.png"))), 0);
        } catch (IOException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        // add the initialized components onto their panels
        buttonBar.add(btnAdd);
        buttonBar.add(btnRemove);
        buttonBar.add(btnClear);
        leftPanel.add(scrollableTable, BorderLayout.CENTER);
        leftPanel.add(buttonBar, BorderLayout.SOUTH);

        rightPanel.add(tabPane, BorderLayout.CENTER);
        JPanel infoWrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        if (infoLabel != null) {
            infoLabel.setBorder(BorderFactory.createEmptyBorder(10, 5, 0, 5));
            infoWrapper.add(infoLabel);
        }
        rightPanel.add(infoWrapper, BorderLayout.SOUTH);

        mainPanel.add(leftPanel, BorderLayout.CENTER);
        mainPanel.add(rightPanel, BorderLayout.EAST);

        // Create the logging panel and add it at the application's south
        logPanel = new LoggingPanel();
        mainPanel.add(logPanel, BorderLayout.SOUTH);

        mainFrame.add(mainPanel);

        // beautify
        leftPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

    }

    public JButton getBtnAdd() {
        return btnAdd;
    }

    public JButton getBtnClear() {
        return btnClear;
    }

    public JButton getBtnRemove() {
        return btnRemove;
    }

    public JTable getFileTable() {
        return fileTable;
    }

    public GeoFileTableModel getTableModel() {
        return tableModel;
    }

    public ClarNotationToolTab getClarNotationTab() {
        return clarNotationTab;
    }

    public VirtualRedrillToolTab getVirtualRedrillTab() {
        return virtualRedrillTab;
    }

    public JFrame getMainFrame() {
        return mainFrame;
    }

    public LoggingPanel getLogPanel() {
        return logPanel;
    }

    public TinCorrelationToolTab getTinCorrelationTab() {
        return tinCorrelationTab;
    }

}
