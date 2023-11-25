package Tema1;

import itumulator.world.World;

public abstract class Organism {
    //Variables for things extending this class
    protected int energy;


    /**
     * Deletes this object when called and throws a DiedOfOldAgeException
     * @param world
     * @throws DiedOfOldAgeException
     */
    public void die(World world){
        world.delete(this);
    }

}
