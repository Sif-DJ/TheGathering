package Tema2;

import Tema1.Food;
import itumulator.world.World;

public class Carcass extends Food {

    int rotTimer = 3;
    public Carcass(int energy){
        this.energy = energy;
    }


    @Override
    public void act(World world) {
        if(world.getCurrentTime() % 20 == 0) rotTimer-=1;
        if(rotTimer <= 0){
            die(world);
            return;
        }
        if(energy > 50){

        }else{

        }
        if (energy<=0){
            die(world);
        }
    }
}
