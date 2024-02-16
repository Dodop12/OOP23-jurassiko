package it.unibo.jurassiko.model.objective.impl;

import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonProperty;

import it.unibo.jurassiko.model.player.api.Player.GameColor;

/**
 * Implementation of objectives based on destroying an enemy army.
 */
public class DestroyArmyObjective extends AbstractObjective {

    private static final long serialVersionUID = -598498354779098964L;
    private static final String TYPE = "destroyArmy";
    @JsonProperty("value")
    private String armyColor;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getType() {
        return TYPE;
    }

    /**
     * @return the color of the army to destroy
     */
    public GameColor getArmyColor() {
        try {
            return GameColor.valueOf(this.armyColor.toUpperCase(Locale.ROOT));
        } catch (final IllegalArgumentException e) {
            throw new IllegalStateException("Invalid army color", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeDescription() {
        final String description = "Distruggi l'armata di colore "
                + this.armyColor.toLowerCase(Locale.ROOT)
                + ". Se l'armata non è in gioco, "
                + super.getDefaultObjectiveDescription().toLowerCase(Locale.ROOT);

        setDescription(description);
    }

}
