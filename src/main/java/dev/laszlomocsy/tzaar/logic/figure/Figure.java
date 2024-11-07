package dev.laszlomocsy.tzaar.logic.figure;

/**
 * A single figure represented on the board.
 */
public class Figure {
    private FigureLocation location;
    private FigureColor color;
    private FigureType type;
    private int height;
    
    //-- Constructor --//
    
    /**
     * Initializes a new figure with the height of 1.
     * 
     * @param location The location of the figure.
     * @param color The color of the figure.
     * @param type The type of the figure.
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
     * @param color The color of the figure.
     * @param type The type of the figure.
     * @param height The height of the figure.
     */
    public Figure(FigureLocation location, FigureColor color, FigureType type, int height) {
        this.location = location;
        this.color = color;
        this.type = type;
        this.height = height;
    }
    
    //-- Getters --//
    
    /**
     * Gets the location of the figure on the board.
     * 
     * @return The location of the figure.
     */
    public FigureLocation getLocation() {
        return location;
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
     * Gets the type of the figure.
     * 
     * @return The type of the figure.
     */
    public FigureType getType() {
        return type;
    }
    
    /**
     * Gets the height of the figure.
     * 
     * @return The height of the figure.
     */
    public int getHeight() {
        return height;
    }
}
