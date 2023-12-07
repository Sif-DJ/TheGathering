package Animals;

import Dubious.DeathException;
import Dubious.Pack;
import NonBlockables.WolfBurrow;
import itumulator.executable.DisplayInformation;
import itumulator.world.*;
import java.awt.*;

public class Wolf extends Predator {
    protected final Pack pack;
    protected WolfBurrow burrow;
    
    public Wolf(Pack pack){
        this.maxEnergy = 400;
        this.energy = maxEnergy;
        this.age = 0;
        this.pack = pack;
        this.ageMax = 15;
        this.power = 5;
        this.health = 15;
    }

    @Override
    public Animal createNewSelf() {
        return new Wolf(this.pack);
    }

    @Override
    public void act(World world){
        try{
            super.act(world);
        }catch (DeathException e){
            System.out.println(e.getMessage());
            return;
        }

        doMove(world);
        if(burrow == null && world.isNight()){
            digBurrow(world);
        }

    }

    @Override
    public void doMove(World world) {
        if(!isHunting()){
            Location packL = pack.getAverageLocation(world);
            if(getyLengthBetweenTiles(packL,world.getLocation(this)) > 3){
                headTowards(world, packL);
            }else if(world.isNight() && burrow != null){
                try{
                    headTowards(world, world.getLocation(burrow));
                }catch(Exception e){
                    wandering(world);
                }
            }else{
                wandering(world);
            }
        }
        else{
            headTowards(world, world.getLocation(targetPrey));
            attemptAttack(world);
        }
    }

    @Override
    public void tryReproduce(World world) {

    }

    @Override
    public void chooseNextTarget(World world) {
        pack.choosePrey(world);
    }

    @Override
    public void chooseNextTarget(Object edible) {
        pack.assignPrey(edible);
    }

    @Override
    public void diggyHole(World world, Location l){
        burrow = new WolfBurrow();
        world.setTile(l, burrow);
        pack.assignBurrow(burrow);
    }

    public void assignBurrow(WolfBurrow burrow){
        this.burrow = burrow;
    }

    public void enterHole(){

    }

    @Override
    public DisplayInformation getInformation() {
        return new DisplayInformation(Color.cyan, "wolf");
    }
}
