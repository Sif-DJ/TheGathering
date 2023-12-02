package Tema2;

import Tema1.*;
import itumulator.world.World;

public class WolfBurrow extends Burrow<Wolf> {

    public WolfBurrow(){
        this.timeUnOccupied = 0;
        this.maxNumAnimalsInHole = 5;
    }

    @Override
    public void act(World world){
        super.act(world);
        if(timeUnOccupied > 50){
            for(Wolf wolf : assignedToBurrow){
                //Pack.unAssignHole();
            }
            caveIn(world);
        }
    }

    @Override
    public void exit(World world){
        if(animals.isEmpty())return;
        world.setTile(world.getLocation(this), animals.get(0));
        animals.remove(0);
    }
}
