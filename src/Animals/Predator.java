package Animals;

import Dubious.DeathException;
import NonBlockables.Food;
import NonBlockables.Carcass;
import itumulator.world.*;
import java.util.ArrayList;

public abstract class Predator extends Animal {

    protected Object targetPrey = null;
    protected int power;

    public Predator(boolean isInfected){
        super(isInfected);
    }

    /**
     * The method called whenever the actor needs to simulate actions.
     * @param world providing details of the position on which the actor is currently located and much more.
     */
    @Override
    public void act(World world) throws DeathException {
        super.act(world);

        try{
            if(world.getLocation(targetPrey) == null)
                targetPrey = null;
        }catch(Exception e){
            targetPrey = null;
        }

        if(isHungry() && !isHunting()){
            Carcass carcass = getAnyCarcass(world);
            if(carcass == null) {
                chooseNextTarget(world);
            }
            else{
                chooseNextPrey(carcass);
            }
        }
    }

    /**
     * Defines how at base a predator moves. Meant to be overwritten.
     * @param world Providing details of the position on which the actor is currently located and much more.
     */
    public void doMove(World world){
        if(!isHunting()){
            wandering(world);
        }
        else{
            try{
                headTowards(world, world.getLocation(targetPrey));
                attemptAttack(world);
            }catch (Exception e){
                System.out.println(e);
                return;
            }
        }
    }

    /**
     * Checks if the targeted thing is near it. If yes, then attack.
     * @param world providing details of the position on which the actor is currently located and much more.
     */
    public void attemptAttack(World world){
        if(!isHunting())return;
        Location[] surroundingTiles = world.getSurroundingTiles(world.getLocation(this)).toArray(new Location[0]);
        for(Location l : surroundingTiles){
            try{
                if(world.getTile(l).equals(targetPrey))attack();
            }catch(Exception e){
                return;
            }

        }
    }

    /**
     * Either deals damage to an animal or eats from a food source.
     */
    public void attack(){
        if(targetPrey instanceof Food)
            eat((Food)targetPrey);
        if(targetPrey instanceof Animal)
            ((Animal)targetPrey).takeDamage(power);

        // System.out.println(this + " attacked " + targetPrey);
    }

    /**
     * Method to find the next target in the world, determined by the individual animal.
     * @param world providing details of the position on which the actor is currently located and much more.
     */
    public abstract void chooseNextTarget(World world);

    /**
     * Assigns what object to prey on.
     * @param edible the thing to prey on.
     */
    public void chooseNextPrey(Object edible){
        targetPrey = edible;
    }

    /**
     * Gets any carcass in the world and returns that carcass.
     * @param world providing details of the position on which the actor is currently located and much more.
     * @return a carcass object
     */
    public Carcass getAnyCarcass(World world){
        Object[] list = world.getEntities().keySet().toArray(new Object[0]);
        ArrayList<Carcass> carcasses = new ArrayList<>();
        for(Object thing : list){
            if(thing instanceof Carcass)
                carcasses.add((Carcass)thing);
        }
        if(carcasses.isEmpty())
            return null;
        return carcasses.get(r.nextInt(carcasses.size()));
    }

    /**
     * Determines if an animal is in the middle of hunting.
     * @return boolean is true if it is hunting.
     */
    public boolean isHunting(){
        return (targetPrey != null);
    }

}
