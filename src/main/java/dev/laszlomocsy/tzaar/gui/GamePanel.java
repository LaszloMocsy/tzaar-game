package dev.laszlomocsy.tzaar.gui;

import dev.laszlomocsy.tzaar.logic.board.Board;
import dev.laszlomocsy.tzaar.logic.board.BoardActionResult;
import dev.laszlomocsy.tzaar.logic.board.BoardStatus;
import dev.laszlomocsy.tzaar.logic.figure.FigureLocation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The main panel of the game, containing the board and the control panel.
 */
public class GamePanel extends JPanel {
    private final BoardPanel boardPanel;
    private final ControlPanel controlPanel;
    private transient Board board;

    //-- Constructor --//

    /**
     * Creates a new GamePanel.
     */
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

    /**
     * Returns the board of the game.
     *
     * @return the board of the game
     */
    public Board getBoard() {
        return this.board;
    }

    //-- Private methods --//

    /**
     * Handles the click event on a space button.
     *
     * @param evt the event
     */
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

    /**
     * Sets the menu panel.
     *
     * @param menuPanel the menu panel
     */
    public void setMenuPanel(MenuPanel menuPanel) {
        this.controlPanel.setMenuPanel(menuPanel);
    }

    /**
     * Starts a new game.
     *
     * @param starterBoard the board to start the game with
     */
    public void startNewGame(Board starterBoard) {
        this.board = starterBoard == null ? Board.initDefault() : starterBoard;
        this.boardPanel.setBoard(this.board);
        this.boardPanel.repaint();
        this.controlPanel.updateStatus(this.board);
    }

    //-- Action listeners --//

    /**
     * Adds a listener to the pass button.
     *
     * @param listener the listener
     */
    public void addSaveGameListener(ActionListener listener) {
        this.controlPanel.addSaveGameListener(listener);
    }

    /**
     * Adds a listener to the pass button.
     *
     * @param listener the listener
     */
    public void addReturnToMenuListener(ActionListener listener) {
        this.controlPanel.addReturnToMenuListener(listener);
    }
}
