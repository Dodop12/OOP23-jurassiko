package it.unibo.jurassiko.view.panel;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import it.unibo.jurassiko.controller.api.MenuController;
import it.unibo.jurassiko.controller.impl.MenuContollerImpl;
import it.unibo.jurassiko.view.gamescreen.impl.StartMenu;
import it.unibo.jurassiko.view.gamescreen.impl.ViewImpl;

/**
 * JPanel used for the StartMenu frame.
 */
public class MenuPanel extends JPanel {

    private static final String START = "Start";
    private static final String QUIT = "Quit";
    private static final double WIDTH_PERC = 0.5;
    private static final double HEIGHT_PERC = 0.5;
    private static final double BUTTON_WIDTH_PERC = WIDTH_PERC * 0.2;
    private static final double BUTTON_HEIGHT_PERC = HEIGHT_PERC * 0.1;
    private static final int FONT_SIZE = 24;

    private static final String SLASH = File.separator;
    private static final String URL_IMAGE = "images" + SLASH + "MenuImage.png";

    private final Dimension dimension;

    /**
     * @param controller
     * @param frame
     */
    public MenuPanel(final MenuContollerImpl controller, final StartMenu frame) {
        final JLayeredPane layeredPane = new JLayeredPane();
        final JPanel buttonPanel = new JPanel();
        final JLabel bgLabel;
        final BufferedImage image;
        final ImageIcon bgImage;
        this.dimension = ViewImpl.getScreenSize();
        this.setPreferredSize(new Dimension(Double.valueOf(dimension.getWidth() * WIDTH_PERC).intValue(),
                Double.valueOf(dimension.getHeight() * HEIGHT_PERC).intValue()));

        try {
            image = ImageIO.read(ClassLoader.getSystemResource(URL_IMAGE));
        } catch (final IOException e) {
            throw new IllegalStateException("Failed to read the menu Background image");
        }
        bgImage = new ImageIcon(fixImageSize(new ImageIcon(image), dimension.getWidth(), dimension.getHeight()));
        bgLabel = new JLabel(bgImage);
        bgLabel.setBounds(0, 0, bgImage.getIconWidth(), bgImage.getIconHeight());
        bgLabel.setOpaque(false);

        final JButton start = createButton(START, getButtonDimension());
        final JButton quit = createButton(QUIT, getButtonDimension());
        buttonPanel.setLayout(new GridBagLayout());
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);

        start.addActionListener(e -> {
            frame.dispose();
            controller.startGame();
        });

        quit.addActionListener(e -> {
            final String[] options = { "Yes", "No" };
            final var result = JOptionPane.showOptionDialog(this, "Do you want to QUIT the game?",
                    QUIT,
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    null, options, options);
            if (result == 0) {
                frame.dispose();
            }
        });
        addButton(buttonPanel, start, gbc);
        addButton(buttonPanel, quit, gbc);

        buttonPanel.setBounds(0, 0, bgImage.getIconWidth(), bgImage.getIconHeight());
        buttonPanel.setOpaque(false);

        layeredPane.add(bgLabel, Integer.valueOf(0));
        layeredPane.setPreferredSize(new Dimension(bgImage.getIconWidth(), bgImage.getIconHeight()));
        layeredPane.add(buttonPanel, Integer.valueOf(1));

        this.add(layeredPane);
    }

    private JButton createButton(final String name, final Dimension dim) {
        final JButton button = new JButton(name);
        button.setPreferredSize(dim);
        button.setFont(new Font("Serif", Font.BOLD, FONT_SIZE));
        return button;
    }

    private Dimension getButtonDimension() {
        return new Dimension(Double.valueOf(dimension.getWidth() * BUTTON_WIDTH_PERC).intValue(),
                Double.valueOf(dimension.getHeight() * BUTTON_HEIGHT_PERC).intValue());
    }

    private void addButton(final JPanel panel, final JButton jb, final GridBagConstraints gbc) {
        panel.add(jb, gbc);
        gbc.gridy++;
    }

    private Image fixImageSize(final ImageIcon image, final double width, final double height) {
        return image.getImage().getScaledInstance(Double.valueOf(width * WIDTH_PERC).intValue(),
                Double.valueOf(height * HEIGHT_PERC).intValue(), Image.SCALE_SMOOTH);
    }
}
