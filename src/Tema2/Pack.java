package Tema2;

import Tema1.*;
import itumulator.world.*;

import java.util.ArrayList;
import java.util.Random;

public class Pack {
    protected ArrayList<Wolf> list;
    protected Object targetPrey;
    protected WolfBurrow packBurrow;
    private final Random r = new Random();
    
    public Pack(){
        list = new ArrayList<>();
    }

    public void add(Wolf wolf){
        list.add(wolf);
    }

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
            if(obj instanceof Wolf && !((Wolf)obj).pack.equals(this))
                edibleTargets.add((Wolf)obj);
        }
        if(edibleTargets.isEmpty()){
            return;
        }

        assignPrey(edibleTargets.get(r.nextInt(edibleTargets.size())));
    }

    public void assignPrey(Object eatable){
        System.out.println(this + " has found and is hunting " + eatable);
        targetPrey = eatable;
        for(Wolf wolf : list){
            wolf.targetPrey = eatable;
        }
    }

    public void digBurrow(World world) {
        Location l = world.getLocation(list.get(0));
        if(world.containsNonBlocking(l)){
            if(world.getNonBlocking(l) instanceof Grass) {
                world.delete(world.getNonBlocking(l));
            } else if (world.getNonBlocking(l) instanceof Burrow){
                return;
            } else if(world.getNonBlocking(l) instanceof Carcass){
                return;
            }
        }
        packBurrow = new WolfBurrow();
        world.setTile(l, packBurrow);
        assignBurrow(packBurrow);
    }

    public void assignBurrow(WolfBurrow burrow) {
        for(Wolf wolf : list) {
            wolf.burrow = burrow;
        }
    }


    public Location getPreyLocation(World world){
        return world.getLocation(targetPrey);
    }

    public Location getAverageLocation(World world){
        double totalX = 0;
        double totalY = 0;
        for (Wolf wolf : list ){
            totalX += world.getLocation(wolf).getX();
            totalY += world.getLocation(wolf).getY();
        }
        double averageX = totalX / list.size();
        double averageY = totalY / list.size();
        return null;
    }

}
