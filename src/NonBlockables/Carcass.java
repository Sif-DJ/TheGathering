package NonBlockables;

import Dubious.DeathException;
import itumulator.executable.DisplayInformation;
import itumulator.world.Location;
import itumulator.world.World;

import java.awt.*;

public class Carcass extends Food {
    public int rotTimer = 3;
    public boolean isInfected;
    public Mushroom mushroom = null;
    public Location l;

    /**
     * The constructor for Carcass.
     * @param isInfected is the Carcass infected with mushrooms.
     * @param energy the amount of energy the animal that died had.
     */
    public Carcass(boolean isInfected, int energy){
        this.energy = energy + 100;
        this.isInfected = isInfected;
        if(isInfected) mushroom = new Mushroom(0);
    }
    /**
     * The method called whenever the actor needs to simulate actions.
     * @param world providing details of the position on which the actor is currently located and much more.
     */
    @Override
    public void act(World world){
        if(world.getCurrentLocation() == null) return;
        if(world.getCurrentTime() % 20 == 0){
            rotTimer-=1;
            if(mushroom != null)
                mushroom.addEnergy(20);
        }

        try {
            if(rotTimer <= 0){
                if(mushroom != null){
                    Location l = world.getLocation(this);
                    world.remove(this);
                    world.setTile(l, mushroom);
                }
                die(world);
                return;
            }
            if (energy<=0){
                die(world);
                return;
            }
        }catch (DeathException e){
            System.out.println(e);

            return;
        }
    }
    /**
     * Provides info on how this object should be displayed in game world.
     * @return DisplayInformation with its name and if it is small or not.
     */
    @Override
    public DisplayInformation getInformation() {
        if(energy > 50){
            return new DisplayInformation(Color.pink,"carcass");
        }
        else{
            return new DisplayInformation(Color.pink,"carcass-small");
        }

    }

    /**
     * Set the Carcass to be Infected with mushrooms.
     */
    public void setIsInfected(){
        isInfected = true;
        if(mushroom == null) mushroom = new Mushroom(0);
    }

}
