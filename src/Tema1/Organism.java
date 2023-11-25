package Tema1;

import itumulator.world.World;

import java.util.Random;

public abstract class Organism {
    //Variables for things extending this class
    protected int energy;
    protected int maxEnergy;
    protected Random r = new Random();

    /**
     * Deletes this object when called
     * @param world
     */
    public void die(World world){
        world.delete(this);
    }

}
