package Tema1;

import itumulator.simulator.Actor;
import itumulator.world.World;
import Tema1.*;

public abstract class Animal implements Actor {
    protected int age;
    protected int health;
    protected int energy;

    public abstract void die(World world);

    public abstract void eat(Food food);

    public abstract void reproduce(World world);
}
