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
     * Deletes this object when called.
     * @param world The world the object is in.
     * @throws DeathException This indicates that the this has died.
     */
    public void die(World world) throws DeathException{
        world.delete(this);
        throw new DeathException(this);
    }

    /**
     * Gets the current energy of this food item.
     * @return int in energy
     */
    public int getEnergy(){
        return energy;
    }

    public void addEnergy(int energy){
        this.energy += energy;
    }

    /**
     * returns the location of the nearest food this animal can eat
     *
     * @param world the world object
     * @param food  an ArrayList of the Food types the animal can eat
     * @return the location of the nearest food this animal can eat
     */
    public <T extends Food> ArrayList<Location> searchForFood(World world, ArrayList<T> food,int searchRadius) {
        if (food.isEmpty()) return null;
        ArrayList<Location> list = new ArrayList<>(world.getSurroundingTiles(world.getCurrentLocation(), searchRadius));
        Iterator<Location> it = list.iterator();
        while (it.hasNext()) {
            Location l = it.next();
            if (!world.containsNonBlocking(l)) {
                it.remove();
            } else if (world.containsNonBlocking(l)) {
                int notFoodCount = 0;
                for (T f : food) {
                    if (!f.getClass().isInstance(world.getNonBlocking(world.getLocation(this)).getClass())) {
                        notFoodCount++;
                    }
                }
                if (notFoodCount >= food.size()) {
                    it.remove();
                }
            }
        }
        return list;
    }
}
