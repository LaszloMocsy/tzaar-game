package dev.laszlomocsy.tzaar.gui;

import dev.laszlomocsy.tzaar.game.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BoardUI extends JPanel {
    private static final float FIGURE_STACK_PADDING = 0.15f;
    private static final int FIGURE_STACK_VISIBLE_MAX = 6;
    private final transient Image boardImage;
    private final transient List<Image> figureImages; // W-Tzaar, W-Tzarra, W-Tott, B-Tzaar, B-Tzarra, B-Tott
    private final GameStatusUI gameStatusUI;
    private int bgImageSize;
    private float cellWidth;
    private float cellHeight;
    private float figureSize;
    private transient Board boardRef;
    private transient Position bgImagePosition = new Position(0, 0);
    private transient Coordinate selectedFigureCoordinate = null;

    //-- CONSTRUCTOR --//

    public BoardUI(GameStatusUI gameStatusUI) {
        this.gameStatusUI = gameStatusUI;
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
        this.selectedFigureCoordinate = null;

        // Update the Figure buttons
        this.removeAll();
        for (Figure figure : board.getFigures()) {
            FigureButton figureButton = new FigureButton();
            figureButton.setActionCommand(figure.getLocation().asString());
            figureButton.addActionListener(this::figureButtonClicked);
            this.add(figureButton);
        }

        // Repaint the board
        repaint();
        
        // Set the game status label
        gameStatusUI.setStatusLabel("Select a figure to move...");
    }

    /// Clear the board
    public void clearBoard() {
        this.boardRef = null;
        this.selectedFigureCoordinate = null;

        // Repaint the board and remove the Figure buttons
        this.removeAll();
        repaint();
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

    /// Handle a figure button click
    ///
    /// @param event The ActionEvent of the button click
    private void figureButtonClicked(ActionEvent event) {
        String clickedCoordinate = event.getActionCommand();
        if (this.selectedFigureCoordinate == null) {
            selectFigure(clickedCoordinate);
        } else {
            handleFigureSelection(clickedCoordinate);
        }
        repaint();
    }

    /// Select a figure to move
    private void selectFigure(String clickedCoordinate) {
        this.selectedFigureCoordinate = Coordinate.fromString(clickedCoordinate);
        gameStatusUI.setStatusLabel("Select a target location...  [%s -> ??]".formatted(clickedCoordinate));
    }

    /// Deselect the selected figure
    private void deselectFigure() {
        this.selectedFigureCoordinate = null;
        gameStatusUI.setStatusLabel("Select a figure to move...");
    }

    private void handleFigureSelection(String clickedCoordinate) {
        if (this.selectedFigureCoordinate.asString().equals(clickedCoordinate)) {
            deselectFigure();
        } else {
            moveFigure(clickedCoordinate);
        }
    }

    /// Move the selected figure to the target location
    private void moveFigure(String clickedCoordinate) {
        Coordinate targetCoordinate = Coordinate.fromString(clickedCoordinate);
        MoveResult moveResult = this.boardRef.moveFigure(this.selectedFigureCoordinate, targetCoordinate);
        if (moveResult == MoveResult.SUCCESS) {
            removeFigureButton();
            deselectFigure();
        } else {
            String resultText = switch (moveResult) {
                case INVALID_MOVE -> "This is an invalid move!";
                case NO_FIGURE_TO_MOVE -> "There is no figure to move!";
                case NO_FIGURE_TO_CAPTURE_OR_STACK -> "There is no figure to capture or stack!";
                case NO_SAME_AXIS -> "The selected figures are not on the same axis!";
                case NOT_THE_CLOSEST_FIGURE -> "There are figures between the selected figures!";
                case GAME_NOT_IN_PROGRESS -> "The game is not in progress!";
                default -> "Unknown error!";
            };
            gameStatusUI.setStatusLabel("%s  [%s -> %s]  /  Select a target location...  [%s -> ??]".formatted(resultText, selectedFigureCoordinate, clickedCoordinate, selectedFigureCoordinate));
        }
    }

    /// Remove the button of the selected figure
    private void removeFigureButton() {
        Component[] components = this.getComponents();
        for (Component component : components) {
            if (((FigureButton) component).getActionCommand().equals(selectedFigureCoordinate.asString())) {
                this.remove(component);
                break;
            }
        }
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

            paintFigure(g, figure, baseAnchor);
        }
    }
    
    /// Paint a single figure to the board
    private void paintFigure(Graphics g, Figure figure, Position baseAnchor) {
        final int figureStackVisibleTextMin = 3;
        final int figureHeight = figure.getHeight();
        final int figureTopIndex = Math.min(figureHeight, FIGURE_STACK_VISIBLE_MAX) - 1;
        for (int i = 0; i <= figureTopIndex; i++) {
            // Draw the selected figure's visualizer
            if (this.selectedFigureCoordinate != null && this.selectedFigureCoordinate.equals(figure.getLocation())) {
                final float ringPadding = 0.25f;
                float ringSize = figureSize * (1 + ringPadding);
                int ringSizeInt = Math.round(ringSize);
                double ringPaddingCalc = Math.round((ringSize * ringPadding) / 2);
                g.setColor(Color.BLUE);
                g.drawOval((int) Math.round(baseAnchor.x - ringPaddingCalc), (int) Math.round(baseAnchor.y - ringPaddingCalc), ringSizeInt, ringSizeInt);
            }

            // Draw the figure's image
            Position anchor = baseAnchor.calculateAnchor(new Position(0, figureSize * FIGURE_STACK_PADDING * i));
            Image image = i == figureTopIndex
                    ? figureImages.get((figure.getColor().ordinal() * 3) + figure.getType().ordinal())
                    : figureImages.get((figure.getColor().ordinal() * 3) + 2);
            g.drawImage(image, anchor.roundX(), anchor.roundY(), Math.round(figureSize), Math.round(figureSize), null);

            // Draw the figure's height as text if it's 5 or more
            if (i == figureTopIndex && figureHeight >= figureStackVisibleTextMin) {
                g.setColor(figure.getColor() == FigureColor.WHITE ? Color.BLACK : Color.WHITE);
                g.setFont(new Font("Arial", Font.PLAIN, (int) Math.floor(figureSize / 4)));
                String text = String.format("x%d", figureHeight);
                FontMetrics metrics = g.getFontMetrics();
                int textWidth = metrics.stringWidth(text);
                int textHeight = metrics.getHeight();
                int textX = anchor.roundX() + (Math.round(figureSize) - textWidth) / 2;
                int textY = anchor.roundY() + (Math.round(figureSize) + textHeight) / 2 - metrics.getDescent();
                g.drawString(text, textX, textY);
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
