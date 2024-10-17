package tzaar.gui;

import tzaar.util.FigureButtonType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FigureButton extends JButton {
    private static final Image whiteStackImage = new ImageIcon("src/main/resources/GameFigure-white-tott.png").getImage();
    private static final Image blackStackImage = new ImageIcon("src/main/resources/GameFigure-black-tott.png").getImage();
    private final transient Image topImage;
    private final boolean isWhite;
    private final int size;
    private boolean isHovered = false;

    public FigureButton(FigureButtonType type, boolean isWhite, int size) {
        String imagePath = String.format("src/main/resources/GameFigure-%s-%s.png", isWhite ? "white" : "black", type.toString().toLowerCase());
        this.topImage = new ImageIcon(imagePath).getImage();
        this.isWhite = isWhite;
        this.size = size;

        // Make the button transparent
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);

        // Add an ActionListener to show an alert when the button is clicked
        // DEBUG! Only for testing
        String message = String.format("Type: %s%nColor: %s%nSize: %d", type, isWhite ? "White" : "Black", size);
        addActionListener(e -> JOptionPane.showMessageDialog(FigureButton.this, message));

        // Add a MouseAdapter to handle hover events
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isHovered = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isHovered = false;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        final float HPADDING_PERCENTAGE = 0.2F; // percentage (%) of the button's width
        final float BASE_FIGURE_HSHIFT_PERCENTAGE = 0.18F; // percentage (%) of the shift from the button's center
        final float STACK_OFFSET_PERCENTAGE = 0.06F; // percentage (%) of the offset between each figure in the stack

        // Draw background if the button is hovered
        if (isHovered) {
            g.setColor(new Color(255, 255, 0, 128)); // semi-transparent yellow
            g.fillRect(0, 0, getWidth(), getHeight());
        }
        
        // Get the button's dimensions
        int buttonWidthPadding = Math.round(getWidth() * HPADDING_PERCENTAGE);
        int buttonWidth = getWidth() - (2 * buttonWidthPadding);
        int buttonHeight = getHeight();

        // Get the image's default dimensions
        int imageWidth = topImage.getWidth(null);
        int imageHeight = topImage.getHeight(null);

        // Calculate the scaling factor to fit the image inside the button
        double scaleFactor = Math.min((double) buttonWidth / imageWidth, (double) buttonHeight / imageHeight);

        // Calculate the new image dimensions
        int newImageWidth = (int) (imageWidth * scaleFactor);
        int newImageHeight = (int) (imageHeight * scaleFactor);

        // Calculate the x and y coordinates to center the image
        int x = buttonWidthPadding + (buttonWidth - newImageWidth) / 2;
        int y = Math.round(buttonHeight * BASE_FIGURE_HSHIFT_PERCENTAGE) + (buttonHeight - newImageHeight) / 2;

        // Draw the figures stacked on top of each other
        for (int i = 0; i < this.size; i++) {
            int stackOffset = Math.round(buttonHeight * STACK_OFFSET_PERCENTAGE) * i;
            Image image = i == this.size - 1 ? topImage : isWhite ? whiteStackImage : blackStackImage;
            g.drawImage(image, x, y - stackOffset, newImageWidth, newImageHeight, this);
        }
    }
    
    
}
