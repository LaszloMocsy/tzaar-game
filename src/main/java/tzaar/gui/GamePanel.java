package tzaar.gui;

import tzaar.component.Board;
import tzaar.component.Space;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class GamePanel extends JPanel {
    private final transient Image bgImage;
    private int bgImageSize;
    private int bgImageX;
    private int bgImageY;

    private final transient Board board;

    public GamePanel(Board board) {
        this.board = board;

        // Load the background image from the resources folder
        bgImage = new ImageIcon("src/main/resources/GameBoard.png").getImage();

        // Set layout to null for manual positioning
        setLayout(null);

        // Place buttons
        for (Space space : this.board.getSpaces()) {
            SpaceButton btn = space.getButton();
            add(btn);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.paintBackgroundImage(g);
        this.paintSpaceButtons();
    }

    private void paintBackgroundImage(Graphics g) {
        // The goal is to paint the background image horizontally and vertically center always
        // To achieve this I need to calculate the image's size and the location for the top left corner (x, y)

        // Get the panel's dimensions
        int panelWidth = getWidth();
        int panelHeight = getHeight();

        // Get the image's default dimensions
        int imageDefaultWidth = bgImage.getWidth(null);
        int imageDefaultHeight = bgImage.getHeight(null);

        // Calculate the image's dimensions to use
        Integer[] sizes = {panelWidth, panelHeight, imageDefaultWidth, imageDefaultHeight};
        this.bgImageSize = Collections.min(Arrays.asList(sizes));

        // Calculate x and y coordinates to center the image
        this.bgImageX = (panelWidth - this.bgImageSize) / 2;
        this.bgImageY = (panelHeight - this.bgImageSize) / 2;

        // Draw the image centered in the panel
        g.drawImage(bgImage, this.bgImageX, this.bgImageY, this.bgImageSize, this.bgImageSize, this);
    }

    private void paintSpaceButtons() {
        double cellWidth = this.bgImageSize / 11.0;
        double cellHeight = this.bgImageSize / 19.0;
        int buttonWidth = (int) (Math.round(cellWidth));
        int buttonHeight = (int) Math.round(cellHeight * 2);

        final Character[] locLetters = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I'};
        final int[] spacesInLetters = {5, 6, 7, 8, 8, 8, 7, 6, 5};

        // Go through each space (A5,...,A1,B6,...,B1,C7,...,I5)
        for (int idxLocLetters = 0, btnX = 0; idxLocLetters < locLetters.length; idxLocLetters++, btnX++) {
            for (int countInLetter = spacesInLetters[idxLocLetters], btnY = 0; countInLetter > 0; countInLetter--, btnY++) {
                final Character locLetter = locLetters[idxLocLetters];
                int locNumber = calculateLocNumber(locLetter, countInLetter, idxLocLetters);
                int[] position = calculateButtonPosition(btnX, btnY, cellWidth, cellHeight, buttonHeight, spacesInLetters);

                Space space = getSpace(locLetter, locNumber);
                if (space != null) {
                    space.getButton().setBounds(position[0], position[1], buttonWidth, buttonHeight);
                }
            }
        }
    }

    private int calculateLocNumber(Character locLetter, int countInLetter, int idxLocLetters) {
        int locNumber = countInLetter;
        if (locLetter == 'E' && locNumber >= 5) {
            locNumber += 1;
        } else if (idxLocLetters >= 5) {
            locNumber += idxLocLetters - 4;
        }
        return locNumber;
    }

    private int[] calculateButtonPosition(int btnX, int btnY, double cellWidth, double cellHeight, int buttonHeight, int[] spacesInLetters) {
        int startX = (int) Math.round(this.bgImageX + cellWidth + (btnX * cellWidth));
        int startY = (int) Math.round(this.bgImageY + (btnY * cellHeight * 2));

        if (btnX == 4 && btnY >= 4) {
            startY += buttonHeight;
        } else if (btnX != 4) {
            int margin = (spacesInLetters[btnX] - 9) * -1;
            startY += (int) Math.round(margin * cellHeight);
        }
        return new int[]{startX, startY};
    }

    private Space getSpace(Character locLetter, int locNumber) {
        return board.getSpaces().stream()
                .filter(s -> s.locLetter.equals(locLetter) && s.locNumber == locNumber)
                .findFirst()
                .orElse(null);
    }
}
