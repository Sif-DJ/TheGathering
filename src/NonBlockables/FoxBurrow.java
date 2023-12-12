package NonBlockables;
import Animals.*;
import itumulator.world.*;

public class FoxBurrow extends Burrow<Fox>{
    public FoxBurrow(){
        this.timeUnOccupied = 0;
        this.maxNumAnimalsInHole = 2;
    }

    @Override
    public void act(World world){
        if(world.isNight() && world.isTileEmpty(world.getLocation(this))){
            exit(world);
        }
        if(animals.isEmpty()){timeUnOccupied++;}else{timeUnOccupied=0;}
        if(timeUnOccupied > 100){
            for(Fox fox : assignedToBurrow){
                fox.assignHole(null);
            }
            caveIn(world);
        }
    }
}
