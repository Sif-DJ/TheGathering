package Tema1;

import Tema2.*;
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

    @Override
    public void die(World world) throws DeathException{
        Location l;
        System.out.println(this + " is going to die");
        try {
            l = world.getLocation(this);
        }catch(IllegalArgumentException e){
            world.remove(this);
            super.die(world);
            return;
        }
        world.remove(this);
        if(world.containsNonBlocking(l)) {
            if (world.getNonBlocking(l) instanceof Grass) {
                world.delete(world.getNonBlocking(l));
            }
            if (world.getNonBlocking(l) instanceof Burrow){
                super.die(world);
                return;
            }
            if(world.getNonBlocking(l) instanceof Carcass){
                Carcass carcass = (Carcass) world.getNonBlocking(l);
                carcass.energy += this.energy + 10;
                super.die(world);
                return;
            }
        }
        world.setTile(l, new Carcass(energy));
        super.die(world);
    }

    @Override
    public void act(World world) throws DeathException{
        if(world.getCurrentTime() % 20 == 0)
            age(world);

        tryReproduce(world);

        if(energy <= 0 || health <= 0){
            die(world);
            // Crash occurs after here
        }
    }

    public abstract void tryReproduce(World world);

    /**
     * Makes a new animal when they reproduce, where the baby will be placed on a tile adjacent to the parents.
     * @param world the world object
     */
    public void reproduce(World world, Animal animal){
        if(energy < 50 || age < 2)return;
        Random r = new Random();
        Animal entity = this.createNewSelf();
        ArrayList<Location> locations = new ArrayList<>(world.getEmptySurroundingTiles(world.getLocation(this)));
        Location l = locations.get(r.nextInt(locations.size()));
        world.setTile(l, entity);
        this.energy -= 50;
    }

    /**
     * Empty method for instantiating new animals on demand in this abstract class. Can mainly be seen in function reproduce().
     * @return returns a copy of the animal
     */
    public abstract Animal createNewSelf();

    /**
     * Method to determine if it is hungry.
     * @return boolean
     */
    public boolean isHungry(){
        return (energy < maxEnergy * 3.0 / 4.0);
    }

    /**
     *  returns the location of the nearest food this animal can eat
     * @param world the world object
     * @param food an ArrayList of the Food types the animal can eat
     * @return the location of the nearest food this animal can eat
     */
    public <T extends Food> Location searchForFood(World world, ArrayList<T> food){
        if (food.isEmpty()) return null;
        ArrayList<Location> list = new ArrayList<>(world.getSurroundingTiles(world.getCurrentLocation(),foodSearchRadius));
        Iterator<Location> it = list.iterator();
        while (it.hasNext()){
            Location l = it.next();
            if(!world.containsNonBlocking(l)){
                it.remove();
            } else if(world.containsNonBlocking(l)){
                int notFoodCount = 0;
                for(T f : food){
                    if(!f.getClass().isInstance(world.getNonBlocking(world.getLocation(this)).getClass())){
                        notFoodCount++;
                    }
                }
                if(notFoodCount >= food.size()){it.remove();}
            }
        }
        return getClosestLocation(world, list);
    }


    public void move(World world,List<Location> list){
        Location l = list.get(r.nextInt(list.size()));
        world.move(this,l);
        energy -= 2;
    }

    /**
     * Determines the next movement towards a location
     * @param world the world object
     * @param locationToReach the location the animal need to reach
     */
    public void determineNextMovement(World world, Location locationToReach){
        Location l = world.getCurrentLocation();
        if(locationToReach == l) return;
        int currentxLength = getxLengthBetweenTiles(l,locationToReach);
        int currentyLength = getyLengthBetweenTiles(l, locationToReach);

        // Iterate through all surrounding empty tiles to determine possible move locations
        List<Location> list = new ArrayList<>(world.getEmptySurroundingTiles());
        Iterator<Location> it = list.iterator();
        int tilexLength;
        int tileyLength;
        while (it.hasNext()) {
            Location tile = it.next();
            tilexLength = getxLengthBetweenTiles(tile,locationToReach);
            tileyLength = getyLengthBetweenTiles(tile,locationToReach);
            if(tilexLength > currentxLength || tileyLength > currentyLength){
                it.remove();
            }
        }
        if(list.isEmpty()){
            wandering(world);
            return;
        }
        move(world,list);
    }

    /**
     * Default wandering for animals.
     * @param world the world object
     */
    public void wandering(World world){
        Set<Location> neighbours = world.getEmptySurroundingTiles();
        List<Location> list = new ArrayList<>(neighbours);
        if(list.isEmpty()) return;
        move(world,list);
    }

    /**
     *  Determines the next movement away from a location
     * @param world the world object
     * @param location the location to flee from
     */
    public void flee(World world, Location location){
        Location l = world.getCurrentLocation();
        int currentxLength = getxLengthBetweenTiles(l,location);
        int currentyLength = getyLengthBetweenTiles(l, location);
        // Iterate through all surrounding empty tiles to determine possible move
        // locations that are away form the location
        List<Location> list = new ArrayList<>(world.getEmptySurroundingTiles());
        Iterator<Location> it = list.iterator();
        int tilexLength;
        int tileyLength;
        while (it.hasNext()) {
            Location tile = it.next();
            tilexLength = getxLengthBetweenTiles(tile,location);
            tileyLength = getyLengthBetweenTiles(tile,location);
            if(currentxLength == 0 && tileyLength <= currentyLength){
                it.remove();
            } else if (currentyLength == 0 && tilexLength <= currentxLength) {
                it.remove();
            } else {
                if(tilexLength < currentxLength && tileyLength <= currentyLength){
                    it.remove();
                } else if (tilexLength <= currentxLength && tileyLength < currentyLength){
                    it.remove();
                }
            }
        }
        if(list.isEmpty()){
            wandering(world);
            return;
        }
        move(world,list);
    }

    /**
     * Determines the next movement away from a location for animal with burrows
     * @param world the world object
     * @param location the location to flee from
     * @param burrow the burrow of an animal if it has one
     */
    public void flee(World world, Location location, Burrow burrow){
        Location l = world.getCurrentLocation();
        Location burrowL = world.getLocation(burrow);
        int burrowxLength = getxLengthBetweenTiles(l, burrowL);
        int burrowyLength = getyLengthBetweenTiles(l, burrowL);
        // Iterate through all empty tiles to determine possible move locations that are closer to the burrow
        List<Location> listBurrow = new ArrayList<>(world.getEmptySurroundingTiles());
        Iterator<Location> itBurrow = listBurrow.iterator();
        int tilexLengthBurrow;
        int tileyLengthBurrow;
        while (itBurrow.hasNext()) {
            Location tile = itBurrow.next();
            tilexLengthBurrow = getxLengthBetweenTiles(tile,location);
            tileyLengthBurrow = getyLengthBetweenTiles(tile,location);
            if(tilexLengthBurrow > burrowxLength || tileyLengthBurrow > burrowyLength){
                itBurrow.remove();
            }
        }
        int currentxLength = getxLengthBetweenTiles(l,location);
        int currentyLength = getyLengthBetweenTiles(l, location);
        // Iterate through all surrounding empty tiles to determine possible move
        // locations that are away form the location
        List<Location> list = new ArrayList<>(world.getEmptySurroundingTiles());
        Iterator<Location> it = list.iterator();
        int tilexLength;
        int tileyLength;
        while (it.hasNext()) {
            Location tile = it.next();
            tilexLength = getxLengthBetweenTiles(tile,location);
            tileyLength = getyLengthBetweenTiles(tile,location);
            if(currentxLength == 0 && tileyLength <= currentyLength){
                it.remove();
            } else if (currentyLength == 0 && tilexLength <= currentxLength) {
                it.remove();
            } else {
                if(tilexLength < currentxLength && tileyLength <= currentyLength){
                    it.remove();
                } else if (tilexLength <= currentxLength && tileyLength < currentyLength){
                    it.remove();
                }
            }
        }
        // looks trough locations determining if it is viable to go to the burrow
        ArrayList<Location> priorityL = new ArrayList<>();
        for(Location loc : list){
            for(Location locb : listBurrow){
                if(loc.equals(locb)){
                    priorityL.add(loc);
                }
            }
        }
        if(list.isEmpty()){
            wandering(world);
            return;
        }
        //looks if a priority location exists
        if(!priorityL.isEmpty()){
            move(world,priorityL);
        }else{
            move(world,list);
        }

    }

    /**
     * returns the distance in the x axis between the locations
     * @param a location 1
     * @param b location 2
     * @return the distance in the x axis between the locations
     */
    public int getxLengthBetweenTiles(Location a,Location b){
        if(a.getX() > b.getX())
            return a.getX() - b.getX();
        else
            return  b.getX() - a.getX();

    }
    /**
     * returns the distance in the y axis between the locations
     * @param a location 1
     * @param b location 2
     * @return the distance in the y axis between the locations
     */
    public int getyLengthBetweenTiles(Location a,Location b){
        if(a.getY() > b.getY())
            return a.getY() - b.getY();
        else
            return b.getY() - a.getY();
    }
    /**
     * gives the length between location a and b
     * @param a location 1
     * @param b location 2
     * @return the length between location a and b
     */
    public double getLengthBetweenTiles(Location a, Location b){
        double length;
        int xLength = getxLengthBetweenTiles(a,b);
        int yLength = getyLengthBetweenTiles(a,b);
        if(xLength == 0) length = yLength;
        else if(yLength == 0) length = xLength;
        else{
            xLength = (int) Math.pow(xLength,2);
            yLength = (int) Math.pow(yLength,2);
            length = Math.sqrt(xLength+yLength);
        }
        return length;
    }

    public Location getClosestLocation(World world,ArrayList<Location> locations){
        if(locations == null) return null;
        Location closestLocation = null;
        Iterator<Location> it = locations.iterator();
        double currentLength = 1;
        Location l = world.getCurrentLocation();
        while (it.hasNext()){
            Location location = it.next();
            double newLength = getLengthBetweenTiles(location,l);
            if(closestLocation == null) {
                closestLocation = location;
                currentLength = newLength;
                continue;
            }
            if(newLength < currentLength){
                closestLocation = location;
                currentLength = newLength;
            }
        }
        return closestLocation;
    }

    /**
     *  returns locations of all animals given in arraylist with a distances
     *  determined by the searchRadius variable which all animals have
     * @param world the world object
     * @param animals arraylist of the animal types you are searching for
     * @return locations of all animals searched for
     * @param <T> things that extends animal
     */
    public <T extends Animal> ArrayList<Location> searchForAnimals(World world, ArrayList<T> animals){
        if (animals.isEmpty() || world.getLocation(this) == null) return null;
        ArrayList<Location> list = new ArrayList<>(world.getSurroundingTiles(world.getLocation(this),searchRadius));
        Iterator<Location> it = list.iterator();
        while (it.hasNext()){
            Location l = it.next();
            if(world.isTileEmpty(l)){
                it.remove();
            } else {
                int notAnimalSearchedFor = 0;
                for(T f : animals){
                    if(!(f.getClass().isInstance(world.getTile(l)))){
                        notAnimalSearchedFor++;

                    }
                }
                if(notAnimalSearchedFor >= animals.size()){it.remove();}
            }
        }
        return list;
    }

    /**
     * Creatures age and loose a maximum energy for every time it ages.
     * At one point it dies when its age can no longer sustain itself.
     * @param world the world object
     * @throws DeathException throws an exception to intervene nested checks.
     */
    public void age(World world) throws DeathException{
        this.age += 1;
        this.maxEnergy -= 5;
        if(this.age >= this.ageMax) {
            die(world);
        }
    }

    /**
     * consumes an amount of energy from a food instance, refilling its own energy.
     * @param food The food the rabbit is trying to eat.
     */
    public void eat(Food food) {
        energy += food.eat(8);
        if(energy > maxEnergy) energy = maxEnergy;
    }

    public void takeDamage(int damageTaken){
        health -= damageTaken;
    }
}
