package Tema1;

import itumulator.world.Location;
import itumulator.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Rabbit extends Animal{
    private Random r = new Random();

    private Burrow burrow;

    //Rabbit constructor
    public Rabbit(){
        maxEnergy = 100;
        energy = maxEnergy;
        age  = 0;
        ageMax = 70;
    }

    @Override
    public void move(World world, Location location) {

    }

    @Override
    public Animal createNewSelf(){
        return new Rabbit();
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
        try{
            if(world.getCurrentTime() % 20 == 0) {
                    age(world);
            }
        }catch (DiedOfOldAgeException e){
            return;
        }
        if(!isInHole()) {
            if(world.isDay()){
                wandering(world);
            }else if(burrow != null){
                determineNextMovement(world, world.getLocation(burrow));
            }


            if (world.containsNonBlocking(world.getLocation(this))) {
                if (world.getNonBlocking(world.getLocation(this)) instanceof Grass) {
                    Grass grass = (Grass) world.getNonBlocking(world.getLocation(this));
                    eat(grass, world);
                }
                // If the rabbit finds a new hole, assign it. No reason to run for the old hole that's far away.
                if (world.getNonBlocking(world.getLocation(this)) instanceof Burrow) {
                    assignHole(world);
                }
            }

            // All nighttime calculations
            if (world.isNight()) {
                if (burrow == null)
                    digHole(world);
                if (world.getLocation(burrow).equals(world.getLocation(this))) {
                    enterHole(world);
                }
            }
            if (energy <= 0) die(world);
        }
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
                assignHole(world);
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

    /**
     * Enters a hole it is assigned to, afterwards it deletes itself.
     * @param world
     */
    public void enterHole(World world){
        burrow.enter(this);
        world.remove(this);
    }

    public boolean isInHole(){
        if(burrow == null)
            return false;
        return (burrow.isRabbitInHole(this));
    }
}
