package Dubious;

import NonBlockables.Food;
import itumulator.executable.DynamicDisplayInformationProvider;
import itumulator.simulator.Actor;
import itumulator.world.Location;
import itumulator.world.World;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public abstract class Organism implements Actor, DynamicDisplayInformationProvider {
    //Variables for things extending this class
    protected int energy;
    protected int maxEnergy;
    protected Random r = new Random();

    /**
     * Deletes the object when called. Used to permanently remove animals and food,
     * when they die or get eaten.
     * @param world Providing details of the position on which the actor is currently located and much more.
     * @throws DeathException This indicates that the this has died.
     */
    public void die(World world) throws DeathException{
        world.delete(this);
        throw new DeathException(this);
    }

    /**
     * Gets the current energy of the organism.
     * @return returns the energy as an Integer.
     */
    public int getEnergy(){
        return energy;
    }

    /**
     * Adds an amount of energy to the organism.
     * @param energy as an Integer value.
     */
    public void addEnergy(int energy){
        this.energy += energy;
    }

    /**
     * Returns the location of the nearest food, that the organism can eat.
     * @param world Providing details of the position on which the actor is currently located and much more.
     * @param food An ArrayList of the Food types the organism can eat.
     * @param searchRadius An Integer that determines the size of the search area.
     * @return The location of the nearest food this organism can eat.
     * @param <T> All types of objects, that extends Food.
     */
    public <T extends Food> ArrayList<Location> searchForFood(World world, ArrayList<T> food,int searchRadius) {
        if (food.isEmpty()) return null; // Did someone input no food types to find, then stop prematurely.
        ArrayList<Location> list = new ArrayList<>(world.getSurroundingTiles(searchRadius)); // Get a list of surrounding locals
        ArrayList<Location> locs = new ArrayList<>(); // Create empty list for additions

        // Loop of all locals
        for(Location l : list){
            // Does this contain a NonBlocking object? No? Move to next location then.
            if(!world.containsNonBlocking(l)) continue;

            // Check all food types input, and if one matches, add it to the list.
            for(T f : food){
                if (world.getNonBlocking(l).getClass().isInstance(f)) {
                    locs.add(l);
                }
            }
        }
        return locs; // Return the list of remaining locations, may be empty if nothing is nearby.
    }
}
