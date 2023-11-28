package Tema1;

import itumulator.simulator.Actor;
import itumulator.world.Location;
import itumulator.world.World;

import java.util.*;

public abstract class Animal extends Organism {
    //variables for tings extending this class
    protected int age;
    protected int ageMax;
    protected int health;

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
     * @param world
     */
    public void reproduce(World world, Animal animal){
        if(energy < 50 || age < 2)return;
        Random r = new Random();
        int size = world.getSize();
        Animal entity = this.createNewSelf();
        ArrayList<Location> locations = new ArrayList<>(world.getEmptySurroundingTiles(world.getLocation(this)));
        Location l = locations.get(r.nextInt(locations.size()));
        world.setTile(l, entity);
        this.energy -= 50;
    }

    /**
     * Empty method for instantiating new animals on demand in this abstract class. Can mainly be seen in function reproduce().
     * @return
     */
    public abstract Animal createNewSelf();


    /**
     * This is here for the future, when hunting becomes relevant.
     * @param world the world object
     * @param food the food type this Animal can eat
     */
    public void searchForFood(World world, Class food){
        List<Location> list = new ArrayList<>(world.getSurroundingTiles());
        if(world.containsNonBlocking(world.getLocation(this))) {
            if (food.isInstance(world.getNonBlocking(world.getLocation(this)))) {
            }
        }
    }

    /**
     *
     * @param world
     * @param locationToReach
     */
    public void determineNextMovement(World world, Location locationToReach){
        Location l = world.getCurrentLocation();
        if(locationToReach == l) return;
        int currentXLength = 0;
        int currentYLength = 0;
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
        int tileXlength = 0;
        int tileYlength = 0;
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
     * @param world
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
     * @param world The world the rabbit is in.
     */
    public void eat(Food food, World world) {
        energy += food.eat(8);
        if(energy > maxEnergy) energy=maxEnergy;
    }

    /**
     * Method to determine if it is hungry.
     * @return boolean
     */
    public boolean isHungry(){
        return (energy < maxEnergy * 3.0 / 4.0);
    }


}
