package Tema1;

import itumulator.world.Location;
import itumulator.world.World;

import java.util.*;

public class Grass extends Food {
    //variables for grass
    private final int spreadchance = 7; // There is a 1:spreadchance chance of spreading.


    //Grass constructor
    public Grass(){
        maxEnergy = 20;
        energy = 10;
    }

    /**
     * All actions taken during a call to simulate the program.
     * @param world providing details of the position on which the actor is currently located and much more.
     */
    @Override
    public void act(World world) {
        if (energy <= 0){
            die(world);
            return;
        }
        energy++;
        if(energy > maxEnergy)
            energy = maxEnergy;
        if(spreadchance == r.nextInt(spreadchance+1)){
            spread(world);
        }
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