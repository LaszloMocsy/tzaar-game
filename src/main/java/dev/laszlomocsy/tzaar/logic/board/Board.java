package dev.laszlomocsy.tzaar.logic.board;

import dev.laszlomocsy.tzaar.logic.figure.Figure;
import dev.laszlomocsy.tzaar.logic.figure.FigureColor;
import dev.laszlomocsy.tzaar.logic.figure.FigureLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The board of the game.
 * <p>
 * The board represents the game area with 60 spaces, where the <code>Figure</code>s are placed.
 */
public class Board {
    private final List<Figure> figures = new ArrayList<>();
    private BoardStatus status;
    private FigureColor nextColor;
    private int moveCounter;

    //-- Constructors --//

    /**
     * Creates a new board with the initial status of SETUP.
     */
    public Board() {
        this.status = BoardStatus.SETUP;
        this.nextColor = FigureColor.WHITE;
        this.moveCounter = 0;
    }

    //-- Getters --//

    /**
     * Retrieves the current status of the board.
     *
     * @return the current status of the board as a {@link BoardStatus} enum value
     */
    public BoardStatus getStatus() {
        return status;
    }

    /**
     * Retrieves the list of figures currently placed on the board.
     *
     * @return the list of figures currently placed on the board
     */
    public List<Figure> getFigures() {
        return figures;
    }

    //-- Private Methods --//

    /**
     * Updates the status of the board.
     * <p>
     * The status of the board is updated based on the remaining figures on the board, and the possible next moves.
     */
    private void updateStatus() {
        // Rule 1. - Check each color's figure count
        int[] figureCounts = new int[]{0, 0, 0, 0, 0, 0}; // W-Tzaar, W-Tzarra, W-Tott, B-Tzaar, B-Tzarra, B-Tott
        for (Figure figure : figures) {
            figureCounts[(figure.getColor().ordinal() * 3) + figure.getType().ordinal()]++;
        }
        if (figureCounts[0] == 0 || figureCounts[1] == 0 || figureCounts[2] == 0) {
            this.status = BoardStatus.BLACK_WON;
        } else if (figureCounts[3] == 0 || figureCounts[4] == 0 || figureCounts[5] == 0) {
            this.status = BoardStatus.WHITE_WON;
        }

        // Rule 2. - Check which color cannot move
        if (isColorUnableToCapture(FigureColor.WHITE)) this.status = BoardStatus.BLACK_WON;
        if (isColorUnableToCapture(FigureColor.BLACK)) this.status = BoardStatus.WHITE_WON;
    }

