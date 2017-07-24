package com.gibson.pricewatch.GUI;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.UIManager;

import com.gibson.pricewatch.Stores;
import com.gibson.pricewatch.datafactory.DataHandler;
import com.gibson.pricewatch.outbound.GoogleConnect;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.JScrollPane;

public class MainWindow {

    private JFrame frmStarWarsDestiny;
    private JTextField txt_SpreadsheetKey;
    private JTextField txt_UpdateRange;
    private Properties prop;
    private Timer displayTimer;
    private JTextArea txtOutput;
    private DataHandler dh;
    private JScrollPane scrollPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainWindow window = new MainWindow();
                    window.frmStarWarsDestiny.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public MainWindow() {
        loadProperties();
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        dh = new DataHandler();

        frmStarWarsDestiny = new JFrame();
        frmStarWarsDestiny.setTitle("Star Wars: Destiny PriceWatch");
        frmStarWarsDestiny.getContentPane().setMaximumSize(new Dimension(800, 600));
        frmStarWarsDestiny.setMaximumSize(new Dimension(800, 800));
        frmStarWarsDestiny.setBounds(100, 100, 450, 412);
        frmStarWarsDestiny.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmStarWarsDestiny.getContentPane().setLayout(new FormLayout(
                new ColumnSpec[] { FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("left:default"),
                        FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"),
                        FormSpecs.RELATED_GAP_COLSPEC, },
                new RowSpec[] { FormSpecs.RELATED_GAP_ROWSPEC, RowSpec.decode("20px"), FormSpecs.RELATED_GAP_ROWSPEC,
                        RowSpec.decode("20px"), FormSpecs.RELATED_GAP_ROWSPEC, RowSpec.decode("274px"),
                        FormSpecs.RELATED_GAP_ROWSPEC, RowSpec.decode("23px"), FormSpecs.RELATED_GAP_ROWSPEC, }));

        JLabel lblSpreadsheetKey = new JLabel("Spreadsheet Key:");
        frmStarWarsDestiny.getContentPane().add(lblSpreadsheetKey, "2, 2, right, center");

        txt_SpreadsheetKey = new JTextField();
        frmStarWarsDestiny.getContentPane().add(txt_SpreadsheetKey, "4, 2, fill, top");
        txt_SpreadsheetKey.setColumns(20);
        txt_SpreadsheetKey.setText(prop.getProperty("spreadsheetKey", ""));

        JLabel lblUpdateRange = new JLabel("Update Range:");
        frmStarWarsDestiny.getContentPane().add(lblUpdateRange, "2, 4, right, center");

        txt_UpdateRange = new JTextField();
        txt_UpdateRange.setToolTipText("A1 notation. For example \"Sheet1!A1\"");
        txt_UpdateRange.setText(prop.getProperty("range", ""));
        frmStarWarsDestiny.getContentPane().add(txt_UpdateRange, "4, 4, fill, top");
        txt_UpdateRange.setColumns(10);

        JButton btnNewButton = new JButton("Update Prices");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                updateCardDB();
            }
        });

        scrollPane = new JScrollPane();
        frmStarWarsDestiny.getContentPane().add(scrollPane, "2, 6, 3, 1, fill, fill");

        txtOutput = new JTextArea();
        scrollPane.setViewportView(txtOutput);
        txtOutput.setFont(UIManager.getFont("Label.font"));
        txtOutput.setWrapStyleWord(true);
        txtOutput.setFocusable(false);
        txtOutput.setLineWrap(true);
        txtOutput.setBackground(UIManager.getColor("Label.background"));
        txtOutput.setEditable(false);
        txtOutput.setText(
                "Spreadsheet Key: The ID of the Google Spreadsheet to save the data to. This ID is the value between the \"/d/\" and the \"/edit\" in the URL of your spreadsheet.\n\nUpdate Range: The top-leftmost cell to save the data in A1 notation.  <SheetName>!<Top-left Cell> - For example Price!D3");
        frmStarWarsDestiny.getContentPane().add(btnNewButton, "4, 8, right, top");

        ActionListener listener = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                txtOutput.append(".");
            }
        };
        displayTimer = new Timer(1000, listener);

    }

    private void loadProperties() {
        prop = new Properties();
        try (InputStream in = new FileInputStream("config.properties")) {
            prop.load(in);
            System.out.println("####Properties.getProperty usage####");
            System.out.println(prop.getProperty("name"));
            System.out.println();

            System.out.println("####Properties.stringPropertyNames usage####");
            for (String property : prop.stringPropertyNames()) {
                String value = prop.getProperty(property);
                System.out.println(property + "=" + value);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void saveProperties() {
        try (OutputStream out = new FileOutputStream("config.properties")) {
            prop.setProperty("name", "PriceWatch");
            prop.setProperty("spreadsheetKey", txt_SpreadsheetKey.getText());
            prop.setProperty("range", txt_UpdateRange.getText());
            prop.store(out, "PriceWatch Properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateCardDB() {
        txtOutput.setText("");

        SwingWorker<Integer, String> worker = new SwingWorker<Integer, String>() {
            @Override
            protected Integer doInBackground() throws Exception {

                publish("Updating Card List.");
                displayTimer.start();
                Integer cardCount = dh.populateSWDCardData();

                return cardCount;
            }

            // Can safely update the GUI from this method.
            protected void done() {
                Integer cardCount;
                displayTimer.stop();
                try {
                    // Retrieve the return value of doInBackground.
                    cardCount = get();
                    txtOutput.append("Done\n" + cardCount + " cards loaded.\n");
                    updatePrices();
                } catch (InterruptedException e) {
                    // This is thrown if the thread's interrupted.
                } catch (ExecutionException e) {
                    displayTimer.stop();
                    txtOutput.setText("Error loading cards. " + e.getMessage());
                }
            }

            @Override
            // Can safely update the GUI from this method.
            protected void process(List<String> chunks) {
                // Here we receive the values that we publish().
                // They may come grouped in chunks.
                String mostRecentValue = chunks.get(chunks.size() - 1);

                // lblOutput.setText(Integer.toString(mostRecentValue));
                txtOutput.append(mostRecentValue);
            }
        };

        worker.execute();
    }

    private void updatePrices() {

        SwingWorker<Boolean, String> priceCardsWorker = new SwingWorker<Boolean, String>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                Boolean finished = false;

                for (Stores store : Stores.values()) {
                    publish("Retrieving pricing for " + store.getStoreName() + " ..");
                    displayTimer.start();
                    finished = dh.populateStorePrice(store);
                    publish("Done\n");
                    displayTimer.stop();
                }

                return finished;
            }

            // Can safely update the GUI from this method.
            protected void done() {
                Boolean finished;
                try {
                    // Retrieve the return value of doInBackground.
                    finished = get();
                    if (finished) {
                        outputToGoogle();
                    }
                } catch (InterruptedException e) {
                    // This is thrown if the thread's interrupted.
                } catch (ExecutionException e) {
                    displayTimer.stop();
                    txtOutput.append("Error pricing cards. " + e.getMessage());
                }
            }

            @Override
            // Can safely update the GUI from this method.
            protected void process(List<String> chunks) {
                // Here we receive the values that we publish().
                // They may come grouped in chunks.
                // String mostRecentValue = chunks.get(chunks.size() - 1);

                // lblOutput.setText(Integer.toString(mostRecentValue));
                // txtOutput.append(mostRecentValue);
                for (final String string : chunks) {
                    txtOutput.append(string);
                }
            }
        };

        priceCardsWorker.execute();

    }

    private void outputToGoogle() {

        SwingWorker<Boolean, String> priceCardsWorker = new SwingWorker<Boolean, String>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                Boolean finished = false;

                GoogleConnect gc = new GoogleConnect();
                try {
                    gc.createConnection(dh.allCards, dh.allSets, txt_SpreadsheetKey.getText(),
                            txt_UpdateRange.getText());
                    saveProperties();
                } catch (GeneralSecurityException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (GoogleJsonResponseException e) {
                    publish("Error writing to Google Sheet: " + e.getStatusCode() + " - " + e.getStatusMessage());
                }

                return finished;
            }

            // Can safely update the GUI from this method.
            protected void done() {
                Boolean finished;
                try {
                    // Retrieve the return value of doInBackground.
                    finished = get();
                } catch (InterruptedException e) {
                    // This is thrown if the thread's interrupted.
                } catch (ExecutionException e) {
                    displayTimer.stop();
                    txtOutput.append("Error pricing cards. " + e.getMessage());
                }
            }

            @Override
            // Can safely update the GUI from this method.
            protected void process(List<String> chunks) {
                // Here we receive the values that we publish().
                // They may come grouped in chunks.
                // String mostRecentValue = chunks.get(chunks.size() - 1);

                // lblOutput.setText(Integer.toString(mostRecentValue));
                // txtOutput.append(mostRecentValue);
                for (final String string : chunks) {
                    txtOutput.append(string);
                }
            }
        };

        priceCardsWorker.execute();

    }
}
