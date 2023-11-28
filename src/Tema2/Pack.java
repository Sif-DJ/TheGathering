package Tema2;

import Tema1.*;

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
}
