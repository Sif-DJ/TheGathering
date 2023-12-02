package Tema2;

import Tema1.DeathException;
import Tema1.Food;
import itumulator.executable.DisplayInformation;
import itumulator.world.World;

import java.awt.*;

public class Carcass extends Food {
    int rotTimer = 3;

    public Carcass(int energy){
        this.energy = energy + 10;
    }

    @Override
    public void act(World world){
        if(world.getCurrentTime() % 20 == 0) rotTimer-=1;

        try {
            if(rotTimer <= 0){
                die(world);
                return;
            }
            if (energy<=0){
                die(world);
                return;
            }
        }catch (DeathException e){
            System.out.println(e);
        }

    }

    @Override
    public DisplayInformation getInformation() {
        if(energy > 50){
            return new DisplayInformation(Color.pink,"carcass");
        }
        else{
            return new DisplayInformation(Color.pink,"carcass-small");
        }

    }
}
