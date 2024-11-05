package dev.laszlomocsy.tzaar.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;

public class FigureButton extends JButton {
    private boolean isMouseHover = false;

    public FigureButton() {
        this.setOpaque(false);
        this.setContentAreaFilled(false);
        this.setBorderPainted(false);
        this.setFocusPainted(false);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                isMouseHover = true;
                repaint();
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                isMouseHover = false;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (isMouseHover) {
            g.setColor(new Color(255, 219, 0, 100));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}
