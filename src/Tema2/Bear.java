package Tema2;

import Tema1.Animal;
import Tema1.DeathException;
import Tema1.Rabbit;
import itumulator.executable.DisplayInformation;
import itumulator.world.*;
import java.awt.*;
import java.util.ArrayList;

public class Bear extends Predator {

    private Location territory;

    public Bear() {
        this.energy = 500;
        this.maxEnergy = 1000;
        this.age = 0;
        this.ageMax = 50;
        this.health = 30;
        this.power = 8;
    }

    @Override
    public void act(World world) throws DeathException {
        if(territory == null)
            territory = world.getLocation(this);
        try{
            super.act(world);
        }catch (DeathException e){
            System.out.println(e.getMessage());
            return;
        }

        // Insert code here
    }

    @Override
    public void chooseNextTarget(World world) {
        Object[] possibleTargets;
        try{
            possibleTargets = world.getEntities().keySet().toArray(new Object[0]);
        }catch (NullPointerException e){
            return;
        }

        ArrayList<Animal> edibleTargets = new ArrayList<>();
        System.out.println(this + " is trying to find a suitable target");
        for(Object obj : possibleTargets){
            try{
                if(world.getLocation(obj) == null)
                    continue;
            }catch(Exception e){
                continue;
            }
            if(obj instanceof Rabbit){
                edibleTargets.add((Rabbit)obj);
            }
        }
        if(edibleTargets.isEmpty()){
            System.out.println(this + " failed to find suitable prey");
            return;
        }

        targetPrey = edibleTargets.get(r.nextInt(edibleTargets.size()));
        System.out.println(this + " found and is hunting " + targetPrey);
    }

    @Override
    public void tryReproduce(World world) {

    }

    @Override
    public Animal createNewSelf() {
        return new Bear();
    }

    @Override
    public DisplayInformation getInformation() {
        return new DisplayInformation(Color.gray,"bear");
    }
}