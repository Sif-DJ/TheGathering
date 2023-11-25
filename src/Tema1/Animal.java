package Tema1;

import itumulator.simulator.Actor;
import itumulator.world.Location;
import itumulator.world.NonBlocking;
import itumulator.world.World;

import java.util.ArrayList;
import java.util.Random;

public abstract class Animal extends Organism implements Actor {
    //variables for tings extending this class
    protected int age;
    protected int ageMax;
    protected int health;
    protected int maxEnergy;
    //function for animal to make more animals
    public void reproduce(World world){
        Random r = new Random();
        int size = world.getSize();
        Animal entity = this.createNewSelf();
        ArrayList<Location> locations = new ArrayList<>(world.getEmptySurroundingTiles(world.getLocation(this)));
        Location l = locations.get(r.nextInt(locations.size()));
        world.setTile(l, entity);
    }

    public abstract Animal createNewSelf();

    public abstract void move(World world);

    /**
     * Creatures age and loose a maximum energy for every time it ages.
     * At one point it dies when its age can no longer sustain itself.
     * @param world the world object
     * @throws DiedOfOldAgeException throws an exception to intervene nested checks.
     */
    public void age(World world) throws DiedOfOldAgeException{
        this.age += 1;
        this.maxEnergy -= 1;
        if(this.age >= this.ageMax) {
            die(world);
            throw new DiedOfOldAgeException(this);
        }
    }

    /**
     * consumes an amount of energy from a food instance, refilling its own energy.
     * @param food The food the rabbit is trying to eat.
     * @param world The world the rabbit is in.
     */
    public void eat(Food food, World world) {
        energy += food.eat(8);
        if(energy > maxEnergy) energy=maxEnergy;
    }
}
