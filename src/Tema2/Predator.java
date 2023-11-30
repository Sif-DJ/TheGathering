package Tema2;

import Tema1.Animal;
import Tema1.DeathException;
import itumulator.executable.Program;
import itumulator.world.*;

import java.util.ArrayList;

public abstract class Predator extends Animal {

    protected Object targetPrey = null;

    @Override
    public void act(World world) throws DeathException {
        super.act(world);

        try{
            if(world.getLocation(targetPrey) == null)
                targetPrey = null;
        }catch(Exception e){
            targetPrey = null;
        }


        if(isHungry() && targetPrey == null){
            Carcass carcass = getAnyCarcass(world);
            if(carcass == null) {
                chooseNextTarget(world);
            }
            else{
                chooseNextTarget(carcass);
            }
        }

        if(targetPrey == null){
            wandering(world);
        }
        else{
            System.out.println("Hunting " + targetPrey);
            determineNextMovement(world, world.getLocation(targetPrey));
        }
    }
    public abstract void chooseNextTarget(World world);
    public void chooseNextTarget(Object edible){
        targetPrey = edible;
    }

    public Carcass getAnyCarcass(World world){
        Object[] list = world.getEntities().keySet().toArray(new Object[0]);
        ArrayList<Carcass> carcasses = new ArrayList<>();
        for(Object thing : list){
            if(thing instanceof Carcass)
                carcasses.add((Carcass)thing);
        }
        if(carcasses.isEmpty())
            return null;
        return carcasses.get(r.nextInt(carcasses.size()));
    }
}
