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
    private int bgImageSize;
    private float cellWidth;
    private float cellHeight;
    private float figureSize;
    private transient Board boardRef;
    private transient Position bgImagePosition = new Position(0, 0);

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

        // Repaint the board
        repaint();

        // Update the Figure buttons
        this.removeAll();
        for (Figure figure : board.getFigures()) {
            FigureButton figureButton = new FigureButton();
            figureButton.setActionCommand(figure.getLocation().asString());
            this.add(figureButton);
        }
    }

    /// Clear the board
    public void clearBoard() {
        this.boardRef = null;

        // Repaint the board and remove the Figure buttons
        repaint();
        this.removeAll();
    }

    //-- METHODS (private) --//

    /// Convert a coordinate to a position on the boardUI
    private Position coordinateToPositionOrigo(Coordinate coordinate) {
        int x = coordinate.x();
        int y = (9 - coordinate.y()) * 2;

        if (x % 2 == 1) y += 1;
        if (x <= 5) y -= ((5 - x) / 2) * 2;
        else y -= ((4 - x) / 2) * 2;

        final float startX = this.bgImagePosition.x;
        final float startY = this.bgImagePosition.y;

        return new Position(startX + (x * cellWidth) + (cellWidth / 2), startY + (y * cellHeight) + (cellHeight / 2));
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
        this.bgImageSize = Collections.min(Arrays.asList(panelWidth, panelHeight, bgImageWidth, bgImageHeight));

        // Calculate the image's position to center it within the panel
        int posX = (panelWidth - bgImageSize) / 2;
        int posY = (panelHeight - bgImageSize) / 2;
        this.bgImagePosition = new Position(posX, posY);

        // Draw the image
        g.drawImage(boardImage, posX, posY, bgImageSize, bgImageSize, null);
    }

    /// Paint the figures on the board
    private void paintFigures(Graphics g) {
        // Loop through the board and draw the figures
        // TODO - Draw figure stacks
        for (Figure figure : this.boardRef.getFigures()) {
            Position origo = coordinateToPositionOrigo(figure.getLocation());
            Position anchorOffset = new Position(figureSize / 2, figureSize / 2);
            Position anchor = origo.calculateAnchor(anchorOffset);
            Image image = figureImages.get((figure.getColor().ordinal() * 3) + figure.getType().ordinal());
            g.drawImage(image, anchor.roundX(), anchor.roundY(), Math.round(figureSize), Math.round(figureSize), null);
        }
    }

    /// Position the figure buttons on the board
    private void positionFigureButtons() {
        // Loop through the board and position the buttons
        for (Component component : this.getComponents()) {
            // Calculate the button's size using the figure's size
            float buttonWidth = figureSize * 1.2f;
            float buttonHeight = figureSize * 1.2f;
            
            // Calculate the button's position
            FigureButton figureButton = (FigureButton) component;
            Coordinate coordinate = Coordinate.fromString(figureButton.getActionCommand());
            Position origo = coordinateToPositionOrigo(coordinate);
            Position anchorOffset = new Position(buttonWidth / 2, buttonHeight / 2);
            Position anchor = origo.calculateAnchor(anchorOffset);
            
            figureButton.setBounds(anchor.roundX(), anchor.roundY(), Math.round(buttonWidth), Math.round(buttonHeight));
        }
    }

    //-- OVERRIDES --//

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintBackground(g);

        // Only draw figures if the board is set up
        if (boardRef == null) return;

        cellWidth = (float) bgImageSize / 11;
        cellHeight = (float) bgImageSize / 19;
        figureSize = cellWidth / 2;
        
        paintFigures(g);
        positionFigureButtons();
    }

    //-- CLASSES --//

    private record Position(float x, float y) {
        public int roundX() {
            return Math.round(this.x);
        }

        public int roundY() {
            return Math.round(this.y);
        }

        public Position calculateAnchor(Position offset) {
            return new Position(this.x - offset.x, this.y - offset.y);
        }
    }
}
