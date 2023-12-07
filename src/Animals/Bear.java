package Animals;

import Dubious.DeathException;
import NonBlockables.Food;
import NonBlockables.BerryBush;
import itumulator.executable.DisplayInformation;
import itumulator.world.*;
import java.awt.*;
import java.util.ArrayList;

public class Bear extends Predator {

    private Location territoryCenter;
    private ArrayList<Location> territoryTiles;
    public Bear(boolean isInfected) {
        super(isInfected);
        this.maxEnergy = 600;
        this.energy = maxEnergy;
        this.age = 0;
        this.ageMax = 50;
        this.health = 30;
        this.power = 8;
    }

    @Override
    public void act(World world) throws DeathException {
        if(territoryCenter == null){
            territoryCenter = world.getLocation(this);
            territoryTiles = new ArrayList<>(world.getSurroundingTiles(territoryCenter,3));
        }
        try{
            super.act(world);
        }catch (DeathException e){
            System.out.println(e.getMessage());
            return;
        }

        if(isHunting()) {
            doMove(world);
            return;
        }


        for(int i = 0; i < territoryTiles.size();i++){
            if(world.getLocation(this).equals(territoryTiles.get(i))){
                break;
            }
            if(i == territoryTiles.size()-1){
                headTowards(world,territoryCenter);
                return;
            }
        }


        wandering(world);
    }

    @Override
    public void doMove(World world){
        try{
            headTowards(world, world.getLocation(targetPrey));
            attemptAttack(world);
        }catch (Exception e){
            System.out.println(e);
            return;
        }
    }

    @Override
    public void chooseNextTarget(World world) {
        Object[] possibleTargets;
        try{
            possibleTargets = world.getEntities().keySet().toArray(new Object[0]);
        }catch (NullPointerException e){
            System.out.println(e);
            return;
        }

        ArrayList<Food> foods = new ArrayList<>();
        foods.add(new BerryBush());
        Location l = getClosestLocation(world , searchForFood(world, foods, foodSearchRadius));
        if(l != null){
            targetPrey = world.getNonBlocking(l);
            System.out.println(this + " found a " + targetPrey);
            return;
        }

        ArrayList<Animal> edibleTargets = new ArrayList<>();
        System.out.println(this + " is trying to find a suitable target");
        for(Object obj : possibleTargets){
            try{
                if(world.getLocation(obj) == null)
                    continue;
            }catch(Exception e){
                continue;
            }
            if(obj instanceof Rabbit)
                edibleTargets.add((Rabbit)obj);
            if(obj instanceof Bear && !obj.equals(this))
                edibleTargets.add((Bear)obj);
            if(obj instanceof Wolf)
                edibleTargets.add((Wolf)obj);
        }
        if(edibleTargets.isEmpty()){
            System.out.println(this + " failed to find suitable prey");
            return;
        }

        targetPrey = edibleTargets.get(r.nextInt(edibleTargets.size()));
        System.out.println(this + " found and is hunting " + targetPrey);
    }

    @Override
    public void diggyHole(World world, Location l) {}

    @Override
    public void tryReproduce(World world) {

    }

    @Override
    public Animal createNewSelf() {
        return new Bear(false);
    }

    @Override
    public DisplayInformation getInformation() {
        return new DisplayInformation(Color.gray,
                "bear"
                        +(isInfected ? "-fungi" : ""));
    }
}