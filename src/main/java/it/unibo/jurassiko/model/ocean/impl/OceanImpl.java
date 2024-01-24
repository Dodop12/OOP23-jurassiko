package it.unibo.jurassiko.model.ocean.impl;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import it.unibo.jurassiko.model.ocean.api.Ocean;
import it.unibo.jurassiko.model.territory.api.Territory;

public class OceanImpl implements Ocean {

    private String name;
    @JsonProperty("neighbours")
    private Set<String> neighbourNames;
    @JsonProperty("territories")
    private Set<String> adjTerritoryNames;

    @JsonIgnore
    private Set<Ocean> neighbours;
    @JsonIgnore
    private Set<Territory> adjTerritories;

    private OceanImpl() {

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Set<String> getNeighbourNames() {
        return neighbourNames;
    }

    @Override
    public Set<String> getAdjTerritoryNames() {
        return adjTerritoryNames;
    }

    @Override
    public void setNeighbours(final Set<Ocean> neighbours) {
        this.neighbours = neighbours;
    }

    @Override
    public void setAdjTerritories(final Set<Territory> adjTerritories) {
        this.adjTerritories = adjTerritories;
    }

    @Override
    public Set<Ocean> getNeighbours() {
        return neighbours;
    }

    @Override
    public Set<Territory> getAdjTerritories() {
        return adjTerritories;
    }

    @Override
    public boolean isNeighbour(final String oceanName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isNeighbour'");
    }

    @Override
    public boolean isAdjTerritory(final String territoryName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isAdjTerritory'");
    }

}
