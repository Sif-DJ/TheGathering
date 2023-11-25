package Tema1;

import itumulator.world.Location;
import itumulator.world.World;

import java.util.*;

public class Grass extends Food {
    //variables for grass
    int spreadchance = 5; // There is a 1:spreadchance chance of spreading.
    final int maxEnergy = 20;
    Random r = new Random();

    //Grass constructor
    public Grass(){
        energy = 10;
    }

    /**
     * Returns an amount of energy back and removes that amount from itself in energy.
     * @param amount how much energy you want to deduct.
     * @return int in energy.
     */
    @Override
    public int eat(int amount) {
        int energyToReturn;
        if(amount <= energy){
            energyToReturn = amount;
            energy -= energyToReturn;
            return energyToReturn;
        }else{
            energyToReturn  = energy;
            energy = 0;
            return energyToReturn;
        }

    }

    /**
     * All actions taken during a call to simulate the program.
     * @param world providing details of the position on which the actor is currently located and much more.
     */
    @Override
    public void act(World world) {
        if (energy <= 0){
            die(world);
        }
        energy++;
        if(energy > maxEnergy)
            energy = maxEnergy;
        if(spreadchance == r.nextInt(spreadchance+1)){
            spread(world);
        }
    }

    /**
     * Deletes the grass instance.
     * @param world the world the grass is in.
     */
    @Override
    public void die(World world) {
        world.delete(this);
    }

    /**
     * Finds a random surrounding tile with no Nonblocking entities and creates a new grass instance.
     * @param world the world the grass is in.
     */
    public void spread(World world){
        List<Location> list = new ArrayList<>(world.getSurroundingTiles());
        Iterator<Location> it = list.iterator();
        while (it.hasNext()) {
            Location tile = it.next();
            if (world.containsNonBlocking(tile))
                it.remove();
        }
        if(list.isEmpty()) return;
        Location l = list.get(r.nextInt(list.size()));
        world.setTile(l, new Grass());
    }

}