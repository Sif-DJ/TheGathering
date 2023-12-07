package NonBlockables;

import Animals.Animal;
import Dubious.DeathException;
import Dubious.Organism;
import itumulator.executable.*;
import itumulator.world.*;

import java.awt.*;
import java.util.ArrayList;

/**
 * Otherwise known as cordyceps. Icky bastards.
 */
public class Mushroom extends Organism implements NonBlocking {


    private final ArrayList<Food> spreadTo = new ArrayList<>();

    public  Mushroom(){
        spreadTo.add(new Carcass(false,100));
    }

    @Override
    public void act(World world) {
        energy--;
        if(energy <= 0){
            try{
                die(world);
                return;
            }catch (DeathException e){
                System.out.println(e);
                return;
            }

        }

        if(world.getLocation(this) != null){
            releaseSpores(world);
        }
    }

    @Override
    public DisplayInformation getInformation() {
        return new DisplayInformation(Color.black,
                "fungi"
                        +(energy > 40 ? "" : "-small"));
    }


    public void releaseSpores(World world){
        ArrayList<Location> locations = searchForFood(world,spreadTo,5);
        for(Location location : locations){
            if(world.containsNonBlocking(location)){
                if (world.getNonBlocking(location) instanceof Carcass) {
                    ((Carcass) world.getNonBlocking(location)).setIsInfected();
                }
            }
        }
    }
}
