package dev.laszlomocsy.tzaar.logic.board;

import dev.laszlomocsy.tzaar.logic.figure.Figure;
import dev.laszlomocsy.tzaar.logic.figure.FigureColor;
import dev.laszlomocsy.tzaar.logic.figure.FigureLocation;
import dev.laszlomocsy.tzaar.logic.figure.FigureType;

import java.util.ArrayList;
import java.util.Arrays;
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
    
    private Board() {
        this.status = BoardStatus.SETUP;
        this.nextColor = FigureColor.WHITE;
        this.moveCounter = 0;
    }
    
    public static Board InitEmpty() {
        return new Board();
    }

    public static Board InitDefault() {
        Board board = new Board();
        
        // Initialize the board with default placing
        int[] spacesInX = {5, 6, 7, 8, 8, 8, 7, 6, 5};
        for (int x = 1; x <= 9; x++) {
            int startY = Math.max(1, x - 4);
            for (int yCount = 0; yCount < spacesInX[x - 1]; yCount++) {
                int y = startY + yCount;
                if (x == 5 && yCount >= 4) y += 1;

                final List<String> whiteFigures = Arrays.asList("A5", "B1", "B5", "B6", "C1", "C2", "C5", "C6", "C7",
                        "D1", "D2", "D3", "D5", "D6", "D7", "D8", "E1", "E2", "E3", "E4", "F6", "G6", "G7", "H6", "H7",
                        "H8", "I6", "I7", "I8", "I9");
                final List<String> tzaarFigures = Arrays.asList("C3", "C4", "C5", "D3", "D6", "E3", "E7", "F4", "F7",
                        "G5", "G6", "G7");
                final List<String> tzarraFigures = Arrays.asList("B2", "B3", "B4", "B5", "C2", "C6", "D2", "D7", "E2",
                        "E8", "F3", "F8", "G4", "G8", "H5", "H6", "H7", "H8");

                FigureLocation location = new FigureLocation(x, y);
                FigureColor color = whiteFigures.contains(location.toString()) ? FigureColor.WHITE : FigureColor.BLACK;
                FigureType type;
                if (tzaarFigures.contains(location.toString())) type = FigureType.TZAAR;
                else if (tzarraFigures.contains(location.toString())) type = FigureType.TZARRA;
                else type = FigureType.TOTT;

                board.placeFigure(new Figure(location, color, type, 1));
            }
        }
        
        return board;
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

    /**
     * Retrieves the next color that can move.
     *
     * @return the next color that can move as a {@link FigureColor} enum value
     */
    public FigureColor getNextColor() {
        return nextColor;
    }

    /**
     * Retrieves the current move counter.
     *
     * @return the current move counter
     */
    public int getMoveCounter() {
        return moveCounter;
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
        for (int i = 1; i < 8; i++) {
            int nextX = location.x() + (x * i);
            int nextY = location.y() + (y * i);
            boolean isValid = FigureLocation.isValid(nextX, nextY);

            // The location is not valid, so we can stop here, because this is the first location outside the board
            if (!isValid) break;

            FigureLocation nextLocation = new FigureLocation(nextX, nextY);
            Figure nextFigure = this.figures.stream().filter(f -> f.getLocation().equals(nextLocation)).findFirst().orElse(null);
            if (nextFigure != null) {
                return Optional.of(nextLocation);
            }
        }

        return Optional.empty();
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

    private void incrementMoveCounter() {
        // Check if this was the very first move
        if (this.figures.size() == 59) {
            this.nextColor = FigureColor.BLACK;
        } else {
            // increment the move counter
            this.moveCounter = (this.moveCounter + 1) % 2;
            if (this.moveCounter == 0) {
                // Change the next color
                this.nextColor = this.nextColor == FigureColor.WHITE ? FigureColor.BLACK : FigureColor.WHITE;
            }
        }
    }

    //-- Public Methods --//

    /**
     * Starts the game.
     */
    public void startGame() {
        this.status = BoardStatus.IN_GAME;
        
        updateStatus();
    }

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
            if (figureA.getHeight() < figureB.getHeight()) return BoardActionResult.FIGURE_HEIGHT_SMALLER;

            figures.remove(figureB);
            figureA.setLocation(locationB);
        } else {
            // 'moveCounter' is 0, and the colors are the same, so this is the first move and cannot stack
            return BoardActionResult.INVALID_MOVE;
        }

        incrementMoveCounter();
        updateStatus();
        return BoardActionResult.SUCCESS;
    }

    /**
     * Passes the move to the next color.
     */
    public void passMove() {
        // Check for valid board state and for second move for passing the move
        if (this.status != BoardStatus.IN_GAME || this.moveCounter != 1) return;

        // increment the move counter
        this.moveCounter = 0;
        this.nextColor = this.nextColor == FigureColor.WHITE ? FigureColor.BLACK : FigureColor.WHITE;
    }
}
