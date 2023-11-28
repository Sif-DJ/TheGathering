package Tema2;

import Tema1.Food;
import itumulator.world.World;

public class BerryBush extends Food {

    public BerryBush(){
        this.energy = 0;
        this.maxEnergy = 50;
    }

    @Override
    public void act(World world) {
        if(world.getCurrentTime() % 20 == 0) this.energy += 25;
        if(energy > maxEnergy)energy = maxEnergy;
    }
}
