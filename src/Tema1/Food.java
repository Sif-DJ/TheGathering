package Tema1;

import itumulator.world.NonBlocking;

public abstract class Food extends Organism implements NonBlocking {
    //function for taking away from the amount of energy food has;
    /**
     * Returns an amount of energy back and removes that amount from itself in energy.
     * @param amount how much energy you want to deduct.
     * @return int in energy.
     */
    public int eat(int amount) {
        int energyToReturn;
        if(amount <= energy){
            energyToReturn = amount;
            energy -= energyToReturn;
        }else{
            energyToReturn  = energy;
            energy = 0;
        }
        return energyToReturn;
    }
}