    /**
     * Checks if the given color can move.
     *
     * @param color the color to check if it can move
     * @return true if the color can move, false otherwise
     */
    private boolean isColorUnableToCapture(FigureColor color) {
        FigureColor oppositeColor = color == FigureColor.WHITE ? FigureColor.BLACK : FigureColor.WHITE;

        for (Figure figure : figures.stream().filter(f -> f.getColor() == color).toList()) {
            List<FigureLocation> nextLocations = getNextLocations(figure.getLocation());
            List<Figure> nextBlackFigures = nextLocations.stream().map(loc -> figures.stream().filter(f -> f.getLocation().equals(loc) && f.getColor() == oppositeColor).findFirst().orElse(null)).toList();
            if (!nextBlackFigures.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    /**
     * Gets the next figure from the given location on the given axis.
     *
     * @param location the location to get the next figure from
     * @param x        the x-axis to get the next figure from
     * @param y        the y-axis to get the next figure from
     * @return the next figure from the given location on the given axis
     */
    private Optional<FigureLocation> getNextFigureFromAxis(FigureLocation location, int x, int y) {
        for (int i = 0; i < 8; i++) {
            int nextX = location.x() + (x * i);
            int nextY = location.y() + (y * i);
            boolean isValid = FigureLocation.isValid(nextX, nextY);

            if (nextX != 5 || nextY != 5) {
                // The location is not the center of the board, check if it is valid and occupied

                // The location is not valid, so we can stop here, because this is the first location outside the board
                if (!isValid) break;

                FigureLocation nextLocation = new FigureLocation(nextX, nextY);
                Figure nextFigure = this.figures.stream().filter(f -> f.getLocation().equals(nextLocation)).findFirst().orElse(null);
                if (nextFigure != null) {
                    return Optional.of(nextLocation);
                }
            }
        }

        return Optional.empty();
    }

    //-- Public Methods --//

    /**
     * Places a new figure on the board.
     *
     * @param figure the figure to place on the board
     * @return the result of the action as a {@link BoardActionResult} enum value
     */
    public BoardActionResult placeFigure(Figure figure) {
        // Check for valid board state for placing a figure
        if (figure == null) return BoardActionResult.FIGURE_NULL;
        else if (this.status != BoardStatus.SETUP) return BoardActionResult.BOARD_STATUS_INVALID;

        // Check if the location is already occupied
        if (this.figures.stream().anyMatch(f -> f.getLocation().equals(figure.getLocation()))) {
            return BoardActionResult.FIGURE_LOCATION_OCCUPIED;
        }

        // Add the figure to the board
        figures.add(figure);

        // Check if the board is full, then change the status to IN_GAME
        if (figures.size() == 60) {
            this.status = BoardStatus.IN_GAME;
        }

        return BoardActionResult.SUCCESS;
    }

    /**
     * Moves a figure from one location to another on the board.
     *
     * @param locationA the location of the figure to move
     * @param locationB the location to move the figure to
     * @return the result of the action as a {@link BoardActionResult} enum value
     */
    public BoardActionResult moveFigure(FigureLocation locationA, FigureLocation locationB) {
        // Check for valid board state for moving a figure
        if (this.status != BoardStatus.IN_GAME) return BoardActionResult.BOARD_STATUS_INVALID;
        // Check if the two locations are the same
        if (locationA == locationB) return BoardActionResult.LOCATIONS_ARE_EQUAL;

        Figure figureA = figures.stream().filter(f -> f.getLocation().equals(locationA)).findFirst().orElse(null);
        if (figureA == null) return BoardActionResult.LOCATION_A_EMPTY;

        Figure figureB = figures.stream().filter(f -> f.getLocation().equals(locationB)).findFirst().orElse(null);
        if (figureB == null) return BoardActionResult.LOCATION_B_EMPTY;

        // Only the next color can move
        if (figureA.getColor() != this.nextColor) return BoardActionResult.COLOR_INVALID;

        // Check if the two locations are on the same axis
        if (!FigureLocation.hasSameAxis(locationA, locationB)) return BoardActionResult.LOCATIONS_HAS_NO_SAME_AXIS;

        // Check if 'locationB' can be the next move from 'locationA'
        List<FigureLocation> nextLocations = getNextLocations(locationA);
        if (!nextLocations.contains(locationB)) return BoardActionResult.INVALID_MOVE;

        // Capture! or Stack!
        if (this.moveCounter == 1 && figureA.getColor() == figureB.getColor()) {
            // Stack! - Available only if this is the second move and the colors are the same
            figureB.setType(figureA.getType());
            figureB.setHeight(figureB.getHeight() + figureA.getHeight());
            figures.remove(figureA);
        } else if (figureA.getColor() != figureB.getColor()) {
            // Capture! - Available only if the colors are different
            figures.remove(figureB);
            figureA.setLocation(locationB);
        } else {
            // 'moveCounter' is 0, and the colors are the same, so this is the first move and cannot stack
            return BoardActionResult.INVALID_MOVE;
        }

        // increment the move counter
        this.moveCounter = (this.moveCounter + 1) % 2;
        if (this.moveCounter == 0) {
            // Change the next color
            this.nextColor = this.nextColor == FigureColor.WHITE ? FigureColor.BLACK : FigureColor.WHITE;
        }

        updateStatus();
        return BoardActionResult.SUCCESS;
    }

    /**
     * Gets the next locations from the given location.
     * <p>
     * These are the locations that are on the same axis
     * and are the closest, first occupied to the given location.
     *
     * @param loc the location to get the next locations from.
     * @return the list of next locations.
     */
    private List<FigureLocation> getNextLocations(FigureLocation loc) {
        List<FigureLocation> locations = new ArrayList<>();

        Optional<FigureLocation> nextUpperX = getNextFigureFromAxis(loc, 1, 0);
        nextUpperX.ifPresent(locations::add);

        Optional<FigureLocation> nextLowerX = getNextFigureFromAxis(loc, -1, 0);
        nextLowerX.ifPresent(locations::add);

        Optional<FigureLocation> nextUpperY = getNextFigureFromAxis(loc, 0, 1);
        nextUpperY.ifPresent(locations::add);

        Optional<FigureLocation> nextLowerY = getNextFigureFromAxis(loc, 0, -1);
        nextLowerY.ifPresent(locations::add);

        Optional<FigureLocation> nextUpperZ = getNextFigureFromAxis(loc, 1, 1);
        nextUpperZ.ifPresent(locations::add);

        Optional<FigureLocation> nextLowerZ = getNextFigureFromAxis(loc, -1, -1);
        nextLowerZ.ifPresent(locations::add);

        return locations;
    }
}
