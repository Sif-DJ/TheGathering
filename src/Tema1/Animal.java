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
    protected int health;

    @Override
    public void die(World world){
        Location l;
        try {
            l = world.getLocation(this);
        }catch(IllegalArgumentException e){
            return;
        }
        world.remove(this);
        if(world.containsNonBlocking(l)) {
            if (world.getNonBlocking(l) instanceof Grass) {
                world.delete(world.getNonBlocking(l));
            }else if (world.getNonBlocking(l) instanceof Burrow){
                return;
            }else if(world.getNonBlocking(l) instanceof Carcass){
                Carcass carcass = (Carcass) world.getNonBlocking(l);
                carcass.energy += this.energy + 10;
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

        if(energy <= 0){
            die(world);
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
        List<Location> list = new ArrayList<>(world.getSurroundingTiles(world.getCurrentLocation(),foodSearchRadius));
        Iterator<Location> it = list.iterator();
        while (it.hasNext()){
            Location l = it.next();
            if(!world.containsNonBlocking(world.getLocation(l))){
                it.remove();
            } else if(world.containsNonBlocking(world.getLocation(l))){
                int notFoodCount = 0;
                for(T f : food){
                    if(!f.getClass().isInstance(world.getNonBlocking(world.getLocation(this)))){
                        notFoodCount++;
                    }
                }
                if(notFoodCount >= food.size()){it.remove();}
                notFoodCount = 0;
            }
        }
        Location clossestFood = null;
        double currentLength = 1.0;
        Location l = world.getCurrentLocation();
        while (it.hasNext()){
            Location locationOfFood = it.next();
            int newFoodXLength;
            int newFoodYLength;
            double newLength = 1.0;
            if(locationOfFood.getX() > l.getX())
                newFoodXLength = locationOfFood.getX() - l.getX();
            else
                newFoodXLength = l.getX() - locationOfFood.getX();
            if(locationOfFood.getY() > l.getY())
                newFoodYLength = locationOfFood.getY() - l.getY();
            else
                newFoodYLength = l.getY() - locationOfFood.getY();
            if(newFoodXLength == 0) newLength = newFoodYLength;
            else if(newFoodYLength == 0) newLength = newFoodXLength;
            else{
                newFoodXLength = (int) Math.pow(newFoodXLength,2);
                newFoodYLength = (int) Math.pow(newFoodYLength,2);
                newLength = Math.sqrt(newFoodXLength+newFoodYLength);
            }
            if(clossestFood == null) {
                clossestFood = locationOfFood;
                currentLength = newLength;
                continue;
            }
            if(newLength < currentLength){
                clossestFood = locationOfFood;
                currentLength = newLength;
            }
        }
        return clossestFood;
    }

    /**
     *
     * @param world the world object
     * @param locationToReach the location the animal need to reach
     */
    public void determineNextMovement(World world, Location locationToReach){
        Location l = world.getCurrentLocation();
        if(locationToReach == l) return;
        int currentXLength;
        int currentYLength;
        if(locationToReach.getX() > l.getX())
            currentXLength = locationToReach.getX() - l.getX();
        else
            currentXLength = l.getX() - locationToReach.getX();
        if(locationToReach.getY() > l.getY())
            currentYLength = locationToReach.getY() - l.getY();
        else
            currentYLength = l.getY() - locationToReach.getY();

        List<Location> list = new ArrayList<>(world.getEmptySurroundingTiles());
        Iterator<Location> it = list.iterator();
        int tileXlength;
        int tileYlength;
        while (it.hasNext()) {
            Location tile = it.next();
            if(locationToReach.getX() > tile.getX())
                tileXlength = locationToReach.getX() - tile.getX();
            else
                tileXlength = tile.getX() - locationToReach.getX();
            if(locationToReach.getY() > tile.getY())
                tileYlength = locationToReach.getY() - tile.getY();
            else
                tileYlength = tile.getY() - locationToReach.getY();
            if(tileXlength > currentXLength || tileYlength > currentYLength){
                it.remove();
            }
        }
        if(list.isEmpty()){
            wandering(world);
            return;
        }
        Location nl = list.get(r.nextInt(list.size()));
        world.move(this, nl);
        energy -= 2;
    }

    /**
     * Default wandering for animals.
     * @param world the world object
     */
    public void wandering(World world){
        Set<Location> neighbours = world.getEmptySurroundingTiles();
        List<Location> list = new ArrayList<>(neighbours);
        if(list.isEmpty()) return;
        Location l = list.get(r.nextInt(list.size()));
        world.move(this,l);
        energy -= 2;
    }

    /**
     *
     * @param world the world object
     * @param location the location to flee from
     */
    public void flee(World world, Location location){

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
            throw new DeathException(this);
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
}
