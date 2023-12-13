package Animals;

import Dubious.Pack;
import NonBlockables.RabbitBurrow;
import Dubious.DeathException;
import NonBlockables.Grass;
import itumulator.executable.DisplayInformation;
import itumulator.world.Location;
import itumulator.world.World;

import java.awt.*;
import java.util.*;


public class Rabbit extends Animal{

    private RabbitBurrow burrow;
    private final ArrayList<Animal> fleeFrom = new ArrayList<>();

    /**
     * The Rabbit constructor
     * @param isInfected A boolean that determines if the Rabbit is infected by spores of mushrooms.
     */
     public Rabbit(boolean isInfected){
         super(isInfected);
        maxEnergy = 100;
        energy = maxEnergy;
        age = 0;
        ageMax = 15;
        health = 7;
        searchRadius = 2;
        fleeFrom.add(new Bear(false));
        fleeFrom.add(new Fox(false));
        fleeFrom.add(new Wolf(false ,new Pack()));
    }

    /**
     * Deletes the animal from the world.
     * @param world Providing details of the position on which the actor is currently located and much more.
     * @throws DeathException Throws an exception that stops parts of the program that could break, because the entity stopped existing.
     */
    @Override
    public void die(World world) throws DeathException {
        if(burrow != null)
            burrow.unAssign(this);
         super.die(world);
    }

    /**
     * Creates a new instance of its type of animal.
     * Mainly used in the reproduce() function.
     * @return returns a new instance of itself.
     */
    @Override
    public Animal createNewSelf(){
        return new Rabbit(false);
    }

    /**
     * The method called whenever the actor needs to simulate actions.
     * @param world providing details of the position on which the actor is currently located and much more.
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
        if(!isInBurrow()) {
            ArrayList<Location> f = searchForAnimals(world,fleeFrom);
            if(!(f == null) && !f.isEmpty()) {
                Location closest = getClosestLocation(world, f);
                if( burrow != null){
                    flee(world,closest,burrow);
                }else{
                    flee(world,closest);
                }
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
                headTowards(world, world.getLocation(burrow));
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
                    digBurrow(world);
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

    /**
     * Defines how a Rabbit reproduces.
     * @param world Providing details of the position on which the actor is currently located and much more.
     */
    @Override
    public void tryReproduce(World world){
        Set<Object> objs = world.getEntities().keySet();
        double rabbitCounter = 0.0;
        for(Object obj : objs){
            if(obj instanceof Rabbit) rabbitCounter += 1.0;
        }
        if(4.0 / rabbitCounter < r.nextDouble() * 2.0) return;
        if(isInBurrow() && burrow != null){
            Random r = new Random();
            ArrayList<Animal> rabbits = burrow.getAnimals();
            rabbits.remove(this);
            if(!rabbits.isEmpty()){
                reproduce(world, rabbits.get(r.nextInt(rabbits.size())));
            }
        }
    }

    /**
     * Handles the actual reproduction of the animal.
     * Checks if the animals have enough energy and are adults.
     * They create 1-3 baby rabbits, which are stored in the burrows.
     * @param world Providing details of the position on which the actor is currently located and much more.
     * @param animal The animal that is being reproduced with.
     */
    @Override
    public void reproduce(World world, Animal animal) {
        if(energy < 40 || isBaby || animal.getEnergy() < 40 || animal.isBaby) return;
        for(int i = 0; i <= r.nextInt(3) + 2; i++){
            Rabbit rabbit = (Rabbit) this.createNewSelf();
            world.add(rabbit);
            rabbit.assignHole(burrow);
            this.burrow.addToList(this);
            burrow.enter(rabbit);
        }
        energy -= 40;
        animal.addEnergy(-40);
    }

    /**
     * Assign the rabbit to hole it is standing on.
     * @param world Providing details of the position on which the actor is currently located and much more.
     */
    public void assignHole(World world){
        Location l = world.getLocation(this);
        if(world.containsNonBlocking(l)) {
            if (world.getNonBlocking(l) instanceof RabbitBurrow) {
                burrow = (RabbitBurrow) world.getNonBlocking(l);
                this.burrow.addToList(this);
            }
        }
    }

    /**
     * Digs a burrow to place in the world and assigns itself to it.
     * @param world providing details of the position on which the actor is currently located and much more.
     * @param l the location of the burrow.
     */
    public void diggyHole(World world, Location l){
        world.setTile(l, new RabbitBurrow());
        assignHole(world);
    }

    /**
     * Sets itself to know this burrow and adds itself to the burrows list.
     * @param burrow to be assigned to.
     */
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
     * @param world Providing details of the position on which the actor is currently located and much more.
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

    /**
     * Gets if this rabbit is in a burrow. True if in hole, otherwise false.
     * @return boolean of its current state.
     */
    public boolean isInBurrow(){
        if(burrow == null)
            return false;
        return (burrow.isInBurrow(this));
    }

    /**
     * Ages the rabbit by one.
     * @param world providing details of the position on which the actor is currently located and much more.
     * @throws DeathException Throws an exception that stops parts of the program that could break, because the entity stopped existing.
     */
    @Override
    public void age(World world) throws DeathException {
        super.age(world);
        if(isBaby && age > 2)
            isBaby = false;
    }

    /**
     * Provides info on how this object should be displayed in game world.
     * @return DisplayInformation with its name, if it small or large, and if it is infected.
     */
    @Override
    public DisplayInformation getInformation() {
        return new DisplayInformation(Color.red,
                "rabbit"
                        +(isBaby ? "-small" : "-large")
                        +(isInfected ? "-fungi" : ""));
    }
}
