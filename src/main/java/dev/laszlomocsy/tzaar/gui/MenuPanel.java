package dev.laszlomocsy.tzaar.gui;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionListener;

public class MenuPanel extends JPanel {
    private JLabel lblWhitePlayer;
    private JTextField txtWhitePlayer;
    private JLabel lblBlackPlayer;
    private JTextField txtBlackPlayer;
    private JButton btnStartGame;
    private JButton btnLoadGame;

    //-- Constructor --//

    /**
     * Constructor to initialize the MenuPanel
     */
    public MenuPanel() {
        this.setName("menuPanel");

        initializeComponents();
        layoutComponents();
    }

    //-- Methods --//

    /**
     * Initialize all UI components
     */
    private void initializeComponents() {
        DocumentListener documentListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                textFieldChanged();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                textFieldChanged();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                textFieldChanged();
            }
        };

        lblWhitePlayer = new JLabel("White Player Name:");
        txtWhitePlayer = new JTextField(15);
        txtWhitePlayer.getDocument().addDocumentListener(documentListener);

        lblBlackPlayer = new JLabel("Black Player Name:");
        txtBlackPlayer = new JTextField(15);
        txtBlackPlayer.getDocument().addDocumentListener(documentListener);

        btnStartGame = new JButton("Start Game");
        btnStartGame.setEnabled(false);
        btnLoadGame = new JButton("Load Game");
    }

    /**
     * Arrange components using GridBagLayout
     */
    private void layoutComponents() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // General constraints
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Row 0: White Player Label
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(lblWhitePlayer, gbc);

        // Row 0: White Player TextField
        gbc.gridx = 1;
        gbc.gridy = 0;
        this.add(txtWhitePlayer, gbc);

        // Row 1: Black Player Label
        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(lblBlackPlayer, gbc);

        // Row 1: Black Player TextField
        gbc.gridx = 1;
        gbc.gridy = 1;
        this.add(txtBlackPlayer, gbc);

        // Row 2: Start Game Button
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        this.add(btnStartGame, gbc);

        // Row 2: Load Game Button
        gbc.gridx = 0;
        gbc.gridy = 3;
        this.add(btnLoadGame, gbc);
    }

    private void textFieldChanged() {
        btnStartGame.setEnabled(!txtWhitePlayer.getText().isEmpty() && !txtBlackPlayer.getText().isEmpty() && !txtWhitePlayer.getText().equals(txtBlackPlayer.getText()));
    }

    /**
     * Get the white player's name
     *
     * @return String entered in white player's text field
     */
    public String getWhitePlayerName() {
        return txtWhitePlayer.getText().trim();
    }

    /**
     * Set the white player's name
     *
     * @param name String to set in white player's text field
     */
    public void setWhitePlayerName(String name) {
        txtWhitePlayer.setText(name);
    }

    /**
     * Get the black player's name
     *
     * @return String entered in black player's text field
     */
    public String getBlackPlayerName() {
        return txtBlackPlayer.getText().trim();
    }

    /**
     * Set the black player's name
     *
     * @param name String to set in black player's text field
     */
    public void setBlackPlayerName(String name) {
        txtBlackPlayer.setText(name);
    }

    /**
     * Add ActionListener to Start Game button
     *
     * @param listener ActionListener
     */
    public void addStartGameListener(ActionListener listener) {
        btnStartGame.addActionListener(listener);
    }

    /**
     * Add ActionListener to Load Game button
     *
     * @param listener ActionListener
     */
    public void addLoadGameListener(ActionListener listener) {
        btnLoadGame.addActionListener(listener);
    }
}