package Tema1;

import itumulator.world.World;

public class Grass extends Food {
    //Grass konstrukter
    public Grass(){}
    //override function
    @Override
    public int eat(int amount) {
        return 0;
    }

    @Override
    public void act(World world) {

    }
    @Override
    public void die(World world) {

    }
    //function for grass to spread
    public void spread(){}



}
