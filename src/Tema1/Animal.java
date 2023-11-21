package Tema1;

import itumulator.simulator.Actor;
import itumulator.world.World;

public abstract class Animal implements Actor {
    protected int age;
    protected int health;
    protected int energy;
    protected int hunger;

    public abstract void die(World world);

    public abstract void eat(Food food);

    public abstract void reproduce(World world);
}
