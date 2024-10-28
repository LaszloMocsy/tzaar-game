package dev.laszlomocsy.tzaar.gui;

import dev.laszlomocsy.tzaar.game.Board;
import dev.laszlomocsy.tzaar.game.Coordinate;
import dev.laszlomocsy.tzaar.game.Figure;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BoardUI extends JPanel {
    private final transient Image boardImage;
    private final transient List<Image> figureImages; // W-Tzaar, W-Tzarra, W-Tott, B-Tzaar, B-Tzarra, B-Tott

    private transient Board boardRef;
    private int imageSize;

    //-- CONSTRUCTOR --//

    public BoardUI() {
        this.boardImage = new ImageIcon("src/main/resources/Tzaar-GameBoard-grid.png").getImage();
        this.figureImages = Arrays.asList(
                new ImageIcon("src/main/resources/GameFigure-white-tzaar.png").getImage(),
                new ImageIcon("src/main/resources/GameFigure-white-tzarra.png").getImage(),
                new ImageIcon("src/main/resources/GameFigure-white-tott.png").getImage(),
                new ImageIcon("src/main/resources/GameFigure-black-tzaar.png").getImage(),
                new ImageIcon("src/main/resources/GameFigure-black-tzarra.png").getImage(),
                new ImageIcon("src/main/resources/GameFigure-black-tott.png").getImage()
        );
    }

    //-- METHODS --//

    /// Set up the board
    public void setupBoard(Board board) {
        this.boardRef = board;
        System.out.println("Setting up the board from BoardUI");
    }

    /// Clear the board
    public void clearBoard() {
        this.boardRef = null;
        System.out.println("Clearing the board from BoardUI");
    }

    // METHODS (paint) --//

    /// Paint the background image horizontally and vertically centered
    private void paintBackground(Graphics g) {
        // Paint the background white, because the image's background is white
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Get the panel's and bgImage's dimensions
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        int bgImageWidth = boardImage.getWidth(null);
        int bgImageHeight = boardImage.getHeight(null);

        // Calculate the image size to fit within the panel
        this.imageSize = Collections.min(Arrays.asList(panelWidth, panelHeight, bgImageWidth, bgImageHeight));

        // Calculate the image's position to center it within the panel
        int posX = (panelWidth - imageSize) / 2;
        int posY = (panelHeight - imageSize) / 2;

        // Draw the image
        g.drawImage(boardImage, posX, posY, imageSize, imageSize, null);
    }

    /// Paint the figures on the board
    private void paintFigures(Graphics g) {
        // Only draw figures if the board is set up
        if (boardRef == null) return;

        //-- CONTANTS --//
        final int gridWidth = 11;
        final int gridHeight = 19;
        final float cellWidth = (float) imageSize / gridWidth;
        final float cellHeight = (float) imageSize / gridHeight;
        final float size = cellWidth / 2;
        final float startX = (float) (getWidth() - imageSize) / 2 - size / 2;
        final float startY = (float) (getHeight() - imageSize) / 2 - size / 2;

        // Loop through the board and draw the figures
        // TODO - Draw figure stacks
        for (Figure figure : this.boardRef.getFigures()) {
            int[] position = getFigurePosition(figure.getLocation());
            Image image = figureImages.get((figure.getColor().ordinal() * 3) + figure.getType().ordinal());

            int posX = Math.round(startX + (position[0] * cellWidth) + (cellWidth / 2));
            int posY = Math.round(startY + (position[1] * cellHeight) + (cellHeight / 2));

            g.drawImage(image, posX, posY, Math.round(size), Math.round(size), null);
        }
    }

    /// Get the position of a figure on the board
    ///
    /// TODO - Refactor this method to be resuable for buttons
    private int[] getFigurePosition(Coordinate coordinate) {
        int y = (9 - coordinate.y()) * 2;
        if (coordinate.x() % 2 == 1) y += 1;
        if (coordinate.x() <= 5) y -= ((5 - coordinate.x()) / 2) * 2;
        else y -= ((4 - coordinate.x()) / 2) * 2;

        return new int[]{coordinate.x(), y};
    }

    //-- OVERRIDES --//

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintBackground(g);
        paintFigures(g);
    }
}
