package Tema1;

import Tema2.*;
import itumulator.executable.DisplayInformation;
import itumulator.world.Location;
import itumulator.world.World;

import java.awt.*;
import java.util.*;


public class Rabbit extends Animal{

    private RabbitBurrow burrow;
    private ArrayList<Animal> fleeFrom = new ArrayList<>();

    //Rabbit constructor
     public Rabbit(){
        maxEnergy = 100;
        energy = maxEnergy;
        age = 0;
        ageMax = 12;
        health = 7;
        searchRadius = 2;
        fleeFrom.add(new Bear());
        fleeFrom.add(new Wolf(new Pack()));
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
            System.out.println(e);
            return;
        }
        if(world.getCurrentLocation() == null) return;
        if(!isInHole()) {
            ArrayList<Location> f = searchForAnimals(world,fleeFrom);
            if(!(f == null) || !f.isEmpty()) {
                Location closest = getClosestLocation(world, f);
                flee(world,closest,burrow);
                if(world.containsNonBlocking(world.getLocation(this))){
                    if (world.getNonBlocking(world.getLocation(this)) instanceof RabbitBurrow) {
                        assignHole(world);
                        enterHole(world);
                    }
                }
                return;
            }

            if(world.isDay()){
                wandering(world);
            }else if(burrow != null){
                determineNextMovement(world, world.getLocation(burrow));
            }


            if (world.containsNonBlocking(world.getLocation(this))) {
                if (world.getNonBlocking(world.getLocation(this)) instanceof Grass) {
                    Grass grass = (Grass) world.getNonBlocking(world.getLocation(this));
                    eat(grass);
                }
                // If the rabbit finds a new hole, assign it. No reason to run for the old hole that's far away.
                if (world.getNonBlocking(world.getLocation(this)) instanceof RabbitBurrow) {
                    assignHole(world);
                }
            }

            // All nighttime calculations
            if (world.isNight()) {
                if (burrow == null){
                    digHole(world);
                } else if (world.getLocation(burrow).equals(world.getLocation(this))) {
                    enterHole(world);
                } else if (burrow == null && world.containsNonBlocking(world.getLocation(this))) {
                    if (world.getNonBlocking(world.getLocation(this)) instanceof RabbitBurrow) {
                        wandering(world);
                    }
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
        if(energy < 40 || isBaby || animal.energy < 40 || animal.isBaby) return;
        for(int i = 0; i <= r.nextInt(3); i++){
            Rabbit rabbit = (Rabbit) this.createNewSelf();
            world.add(rabbit);
            rabbit.assignHole(burrow);
            this.burrow.addToList(this);
            burrow.enter(rabbit);
        }
        energy -= 40;
        animal.energy -= 40;
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
            }else if (world.getNonBlocking(l) instanceof RabbitBurrow){
                assignHole(world);
                return;
            }else if(world.getNonBlocking(l) instanceof Carcass){
                return;
            }
        }
        world.setTile(l, new RabbitBurrow());
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
            if (world.getNonBlocking(l) instanceof RabbitBurrow) {
                burrow = (RabbitBurrow) world.getNonBlocking(l);
                this.burrow.addToList(this);
            }
        }
    }

    public void assignHole(RabbitBurrow burrow){
        this.burrow = burrow;
        this.burrow.addToList(this);
    }

    /**
     * Should only be called from the caveIn function in Burrow
     */
    public void unAssignHole(){
        this.burrow = null;
    }

    /**
     * Enters a hole it is assigned to, afterwards it deletes itself.
     * Gets skipped if and only if, there is no more space in the burrow.
     * @param world the world object
     */
    public void enterHole(World world){
        if(burrow.isBurrowFull()){
            burrow.unAssign(this);
            unAssignHole();
            return;
        }
        burrow.enter(this);
        world.remove(this);
        world.setCurrentLocation(world.getLocation(burrow));
    }

    public boolean isInHole(){
        if(burrow == null)
            return false;
        return (burrow.isInHole(this));
    }

    @Override
    public void age(World world) throws DeathException {
        super.age(world);
        if(isBaby && age > 2)
            isBaby = false;
    }

    @Override
    public DisplayInformation getInformation() {
        if (isBaby){
            return new DisplayInformation(Color.red, "rabbit-small");
        }else{
            return  new DisplayInformation(Color.red, "rabbit-large");
        }
    }
}
