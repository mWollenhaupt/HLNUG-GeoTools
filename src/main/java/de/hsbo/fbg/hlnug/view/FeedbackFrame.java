package de.hsbo.fbg.hlnug.view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.RoundRectangle2D;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * @author Moritz Wollenhaupt <moritz.wollenhaupt@hs-bochum.de>
 */
public class FeedbackFrame extends JFrame implements Runnable, MouseListener, MouseMotionListener {

    private Thread thread;
    private boolean running;
    private Color backgroudColor;
    private JLabel label;
    private String[] states = {"-", "\\", "|", "/"};
    private JButton button;
    private GridBagConstraints gbc;
    private Point mouseDownCompCoords = null;

    public FeedbackFrame(Container parent) {
        super();
        thread = new Thread(this);
        backgroudColor = new Color(220, 220, 220);
        running = false;
        initFrame(parent);
        
    }
    
    private void initFrame(Container parent) {
        setLayout(new GridBagLayout());
        addMouseListener(this);
        addMouseMotionListener(this);

        this.gbc = new GridBagConstraints();
        this.gbc.anchor = GridBagConstraints.CENTER;
        this.gbc.insets = new Insets(2, 2, 2, 2);

        getContentPane().setBackground(backgroudColor);
        label = new JLabel();
        label.setPreferredSize(new Dimension(300, 24));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(label, gbc);

        button = new JButton("OK");
        button.addActionListener(e -> closeOnClick());
        button.setVisible(false);
        button.setEnabled(false);
        gbc.gridx = 0;
        gbc.gridy = 1;

        add(button, gbc);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Already there
        setPreferredSize(new Dimension(320, 140));
        setLocationRelativeTo(parent);
        toFront();
        setUndecorated(true);
        setShape(new RoundRectangle2D.Double(0, 0, 320, 140, 25, 25));
        
        this.pack();
    }

    public void start() {
        setVisible(true);
        thread.start();
    }

    public void stopSuccessful() {
        running = false;
        label.setText("Berechnung erfolgreich abgeschlossen!");
        button.setVisible(true);
        button.setEnabled(true);
    }
    
    public void stopUnuccessful() {
        running = false;
        label.setText("Ein Problem ist aufgetreten!");
        button.setVisible(true);
        button.setEnabled(true);
    }

    @Override
    public void run() {
        try {
            running = true;
            int i = 0;
            while (running) {
                label.setText("Berechnung läuft.." + states[i % states.length]);
                i++;
                Thread.sleep(250);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(FeedbackFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void closeOnClick() {
        this.dispose();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Point currCoords = e.getLocationOnScreen();
        this.setLocation(currCoords.x - mouseDownCompCoords.x, currCoords.y - mouseDownCompCoords.y);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.mouseDownCompCoords = e.getPoint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseDownCompCoords = null;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    

}
