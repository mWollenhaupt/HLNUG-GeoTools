package de.hsbo.fbg.hlnug.model;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * The in the GUI shown file table model
 * 
 * @author Moritz Wollenhaupt <moritz.wollenhaupt@hs-bochum.de>
 */
public class GeoFileTableModel extends AbstractTableModel {
    
    private List<GeoFileObject> data;                                           // The shown data
    private final String[] columnNames = {"Typ", "Datei", "Objekt"};            // Columnnames
    private final Class[] classes = {String.class, String.class, String.class}; // Column data types
    
    public GeoFileTableModel() {
        data = new ArrayList<>();
    }

    // All operations should be self-explanatory
    
    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return classes[columnIndex];
    }
    
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data.get(rowIndex);
    }
    
    public void removeAllElements() {
        data.clear();
    }
    
    public List<GeoFileObject> getData() {
        return data;
    }
    
    public void addRow(GeoFileObject entry) {
        data.add(entry);   
    }

    public void removeRow(int selectedRow) {
        data.remove(selectedRow);
    }
    
    
}
