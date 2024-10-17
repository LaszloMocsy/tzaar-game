package tzaar.component;

import java.util.ArrayList;
import java.util.List;

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

                spaces.add(new Space(locLetter, locNumber));
            }
        }
    }

    public List<Space> getSpaces() {
        return spaces;
    }
}
