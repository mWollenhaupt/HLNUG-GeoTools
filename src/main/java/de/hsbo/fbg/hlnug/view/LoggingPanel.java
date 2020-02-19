package de.hsbo.fbg.hlnug.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Insets;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.Document;
import javax.swing.text.Element;

/**
 * @author Moritz Wollenhaupt <moritz.wollenhaupt@hs-bochum.de>
 */
public class LoggingPanel extends JPanel implements Runnable {

    private JTextArea logArea;
    private Thread calcInProgressThread;
    private boolean calcRunning;
    private Color logColor;

    public LoggingPanel() {
        super();
        logColor = new Color(110, 110, 110);
        calcRunning = false;
        calcInProgressThread = new Thread(this);
        calcInProgressThread.setDaemon(true);
        initLoggingPanel();
    }

    private void initLoggingPanel() {
        setLayout(new BorderLayout());
        setBorder(new CompoundBorder(new EmptyBorder(5, 5, 5, 5), new LineBorder(new Color(190, 190, 190), 3)));
        logArea = new JTextArea(3, 20);
        logArea.setLineWrap(true);
        logArea.setWrapStyleWord(true);
        logArea.setEnabled(false);
        logArea.setDisabledTextColor(logColor);
        logArea.setMargin(new Insets(5, 5, 5, 5));
        logArea.setBackground(new Color(220, 220, 220));
        DefaultCaret caret = (DefaultCaret) logArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        clearLog();
        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(scrollPane, BorderLayout.CENTER);

    }
    
    public void errorLog(String message) {
        logArea.setDisabledTextColor(Color.RED);
        appendLogString("[Error]\t"+message);
        logArea.setDisabledTextColor(logColor);
    }

    public void clearLog() {
        setLogString("> [LOG] Fügen Sie Dateien zum Bearbeiten hinzu!\n");
    }

    public void setLogString(String message) {
        logArea.setText(message);
    }

    public void appendLogString(String message) {
        logArea.append("> [LOG] " + message + "\n");
    }

    public void startCalculationFeedback(String message) {
        appendLogString(message);
        calcRunning = true;
        calcInProgressThread.start();
    }

    public void stopCalculationFeedback(String message) {
        calcRunning = false;
        appendLogString(message);
    }

    private void updateLog(String message) throws BadLocationException {
        Document document = logArea.getDocument();
        Element root = document.getDefaultRootElement();
        int numLines = root.getElementCount();
        Element content = root.getElement(numLines - 2);
        int start = content.getStartOffset();
        int end = content.getEndOffset();
        document.remove(start, end - start - 2);
        document.insertString(start, "> " + message, null);

    }

    @Override
    public void run() {
        try {
            int i = 0;
            String[] calcStates = {"Berechnung läuft.", "Berechnung läuft..", "Berechnung läuft...", "Berechnung läuft.."};
            appendLogString(calcStates[i]);
            while (calcRunning) {
                i++;
                updateLog(calcStates[i%calcStates.length]);
                Thread.sleep(250);
            }
        } catch (InterruptedException | BadLocationException ex) {
            Logger.getLogger(LoggingPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
