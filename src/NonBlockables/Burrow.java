package NonBlockables;

import Animals.Animal;
import itumulator.world.NonBlocking;
import itumulator.world.World;
import itumulator.simulator.*;
import java.util.ArrayList;

public abstract class Burrow<T extends Animal> implements NonBlocking, Actor {

    // variables
    protected ArrayList<Animal> animals = new ArrayList<>();
    protected ArrayList<T> assignedToBurrow = new ArrayList<>();
    protected int maxNumAnimalsInHole;
    protected int timeUnOccupied;
    /**
     * The method called whenever the actor needs to simulate actions.
     * @param world providing details of the position on which the actor is currently located and much more.
     */
    @Override
    public void act(World world){
        if(world.isDay() && world.isTileEmpty(world.getLocation(this))){
            exit(world);
        }
        if(animals.isEmpty()){timeUnOccupied++;}else{timeUnOccupied=0;}
    }

    /**
     * The burrow can cave in during certain events. This does that.
     * Rabbits may get stuck.
     * @param world The world the burrow is in.
     */
    public void caveIn(World world){
        world.delete(this);
    }

    /**
     * UnAssigns animal that belongs to this burrow.
     * @param animal the animal you want to unAssign.
     */
    public void unAssign(T animal){
        if(animal == null)return;
        assignedToBurrow.remove(animal);
    }

    /**
     * Assigns an animal to this burrow.
     * @param animal the animal you want to assign.
     */
    public void addToList(T animal){
        this.assignedToBurrow.add(animal);
    }

    /**
     * Enter this burrow.
     * @param animal the animal you want to enter.
     */
    public void enter(Animal animal){
        this.animals.add(animal);
    }

    /**
     * Makes an animal get out of the burrow without appearing on the map.
     * @param animal The animal that is forced to exit.
     */
    public void forceExit(Animal animal){this.animals.remove(animal);}

    /**
     * Exit this burrow.
     * @param world providing details of the position on which the actor is currently located and much more.
     */
    public void exit(World world){
        if(animals.isEmpty())return;
        world.setTile(world.getLocation(this), animals.get(0));
        animals.remove(0);
    }

    /**
     * Checks if the animal is in this hole.
     * @param animal the animal you want to check if it is in this hole.
     * @return true if the animal is in this hole.
     */
    public boolean isInBurrow(Animal animal){
        return (animals.contains(animal));
    }

    /**
     * Returns the animals in this hole.
     * @return the animals in this hole.
     */
    public ArrayList<Animal> getAnimals(){return new ArrayList<>(animals);}

    /**
     * Returns true if the burrow is full.
     * @return true if the burrow is full.
     */
    public boolean isBurrowFull(){
        return (animals.size() >= maxNumAnimalsInHole);
    }
}
