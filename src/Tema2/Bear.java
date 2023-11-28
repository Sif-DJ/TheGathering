package Tema2;

import Tema1.Animal;
import Tema1.DeathException;
import itumulator.world.World;

public class Bear extends Predator {

    public Bear() {
    }

    @Override
    public void act(World world) throws DeathException {
        super.act(world);
    }

    @Override
    public void tryReproduce(World world) {

    }

    @Override
    public Animal createNewSelf() {
        return new Bear();
    }
}