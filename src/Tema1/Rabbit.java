package Tema1;

import itumulator.world.Location;
import itumulator.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Rabbit extends Animal{
    Random r = new Random();

    Burrow burrow;

    //Rabbit constructor
    public Rabbit(){
        maxEnergy = 50;
        energy = maxEnergy;
        age  = 0;
    }

    /**
     * Deletes the rabbit instance.
     * @param world The world the rabbit is in.
     */
    @Override
    public void die(World world) {
        world.delete(this);
    }

    /**
     * consumes an amount of energy from a food instance, refilling its own energy.
     * @param food The food the rabbit is trying to eat.
     * @param world The world the rabbit is in.
     */
    @Override
    public void eat(Food food, World world) {
        energy += food.eat(8);
        if(energy > maxEnergy) energy=maxEnergy;
    }

    /**
     *
     * @param world The world the rabbit is in.
     */
    @Override
    public void reproduce(World world) {

    }

    /**
     * All actions taken during a call to simulate the program.
     * @param world The world the rabbit is in.
     */
    @Override
    public void act(World world) {
        if(world.getCurrentTime() == 20) age++;
        System.out.println(age);
        if(age >= 70) die(world);
        move(world);
        if(world.containsNonBlocking(world.getLocation(this))){
            if(world.getNonBlocking(world.getLocation(this)) instanceof Grass){
                Grass grass = (Grass) world.getNonBlocking(world.getLocation(this)) ;
                eat(grass, world);
            }
        }
        if(energy <= 0 ) die(world);
    }

    /**
     * Tries to move to an empty surrounding tile, loosing energy in the progress.
     * @param world The world the rabbit is in.
     */
    @Override
    public void move(World world) {
        Set<Location> neighbours  = world.getEmptySurroundingTiles();
        List<Location> list = new ArrayList<>(neighbours);
        if(list.isEmpty()) return;
        Location l = list.get(r.nextInt(list.size()));
        world.move(this,l);
        energy -= 2;
    }

    /**
     * Checks if the current tile contains Nonblocking entities.
     * If yes, it checks if its valid for creating a RabbitHole.
     * If yes, it creates a new RabbitHole instance.
     * @param world The world the rabbit is in.
     */
    public void digHole(World world){
        Location l = world.getLocation(this);
        if(world.containsNonBlocking(l)){
            if(world.getNonBlocking(l) instanceof Grass){
                 world.delete(world.getNonBlocking(l));
            }else if (world.getNonBlocking(l) instanceof Burrow){
                return;
            }
        }
        world.setTile(l, new Burrow());
        assignHole(world);
    }

    /**
     *
     * @param world The world the rabbit is in.
     */
    //function to assign rabbit specific hole
    public void assignHole(World world){
        Location l = world.getLocation(this);
        if(world.containsNonBlocking(l)) {
            if (world.getNonBlocking(l) instanceof Burrow) {
                burrow = (Burrow) world.getNonBlocking(l);
            }
        }
    }
}
