package Tema1;

import itumulator.world.World;

public class Grass extends Food {

    public Grass(){}

    @Override
    public int eat(int amount) {
        return 0;
    }

    @Override
    public void act(World world) {

    }

    public void spread(){}

    public void die(){}
}
