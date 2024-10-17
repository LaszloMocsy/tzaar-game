package tzaar.component;

import tzaar.util.BoardFillPattern;
import tzaar.util.FigureColor;
import tzaar.util.FigureType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Board {
    private static final Random random = new Random();
    private final List<Space> spaces;

    public Board(BoardFillPattern fillPattern) {
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
                switch (fillPattern) {
                    case RANDOM -> fillBoardRandomly(space);
                    case DEFAULT -> fillBoardDefault(space);
                    default -> space.setFigure(null, null, 0);
                }
                spaces.add(space);
            }
        }
    }

    public List<Space> getSpaces() {
        return spaces;
    }

    private static void fillBoardRandomly(Space space) {
        FigureColor color = random.nextBoolean() ? FigureColor.WHITE : FigureColor.BLACK;
        FigureType type = switch (random.nextInt(3)) {
            case 1 -> FigureType.TZARRA;
            case 2 -> FigureType.TZAAR;
            default -> FigureType.TOTT;
        };
        int size = random.nextInt(5) + 1;

        space.setFigure(type, color, size);
    }

    private static void fillBoardDefault(Space space) {
        String[] blackFigures = {"A1", "A2", "A3", "A4", "B2", "B3", "B4", "C3", "C4", "D4", "E6", "E7", "E8", "E9",
                "F2", "F3", "F4", "F5", "F7", "F8", "F9", "G3", "G4", "G5", "G8", "G9", "H4", "H5", "H9", "I5"};
        String[] tzarraFigures = {"B2", "B3", "B4", "B5", "C2", "C6", "D2", "D7", "E2", "E8", "F3", "F8", "G4", "G8",
                "H5", "H6", "H7", "H8"};
        String[] tzaarFigures = {"C3", "C4", "C5", "D3", "D6", "E3", "E7", "F4", "F7", "G5", "G6", "G7"};

        String loc = space.getLocation();
        FigureType type;
        if (Arrays.asList(tzarraFigures).contains(loc)) type = FigureType.TZARRA;
        else if (Arrays.asList(tzaarFigures).contains(loc)) type = FigureType.TZAAR;
        else type = FigureType.TOTT;

        space.setFigure(type, Arrays.asList(blackFigures).contains(loc) ? FigureColor.BLACK : FigureColor.WHITE, 1);
    }
}
