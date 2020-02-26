package de.hsbo.fbg.hlnug.view;

import de.hsbo.fbg.hlnug.util.ToolExecutionThreadPool;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

/**
 * @author Moritz Wollenhaupt <moritz.wollenhaupt@hs-bochum.de>
 */
public class LoggingPanel extends JPanel {

    private JTextPane logArea;
    private ToolExecutionThreadPool threadPool;
    private boolean calcRunning;
    private DefaultCaret caret;
    private SimpleAttributeSet set;

    private enum LogType {
        LOG, ERROR, WARNING, RUNNING
    }
    private Color cLog, cError, cWarning, cRunning;

    public LoggingPanel() {
        super();
        this.threadPool = new ToolExecutionThreadPool();
        this.calcRunning = false;
        this.cLog = new Color(27, 38, 44);
        this.cRunning = new Color(15, 76, 129);
        this.cError = new Color(237, 102, 99);
        this.cWarning = new Color(255, 163, 114);
        this.set = new SimpleAttributeSet();
        initLoggingPanel();
    }

    private void initLoggingPanel() {
        setLayout(new BorderLayout());
        setBorder(new CompoundBorder(new EmptyBorder(5, 5, 5, 5), new LineBorder(new Color(190, 190, 190), 3)));

        logArea = new JTextPane();
        logArea.setPreferredSize(new Dimension(logArea.getPreferredSize().width, 70));
        logArea.setEditable(false);
        logArea.setMargin(new Insets(5, 5, 5, 5));
        logArea.setBackground(new Color(220, 220, 220));
        this.caret = (DefaultCaret) logArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(scrollPane, BorderLayout.CENTER);

        clearLog();
    }

    public void errorLog(String msg) {
        appendToLogArea(msg, LogType.ERROR);
    }

    public void log(String msg) {
        appendToLogArea(msg, LogType.LOG);
    }

    public void warningLog(String msg) {
        appendToLogArea(msg, LogType.WARNING);
    }

    public void runningLog(String msg) {
        appendToLogArea(msg, LogType.RUNNING);
    }

    private void appendToLogArea(String msg, LogType type) {
        logArea.setEditable(true);
        switch (type) {
            case LOG:
                msg = "\n[LOG]\t" + msg;
                StyleConstants.setForeground(set, cLog);
                break;
            case ERROR:
                msg = "\n[ERROR]\t" + msg;
                StyleConstants.setForeground(set, cError);
                break;
            case WARNING:
                msg = "\n[WARNING]\t" + msg;
                StyleConstants.setForeground(set, cWarning);
                break;
            case RUNNING:
                msg = "\n[RUNNING]\t" + msg;
                StyleConstants.setForeground(set, cRunning);
                break;
            default:
                break;
        }
        logArea.setCharacterAttributes(set, false);
        logArea.replaceSelection(msg);
        logArea.setEditable(false);
    }

    public void clearLog() {
        StyleConstants.setForeground(set, cLog);
        logArea.setCharacterAttributes(set, false);
        logArea.setText("[LOG]\tBitte Dateien zum bearbeiten einladen.");
    }

    public void stopCalculationFeedback(boolean success, String msg) {
        calcRunning = false;
        if (success) {
            log(msg);
        } else {
            errorLog(msg);
        }
    }

    public synchronized void startCalculationFeedback(String msg) {
        log(msg);
        calcRunning = true;
        threadPool.execute(() -> {
            try {
                int i = 0;
                String[] calcStates = {"Berechnung läuft.", "Berechnung läuft...", "Berechnung läuft.....", "Berechnung läuft..."};
                runningLog(calcStates[i]);
                long lastTime = System.currentTimeMillis();
                while (calcRunning) {
                    long newTime = System.currentTimeMillis();
                    float delta = (newTime - lastTime) / 1000;
                    if (delta >= 1) {
                        i++;
                        String content = logArea.getDocument().getText(0, logArea.getDocument().getLength());
                        int lastLineBreak = content.lastIndexOf("\n");
                        logArea.select(lastLineBreak, logArea.getDocument().getLength());
                        runningLog(calcStates[i % calcStates.length]);
                        lastTime = newTime;
                    }
                }
            } catch (BadLocationException ex) {
                Logger.getLogger(LoggingPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

}
