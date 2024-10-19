package tzaar.component;

import tzaar.util.FigureColor;
import tzaar.util.FigureType;

public class Figure {
    private final FigureColor color;
    private FigureType type;
    private int size;

    public Figure(FigureColor color, FigureType type) {
        this.color = color;
        this.type = type;
        this.size = 1;
    }

    public FigureColor getColor() {
        return color;
    }

    public FigureType getType() {
        return type;
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        return String.format("Figure{color: %s, type: %s, size: %d}", color, type, size);
    }
}
