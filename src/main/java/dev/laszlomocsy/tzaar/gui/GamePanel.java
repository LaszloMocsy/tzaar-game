package dev.laszlomocsy.tzaar.gui;

import dev.laszlomocsy.tzaar.logic.board.Board;
import dev.laszlomocsy.tzaar.logic.board.BoardStatus;
import dev.laszlomocsy.tzaar.logic.figure.Figure;
import dev.laszlomocsy.tzaar.logic.figure.FigureColor;
import dev.laszlomocsy.tzaar.logic.figure.FigureType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GamePanel extends JPanel {
    private final BoardPanel boardPanel;
    private final ControlPanel controlPanel;
    private Random random = new Random();
    private Board board;
    private Figure nextFigureToPlace;

    public GamePanel() {
        this.setName("gamePanel");
        this.board = null;
        this.nextFigureToPlace = null;

        setLayout(new BorderLayout());

        boardPanel = new BoardPanel();
        add(boardPanel, BorderLayout.CENTER);

        controlPanel = new ControlPanel();
        add(controlPanel, BorderLayout.SOUTH);

        boardPanel.addSpaceButtonListener(this::spaceButtonClicked);
    }

    //-- Private methods --//

    private void spaceButtonClicked(ActionEvent evt) {
        System.out.println("Space button clicked: " + evt.getActionCommand());

        if (this.board.getStatus() == BoardStatus.IN_GAME) {
            // TODO move figure
        }
    }

    //-- Public methods --//

    public void startNewGame() {
        this.board = new Board();
        this.boardPanel.setBoard(this.board);
        this.boardPanel.repaint();
    }

    //-- Action listeners --//

    public void addSaveGameListener(ActionListener listener) {
        this.controlPanel.addSaveGameListener(listener);
    }

    public void addReturnToMenuListener(ActionListener listener) {
        this.controlPanel.addReturnToMenuListener(listener);
    }
}
