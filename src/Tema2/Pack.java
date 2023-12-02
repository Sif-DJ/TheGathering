package Tema2;

import Tema1.*;
import itumulator.world.*;

import java.util.ArrayList;
import java.util.Random;

public class Pack {
    protected ArrayList<Wolf> list;
    protected Animal targetPrey;
    private final Random r = new Random();
    
    public Pack(){
        list = new ArrayList<>();
    }

    public void add(Wolf wolf){
        list.add(wolf);
    }

    public void choosePrey(World world){
        Object[] possibleTargets;
        try{
            possibleTargets = world.getEntities().keySet().toArray(new Object[0]);
        }catch (NullPointerException e){
            System.out.println("Are we there yet?");
            return;
        }

        ArrayList<Animal> edibleTargets = new ArrayList<>();
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
            return;
        }

        assignPrey(edibleTargets.get(r.nextInt(edibleTargets.size())));
    }

    public void assignPrey(Animal animal){
        System.out.println(this + " has found and is hunting " + animal);
        targetPrey = animal;
        for(Wolf wolf : list){
            wolf.targetPrey = animal;
        }
    }

    public Location getPreyLocation(World world){
        return world.getLocation(targetPrey);
    }
}
