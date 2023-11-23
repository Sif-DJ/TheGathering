package Tema1;

import itumulator.world.World;

public abstract class Organism {
    //Variables for things extending this class
    protected int energy;
    //Function for deleting instances
    public abstract void die(World world);

}
