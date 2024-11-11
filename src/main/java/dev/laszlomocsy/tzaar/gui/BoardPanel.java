package dev.laszlomocsy.tzaar.gui;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Collections;

public class BoardPanel extends JPanel {
    private static final Image BOARD_IMAGE = new ImageIcon("src/main/resources/board.png").getImage();

    //-- Paint methods --//

    @Override
    protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
        paintBoard(g);
    }

    private void paintBoard(Graphics g) {
        // Paint the background white, because the image's background is white
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Get the panel's and bgImage's dimensions
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        int bgImageWidth = BOARD_IMAGE.getWidth(null);
        int bgImageHeight = BOARD_IMAGE.getHeight(null);

        // Calculate the image size to fit within the panel
        int bgImageSize = Collections.min(Arrays.asList(panelWidth, panelHeight, bgImageWidth, bgImageHeight));

        // Calculate the image's position to center it within the panel
        int posX = (panelWidth - bgImageSize) / 2;
        int posY = (panelHeight - bgImageSize) / 2;

        // Draw the image
        g.drawImage(BOARD_IMAGE, posX, posY, bgImageSize, bgImageSize, null);
    }
}
