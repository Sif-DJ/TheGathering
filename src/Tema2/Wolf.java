package Tema2;
import Tema1.*;
import itumulator.world.Location;
import itumulator.world.World;

public class Wolf extends Predator {
    private Pack pack;
    public Wolf(Pack pack){
        this.pack = pack;
    }

    @Override
    public Animal createNewSelf() {
        return new Wolf(this.pack);
    }


    @Override
    public void tryReproduce(World world) {

    }
}
