package NonBlockables;

import Dubious.DeathException;
import itumulator.executable.DisplayInformation;
import itumulator.world.Location;
import itumulator.world.World;

import java.awt.*;

public class Carcass extends Food {
    int rotTimer = 3;
    boolean isInfected;
    Mushroom mushroom = null;
    Location l;
    public Carcass(boolean isInfected,int energy){
        this.energy = energy + 100;
        this.isInfected = isInfected;
        if(isInfected) mushroom = new Mushroom();
    }

    @Override
    public void act(World world){
        if(world.getCurrentTime() % 20 == 0){
            rotTimer-=1;
            mushroom.addEnergy(20);
        }

        try {
            if(rotTimer <= 0){
                Location l = world.getLocation(this);
                world.remove(this);
                world.setTile(l, mushroom);
                die(world);
                return;
            }
            if (energy<=0){
                die(world);
                return;
            }
        }catch (DeathException e){
            System.out.println(e);
        }

    }

    @Override
    public DisplayInformation getInformation() {
        if(energy > 50){
            return new DisplayInformation(Color.pink,"carcass");
        }
        else{
            return new DisplayInformation(Color.pink,"carcass-small");
        }

    }

    public void setIsInfected(){
        isInfected = true;
        if(mushroom == null) mushroom = new Mushroom();
    }

}
