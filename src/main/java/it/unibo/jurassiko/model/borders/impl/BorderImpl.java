package it.unibo.jurassiko.model.borders.impl;

import java.util.HashSet;
import java.util.Set;

import it.unibo.jurassiko.model.borders.api.Border;
import it.unibo.jurassiko.model.territory.api.Ocean;
import it.unibo.jurassiko.model.territory.api.Territory;

public class BorderImpl implements Border{

    /**
     * {@inheritDoc}
     */
    public Set<String> getTerritoriesBorder(final Territory terr, final Ocean ocean){
        if(ocean.isAdjTerritory(terr.getName())){
            final Set<String> temp = new HashSet<>();
            temp.addAll(terr.getNeighbourNames());
            temp.addAll(ocean.getAdjTerritoryNames());
            return temp;
        }
        return terr.getNeighbourNames();
    }
}
