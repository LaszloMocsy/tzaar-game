package tzaar.gui;

import tzaar.component.Board;
import tzaar.component.Space;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class BoardUI extends JPanel {
    private final transient Image bgImage;
    private int bgImageSize;
    private int bgImageX;
    private int bgImageY;

    private final transient Board board;

    public BoardUI(Board board) {
        this.board = board;

        // Load the background image from the resources folder
        bgImage = new ImageIcon("src/main/resources/GameBoard.png").getImage();

        // Set layout to null for manual positioning
        setLayout(null);

        // Place buttons
        for (Space space : this.board.getSpaces()) {
            SpaceUI btn = new SpaceUI(space);
            add(btn);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.paintBackgroundImage(g);
        this.paintSpaceButtons();
    }

    private void paintBackgroundImage(Graphics g) {
        // The goal is to paint the background image horizontally and vertically center always
        // To achieve this I need to calculate the image's size and the location for the top left corner (x, y)

        // Get the panel's dimensions
        int panelWidth = getWidth();
        int panelHeight = getHeight();

        // Get the image's default dimensions
        int imageDefaultWidth = bgImage.getWidth(null);
        int imageDefaultHeight = bgImage.getHeight(null);

        // Calculate the image's dimensions to use
        Integer[] sizes = {panelWidth, panelHeight, imageDefaultWidth, imageDefaultHeight};
        this.bgImageSize = Collections.min(Arrays.asList(sizes));

        // Calculate x and y coordinates to center the image
        this.bgImageX = (panelWidth - this.bgImageSize) / 2;
        this.bgImageY = (panelHeight - this.bgImageSize) / 2;

        // Draw the image centered in the panel
        g.drawImage(bgImage, this.bgImageX, this.bgImageY, this.bgImageSize, this.bgImageSize, this);
    }

    private void paintSpaceButtons() {
        double cellWidth = this.bgImageSize / 11.0;
        double cellHeight = this.bgImageSize / 19.0;
        int buttonWidth = (int) (Math.round(cellWidth));
        int buttonHeight = (int) Math.round(cellHeight * 2);

        // Go through each space (A1,...,I9)
        for (Component comp : this.getComponents()) {
            SpaceUI btn = (SpaceUI) comp;
            int[] btnXY = convertSpaceCoordinatesToButtonCoordinates(btn.getSpaceCoordinates()); // [x, y, z] -> [left, top]

            double startX = this.bgImageX + (btnXY[0] * cellWidth);
            double startY = this.bgImageY + (btnXY[1] * cellHeight * 2);

            if (btnXY[0] % 2 == 0) {
                startY -= cellHeight;
            }

            btn.setBounds((int) startX, (int) startY, buttonWidth, buttonHeight);
        }
    }

    private int[] convertSpaceCoordinatesToButtonCoordinates(int[] spaceCoordinates) {
        int x = spaceCoordinates[0];
        int y = spaceCoordinates[1];
        int z = spaceCoordinates[2];

        int coordY = (((x <= 5 ? y : z) - 10) * -1) - 1;
        int origoDiff = x <= 5 ? 5 - x : x - 5;
        coordY -= origoDiff / 2;

        return new int[]{x, coordY};
    }
}
