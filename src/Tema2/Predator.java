package Tema2;

import Tema1.*;
import itumulator.world.*;
import java.util.ArrayList;

public abstract class Predator extends Animal {

    protected Object targetPrey = null;
    protected int power;

    @Override
    public void act(World world) throws DeathException {
        super.act(world);

        try{
            if(world.getLocation(targetPrey) == null)
                targetPrey = null;
        }catch(Exception e){
            targetPrey = null;
        }

        if(isHungry() && targetPrey == null){
            Carcass carcass = getAnyCarcass(world);
            if(carcass == null) {
                chooseNextTarget(world);
            }
            else{
                chooseNextTarget(carcass);
            }
        }

        if(targetPrey == null){
            wandering(world);
        }
        else{
            determineNextMovement(world, world.getLocation(targetPrey));
            attemptAttack(world);
        }
    }

    /**
     * Checks if the targeted thing is near it. If yes, then attack.
     * @param world
     */
    public void attemptAttack(World world){
        if(targetPrey == null)return;
        Location[] surroundingTiles = world.getSurroundingTiles(world.getLocation(this)).toArray(new Location[0]);
        for(Location l : surroundingTiles){
            try{
                if(world.getTile(l).equals(targetPrey))attack(world);
            }catch(Exception e){
                return;
            }

        }
    }

    /**
     * Either deals damage to an animal or eats from a food source.
     */
    public void attack(World world){
        if(targetPrey instanceof Food)
            eat((Food)targetPrey);
        if(targetPrey instanceof Animal)
            ((Animal)targetPrey).takeDamage(power);

        System.out.println(this + " attacked " + targetPrey);
    }

    public abstract void chooseNextTarget(World world);

    /**
     * Assigns what to prey on.
     * @param edible the thing to prey on.
     */
    public void chooseNextTarget(Object edible){
        targetPrey = edible;
    }

    /**
     * Gets any carcass in the world and returns that carcess.
     * @param world to look in
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
}
