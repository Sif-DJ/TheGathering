package NonBlockables;

import Dubious.DeathException;
import Dubious.Organism;
import itumulator.executable.*;
import itumulator.world.*;

import java.awt.*;
import java.util.ArrayList;

public class Mushroom extends Organism implements NonBlocking {

    /**
     * A list of all things this mushroom should be able to spread to. Is added to in the instantiate function.
     */
    private final ArrayList<Food> spreadTo = new ArrayList<>();

    /**
     * The constructor for Mushroom.
     * @param startingEnergy the amount of energy the Mushroom starts with.
     */
    public Mushroom(int startingEnergy){
        energy = startingEnergy;
        spreadTo.add(new Carcass(false,100));
    }

    /**
     * The method called whenever the actor needs to simulate actions.
     * @param world providing details of the position on which the actor is currently located and much more.
     */
    @Override
    public void act(World world) {
        energy -= 1;
        if(energy <= 0){
            try{
                die(world);
                return;
            }catch (DeathException e){
                System.out.println(e);
                return;
            }

        }

        if(world.getCurrentLocation() != null){
            releaseSpores(world);
        }
    }

    /**
     * Provides info on how this object should be displayed in game world.
     * @return DisplayInformation with its name and if it is small or not.
     */
    @Override
    public DisplayInformation getInformation() {
        return new DisplayInformation(Color.black,
                "fungi"
                        +(energy > 40 ? "" : "-small"));
    }


    /**
     * Sends spores to all nearby available carcasses.
     * @param world providing details of the position on which the actor is currently located and much more.
     */
    public void releaseSpores(World world){
        ArrayList<Location> locations = searchForFood(world,spreadTo,5);
        for(Location location : locations){
            ((Carcass) world.getNonBlocking(location)).setIsInfected();
        }
    }
}