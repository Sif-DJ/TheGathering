package Tema2;

import Tema1.*;
import itumulator.world.*;

import java.util.ArrayList;

public class Pack {
    protected ArrayList<Wolf> list;
    protected Animal targetPrey;
    public Pack(){
        list = new ArrayList<>();
    }

    public void add(Wolf wolf){
        list.add(wolf);
    }

    public void choosePrey(World world){
        if(targetPrey == null){
            Object[] possibleTargets = world.getEntities().keySet().toArray(new Object[0]);
            for(Object obj : possibleTargets){
                if(obj instanceof NonBlocking)continue;
                if(obj instanceof Bear && list.size() >= 3) {
                    assignPrey((Animal) obj);
                    break;
                }
                if(obj instanceof Rabbit) {
                    assignPrey((Rabbit) obj);
                    break;
                }
                if(obj instanceof Wolf)
                    if(!list.contains(obj)){
                        assignPrey((Wolf)obj);
                        break;
                    }
            }
        }
    }

    public void assignPrey(Animal animal){
        targetPrey = animal;
        for(Wolf wolf : list){
            wolf.targetPrey = animal;
        }
    }

    public Location getPreyLocation(World world){
        return world.getLocation(targetPrey);
    }
}
