package dev.laszlomocsy.tzaar.gui;

import dev.laszlomocsy.tzaar.logic.board.Board;
import dev.laszlomocsy.tzaar.logic.board.BoardStatus;
import dev.laszlomocsy.tzaar.logic.figure.FigureColor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ControlPanel extends JPanel {
    private MenuPanel menuPanel;
    private JButton btnSaveGame;
    private JButton btnReturnToMenu;
    private JButton btnPass;
    private JLabel lblStatus;
    private JLabel lblInfo;
    private JPanel pMiddle;

    /**
     * Constructor to initialize the ControlPanel
     */
    public ControlPanel() {
        initializeComponents();
        layoutComponents();
    }

    /**
     * Initialize all UI components
     */
    private void initializeComponents() {
        btnSaveGame = new JButton("Save Game");
        btnReturnToMenu = new JButton("Return to Menu");
        btnPass = new JButton("pass second move");
        btnPass.setVisible(false);
        lblStatus = new JLabel("Ready to play?");
        lblStatus.setHorizontalAlignment(SwingConstants.CENTER);
        lblStatus.setFont(new Font("Arial", Font.PLAIN, 20));
        lblInfo = new JLabel("Ready to play?");
        lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
        lblInfo.setForeground(Color.BLUE);
        lblInfo.setFont(new Font("Arial", Font.PLAIN, 12));
        lblInfo.setVisible(false);
        pMiddle = new JPanel();
        pMiddle.setLayout(new FlowLayout());
    }

    /**
     * Arrange components using BorderLayout
     */
    private void layoutComponents() {
        this.setLayout(new BorderLayout());

        pMiddle.add(lblStatus);
        pMiddle.add(btnPass);

        this.add(btnSaveGame, BorderLayout.WEST);
        this.add(btnReturnToMenu, BorderLayout.EAST);
        this.add(pMiddle, BorderLayout.CENTER);
        this.add(lblInfo, BorderLayout.SOUTH);

        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    /**
     * Add ActionListener to Save Game button
     *
     * @param listener ActionListener
     */
    public void addSaveGameListener(ActionListener listener) {
        btnSaveGame.addActionListener(listener);
    }

    /**
     * Add ActionListener to Return to Menu button
     *
     * @param listener ActionListener
     */
    public void addReturnToMenuListener(ActionListener listener) {
        btnReturnToMenu.addActionListener(listener);
    }

    /**
     * Add ActionListener to Pass button
     *
     * @param listener ActionListener
     */
    public void addPassListener(ActionListener listener) {
        btnPass.addActionListener(listener);
    }

    public void updateStatus(Board board) {
        if (board.getStatus() == BoardStatus.IN_GAME) {
            String statusSecondHalf = board.getMoveCounter() == 0 ? "Must capture!" : "Capture, stack or ";
            if (board.getNextColor() == FigureColor.WHITE) {
                lblStatus.setText(String.format("%s's turn! %s", this.menuPanel.getWhitePlayerName(), statusSecondHalf));
            } else {
                lblStatus.setText(String.format("%s's turn! %s", this.menuPanel.getBlackPlayerName(), statusSecondHalf));
            }
        } else if (board.getStatus() == BoardStatus.WHITE_WON) {
            lblStatus.setText("%s won".formatted(this.menuPanel.getWhitePlayerName()));
        } else if (board.getStatus() == BoardStatus.BLACK_WON) {
            lblStatus.setText("%s won".formatted(this.menuPanel.getBlackPlayerName()));
        }

        btnPass.setVisible(board.getStatus() == BoardStatus.IN_GAME && board.getMoveCounter() == 1);
        lblInfo.setVisible(false);
    }

    public void updateInfo(String info) {
        lblInfo.setText(info);
        lblInfo.setVisible(true);
    }
    
    public void setMenuPanel(MenuPanel menuPanel) {
        this.menuPanel = menuPanel;
    }
}
