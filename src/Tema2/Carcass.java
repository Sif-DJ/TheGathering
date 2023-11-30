package Tema2;

import Tema1.DeathException;
import Tema1.Food;
import itumulator.world.World;

public class Carcass extends Food {

    int rotTimer = 3;
    public Carcass(int energy){
        this.energy = energy + 10;
    }

    @Override
    public void act(World world) throws DeathException {
        if(world.getCurrentTime() % 20 == 0) rotTimer-=1;

        if(energy > 50){
            // Gain big sprite
        }else{
            // Gain small sprite
        }
        try {
            if(rotTimer <= 0){
                die(world);
                return;
            }
            if (energy<=0){
                die(world);
            }
        }catch (DeathException e){
            System.out.println(e.getMessage());
        }

    }
}
