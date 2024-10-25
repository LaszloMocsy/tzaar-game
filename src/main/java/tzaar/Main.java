package tzaar;

import tzaar.component.Board;
import tzaar.gui.BoardUI;
import tzaar.util.BoardFillPattern;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        // Initialize the frame
        JFrame frame = new JFrame("Tzaar game");

        // Initialize the GamePanel
        Board board = new Board(BoardFillPattern.DEFAULT);
        BoardUI boardUI = new BoardUI(board);
        frame.add(boardUI, BorderLayout.CENTER);

        // Make the frame visible
        frame.setSize(740, 600);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}