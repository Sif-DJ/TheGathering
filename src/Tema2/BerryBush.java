package Tema2;

import Tema1.Food;
import itumulator.executable.DisplayInformation;
import itumulator.executable.DynamicDisplayInformationProvider;
import itumulator.world.World;

import java.awt.*;

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

    @Override
    public DisplayInformation getInformation() {
        if(energy > 0){
            return new DisplayInformation(Color.blue,"bush-berries");
        }else{
            return new DisplayInformation(Color.blue,"bush");
        }
    }
}
