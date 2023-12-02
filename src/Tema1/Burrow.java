package Tema1;

import itumulator.world.NonBlocking;
import itumulator.world.World;
import itumulator.simulator.*;
import java.util.ArrayList;

public abstract class Burrow<T extends  Animal> implements NonBlocking, Actor {

    // creates
    protected ArrayList<Animal> animals = new ArrayList<>();
    protected ArrayList<T> assignedToBurrow = new ArrayList<>();
    protected int maxNumAnimalsInHole;
    protected int timeUnOccupied;

    @Override
    public void act(World world){
        if(world.isDay() && world.isTileEmpty(world.getLocation(this))){
            exit(world);
        }
        if(animals.isEmpty()){timeUnOccupied++;}else{timeUnOccupied=0;}
    }

    /**
     * The burrow can cave in during certain events. This does that.
     * Rabbits may get stuck
     * @param world The world the burrow is in.
     */
    public void caveIn(World world){
        world.delete(this);
    }

    public void unAssign(T animal){
        if(animal == null)return;
        assignedToBurrow.remove(animal);
    }

    public void addToList(T animal){
        this.assignedToBurrow.add(animal);
    }

    public void enter(Animal animal){
        this.animals.add(animal);
    }

    public void exit(World world){
        if(animals.isEmpty())return;
        world.setTile(world.getLocation(this), animals.get(0));
        animals.remove(0);
    }

    public boolean isInHole(Animal animal){
        return (animals.contains(animal));
    }

    public ArrayList<Animal> getAnimals(){return new ArrayList<>(animals);}

    public boolean isBurrowFull(){
        return (animals.size() >= maxNumAnimalsInHole);
    }
}
