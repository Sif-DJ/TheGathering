package Tema1;

import itumulator.simulator.Actor;
import itumulator.world.NonBlocking;

public abstract class Food implements Actor, NonBlocking {
    protected int energy;

    public abstract int eat(int amount);
}
