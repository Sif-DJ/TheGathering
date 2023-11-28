package Tema1;

import Tema2.BurrowRabbit;
import itumulator.world.Location;
import itumulator.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Rabbit extends Animal{


    private BurrowRabbit burrow;

    //Rabbit constructor
     public Rabbit(){
        maxEnergy = 100;
        energy = maxEnergy;
        age = 0;
        ageMax = 12;
    }

    @Override
    public void move(World world, Location location) {

    }

    @Override
    public Animal createNewSelf(){
        return new Rabbit();
    }


    /**
     * All actions taken during a call to simulate the program.
     * @param world The world the rabbit is in.
     */
    @Override
    public void act(World world) {
        try{
            super.act(world);
        }catch (DeathException e){
            return;
        }

        if(world.getCurrentLocation() == null) return;
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
                if (world.getNonBlocking(world.getLocation(this)) instanceof BurrowRabbit) {
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
        }
    }

    @Override
    public void tryReproduce(World world){
        Set<Object> objs = world.getEntities().keySet();
        double rabbitCounter = 0.0;
        for(Object obj : objs){
            if(obj instanceof Rabbit) rabbitCounter += 1.0;
        }
        if(4.0 / rabbitCounter < r.nextDouble() * 2.0) return;
        if(isInHole() && burrow != null){
            Random r = new Random();
            ArrayList<Animal> rabbits = burrow.getAnimals();
            rabbits.remove(this);
            if(!rabbits.isEmpty()){
                reproduce(world, rabbits.get(r.nextInt(rabbits.size())));
            }
        }
    }

    @Override
    public void reproduce(World world, Animal animal) {
        if(energy < 50 || age < 4 || animal.energy < 50 || animal.age < 4) return;
        for(int i = 0; i <= r.nextInt(3); i++){
            Rabbit rabbit = (Rabbit) this.createNewSelf();
            world.add(rabbit);
            burrow.enter(rabbit);
        }
        energy -= 50;
        animal.energy -= 50;
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
        world.setTile(l, new BurrowRabbit());
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
            if (world.getNonBlocking(l) instanceof BurrowRabbit) {
                burrow = (BurrowRabbit) world.getNonBlocking(l);
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
        return (burrow.isInHole(this));
    }
}
