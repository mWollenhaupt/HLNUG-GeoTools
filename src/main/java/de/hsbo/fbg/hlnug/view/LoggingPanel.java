package de.hsbo.fbg.hlnug.view;

import de.hsbo.fbg.hlnug.util.ToolExecutionThreadPool;
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
 * An expaneded JPanel class that holds an JTextArea, used for logging.
 *
 * @author Moritz Wollenhaupt <moritz.wollenhaupt@hs-bochum.de>
 */
public class LoggingPanel extends JPanel implements Runnable {

    private JTextArea logArea;              // logging area
    private Thread calcInProgressThread;    // Used for user feedback
    private boolean calcRunning;            // true while long term calculations running
    private Color logColor;                 // font color
    
    private ToolExecutionThreadPool threadPool;

    public LoggingPanel() {
        super();
        this.threadPool = new ToolExecutionThreadPool();
        logColor = new Color(110, 110, 110);
        calcRunning = false;
        initLoggingPanel();
    }

    /**
     * initializes GUI elements
     */
    private void initLoggingPanel() {
        setLayout(new BorderLayout());
        setBorder(new CompoundBorder(new EmptyBorder(5, 5, 5, 5), new LineBorder(new Color(190, 190, 190), 3)));
        logArea = new JTextArea(4, 20);
        logArea.setLineWrap(true);
        logArea.setWrapStyleWord(true);
        DefaultCaret caret = (DefaultCaret) logArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        logArea.setEnabled(false);
        logArea.setDisabledTextColor(logColor);
        logArea.setMargin(new Insets(5, 5, 5, 5));
        logArea.setBackground(new Color(220, 220, 220));

        clearLog();
        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(scrollPane, BorderLayout.CENTER);

    }

    /**
     * used for error message logging
     *
     * @param message The message to be printed
     */
    public void errorLog(String message) {
        logArea.append("> [Error]\t" + message + "\n");
    }

    /**
     * clears and re-initializes the log
     */
    public void clearLog() {
        setLogString("> [LOG] Fügen Sie Dateien zum Bearbeiten hinzu!\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }

    /**
     * overwrites all log input with the given message
     *
     * @param message The message to be printed
     */
    public void setLogString(String message) {
        logArea.setText(message);
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }

    /**
     * appends the given message as a new log line
     *
     * @param message The message to be printed
     */
    public void appendLogString(String message) {
        logArea.append("> [LOG] " + message + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }

    /**
     * starts a new feedback thread, so the user can see, that a long term
     * calculation is still in progress and prints the given message
     *
     * @param message The message to be printed
     */
    public void startCalculationFeedback(String message) {
        appendLogString(message);
        calcRunning = true;
        threadPool.execute(this);
    }

    /**
     * stops the feedback thread after long term calculation and prints the
     * given message
     *
     * @param message The message to be printed
     */
    public void stopCalculationFeedback(boolean success, String message) {
        calcRunning = false;
        if (success) {
            appendLogString(message);
        }
        else {
            errorLog(message);
        }
    }

    /**
     * method that updates the last log line only. Used by feedback frame only
     *
     * @param message The message to be printed
     * @throws BadLocationException
     */
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

    /**
     * Feedback threads' logic
     */
    @Override
    public void run() {
        try {
            int i = 0;
            String[] calcStates = {"[LOG] Berechnung läuft.", "[LOG] Berechnung läuft..", "[LOG] Berechnung läuft...", "[LOG] Berechnung läuft.."};
            appendLogString(calcStates[i]);
            while (calcRunning) {
                i++;
                updateLog(calcStates[i % calcStates.length]);
                Thread.sleep(250);
            }
        } catch (InterruptedException | BadLocationException ex) {
            Logger.getLogger(LoggingPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
