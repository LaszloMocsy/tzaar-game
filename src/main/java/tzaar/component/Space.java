package tzaar.component;

import tzaar.gui.SpaceButton;
import tzaar.util.FigureColor;
import tzaar.util.FigureType;

public class Space {
    public final Character locLetter;
    public final int locNumber;

    private FigureType type;
    private FigureColor color;
    private int size;

    private final SpaceButton button;

    public Space(Character locLetter, Integer locNumber) {
        this.locLetter = locLetter;
        this.locNumber = locNumber;
        this.type = null;
        this.color = null;
        this.size = 0;
        this.button = new SpaceButton(this);
    }

    public String toString() {
        if (this.isEmpty()) {
            return String.format("Space{loc: %s%d, empty}", locLetter, locNumber);
        }
        return String.format("Space{loc: %s%d, color: %s, type: %s, size %d}", locLetter, locNumber, color.toString().toLowerCase(), type.toString().toLowerCase(), size);
    }

    public boolean isEmpty() {
        return size == 0 || type == null || color == null;
    }

    public FigureType getType() {
        return type;
    }

    public FigureColor getColor() {
        return color;
    }

    public int getSize() {
        return size;
    }

    public SpaceButton getButton() {
        return button;
    }

    public void setFigure(FigureType type, FigureColor color, int size) {
        this.type = type;
        this.color = color;
        this.size = size;
        this.button.repaint();
    }
}
