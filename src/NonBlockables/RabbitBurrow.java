package NonBlockables;

import Animals.Predator;
import Animals.Rabbit;
import itumulator.world.*;

public class RabbitBurrow extends Burrow<Rabbit> {
    /**
     * The constructor for RabbitBurrow.
     */
    public RabbitBurrow(){
        this.timeUnOccupied = 0;
        this.maxNumAnimalsInHole = 5;
    }
    /**
     * The method called whenever the actor needs to simulate actions.
     * @param world providing details of the position on which the actor is currently located and much more.
     */
    @Override
    public void act(World world){
        super.act(world);
        if(timeUnOccupied > 35){
            for(Rabbit rabbit : assignedToBurrow){
                rabbit.unAssignHole();
            }
            caveIn(world);
        }
    }
    /**
     * The function to exit the RabbitBurrow.
     * @param world providing details of the position on which the actor is currently located and much more.
     */
    @Override
    public void exit(World world){
        if(animals.isEmpty())return;
        Location[] surroundingTiles = world.getSurroundingTiles(world.getCurrentLocation()).toArray(new Location[0]);
        for(Location l : surroundingTiles)
            if(world.getTile(l) instanceof Predator)
                return;
        world.setTile(world.getLocation(this), animals.get(0));
        animals.remove(0);
    }
}