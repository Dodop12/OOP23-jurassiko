package it.unibo.jurassiko.view.panel;

import it.unibo.jurassiko.controller.game.api.MainController;
import it.unibo.jurassiko.core.api.GamePhase.Phase;
import it.unibo.jurassiko.model.player.api.Player.GameColor;
import it.unibo.jurassiko.view.gamescreen.impl.ViewImpl;
import it.unibo.jurassiko.view.window.ObjectiveWindow;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Makes the top-bar for the GUI.
 */
public class TopBarPanel extends JPanel {

    private static final long serialVersionUID = 1379037036670658465L;
    private static final double HEIGHT_RATIO = 0.1;
    private static final double WIDTH_RATIO = 0.8;
    private static final int FONT_SIZE = 24;
    private static final int DISTANCE_BUTTON_L_R = 48;
    private static final int BG_PLAYER_RGB = 175;
    private static final String URL_IMAGE = "images/topbar.png";
    private static final String OBJ_BUTTON_NAME = "Obiettivo";
    private static final String PLACE_BUTTON_NAME = "Piazzamento";
    private static final String ATK_BUTTON_NAME = "Attacco";
    private static final String ENDTURN_BUTTON_NAME = "Fine Turno";
    private static final String ENDTURN_DIALOG_QUESTION = "Vuoi effettuare uno spostamento prima di terminare il turno?";

    private final MainController controller;
    private final JLabel topLabel;
    private JLabel currentPlayer;
    private final ObjectiveWindow objectiveCard;
    final JButton objective;
    final JButton place;
    final JButton attack;
    final JButton endTurn;

    /**
     * Set the top-bar in the relevant label load the buttons in it,
     * and add all to the relevant panel.
     */
    public TopBarPanel(final MainController controller, final ObjectiveWindow objectiveCard) {
        this.objectiveCard = objectiveCard;
        this.controller = controller;

        BufferedImage imageBar;
        try {
            imageBar = ImageIO.read(ClassLoader.getSystemResource(URL_IMAGE));
        } catch (final IOException e) {
            throw new IllegalStateException("Failed to read the top bar file", e);
        }

        final int width = (int) (WIDTH_RATIO * ViewImpl.getScreenSize().getWidth());
        final int height = (int) (HEIGHT_RATIO * ViewImpl.getScreenSize().getHeight());
        final ImageIcon imageTopBar = ViewImpl.scaleImage(imageBar, width, height);
        this.topLabel = new JLabel(imageTopBar);
        this.topLabel.setLayout(new GridBagLayout());
        this.objective = new JButton(OBJ_BUTTON_NAME);
        this.place = new JButton(PLACE_BUTTON_NAME);
        this.attack = new JButton(ATK_BUTTON_NAME);
        this.endTurn = new JButton(ENDTURN_BUTTON_NAME);
        this.loadLabel();
        this.topLabel.setBounds(0, 0, width, height);
        this.setLayout(new BorderLayout());

        this.add(topLabel);
        this.setPreferredSize(new Dimension(width, height));
    }

    /**
     * Load the button in the relevant label.
     */
    private void loadLabel() {
        this.objective.addActionListener(e -> this.objectiveCard.showObjectiveCard());
        this.place.addActionListener(e -> this.controller.startGameLoop());
        this.attack.addActionListener(e -> this.controller.openTerritorySelector());
        this.endTurn.addActionListener(e -> {
            final String[] options = { "Sì", "No" };
            final int result = JOptionPane.showOptionDialog(this,
                    ENDTURN_DIALOG_QUESTION,
                    ENDTURN_BUTTON_NAME,
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    0);
            switch (result) {
                case 0:
                    controller.setGamePhase(Phase.MOVEMENT_FIRST_PART);
                    controller.startGameLoop();
                    break;
                case 1:
                    controller.endTurn();
                    break;
                default:
                    throw new IllegalArgumentException("Invalid option");
            }
        });
        this.currentPlayer = new JLabel();
        this.currentPlayer.setBackground(new Color(BG_PLAYER_RGB, BG_PLAYER_RGB, BG_PLAYER_RGB));
        this.currentPlayer.setOpaque(true);
        setCurrentPlayer();

        final Font font = new Font("Serif", Font.BOLD, FONT_SIZE);
        this.objective.setFont(font);
        this.place.setFont(font);
        this.attack.setFont(font);
        this.endTurn.setFont(font);
        this.currentPlayer.setFont(font);

        addComponent(currentPlayer, 0, 0);
        addComponent(objective, 1, 0);
        addComponent(place, 2, 0);
        addComponent(attack, 3, 0);
        addComponent(endTurn, 4, 0);
    }

    private void addComponent(final Component component, final int gridx, final int gridy) {
        final Insets insets = new Insets(8, DISTANCE_BUTTON_L_R, 8, DISTANCE_BUTTON_L_R);
        final var gbc = new GridBagConstraints(gridx, gridy, 1, 1, 0, 0, GridBagConstraints.CENTER,
                GridBagConstraints.CENTER, insets, 0, 0);
        this.topLabel.add(component, gbc);
    }

    private Color getLabelColor(final GameColor color) {
        return switch (color) {
            case RED -> Color.RED;
            case GREEN -> Color.GREEN;
            case BLUE -> Color.BLUE;
            default -> throw new IllegalArgumentException("Invalid color");
        };
    }

    private void setCurrentPlayer() {
        final var currentColor = this.controller.getCurrentPlayer().getColor();
        this.currentPlayer.setForeground(getLabelColor(currentColor));
        this.currentPlayer.setText("Player: " + currentColor.getColorName());
    }

    private void setActiveButton() {
        final var phase = controller.getGamePhase();
        disableAllJButtons();
        switch (phase) {
            case PLACEMENT -> this.place.setEnabled(true);
            case ATTACK_FIRST_PART -> {
                this.attack.setEnabled(true);
                this.endTurn.setEnabled(true);
            }
            case MOVEMENT_FIRST_PART -> this.endTurn.setEnabled(true);
        }
    }

    private void disableAllJButtons() {
        place.setEnabled(false);
        attack.setEnabled(false);
        endTurn.setEnabled(false);
    }

    public void updateTopBar() {
        setCurrentPlayer();
        setActiveButton();
    }
}
