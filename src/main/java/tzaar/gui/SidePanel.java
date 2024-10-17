package tzaar.gui;

import javax.swing.*;
import java.awt.*;

public class SidePanel extends JPanel {
    public SidePanel() {
        // Initialize and add status label to the panel
        JLabel label = new JLabel("Side panel");
        label.setFont(new Font("Arial", Font.BOLD, 24));
        add(label);
    }
}
