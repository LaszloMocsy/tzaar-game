package tzaar.gui;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Collections;

public class GamePanel extends JPanel {
    private final transient Image backgroundImage;

    public GamePanel() {
        // Load the background image from the resources folder
        backgroundImage = new ImageIcon("src/main/resources/TZAAR_board_blank.png").getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // The goal is to paint the image horizontally center always
        // To achieve this I need to calculate the image's size and the location for the top left corner (x, y)

        // Get the panel's dimensions
        int panelWidth = getWidth();
        int panelHeight = getHeight();

        // Get the image's default dimensions
        int imageDefaultWidth = backgroundImage.getWidth(null);
        int imageDefaultHeight = backgroundImage.getHeight(null);

        // Calculate the image's dimensions to use
        Integer[] sizes = {panelWidth, panelHeight, imageDefaultWidth, imageDefaultHeight};
        int minSize = Collections.min(Arrays.asList(sizes));
        int imageWidth = minSize;
        int imageHeight = minSize;

        // Calculate x and y coordinates to center the image
        int x = (panelWidth - imageWidth) / 2;
        int y = (panelHeight - imageHeight) / 2;

        // Draw the image centered in the panel
        g.drawImage(backgroundImage, x, y, imageWidth, imageHeight, this);
    }
}
