package dev.laszlomocsy.tzaar.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ControlPanel extends JPanel {
    private JButton btnSaveGame;
    private JButton btnReturnToMenu;
    private JLabel lblStatus;

    /**
     * Constructor to initialize the ControlPanel
     */
    public ControlPanel() {
        initializeComponents();
        layoutComponents();
    }

    /**
     * Initialize all UI components
     */
    private void initializeComponents() {
        btnSaveGame = new JButton("Save Game");
        btnReturnToMenu = new JButton("Return to Menu");
        lblStatus = new JLabel("Ready to play?");
        lblStatus.setHorizontalAlignment(SwingConstants.CENTER);
        lblStatus.setFont(new Font("Arial", Font.PLAIN, 20));
    }

    /**
     * Arrange components using BorderLayout
     */
    private void layoutComponents() {
        this.setLayout(new BorderLayout());

        this.add(btnSaveGame, BorderLayout.WEST);
        this.add(btnReturnToMenu, BorderLayout.EAST);
        this.add(lblStatus, BorderLayout.CENTER);

        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    /**
     * Add ActionListener to Save Game button
     * @param listener ActionListener
     */
    public void addSaveGameListener(ActionListener listener) {
        btnSaveGame.addActionListener(listener);
    }

    /**
     * Add ActionListener to Return to Menu button
     * @param listener ActionListener
     */
    public void addReturnToMenuListener(ActionListener listener) {
        btnReturnToMenu.addActionListener(listener);
    }

    /**
     * Set the status text
     * @param status String to display in the status label
     */
    public void setStatus(String status) {
        lblStatus.setText("Status: " + status);
    }

    /**
     * Get the Save Game button (optional, for more advanced customization)
     */
    public JButton getSaveGameButton() {
        return btnSaveGame;
    }

    /**
     * Get the Return to Menu button (optional, for more advanced customization)
     */
    public JButton getReturnToMenuButton() {
        return btnReturnToMenu;
    }

    /**
     * Get the Status label (optional, for more advanced customization)
     */
    public JLabel getStatusLabel() {
        return lblStatus;
    }
}
