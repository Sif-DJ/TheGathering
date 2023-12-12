package NonBlockables;

import Animals.Wolf;
import itumulator.world.World;

public class WolfBurrow extends Burrow<Wolf> {
    /**
     * The constructor for WolfBurrow.
     */
    public WolfBurrow(){
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
        if(timeUnOccupied > 50){
            while(!animals.isEmpty()){
                ((Wolf)animals.get(0)).getPack().unAssignBurrow();
            }
            caveIn(world);
        }
    }

    /**
     * The function to exit the wolfburrow.
     * @param world providing details of the position on which the actor is currently located and much more.
     */
    @Override
    public void exit(World world){
        if(animals.isEmpty())return;
        world.setTile(world.getLocation(this), animals.get(0));
        animals.remove(0);
    }
}
