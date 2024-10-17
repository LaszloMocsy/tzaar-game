package tzaar.gui;

import tzaar.util.FigureButtonType;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class GamePanel extends JPanel {
    private final transient Image backgroundImage;
    private final transient List<FigureButton> buttons = new ArrayList<>();

    public GamePanel() {
        // Load the background image from the resources folder
        backgroundImage = new ImageIcon("src/main/resources/GameBoard_test.png").getImage();

        // Set layout to null for manual positioning
        setLayout(null);

        // Place buttons
        for (int i = 0; i < 60; i++) {
            // Randomly select a figure type
            // DEBUG! Only for testing
            FigureButtonType type = switch (new Random().nextInt(3)) {
                case 1 -> FigureButtonType.TZARRA;
                case 2 -> FigureButtonType.TZAAR;
                default -> FigureButtonType.TOTT;
            };

            FigureButton btn = new FigureButton(type, new Random().nextInt(2) == 0, new Random().nextInt(5) + 1);
            buttons.add(btn);
            add(btn);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // The goal is to paint the background image horizontally and vertically center always
        // To achieve this I need to calculate the image's size and the location for the top left corner (x, y)

        // Get the panel's dimensions
        int panelWidth = getWidth();
        int panelHeight = getHeight();

        // Get the image's default dimensions
        int imageDefaultWidth = backgroundImage.getWidth(null);
        int imageDefaultHeight = backgroundImage.getHeight(null);

        // Calculate the image's dimensions to use
        Integer[] sizes = {panelWidth, panelHeight, imageDefaultWidth, imageDefaultHeight};
        int imageSize = Collections.min(Arrays.asList(sizes));

        // Calculate x and y coordinates to center the image
        int x = (panelWidth - imageSize) / 2;
        int y = (panelHeight - imageSize) / 2;

        // Draw the image centered in the panel
        g.drawImage(backgroundImage, x, y, imageSize, imageSize, this);

        // Reposition all buttons
        double cellWidth = imageSize / 11.0;
        double cellHeight = imageSize / 19.0;
        int buttonWidth = (int) (Math.round(cellWidth));
        int buttonHeight = (int) Math.round(cellHeight * 2);

        int[] cellCounts = new int[]{5, 6, 7, 8, 8, 8, 7, 6, 5};
        int btnIndex = 0;
        for (int btnX = 0; btnX < cellCounts.length; btnX++) {
            for (int btnY = 0; btnY < cellCounts[btnX]; btnY++) {
                // Calculate the button's x and y coordinates
                int startX = (int) Math.round(x + cellWidth + (btnX * cellWidth));
                int startY = (int) Math.round(y + (btnY * cellHeight * 2));

                if (btnX == 4 && btnY >= 4) {
                    // Jump over the middle space
                    startY += buttonHeight;
                } else if (btnX != 4) {
                    // Align the column start from the left and from the right
                    int margin = (cellCounts[btnX] - 9) * -1;
                    startY += (int) Math.round(margin * cellHeight);
                }

                // Position the button
                buttons.get(btnIndex).setBounds(startX, startY, buttonWidth, buttonHeight);
                btnIndex++;
            }
        }
    }
}
