package dev.laszlomocsy.tzaar.gui;

import dev.laszlomocsy.tzaar.logic.board.Board;
import dev.laszlomocsy.tzaar.logic.figure.Figure;
import dev.laszlomocsy.tzaar.logic.figure.FigureColor;
import dev.laszlomocsy.tzaar.logic.figure.FigureLocation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Represents the board panel of the game.
 */
public class BoardPanel extends JPanel {
    private static final Image BOARD_IMAGE;
    private static final List<Image> FIGURE_IMAGES;
    private static final float FIGURE_STACK_PADDING = 0.15f;
    private static final int FIGURE_STACK_VISIBLE_MAX = 6;

    static {
        URL boardImg = BoardPanel.class.getResource("/board.png");
        URL whiteTzaarImg = BoardPanel.class.getResource("/white-tzaar.png");
        URL whiteTzarraImg = BoardPanel.class.getResource("/white-tzarra.png");
        URL whiteTottImg = BoardPanel.class.getResource("/white-tott.png");
        URL blackTzaarImg = BoardPanel.class.getResource("/black-tzaar.png");
        URL blackTzarraImg = BoardPanel.class.getResource("/black-tzarra.png");
        URL blackTottImg = BoardPanel.class.getResource("/black-tott.png");

        if (boardImg == null) {
            throw new IllegalArgumentException("Resource not found: board.png");
        } else if (whiteTzaarImg == null) {
            throw new IllegalArgumentException("Resource not found: white-tzaar.png");
        } else if (whiteTzarraImg == null) {
            throw new IllegalArgumentException("Resource not found: white-tzarra.png");
        } else if (whiteTottImg == null) {
            throw new IllegalArgumentException("Resource not found: white-tott.png");
        } else if (blackTzaarImg == null) {
            throw new IllegalArgumentException("Resource not found: black-tzaar.png");
        } else if (blackTzarraImg == null) {
            throw new IllegalArgumentException("Resource not found: black-tzarra.png");
        } else if (blackTottImg == null) {
            throw new IllegalArgumentException("Resource not found: black-tott.png");
        }

        BOARD_IMAGE = new ImageIcon(boardImg).getImage();
        FIGURE_IMAGES = Arrays.asList(
                new ImageIcon(whiteTzaarImg).getImage(),
                new ImageIcon(whiteTzarraImg).getImage(),
                new ImageIcon(whiteTottImg).getImage(),
                new ImageIcon(blackTzaarImg).getImage(),
                new ImageIcon(blackTzarraImg).getImage(),
                new ImageIcon(blackTottImg).getImage()
        );
    }

    private final List<SpaceButton> spaceButtons;
    private transient Board board;
    private int bgImageSize;
    private float cellWidth;
    private float cellHeight;
    private float figureSize;
    private transient Position bgImagePosition;
    private transient FigureLocation selectedFigureLocation;

    //-- Constructor --//

    /**
     * Creates a new board panel.
     */
    public BoardPanel() {
        // Create buttons for each figure on the board
        this.spaceButtons = new ArrayList<>();
        for (FigureLocation location : FigureLocation.getAllLocation()) {
            SpaceButton spaceButton = new SpaceButton();
            spaceButton.setActionCommand(location.toString());
            this.spaceButtons.add(spaceButton);
            this.add(spaceButton);
        }
    }

    //-- Getter --//

    /**
     * Gets the board.
     *
     * @return The board.
     */
    public FigureLocation getSelectedFigureLocation() {
        return selectedFigureLocation;
    }

    //-- Setter --//

    /**
     * Sets the selected figure location.
     *
     * @param location The selected figure location.
     */
    public void setSelectedFigureLocation(FigureLocation location) {
        this.selectedFigureLocation = location;
    }

    /**
     * Sets the board.
     *
     * @param board The board to set.
     */
    public void setBoard(Board board) {
        this.board = board;
    }

    //-- Private methods --//

    /**
     * Converts a figure location to a position on the board.
     *
     * @param location The figure location.
     * @return The position on the board.
     */
    private Position locationToPositionOrigo(FigureLocation location) {
        int x = location.x();
        int y = (9 - location.y()) * 2;

        if (x % 2 == 1) y += 1;
        if (x <= 5) y -= ((5 - x) / 2) * 2;
        else y -= ((4 - x) / 2) * 2;

        final float startX = this.bgImagePosition.x;
        final float startY = this.bgImagePosition.y;

        return new Position(startX + (x * cellWidth) + (cellWidth / 2), startY + (y * cellHeight) + (cellHeight / 2));
    }

    //-- Public methods --//

    /**
     * Adds a space button listener.
     *
     * @param listener The listener to add.
     */
    public void addSpaceButtonListener(ActionListener listener) {
        for (SpaceButton btnSpace : this.spaceButtons) {
            btnSpace.addActionListener(listener);
        }
    }

    //-- Paint methods --//

    @Override
    protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
        paintBackground(g);

