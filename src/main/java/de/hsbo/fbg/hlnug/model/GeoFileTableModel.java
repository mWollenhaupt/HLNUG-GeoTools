package de.hsbo.fbg.hlnug.model;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * @author Moritz Wollenhaupt <moritz.wollenhaupt@hs-bochum.de>
 */
public class GeoFileTableModel extends AbstractTableModel {
    
    private List<GeoFileObject> data;
    private final String[] columnNames = {"Type", "File", "Identifier"};
    private final Class[] classes = {String.class, String.class, String.class};
    
    public GeoFileTableModel() {
        data = new ArrayList<>();
    }

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
    
    
}
