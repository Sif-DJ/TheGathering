package Tema1;

import itumulator.simulator.Actor;
import itumulator.world.World;

public abstract class Animal extends Organism implements Actor {
    //variables for tings extending this class
    protected int age;
    protected int ageMax;
    protected int health;
    protected int maxEnergy;
    //function for the animal to be able to eat
    public abstract void eat(Food food, World world);
    //function for animal to make more animals
    public abstract void reproduce(World world);

    public abstract void move(World world);

    /**
     * Creatures age and loose a maximum energy for every time it ages.
     * At one point it dies when its age can no longer sustain itself.
     * @param world the world object
     */
    public void age(World world) throws DiedOfOldAgeException{
        this.age += 1;
        this.maxEnergy -= 1;
        if(this.age >= this.ageMax) {
            die(world);
            throw new DiedOfOldAgeException(this);
        }
    }
}
