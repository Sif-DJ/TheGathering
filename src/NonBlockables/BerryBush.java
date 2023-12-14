package NonBlockables;

import itumulator.executable.DisplayInformation;
import itumulator.world.World;

import java.awt.*;

public class BerryBush extends Food {
    /**
     * The constructor for Carcass.
     */
    public BerryBush(){
        this.energy = 0;
        this.maxEnergy = 50;
    }
    /**
     * The method called whenever the actor needs to simulate actions.
     * @param world providing details of the position on which the actor is currently located and much more.
     */
    @Override
    public void act(World world) {
        if(world.getCurrentTime() % 20 == 0) this.energy += 25;
        if(energy > maxEnergy)energy = maxEnergy;
    }
    /**
     * Provides info on how this object should be displayed in game world.
     * @return DisplayInformation that is relevant for the BerryBush currently.
     */
    @Override
    public DisplayInformation getInformation() {
        return new DisplayInformation(Color.blue,
                "bush"
                        +(energy > 0 ? "-berries" : ""));
    }
}
