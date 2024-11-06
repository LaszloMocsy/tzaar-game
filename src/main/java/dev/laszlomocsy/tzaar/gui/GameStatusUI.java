package dev.laszlomocsy.tzaar.gui;

import javax.swing.*;

public class GameStatusUI extends JPanel {
    private final JLabel statusLabel;

    public GameStatusUI() {
        statusLabel = new JLabel("Status: ");
        this.add(statusLabel);
    }

    //-- METHODS --//

    /// Set the text of the status label.
    public void setStatusLabel(String text) {
        statusLabel.setText(text);
    }
}
