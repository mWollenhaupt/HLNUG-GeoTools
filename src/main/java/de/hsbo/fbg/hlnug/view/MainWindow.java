/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsbo.fbg.hlnug.view;

import de.hsbo.fbg.hlnug.model.GeoFileTableModel;
import de.hsbo.fbg.hlnug.view.tooltabs.ClarNotationToolTab;
import de.hsbo.fbg.hlnug.view.tooltabs.ToolTabPane;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;

/**
 *
 * @author Moritz Wollenhaupt <moritz.wollenhaupt@hs-bochum.de>
 */
public class MainWindow {
    
    private JButton btnAdd, btnClear;
    private GeoFileTableModel tableModel;
    private JTable fileTable;

    public MainWindow() {
        initMainWindow();
    }

    private void initMainWindow() {
        // init the frame
        JFrame mainFrame = new JFrame("HLNUG-GeoTools");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setPreferredSize(new Dimension(840, 480));
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
        // do table modification
        fileTable.getColumnModel().getColumn(0).setCellRenderer(new GeoFileTableCellRenderer(fileTable));
        fileTable.getColumnModel().getColumn(1).setCellRenderer(new GeoFileTableCellRenderer(fileTable));
        fileTable.getColumnModel().getColumn(2).setCellRenderer(new GeoFileTableCellRenderer(fileTable));
        // make table scrollable as needed
        JScrollPane scrollableTable = new JScrollPane(fileTable);
        scrollableTable.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollableTable.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        // init buttonbar for insert/delete/.. table data
        JPanel buttonBar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnAdd = new JButton("add");
        btnClear = new JButton("clear");
        // init TabPane for arranging tools
        ToolTabPane tabPane = new ToolTabPane();
        // here: add tabs!
        tabPane.addTab(new ClarNotationToolTab("Clarwerte", null, "Hier werden Clarwerte berechnet!"));
        
        // init logo and info
        JLabel infoLabel = new JLabel("Version 0.1", IconManager.HLNUG_LOGO, 0);
        // add the initialized components onto their panels
        buttonBar.add(btnAdd);
        buttonBar.add(btnClear);
        leftPanel.add(scrollableTable, BorderLayout.CENTER);
        leftPanel.add(buttonBar, BorderLayout.SOUTH);

        rightPanel.add(tabPane, BorderLayout.CENTER);
        JPanel infoWrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        infoWrapper.add(infoLabel);
        rightPanel.add(infoWrapper, BorderLayout.SOUTH);

        mainPanel.add(leftPanel, BorderLayout.CENTER);
        mainPanel.add(rightPanel, BorderLayout.EAST);
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

    public JTable getFileTable() {
        return fileTable;
    }

    public GeoFileTableModel getTableModel() {
        return tableModel;
    }
    
    
    
    

}
