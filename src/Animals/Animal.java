package Animals;

import Dubious.DeathException;
import Dubious.Organism;
import NonBlockables.*;
import itumulator.world.Location;
import itumulator.world.World;
import java.util.*;

public abstract class Animal extends Organism {
    //variables for tings extending this class
    protected int age;
    protected int ageMax;
    protected int foodSearchRadius;
    protected int searchRadius;
    protected int health;
    protected boolean isBaby = true;
    protected boolean isInfected;

    /**
     * Instantiation of the Animal object.
     * @param isInfected determines if this object has been infected by a mushroom.
     */
    public Animal(boolean isInfected){
        this.isInfected = isInfected;
    }

    /**
     * When an animal is going to die, this function needs to be called. It places all necessary objects when it dies,
     * and throws an exception to break the function out of doing anything more.
     * @param world Providing details of the position on which the actor is currently located and much more.
     * @throws DeathException is used to break out of all functions until it is caught by a non abstract type that ends itself.
     */
    @Override
    public void die(World world) throws DeathException {
        Location l;
        System.out.println(this + " is going to die");
        try {
            l = world.getLocation(this);
        } catch (IllegalArgumentException e) {
            super.die(world);
            return;
        }
        world.remove(this);
        if (world.containsNonBlocking(l)) {
            if (world.getNonBlocking(l) instanceof Burrow || world.getNonBlocking(l) instanceof BerryBush) {
                super.die(world);
            }
            if (world.getNonBlocking(l) instanceof Carcass) {
                Carcass carcass = (Carcass) world.getNonBlocking(l);
                carcass.addEnergy(this.energy + 10);
                carcass.rotTimer = 3;
                if(isInfected){
                    carcass.setIsInfected();
                }
                super.die(world);
            }
            if (world.getNonBlocking(l) instanceof Grass) {
                world.delete(world.getNonBlocking(l));
            }
        }
        world.setTile(l, new Carcass(isInfected, energy));
        super.die(world);
    }

    /**
     * Provides all basic functions to be done by the animal when simulating a world.
     * Designed to be called as a super statement by creatures inheriting this class.
     * @param world providing details of the position on which the actor is currently located and much more.
     * @throws DeathException Throws an exception that stops parts of the program that could break, because the entity stopped existing.
     */
    @Override
    public void act(World world) throws DeathException {
        if (world.getCurrentTime() % 20 == 0)
            age(world);

        tryReproduce(world);

        checkAge(world);
        if (energy <= 0 || health <= 0)
            die(world);
    }

    /**
     * All animals possibly has a unique way of reproducing, and is therefore a necessary inclusion when creating a non abstract animal.
     * Call the reproduce() when it successfully reproduces.
     * @param world providing details of the position on which the actor is currently located and much more.
     */
    public abstract void tryReproduce(World world);

    /**
     * Makes a new animal when they reproduce, where the baby will be placed on a tile adjacent to the parents.
     *
     * @param world providing details of the position on which the actor is currently located and much more.
     */
    public void reproduce(World world, Animal animal) {
        if (energy < 50 || age < 2) return;
        Random r = new Random();
        Animal entity = this.createNewSelf();
        ArrayList<Location> locations = new ArrayList<>(world.getEmptySurroundingTiles(world.getLocation(this)));
        Location l = locations.get(r.nextInt(locations.size()));
        world.setTile(l, entity);
        this.energy -= 50;
    }

    /**
     * Empty method for instantiating new animals on demand in this abstract class.
     * Can mainly be seen in function reproduce().
     * @return returns a copy of the animal
     */
    public abstract Animal createNewSelf();

    /**
     * Method to determine if it is hungry.
     *
     * @return boolean
     */
    public boolean isHungry() {
        return (energy < (maxEnergy * 3.0) /4.0);
    }

    /**
     * Default wandering for animals.
     * @param world providing details of the position on which the actor is currently located and much more.
     */
    public void wandering(World world) {
        try{
            world.getLocation(this);
            world.setCurrentLocation(world.getLocation(this));
        }catch(Exception e){
            return;
        }
        Set<Location> neighbours = world.getEmptySurroundingTiles(world.getLocation(this));
        List<Location> list = new ArrayList<>(neighbours);
        if (list.isEmpty()) return;
        move(world, list);
    }

    /**
     * Moves the animal around on the Map.
     * @param world providing details of the position on which the actor is currently located and much more
     * @param list a list of location that can be moved to.
     */
    public void move(World world, List<Location> list) {
        Location l = list.get(r.nextInt(list.size()));
        world.move(this, l);
        energy -= 1;
    }

