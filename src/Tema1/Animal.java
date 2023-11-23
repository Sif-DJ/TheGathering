package Tema1;

import itumulator.simulator.Actor;
import itumulator.world.World;
import Tema1.*;

public abstract class Animal extends Organism implements Actor {
    //variables for tings extending this class
    protected int age;
    protected int health;
    protected int maxEnergy;
    //function for the animal to be able to eat
    public abstract void eat(Food food, World world);
    //function for animal to make more animals
    public abstract void reproduce(World world);

    public abstract  void move(World world);

}
