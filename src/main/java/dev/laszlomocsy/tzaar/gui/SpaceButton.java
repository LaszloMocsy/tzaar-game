package dev.laszlomocsy.tzaar.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;

/**
 * Custom JButton class for the game board.
 */
public class SpaceButton extends JButton {
    /**
     * Constructor for the SpaceButton class.
     */
    public SpaceButton() {
        this.setOpaque(false);
        this.setContentAreaFilled(false);
        this.setBorderPainted(false);
        this.setFocusPainted(false);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                setCursor(Cursor.getDefaultCursor());
            }
        });
    }
}