        cellWidth = (float) bgImageSize / 11;
        cellHeight = (float) bgImageSize / 19;
        figureSize = cellWidth / 2;

        positionSpaceButtons();

        // Only draw figures if the board is set up
        if (board != null) paintFigures(g);
    }

    /**
     * Paints the background of the board.
     *
     * @param g The graphics object.
     */
    private void paintBackground(Graphics g) {
        // Paint the background white, because the image's background is white
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Get the panel's and bgImage's dimensions
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        int bgImageWidth = BOARD_IMAGE.getWidth(null);
        int bgImageHeight = BOARD_IMAGE.getHeight(null);

        // Calculate the image size to fit within the panel
        this.bgImageSize = Collections.min(Arrays.asList(panelWidth, panelHeight, bgImageWidth, bgImageHeight));

        // Calculate the image's position to center it within the panel
        int posX = (panelWidth - bgImageSize) / 2;
        int posY = (panelHeight - bgImageSize) / 2;
        this.bgImagePosition = new Position(posX, posY);

        // Draw the image
        g.drawImage(BOARD_IMAGE, posX, posY, bgImageSize, bgImageSize, null);
    }

    /**
     * Paints the figures on the board.
     *
     * @param g The graphics object.
     */
    private void paintFigures(Graphics g) {
        // Loop through the board and draw the figures
        for (Figure figure : this.board.getFigures()) {
            Position origo = locationToPositionOrigo(figure.getLocation());
            Position anchorOffset = new Position(figureSize / 2, figureSize / 2);
            Position baseAnchor = origo.calculateAnchor(anchorOffset);

            paintFigure(g, figure, baseAnchor);
        }
    }

    /**
     * Paints a figure on the board.
     *
     * @param g          The graphics object.
     * @param figure     The figure to paint.
     * @param baseAnchor The base anchor position.
     */
    private void paintFigure(Graphics g, Figure figure, Position baseAnchor) {
        final int figureStackVisibleTextMin = 3;
        final int figureHeight = figure.getHeight();
        final int figureTopIndex = Math.min(figureHeight, FIGURE_STACK_VISIBLE_MAX) - 1;
        for (int i = 0; i <= figureTopIndex; i++) {
            // Draw the selected figure's visualizer
            if (this.selectedFigureLocation != null && this.selectedFigureLocation.equals(figure.getLocation())) {
                final float ringPadding = 0.25f;
                float ringSize = figureSize * (1 + ringPadding);
                int ringSizeInt = Math.round(ringSize);
                double ringPaddingCalc = Math.round((ringSize * ringPadding) / 2);
                g.setColor(Color.BLUE);
                g.drawOval((int) Math.round(baseAnchor.x - ringPaddingCalc), (int) Math.round(baseAnchor.y - ringPaddingCalc), ringSizeInt, ringSizeInt);
            }

            // Draw the figure's image
            Position anchor = baseAnchor.calculateAnchor(new Position(0, figureSize * FIGURE_STACK_PADDING * i));
            Image image = i == figureTopIndex ? FIGURE_IMAGES.get((figure.getColor().ordinal() * 3) + figure.getType().ordinal()) : FIGURE_IMAGES.get((figure.getColor().ordinal() * 3) + 2);
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

    /**
     * Positions the space buttons on the board.
     */
    private void positionSpaceButtons() {
        // Loop through all space buttons
        for (SpaceButton btnSpace : this.spaceButtons) {
            // Get the figure represented by the button
            FigureLocation location = FigureLocation.fromString(btnSpace.getActionCommand());
            Figure figure = this.board != null ? this.board.getFigures().stream().filter(f -> f.getLocation().equals(location)).findFirst().orElse(null) : null;

            // Calculate the button's size using the figure's size
            int figureHeight = figure != null ? figure.getHeight() : 1;
            final float figureButtonPadding = 1.2f;
            float buttonWidth = figureSize * figureButtonPadding;
            float buttonHeight = figureSize * figureButtonPadding;
            float buttonHeightStack = figureSize * FIGURE_STACK_PADDING * Math.min(figureHeight - 1, FIGURE_STACK_VISIBLE_MAX);

            // Calculate the button's position
            Position origo = locationToPositionOrigo(location);
            Position anchorOffset = new Position(buttonWidth / 2, (buttonHeight / 2) + buttonHeightStack);
            Position anchor = origo.calculateAnchor(anchorOffset);

            btnSpace.setBounds(anchor.roundX(), anchor.roundY(), Math.round(buttonWidth), Math.round(buttonHeight + buttonHeightStack));
        }
    }

    //-- CLASSES --//

    /**
     * Represents a position on the board.
     */
    private record Position(float x, float y) {
        public int roundX() {
            return Math.round(this.x);
        }

        public int roundY() {
            return Math.round(this.y);
        }

        /**
         * Calculates the anchor position.
         *
         * @param offset The offset position.
         * @return The anchor position.
         */
        public Position calculateAnchor(Position offset) {
            return new Position(this.x - offset.x, this.y - offset.y);
        }
    }
}
