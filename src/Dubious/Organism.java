package Dubious;

import itumulator.executable.DynamicDisplayInformationProvider;
import itumulator.simulator.Actor;
import itumulator.world.World;

import java.util.Random;

public abstract class Organism implements Actor, DynamicDisplayInformationProvider {
    //Variables for things extending this class
    protected int energy;
    protected int maxEnergy;
    protected Random r = new Random();

    /**
     * Deletes this object when called.
     * @param world The world the object is in.
     * @throws DeathException This indicates that the this has died.
     */
    public void die(World world) throws DeathException{
        world.delete(this);
        throw new DeathException(this);
    }

}
