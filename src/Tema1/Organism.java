package Tema1;

import itumulator.world.World;

public abstract class Organism {
    //variables for tings extending this class
    protected int energy;
    // function for deleting things
    public abstract void die(World world);

}
