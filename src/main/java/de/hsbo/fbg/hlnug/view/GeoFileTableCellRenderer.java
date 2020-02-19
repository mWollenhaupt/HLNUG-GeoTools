package de.hsbo.fbg.hlnug.view;

import de.hsbo.fbg.hlnug.model.GeoFileObject;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * Class for better visualization of the table model. Enables, e.g, the
 * visualization of the selection
 *
 * @author Moritz Wollenhaupt <moritz.wollenhaupt@hs-bochum.de>
 */
public class GeoFileTableCellRenderer extends JLabel implements TableCellRenderer {

    private int hoveredRow;
    private Color bgSelectedColor;
    private JTable table;

    public GeoFileTableCellRenderer(JTable table) {
        this.table = table;
        hoveredRow = -1;
        bgSelectedColor = new Color(147, 167, 186);
        TableMouseListener listener = new TableMouseListener();
        table.addMouseListener(listener);
        table.addMouseMotionListener(listener);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        GeoFileObject obj = (GeoFileObject) value;
        switch (column) {
            case 0:
                setText(obj.getType());
                break;
            case 1:
                File temp = new File(obj.getPath());
                setText(temp.getName());
                break;
            case 2:
                setText(obj.getName());
                break;
            default:
                break;
        }
        if (isSelected) {
            setForeground(table.getSelectionForeground());
            setBackground(bgSelectedColor);
        } else if (hoveredRow == row) {
            setForeground(table.getSelectionForeground());
            setBackground(table.getSelectionBackground());
        } else {
            setForeground(table.getForeground());
            setBackground(table.getBackground());
        }

        setEnabled(table.isEnabled());
        setFont(table.getFont());
        setOpaque(true);
        return this;
    }

    private class TableMouseListener implements MouseListener, MouseMotionListener {

        public TableMouseListener() {
        }

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
            hoveredRow = -1;
            table.updateUI();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            hoveredRow = table.rowAtPoint(e.getPoint());
            table.updateUI();
        }

    }

}
