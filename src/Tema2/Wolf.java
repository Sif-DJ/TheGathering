package Tema2;
import Tema1.*;
import itumulator.world.Location;
import itumulator.world.World;

public class Wolf extends Predator {
    private Pack pack;
    public Wolf(Pack pack){
        this.maxEnergy = 400;
        this.energy = 200;
        this.age = 0;
        this.pack = pack;
        this.ageMax = 15;
    }

    @Override
    public Animal createNewSelf() {
        return new Wolf(this.pack);
    }


    @Override
    public void act(World world){
        try{
            super.act(world);
        }catch (DeathException e){
            System.out.println(e.getMessage());
            return;
        }
    }
    @Override
    public void tryReproduce(World world) {

    }
}
