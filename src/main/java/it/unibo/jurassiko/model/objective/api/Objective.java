package it.unibo.jurassiko.model.objective.api;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

import it.unibo.jurassiko.model.objective.impl.ConquerContinentsObjective;
import it.unibo.jurassiko.model.objective.impl.ConquerTerritoriesObjective;
import it.unibo.jurassiko.model.objective.impl.DestroyArmyObjective;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @Type(value = ConquerTerritoriesObjective.class, name = "conquerTerritories"),
        @Type(value = DestroyArmyObjective.class, name = "destroyArmy"),
        @Type(value = ConquerContinentsObjective.class, name = "conquerContinents")
})
public interface Objective {

    /**
     * @return true if the objective has been accomplished successfully, false
     *         otherwise
     */
    boolean isAchieved();

    void writeDescription();

    /**
     * @return the description of the objective
     */
    String getDescription();

}
