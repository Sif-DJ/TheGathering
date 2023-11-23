package Tema1;

import itumulator.world.Location;
import itumulator.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Rabbit extends Animal{


    Random r = new Random();

    //Rabbit konstrukter
    public Rabbit(){
        maxEnergy = 50;
        energy = maxEnergy;
    }
    //override functions
    @Override
    public void die(World world) {
        world.delete(this);
    }
    @Override
    public void eat(Food food, World world) {
        energy += food.eat(15);
        if(energy > maxEnergy) energy=maxEnergy;
        System.out.println("i have eaten");
    }
    @Override
    public void reproduce(World world) {

    }
    @Override
    public void act(World world) {
        move(world);
        if(world.containsNonBlocking(world.getLocation(this))){
            if(world.getNonBlocking(world.getLocation(this)) instanceof Grass){
                Grass grass = (Grass)world.getNonBlocking(world.getLocation(this)) ;
                eat(grass, world);
            }
        }

    }

    @Override
    public void move(World world) {
        Set<Location> neighbours  = world.getEmptySurroundingTiles();
        List<Location> list = new ArrayList<>(neighbours);
        if(list.isEmpty()) return;
        Location l = list.get(r.nextInt(list.size()));
        world.move(this,l);
        energy -= 2;
    }

    //function for the digging of rabbit holes
    public void digHole(){

    }
    //function to assing rabbit specific hole
    public void assingHole(){

    }




}
