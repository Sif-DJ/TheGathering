package Tema2;

import Tema1.Animal;
import Tema1.DeathException;
import itumulator.world.World;

public class Bear extends Predator {

    public Bear() {
        this.energy = 500;
        this.age = 0;
        this.ageMax = 50;
    }

    @Override
    public void act(World world) throws DeathException {
        try{
            super.act(world);
        }catch (DeathException e){
            System.out.println(e.getMessage());
            return;
        }

        // Insert code here
    }

    @Override
    public void tryReproduce(World world) {

    }

    @Override
    public Animal createNewSelf() {
        return new Bear();
    }
}