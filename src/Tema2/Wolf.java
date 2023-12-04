package Tema2;

import Tema1.*;
import itumulator.executable.DisplayInformation;
import itumulator.world.*;
import java.awt.*;

public class Wolf extends Predator {
    protected final Pack pack;
    protected WolfBurrow burrow;
    
    public Wolf(Pack pack){
        this.maxEnergy = 400;
        this.energy = 200;
        this.age = 0;
        this.pack = pack;
        this.ageMax = 15;
        this.power = 5;
        this.health = 15;
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

        doMove(world);
    }
    @Override
    public void tryReproduce(World world) {

    }

    @Override
    public void chooseNextTarget(World world) {
        pack.choosePrey(world);
    }

    @Override
    public void chooseNextTarget(Object edible) {
        pack.assignPrey(edible);
    }

    public void digHole(World world){
        Location l = world.getLocation(this);
        if(world.containsNonBlocking(l)){
            Object nonBlocking = world.getNonBlocking(l);
            if(nonBlocking instanceof Grass)
                world.delete(nonBlocking);
            if(nonBlocking instanceof Burrow)
                return;
            if(nonBlocking instanceof Carcass)
                return;
        }
        burrow = new WolfBurrow();
        world.setTile(l, burrow);
        enterHole();
    }

    public void enterHole(){

    }

    @Override
    public DisplayInformation getInformation() {
        return new DisplayInformation(Color.cyan, "wolf");
    }
}
