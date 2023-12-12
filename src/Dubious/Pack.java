package Dubious;

import Animals.*;
import NonBlockables.WolfBurrow;
import itumulator.world.*;

import java.util.ArrayList;
import java.util.Random;

public class Pack {
    //Variables
    protected ArrayList<Wolf> list;
    protected Object targetPrey;
    protected WolfBurrow packBurrow;
    private final Random r = new Random();

    /**
     * The constructor for Pack.
     */
    public Pack(){
        list = new ArrayList<>();
    }

    /**
     * Adds a specific wolf to the pack.
     * @param wolf the wolf to add to the pack.
     */
    public void add(Wolf wolf){
        list.add(wolf);
    }

    /**
     * Removes a specific wolf to the pack.
     * @param wolf the wolf to remove to the pack.
     */
    public void remove(Wolf wolf){ list.remove(wolf); }

    /**
     * Chooses a prey for the whole pack.
     * @param world providing details of the position on which the actor is currently located and much more.
     */
    public void choosePrey(World world){
        Object[] possibleTargets;
        try{
            possibleTargets = world.getEntities().keySet().toArray(new Object[0]);
        }catch (NullPointerException e){
            return;
        }

        ArrayList<Animal> edibleTargets = new ArrayList<>();
        for(Object obj : possibleTargets){
            try{
                if(world.getLocation(obj) == null)
                    continue;
            }catch(Exception e){
                continue;
            }
            if(obj instanceof Rabbit)
                edibleTargets.add((Rabbit)obj);
            if(obj instanceof Bear && list.size() >= 3)
                edibleTargets.add((Bear)obj);
            if(obj instanceof Wolf && !((Wolf)obj).getPack().equals(this))
                edibleTargets.add((Wolf)obj);
        }
        if(edibleTargets.isEmpty()){
            return;
        }

        assignPrey(edibleTargets.get(r.nextInt(edibleTargets.size())));
    }

    /**
     * Assigns the prey to the hole pack.
     * @param eatable the prey to be assigned.
     */
    public void assignPrey(Object eatable){
        System.out.println(this + " has found and is hunting " + eatable);
        targetPrey = eatable;
        for(Wolf wolf : list){
            wolf.assignPrey(eatable);
        }
    }

    /**
     * Assigns the burrow to the hole pack.
     * @param burrow the burrow to be assigned
     */
    public void assignBurrow(WolfBurrow burrow) {
        for(Wolf wolf : list) {
            wolf.assignBurrow(burrow);
        }
    }

    /**
     * Unassigns the pack burrow from all wolfs in pack.
     */
    public void unAssignBurrow() {
        for(Wolf wolf : list) {
            wolf.assignBurrow(null);
        }
    }

    /**
     * returns the location of the prey.
     * @param world providing details of the position on which the actor is currently located and much more.
     * @return the location of the prey.
     */
    public Location getPreyLocation(World world){
        return world.getLocation(targetPrey);
    }

    /**
     * Calculates the average location of the hole pack.
     * @param world providing details of the position on which the actor is currently located and much more.
     * @return the average location of the hole pack.
     */
    public Location getAverageLocation(World world){
        double totalX = 0;
        double totalY = 0;
        for (Wolf wolf : list ){
            totalX += world.getLocation(wolf).getX();
            totalY += world.getLocation(wolf).getY();
        }
        double averageX = totalX / list.size();
        double averageY = totalY / list.size();
        return new Location((int) averageX,(int) averageY);
    }

    /**
     * Returns the wolf in the pack.
     * @return the wolf in the pack.
     */
    public ArrayList<Wolf> getPack(){
        return list;
    }

}
