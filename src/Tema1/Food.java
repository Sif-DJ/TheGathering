package Tema1;

import itumulator.simulator.Actor;
import itumulator.world.NonBlocking;

public abstract class Food extends Organism implements Actor, NonBlocking {
    //function for taking away from the amount of energy food has;
    public abstract int eat(int amount);
}
