package Tema2;

import Tema1.*;
import itumulator.world.*;

import java.util.ArrayList;


public class RabbitBurrow extends Burrow<Rabbit> {

    public RabbitBurrow(){
        this.timeUnOccupied = 0;
        this.maxNumAnimalsInHole = 5;
    }

    @Override
    public void act(World world){
        super.act(world);
        if(timeUnOccupied > 35){
            for(Rabbit rabbit : assignedToBurrow){
                rabbit.unAssignHole();
            }
            caveIn(world);
        }
    }

    @Override
    public void exit(World world){
        if(animals.isEmpty())return;
        Location[] surroundingTiles = world.getSurroundingTiles(world.getCurrentLocation()).toArray(new Location[0]);
        for(Location l : surroundingTiles)
            if(world.getTile(l) instanceof Predator)
                return;
        world.setTile(world.getLocation(this), animals.get(0));
        animals.remove(0);
    }
}