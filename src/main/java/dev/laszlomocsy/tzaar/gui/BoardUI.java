package dev.laszlomocsy.tzaar.gui;

import dev.laszlomocsy.tzaar.game.Board;
import dev.laszlomocsy.tzaar.game.Coordinate;
import dev.laszlomocsy.tzaar.game.Figure;
import dev.laszlomocsy.tzaar.game.FigureColor;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BoardUI extends JPanel {
    private static final float FIGURE_STACK_PADDING = 0.15f;
    private static final int FIGURE_STACK_VISIBLE_MAX = 6;
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
        this.boardImage = new ImageIcon("src/main/resources/Tzaar-GameBoard.png").getImage();
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
            figureButton.addActionListener(e -> {
                // Show a dialog with the figure's details
                String message = String.format("Location: %s%nColor: %s%nType: %s%nHeight: %d",
                        figure.getLocation().asString(), figure.getColor(), figure.getType(), figure.getHeight());
                JOptionPane.showMessageDialog(this, message, "Figure Details", JOptionPane.INFORMATION_MESSAGE);
            });
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
        for (Figure figure : this.boardRef.getFigures()) {
            Position origo = coordinateToPositionOrigo(figure.getLocation());
            Position anchorOffset = new Position(figureSize / 2, figureSize / 2);
            Position baseAnchor = origo.calculateAnchor(anchorOffset);

            for (int i = 0; i < figure.getHeight(); i++) {
                Position anchor = baseAnchor.calculateAnchor(new Position(0, figureSize * FIGURE_STACK_PADDING * Math.min(i, FIGURE_STACK_VISIBLE_MAX)));
                Image image = figure.getHeight() - 1 == i
                        ? figureImages.get((figure.getColor().ordinal() * 3) + figure.getType().ordinal())
                        : figureImages.get((figure.getColor().ordinal() * 3) + 2);
                g.drawImage(image, anchor.roundX(), anchor.roundY(), Math.round(figureSize), Math.round(figureSize), null);

                // Draw the figure's height as text if it's 5 or more
                final int figureStackVisibleTextMin = 3;
                if (figure.getHeight() >= figureStackVisibleTextMin) {
                    g.setColor(figure.getColor() == FigureColor.WHITE ? Color.BLACK : Color.WHITE);
                    g.setFont(new Font("Arial", Font.PLAIN, (int) Math.floor(figureSize / 4)));
                    String text = String.format("x%d", figure.getHeight());
                    FontMetrics metrics = g.getFontMetrics();
                    int textWidth = metrics.stringWidth(text);
                    int textHeight = metrics.getHeight();
                    int textX = anchor.roundX() + (Math.round(figureSize) - textWidth) / 2;
                    int textY = anchor.roundY() + (Math.round(figureSize) + textHeight) / 2 - metrics.getDescent();
                    g.drawString(text, textX, textY);
                }
            }
        }
    }

    /// Position the figure buttons on the board
    private void positionFigureButtons() {
        // Loop through the board and position the buttons
        for (Component component : this.getComponents()) {
            // Get the figure represented by the button
            FigureButton figureButton = (FigureButton) component;
            Figure figure = this.boardRef.getFigureFrom(Coordinate.fromString(figureButton.getActionCommand()));

            // Calculate the button's size using the figure's size
            final float figureButtonPadding = 1.2f;
            float buttonWidth = figureSize * figureButtonPadding;
            float buttonHeight = figureSize * figureButtonPadding;
            float buttonHeightStack = figureSize * FIGURE_STACK_PADDING * Math.min(figure.getHeight() - 1, FIGURE_STACK_VISIBLE_MAX);

            // Calculate the button's position
            Coordinate coordinate = Coordinate.fromString(figureButton.getActionCommand());
            Position origo = coordinateToPositionOrigo(coordinate);
            Position anchorOffset = new Position(buttonWidth / 2, (buttonHeight / 2) + buttonHeightStack);
            Position anchor = origo.calculateAnchor(anchorOffset);

            figureButton.setBounds(anchor.roundX(), anchor.roundY(), Math.round(buttonWidth), Math.round(buttonHeight + buttonHeightStack));
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