    /**
     * returns an arraylist of possible locations to move to
     * @param world providing details of the position on which the actor is currently located and much more.
     * @param locationToReach Location to reach
     * @return an arraylist of possible locations to move to
     */
    public ArrayList<Location> determineNextMovement(World world, Location locationToReach) {
        Location l = world.getLocation(this);
        int currentxLength = getxLengthBetweenTiles(l, locationToReach);
        int currentyLength = getyLengthBetweenTiles(l, locationToReach);
        // Iterate through all surrounding empty tiles to determine possible move locations
        ArrayList<Location> list = new ArrayList<>(world.getEmptySurroundingTiles(world.getLocation(this)));
        Iterator<Location> it = list.iterator();
        int tilexLength;
        int tileyLength;
        while (it.hasNext()) {
            Location tile = it.next();
            tilexLength = getxLengthBetweenTiles(tile, locationToReach);
            tileyLength = getyLengthBetweenTiles(tile, locationToReach);
            if (tilexLength > currentxLength || tileyLength > currentyLength) {
                it.remove();
            }
        }
        return list;
    }

    /**
     * returns an arraylist of possible locations to move to
     * @param world providing details of the position on which the actor is currently located and much more.
     * @param moveAwayForm the location to move away form
     * @return an arraylist of possible locations to move to
     */
    public ArrayList<Location> determineNextMovementAway(World world, Location moveAwayForm){
        Location l = world.getLocation(this);
        int currentxLength = getxLengthBetweenTiles(l, moveAwayForm);
        int currentyLength = getyLengthBetweenTiles(l, moveAwayForm);
        // Iterate through all surrounding empty tiles to determine possible move
        // locations that are away form the location
        ArrayList<Location> list = new ArrayList<>(world.getEmptySurroundingTiles(world.getLocation(this)));
        Iterator<Location> it = list.iterator();
        int tilexLength;
        int tileyLength;
        while (it.hasNext()) {
            Location tile = it.next();
            tilexLength = getxLengthBetweenTiles(tile, moveAwayForm);
            tileyLength = getyLengthBetweenTiles(tile, moveAwayForm);
            if (currentxLength == 0 && tileyLength <= currentyLength) {
                it.remove();
            } else if (currentyLength == 0 && tilexLength <= currentxLength) {
                it.remove();
            } else {
                if (tilexLength < currentxLength && tileyLength <= currentyLength) {
                    it.remove();
                } else if (tilexLength <= currentxLength && tileyLength < currentyLength) {
                    it.remove();
                }
            }
        }
        return list;
    }

    /**
     * Determines the next movement towards a location
     * @param world providing details of the position on which the actor is currently located and much more.
     * @param locationToReach the location the animal need to reach
     */
    public void headTowards(World world,Location locationToReach){
        ArrayList<Location> list = determineNextMovement(world,locationToReach);
        if (list.isEmpty()) {
            wandering(world);
            return;
        }
        move(world, list);
    }
    /**
     * Determines the next movement away from a location
     *
     * @param world providing details of the position on which the actor is currently located and much more.
     * @param location the location to flee from
     */
    public void flee(World world, Location location) {
        List<Location> list = determineNextMovementAway(world,location);
        if (list.isEmpty()) {
            wandering(world);
            return;
        }
        move(world, list);
    }

    /**
     * Determines the next movement away from a location for animal with burrows
     *
     * @param world    providing details of the position on which the actor is currently located and much more.
     * @param location the location to flee from
     * @param burrow   the burrow of an animal if it has one
     */
    public void flee(World world, Location location, Burrow burrow) {
        Location burrowL = world.getLocation(burrow);
        List<Location> listBurrow = determineNextMovement(world,burrowL);
        List<Location> list = determineNextMovementAway(world,location);
        // looks trough locations determining if it is viable to go to the burrow
        ArrayList<Location> priorityL = new ArrayList<>();
        for (Location loc : list) {
            for (Location locb : listBurrow) {
                if (loc.equals(locb)) {
                    priorityL.add(loc);
                }
            }
        }
        if (list.isEmpty()) {
            wandering(world);
            return;
        }
        //looks if a priority location exists
        if (!priorityL.isEmpty()) {
            move(world, priorityL);
        } else {
            move(world, list);
        }

    }

    /**
     * returns the distance in the x axis between the locations
     *
     * @param a location 1
     * @param b location 2
     * @return the distance in the x axis between the locations
     */
    public int getxLengthBetweenTiles(Location a, Location b) {
        if (a.getX() > b.getX())
            return a.getX() - b.getX();
        else
            return b.getX() - a.getX();

    }

