package tzaar.component;

import tzaar.util.FigureColor;
import tzaar.util.FigureType;

public class Space {
    public final SpaceLocation location;
    private Figure figure;

    public Space(SpaceLocation location) {
        this.location = location;
        this.figure = null;
    }

    public Space(SpaceLocation location, Figure figure) {
        this.location = location;
        this.figure = figure;
    }

    public boolean hasFigure() {
        return this.figure != null;
    }

    public FigureColor getFigureColor() {
        return this.figure.getColor();
    }

    public FigureType getFigureType() {
        return this.figure.getType();
    }

    public int getFigureSize() {
        return this.figure.getSize();
    }

    @Override
    public String toString() {
        return String.format("Space{loc: %s}", location);
    }
}
