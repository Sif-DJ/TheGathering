package Dubious;

import Animals.*;
import NonBlockables.WolfBurrow;
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
    public void remove(Wolf wolf){ list.remove(wolf); }

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

    public void assignPrey(Object eatable){
        System.out.println(this + " has found and is hunting " + eatable);
        targetPrey = eatable;
        for(Wolf wolf : list){
            wolf.assignPrey(eatable);
        }
    }

    public void assignBurrow(WolfBurrow burrow) {
        for(Wolf wolf : list) {
            wolf.assignBurrow(burrow);
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
        return new Location((int) averageX,(int) averageY);
    }

    public ArrayList<Wolf> getPack(){
        return list;
    }

}
