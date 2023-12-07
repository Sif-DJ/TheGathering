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
    
    public Wolf(boolean isInfected, Pack pack){
        super(isInfected);
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
        return new Wolf(false, this.pack);
    }

    @Override
    public void die(World world) throws DeathException{
        pack.remove(this);
        super.die(world);
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
            try{
                headTowards(world, world.getLocation(targetPrey));
                attemptAttack(world);
            }catch (Exception e){
                System.out.println(e);
                return;
            }
        }
    }

    @Override
    public void tryReproduce(World world) {

    }

    @Override
    public void chooseNextTarget(World world) {
        pack.choosePrey(world);
    }

    public void chooseNextPrey(Object edible) {
        pack.assignPrey(edible);
    }

    public void assignPrey(Object edible){
        targetPrey = edible;
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

    public Pack getPack(){
        return pack;
    }

    public void enterHole(){

    }

    @Override
    public DisplayInformation getInformation() {
        return new DisplayInformation(Color.cyan,
                "wolf"
                        +(isInfected ? "-fungi" : ""));
    }
}
