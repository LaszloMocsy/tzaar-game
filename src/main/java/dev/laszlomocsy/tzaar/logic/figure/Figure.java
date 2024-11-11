package dev.laszlomocsy.tzaar.logic.figure;

/**
 * A single figure represented on the board.
 */
public class Figure {
    private FigureColor color;
    private FigureLocation location;
    private FigureType type;
    private int height;

    //-- Constructor --//

    public Figure() {
        this.location = null;
        this.color = null;
        this.type = null;
        this.height = 0;
    }

    /**
     * Initializes a new figure with the height of 1.
     *
     * @param location The location of the figure.
     * @param color    The color of the figure.
     * @param type     The type of the figure.
     */
    public Figure(FigureLocation location, FigureColor color, FigureType type) {
        this.location = location;
        this.color = color;
        this.type = type;
        this.height = 1;
    }

    /**
     * Initializes a new figure.
     *
     * @param location The location of the figure.
     * @param color    The color of the figure.
     * @param type     The type of the figure.
     * @param height   The height of the figure.
     */
    public Figure(FigureLocation location, FigureColor color, FigureType type, int height) {
        this.location = location;
        this.color = color;
        this.type = type;
        this.height = height;
    }

    //-- Getters/Setters --//

    /**
     * Gets the location of the figure on the board.
     *
     * @return The location of the figure.
     */
    public FigureLocation getLocation() {
        return location;
    }

    /**
     * Sets the location of the figure on the board.
     *
     * @param location The new location of the figure.
     */
    public void setLocation(FigureLocation location) {
        this.location = location;
    }

    /**
     * Gets the color of the figure.
     *
     * @return The color of the figure.
     */
    public FigureColor getColor() {
        return color;
    }

    /**
     * Sets the color of the figure.
     *
     * @param color The new color of the figure.
     */
    public void setColor(FigureColor color) {
        this.color = color;
    }

    /**
     * Gets the type of the figure.
     *
     * @return The type of the figure.
     */
    public FigureType getType() {
        return type;
    }

    /**
     * Sets the type of the figure.
     *
     * @param type The new type of the figure.
     */
    public void setType(FigureType type) {
        this.type = type;
    }

    /**
     * Gets the height of the figure.
     *
     * @return The height of the figure.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Sets the height of the figure.
     *
     * @param height The new height of the figure.
     */
    public void setHeight(int height) {
        this.height = height;
    }
}
