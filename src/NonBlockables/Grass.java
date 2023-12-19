package NonBlockables;

import Dubious.DeathException;
import itumulator.executable.DisplayInformation;
import itumulator.world.Location;
import itumulator.world.World;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Grass extends Food {
    //variables for grass
    private final int spreadChance; // There is a 1:spreadChance chance of spreading.

    /**
     * The constructor for Grass.
     */
    public Grass(){
        maxEnergy = 20;
        energy = 10;
        spreadChance = 10;
    }

    /**
     * Provides info on how this object should be displayed in game world.
     * @return the DisplayInformation that is used for how it looks on the map.
     */
    @Override
    public DisplayInformation getInformation() {
        return new DisplayInformation(Color.green, "grass");
    }

    /**
     * The method called whenever the actor needs to simulate actions.
     * @param world providing details of the position on which the actor is currently located and much more.
     */
    @Override
    public void act(World world) {
        try{
            if (energy <= 0){
                die(world);// We don't know why by this only sometime throws an Exception with the message "No such object in the world"
                return;
            }
        }catch(DeathException e){
            return;
        }catch (Exception e){
            System.out.println("Grass did something wierd");
            return;
        }

        energy++;
        if(energy > maxEnergy)
            energy = maxEnergy;
        if(spreadChance == r.nextInt(spreadChance+1)){
            spread(world);
        }
    }

    /**
     * Finds a random surrounding tile with no Nonblocking entities and creates a new grass instance.
     * @param world providing details of the position on which the actor is currently located and much more.
     */
    public void spread(World world){
        List<Location> list = new ArrayList<>(world.getSurroundingTiles(world.getLocation(this)));
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