    /**
     * returns the distance in the y axis between the locations
     *
     * @param a location 1
     * @param b location 2
     * @return the distance in the y axis between the locations
     */
    public int getyLengthBetweenTiles(Location a, Location b) {
        if (a.getY() > b.getY())
            return a.getY() - b.getY();
        else
            return b.getY() - a.getY();
    }

    /**
     * gives the length between location a and b
     *
     * @param a location 1
     * @param b location 2
     * @return the length between location a and b
     */
    public double getLengthBetweenTiles(Location a, Location b) {
        double length;
        int xLength = getxLengthBetweenTiles(a, b);
        int yLength = getyLengthBetweenTiles(a, b);
        if (xLength == 0) length = yLength;
        else if (yLength == 0) length = xLength;
        else {
            xLength = (int) Math.pow(xLength, 2);
            yLength = (int) Math.pow(yLength, 2);
            length = Math.sqrt(xLength + yLength);
        }
        return length;
    }

    /**
     * a function that return the closest location
     * @param world providing details of the position on which the actor is currently located and much more.
     * @param locations an arraylist of location you want the closest location of
     * @return the closest location
     */
    public Location getClosestLocation(World world, ArrayList<Location> locations) {
        if (locations == null) return null;
        Location closestLocation = null;
        Iterator<Location> it = locations.iterator();
        double currentLength = 1;
        Location l = world.getLocation(this);
        while (it.hasNext()) {
            Location location = it.next();
            double newLength = getLengthBetweenTiles(location, l);
            if (closestLocation == null) {
                closestLocation = location;
                currentLength = newLength;
                continue;
            }
            if (newLength < currentLength) {
                closestLocation = location;
                currentLength = newLength;
            }
        }
        return closestLocation;
    }

    /**
     * returns locations of all animals given in arraylist with a distances
     * determined by the searchRadius variable which all animals have
     *
     * @param world   providing details of the position on which the actor is currently located and much more.
     * @param animals arraylist of the animal types you are searching for
     * @param <T>     things that extends animal
     * @return locations of all animals searched for
     */
    public <T extends Animal> ArrayList<Location> searchForAnimals(World world, ArrayList<T> animals) {
        if (animals.isEmpty() || world.getLocation(this) == null) return null;
        ArrayList<Location> list = new ArrayList<>(world.getSurroundingTiles(world.getLocation(this), searchRadius));
        ArrayList<Location> locs = new ArrayList<>();
        for(Location l : list){
            if(world.isTileEmpty(l)) continue;
            for(T a : animals){
                if (world.getTile(l).getClass().isInstance(a)) {
                    locs.add(l);
                }
            }
        }
        return locs;
    }


    /**
     * Creatures age and loose a maximum energy for every time it ages.
     * At one point it dies when its age can no longer sustain itself.
     *
     * @param world providing details of the position on which the actor is currently located and much more.
     * @throws DeathException throws an exception to intervene nested checks.
     */
    public void age(World world) throws DeathException {
        this.age += 1;
        this.maxEnergy -= 5;
        checkAge(world);
    }

    /**
     * Checks the age of the animal and kills it if has died of old age.
     * @param world providing details of the position on which the actor is currently located and much more.
     * @throws DeathException to stop the animal from doing anything and ends its processes in
     */
    public void checkAge(World world) throws DeathException {
        if (this.age >= this.ageMax) {
            die(world);
        }
    }

    /**
     * Get the age of this animal.
     * @return int of age.
     */
    public boolean isBaby(){
        return isBaby;
    }

    /**
     * Consumes an amount of energy from a food instance, refilling its own energy.
     *
     * @param food The food the rabbit is trying to eat.
     */
    public void eat(Food food) {
        energy += food.eat(12);
        if (energy > maxEnergy) energy = maxEnergy;
    }

    public void takeDamage(int damageTaken){
        health -= damageTaken;
    }

    /**
     * Checks if the current tile contains Nonblocking entities.
     * Then runs the diggyHole() function.
     *
     * @param world providing details of the position on which the actor is currently located and much more.
     */
    public void digBurrow(World world) {
        Location l = world.getLocation(this);
        if (world.containsNonBlocking(l)) {
            if (world.getNonBlocking(l) instanceof Grass) {
                world.delete(world.getNonBlocking(l));
            } else if (world.getNonBlocking(l) instanceof Burrow) {
                return;
            } else if (world.getNonBlocking(l) instanceof Carcass) {
                return;
            }else if (world.getNonBlocking(l) instanceof Mushroom) {
                return;
            }
        }
        diggyHole(world, l);
    }

    /**
     * Creates a hole of the right kind dependant on the type of animal that digs the burrow.
     * @param world providing details of the position on which the actor is currently located and much more.
     * @param l the location of the burrow.
     */
    public abstract void diggyHole(World world, Location l);
}