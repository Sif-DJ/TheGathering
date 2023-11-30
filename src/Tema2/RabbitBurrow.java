package Tema2;

import Tema1.*;
import itumulator.world.*;


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
                rabbit.assignHole();
            }
            caveIn(world);
        }
    }
}