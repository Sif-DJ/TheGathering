package Tema1;

import itumulator.world.Location;
import itumulator.world.NonBlocking;
import itumulator.world.World;
import itumulator.simulator.*;

import java.util.ArrayList;

public abstract class Burrow implements NonBlocking, Actor {

    // creates
    protected ArrayList<Animal> animals = new ArrayList<>();
    //


    @Override
    public void act(World world){
        if(world.isDay() && world.isTileEmpty(world.getLocation(this))){
            exit(world);
        }
    }
    public void enter(Animal animal){
        this.animals.add(animal);
    }

    public void exit(World world){
        if(animals.isEmpty())return;
        world.setTile(world.getCurrentLocation(), animals.get(0));
        animals.remove(0);
    }

    public boolean isInHole(Animal animal){
        return (animals.contains(animal));
    }

    public ArrayList<Animal> getAnimals(){return new ArrayList<Animal>(animals);}
}
