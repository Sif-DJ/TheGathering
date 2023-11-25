package Tema1;

import itumulator.world.World;

import java.util.Random;

public abstract class Organism {
    //Variables for things extending this class
    protected int energy;
    protected int maxEnergy;
    protected Random r = new Random();

    /**
     * Deletes this object when called.
     * @param world the world object.
     * @throws DeathException this indicates that the this has died.
     */
    public void die(World world) throws DeathException{
        world.delete(this);
        throw new DeathException(this);
    }

}
