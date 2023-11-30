package Tema2;

import Tema1.*;
import itumulator.world.*;

import java.util.ArrayList;

public class Pack {
    protected ArrayList<Animal> list;
    protected Animal targetPrey;
    public Pack(){
        list = new ArrayList<>();
    }

    public void add(Animal animal){
        list.add(animal);
    }

    public void assignPrey(Animal animal){
        targetPrey = animal;
    }

    public Location getPreyLocation(World world){
        return world.getLocation(targetPrey);
    }
}
