package dev.laszlomocsy.tzaar.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GamePanel extends JPanel {
    private final BoardPanel boardPanel;
    private final ControlPanel controlPanel;
    
    public GamePanel() {
        this.setName("gamePanel");
        
        setLayout(new BorderLayout());
        
        boardPanel = new BoardPanel();
        add(boardPanel, BorderLayout.CENTER);
        
        controlPanel = new ControlPanel();
        add(controlPanel, BorderLayout.SOUTH);
    }
    
    //-- Action listeners --//
    
    public void addSaveGameListener(ActionListener listener) {
        this.controlPanel.addSaveGameListener(listener);
    }
    
    public void addReturnToMenuListener(ActionListener listener) {
        this.controlPanel.addReturnToMenuListener(listener);
    }
}
