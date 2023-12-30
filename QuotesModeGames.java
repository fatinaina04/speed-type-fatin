import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

public class QuotesModeGames extends javax.swing.JFrame {
    private Random random = new Random();
    private boolean isGameEnded = false;
    private Timer timer;
    private javax.swing.JTextArea jTextArea1;
    private JLabel timerLabel;

    private String currentWord;
    private int currentIndex;
    private Highlighter.HighlightPainter painterCorrect;
    private Highlighter.HighlightPainter painterWrong;
    private List<Boolean> wordStatus;
    private static String selectedGenre;
    private long startTime; // Added to track the start time

    public QuotesModeGames(String selectedGenre) {
        initComponents(); // This initializes components, including jTextArea1
        this.selectedGenre = selectedGenre;
        setupTextFieldListener();
        generateRandomQuote();
        setupHighlightPainters();
        startTimer();
        startTime = System.currentTimeMillis();
    }


    private void handleGameEnd() {
        jTextField1.setEditable(false); // Disable further input in the text field
        timer.stop(); // Stop the timer when the game ends
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        JOptionPane.showMessageDialog(this, "Game Over!\nTime taken: " + formatTime(totalTime));

    }



    private void startTimer() {
        timer = new Timer(1000, new ActionListener() {
            int seconds = 0;
            int minutes = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                seconds++;
                if (seconds == 60) {
                    seconds = 0;
                    minutes++;
                }
                timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
            }
        });
        timer.start();
    }

    private String formatTime(long milliseconds) {
        long seconds = milliseconds / 1000;
        long minutes = seconds / 60;
        seconds = seconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }


    private void handleTextFieldEnter() {
        if (!isGameEnded) {
            checkUserInput();
        }
    }

    private void highlightUserInput() {
        String userInput = jTextField1.getText().trim();

        try {
            String text = jTextArea1.getText();
            int start = 0;
            int end;

            Highlighter highlighter = jTextField1.getHighlighter();
            highlighter.removeAllHighlights(); // Clear previous highlights

            String[] words = text.split("\\s+");
            for (String word : words) {
                end = start + word.length();
                if (userInput.startsWith(word) && userInput.length() >= word.length()) {
                    // 设置字体颜色为绿色
                    highlighter.addHighlight(start, end, painterCorrect);
                } else {
                    // 设置字体颜色为默认颜色
                    highlighter.addHighlight(start, end, painterWrong);
                }
                start = end + 1; // Move to the next word
            }
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    private void setupHighlightPainters() {
        painterCorrect = new DefaultHighlighter.DefaultHighlightPainter(Color.GREEN);
        painterWrong = new DefaultHighlighter.DefaultHighlightPainter(Color.RED);
    }

    private void generateRandomQuote() {
        try {
            // Construct the filename based on the selected genre
            String filename = "quotes_" + selectedGenre + ".txt";

            // Use the class loader to get the resource
            InputStream inputStream = getClass().getResourceAsStream(filename);

            if (inputStream != null) {
                Scanner scanner = new Scanner(inputStream);
                List<String> sentenceList = new ArrayList<>();

                // Read all words from the file
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    sentenceList.add(line);
                }

                // Shuffle the list to get a random quote
                Collections.shuffle(sentenceList);
                String randomQuote = sentenceList.get(0);
                int wordsToDisplay = Math.min(sentenceList.size(), 3);
                StringBuilder randomWords = new StringBuilder();

                for (int i = 0; i < wordsToDisplay; i++) {
                    randomWords.append(sentenceList.get(i)).append(" ");
                }

                jTextArea1.setLineWrap(true);
                jTextArea1.setWrapStyleWord(true);
                jTextArea1.setText(randomWords.toString().trim());

                // Center align the text
                jTextArea1.setAlignmentX(CENTER_ALIGNMENT);
                jTextArea1.setAlignmentY(CENTER_ALIGNMENT);
                wordStatus = new ArrayList<>(Collections.nCopies(randomQuote.split("\\s+").length, false));
                currentWord = randomQuote.split("\\s+")[0];
                currentIndex = 0;

                jTextField1.getDocument().addDocumentListener(new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        highlightUserInput();
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        highlightUserInput();
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        highlightUserInput();
                    }
                });
            } else {
                System.err.println("File not found: " + filename);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void checkUserInput() {
        String userInput = jTextField1.getText().trim();
        String[] words = jTextArea1.getText().split("\\s+");
        for (int i = currentIndex; i < words.length; i++) {
            if (userInput.equals(words[i])) {
                wordStatus.set(i, true); // Set the word as correct
                currentIndex = i + 1;
            } else {
                wordStatus.set(i, false); // Set the word as wrong
                break;
            }
        }
        highlightUserInput();
        handleGameEnd();

    }


    private void setupTextFieldListener() {
        jTextField1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleTextFieldEnter();
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {


        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        timerLabel = new JLabel();
        timerLabel.setText("00:00");
        jTextField1 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setBorder(null);
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
                                        .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(74, 74, 74)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(96, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
        handleTextFieldEnter();
    }//GEN-LAST:event_jTextField2ActionPerformed

    /**
     * @param args the command line arguments
     */

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(WordsModeGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(WordsModeGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(WordsModeGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(WordsModeGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QuotesModeGames(selectedGenre).setVisible(true);
            }
        });
    }

    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
