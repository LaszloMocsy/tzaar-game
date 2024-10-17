package tzaar.component;

import tzaar.util.FigureColor;
import tzaar.util.FigureType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
    private final List<Space> spaces;

    public Board() {
        this.spaces = new ArrayList<>();

        final Character[] locLetters = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I'};
        final int[] spacesInLetters = {5, 6, 7, 8, 8, 8, 7, 6, 5};

        for (int idxLocLetters = 0; idxLocLetters < locLetters.length; idxLocLetters++) {
            for (int countInLetter = 0; countInLetter < spacesInLetters[idxLocLetters]; countInLetter++) {
                Character locLetter = locLetters[idxLocLetters];
                int locNumber = countInLetter + 1;

                if (locLetter == 'E' && locNumber >= 5) {
                    locNumber += 1;
                } else if (idxLocLetters >= 5) {
                    locNumber += idxLocLetters - 4;
                }

                Space space = new Space(locLetter, locNumber);

                FigureColor color = new Random().nextBoolean() ? FigureColor.WHITE : FigureColor.BLACK;
                FigureType type = switch (new Random().nextInt(3)) {
                    case 1 -> FigureType.TZARRA;
                    case 2 -> FigureType.TZAAR;
                    default -> FigureType.TOTT;
                };
                int size = new Random().nextInt(5) + 1;

                space.setFigure(type, color, size);
                spaces.add(space);
            }
        }
    }

    public List<Space> getSpaces() {
        return spaces;
    }
}
