package tzaar.gui;

import tzaar.component.Space;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SpaceButton extends JButton {
    private final transient Space space;
    private boolean isHovered = false;

    public SpaceButton(Space space) {
        this.space = space;

        // Make the button transparent
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);

        // Add an ActionListener to show an alert when the button is clicked
        // DEBUG! Only for testing
        addActionListener(e -> JOptionPane.showMessageDialog(SpaceButton.this, this.space.toString()));

        // Add a MouseAdapter to handle hover events
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isHovered = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isHovered = false;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw background if the button is hovered
        if (isHovered) {
            g.setColor(new Color(255, 255, 0, 128)); // semi-transparent yellow
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}
