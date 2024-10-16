package tzaar.gui;

import javax.swing.*;
import java.awt.*;

public class StatusPanel extends JPanel {
    private final JLabel statusLabel;

    public StatusPanel(String initialText) {
        // Initialize and add status label to the panel
        this.statusLabel = new JLabel(initialText);
        this.statusLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(statusLabel);
    }

    public void setText(String newText) {
        this.statusLabel.setText(newText);
    }
}
