package tzaar.gui;

import tzaar.component.Space;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SpaceUI extends JButton {
    private final transient Space space;
    private boolean isHovered = false;

    public SpaceUI(Space space) {
        this.space = space;

        // Make the button transparent
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);

        // Add an ActionListener to show an alert when the button is clicked
        // DEBUG! Only for testing
        addActionListener(e -> JOptionPane.showMessageDialog(SpaceUI.this, this.space.toString()));

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

    public int[] getSpaceCoordinates() {
        return this.space.location.getCoordinates();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the figure stack
        if (space.hasFigure()) {
            this.paintFigureStack(g);

            // Draw background if the button is hovered while having a figure
            if (isHovered) {
                g.setColor(new Color(255, 255, 0, 128)); // semi-transparent yellow
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        }
    }

    private void paintFigureStack(Graphics g) {
        final float HPADDING_PERCENTAGE = 0.2F; // percentage (%) of the button's width
        final float STACK_OFFSET_PERCENTAGE = 0.06F; // percentage (%) of the offset between each figure in the stack

        String topImageFilename = String.format("src/main/resources/GameFigure-%s-%s.png", this.space.getFigureColor().toString().toLowerCase(), this.space.getFigureType().toString().toLowerCase());
        String stackImageFilename = String.format("src/main/resources/GameFigure-%s-tott.png", this.space.getFigureColor().toString().toLowerCase());
        Image topImage = new ImageIcon(topImageFilename).getImage();
        Image stackImage = new ImageIcon(stackImageFilename).getImage();

        // Get the button's dimensions
        int buttonWidthPadding = Math.round(getWidth() * HPADDING_PERCENTAGE);
        int buttonWidth = getWidth() - (2 * buttonWidthPadding);
        int buttonHeight = getHeight();
        int[] buttonDimensions = new int[]{buttonWidth, buttonHeight};

        int[] imageDimensions = calculateFigureImageDimensions(topImage, buttonDimensions);
        int[] baseImagePosition = calculateBaseImagePosition(buttonWidthPadding, buttonDimensions, imageDimensions);

        // Draw the figures stacked on top of each other
        for (int i = 0; i < this.space.getFigureSize(); i++) {
            int stackOffset = Math.round(buttonHeight * STACK_OFFSET_PERCENTAGE) * i;
            Image image = i == this.space.getFigureSize() - 1 ? topImage : stackImage;
            g.drawImage(image, baseImagePosition[0], baseImagePosition[1] - stackOffset, imageDimensions[0], imageDimensions[1], this);
        }
    }

    private int[] calculateFigureImageDimensions(Image image, int[] buttonDimensions) {
        // Get the image's default dimensions
        int imageWidth = image.getWidth(null);
        int imageHeight = image.getHeight(null);

        // Calculate the scaling factor to fit the image inside the button
        double scaleFactor = Math.min((double) buttonDimensions[0] / imageWidth, (double) buttonDimensions[1] / imageHeight);

        // Calculate the new image dimensions
        int newImageWidth = (int) (imageWidth * scaleFactor);
        int newImageHeight = (int) (imageHeight * scaleFactor);

        return new int[]{newImageWidth, newImageHeight};
    }

    private int[] calculateBaseImagePosition(int buttonWidthPadding, int[] buttonDimensions, int[] imageDimensions) {
        final float BASE_FIGURE_HSHIFT_PERCENTAGE = 0.18F; // percentage (%) of the shift from the button's center

        // Calculate the x and y coordinates to center the image
        int x = buttonWidthPadding + (buttonDimensions[0] - imageDimensions[0]) / 2;
        int y = Math.round(buttonDimensions[1] * BASE_FIGURE_HSHIFT_PERCENTAGE) + (buttonDimensions[1] - imageDimensions[1]) / 2;

        return new int[]{x, y};
    }
}
