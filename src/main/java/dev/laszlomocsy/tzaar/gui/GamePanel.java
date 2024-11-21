package dev.laszlomocsy.tzaar.gui;

import dev.laszlomocsy.tzaar.logic.board.Board;
import dev.laszlomocsy.tzaar.logic.board.BoardActionResult;
import dev.laszlomocsy.tzaar.logic.board.BoardStatus;
import dev.laszlomocsy.tzaar.logic.figure.FigureLocation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanel extends JPanel {
    private final BoardPanel boardPanel;
    private final ControlPanel controlPanel;
    private transient Board board;

    public GamePanel() {
        this.setName("gamePanel");
        this.board = null;

        setLayout(new BorderLayout());

        boardPanel = new BoardPanel();
        add(boardPanel, BorderLayout.CENTER);

        controlPanel = new ControlPanel();
        controlPanel.addPassListener(e -> {
            this.board.passMove();
            controlPanel.updateStatus(this.board);
        });
        add(controlPanel, BorderLayout.SOUTH);

        boardPanel.addSpaceButtonListener(this::spaceButtonClicked);
    }

    //-- Getters --//

    public Board getBoard() {
        return this.board;
    }

    //-- Private methods --//

    private void spaceButtonClicked(ActionEvent evt) {
        if (this.board.getStatus() == BoardStatus.IN_GAME) {
            FigureLocation clickedLocation = FigureLocation.fromString(evt.getActionCommand());
            FigureLocation selectedLocation = this.boardPanel.getSelectedFigureLocation();

            if (selectedLocation != null) {
                if (!clickedLocation.equals(selectedLocation)) {
                    BoardActionResult result = this.board.moveFigure(selectedLocation, clickedLocation);
                    if (result != BoardActionResult.SUCCESS) this.controlPanel.updateInfo(result);
                    else this.controlPanel.updateStatus(this.board);
                }

                this.boardPanel.setSelectedFigureLocation(null);
            } else {
                this.boardPanel.setSelectedFigureLocation(clickedLocation);
            }

            this.boardPanel.repaint();
        }
    }

    //-- Public methods --//

    public void setMenuPanel(MenuPanel menuPanel) {
        this.controlPanel.setMenuPanel(menuPanel);
    }

    public void startNewGame(Board starterBoard) {
        this.board = starterBoard == null ? Board.InitDefault() : starterBoard;
        this.boardPanel.setBoard(this.board);
        this.boardPanel.repaint();
        this.controlPanel.updateStatus(this.board);
    }

    //-- Action listeners --//

    public void addSaveGameListener(ActionListener listener) {
        this.controlPanel.addSaveGameListener(listener);
    }

    public void addReturnToMenuListener(ActionListener listener) {
        this.controlPanel.addReturnToMenuListener(listener);
    }
}
