package Animals;

import Dubious.DeathException;
import Dubious.Pack;
import NonBlockables.WolfBurrow;
import itumulator.executable.DisplayInformation;
import itumulator.world.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class Wolf extends Predator {
    protected final Pack pack;
    protected WolfBurrow burrow;

    /**
     * The Wolf constructor.
     * @param isInfected A boolean that determines if the wolf is infected by spores of mushrooms.
     * @param pack An object created to help keep track of information across a pack of wolves.
     */
    public Wolf(boolean isInfected, Pack pack){
        super(isInfected);
        this.maxEnergy = 400;
        this.energy = maxEnergy;
        this.age = 0;
        this.pack = pack;
        this.ageMax = 25;
        this.power = 5;
        this.health = 15;
    }

    /**
     * Creates a new instance of its type of animal.
     * Mainly used in the reproduce() function.
     * @return returns a new instance of itself.
     */
    @Override
    public Animal createNewSelf() {
        return new Wolf(false, this.pack);
    }

    /**
     * Deletes the animal from the world.
     * @param world Providing details of the position on which the actor is currently located and much more.
     * @throws DeathException Throws an exception that stops parts of the program that could break, because the entity stopped existing.
     */
    @Override
    public void die(World world) throws DeathException{
        pack.remove(this);
        super.die(world);
    }

    /**
     * The method called whenever the actor needs to simulate its actions.
     * @param world Providing details of the position on which the actor is currently located and much more.
     */
    @Override
    public void act(World world){
        try{
            super.act(world);
        }catch (DeathException e){
            System.out.println(e.getMessage());
            return;
        }

        if(!isInBurrow())doMove(world);
        if(burrow == null && world.isNight()){
            digBurrow(world);
        }

    }

    /**
     * Defines how a wolf moves.
     * @param world Providing details of the position on which the actor is currently located and much more.
     */
    @Override
    public void doMove(World world) {
        if(!isHunting()){
            Location packL = pack.getAverageLocation(world);
            if(getyLengthBetweenTiles(packL,world.getLocation(this)) > 3){
                headTowards(world, packL);
            }else if(world.isNight() && burrow != null){
                try{
                    headTowards(world, world.getLocation(burrow));
                    if(world.getLocation(this).equals(world.getLocation(burrow)))
                        enterHole(world);
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
            }
        }
    }

    /**
     * Defines how a wolf reproduces. If there are a lot of wolves, then they have a low chance of reproducing.
     * @param world Providing details of the position on which the actor is currently located and much more.
     */
    @Override
    public void tryReproduce(World world) {
        if(!isInBurrow())return;
        Set<Object> objs = world.getEntities().keySet();
        double wolfCounter = 0.0;
        for(Object obj : objs){
            if(obj instanceof Wolf) wolfCounter += 1.0;
        }
        if(3.0 / wolfCounter < r.nextDouble() * 2.0) return;

        for(Animal wolf : burrow.getAnimals()){
            if(wolf.equals(this) || wolf.isBaby())continue;
            reproduce(world, wolf);
        }
    }

    /**
     * Creates a new wolf, if both parents are of acceptable quality.
     * @param world providing details of the position on which the actor is currently located and much more.
     * @param wolf the wolf animal to reproduce with.
     */
    @Override
    public void reproduce(World world, Animal wolf){
        if(this.getEnergy() < 40 || wolf.getEnergy() < 40)return;
        this.addEnergy(-40); wolf.addEnergy(-40);
        Wolf baby = (Wolf)this.createNewSelf();
        this.pack.add(baby);
        this.burrow.addToList(baby);
        this.burrow.enter(baby);
    }

    /**
     * Asks the pack object to find a new prey to hunt.
     * @param world Providing details of the position on which the actor is currently located and much more.
     */
    @Override
    public void chooseNextTarget(World world) {
        pack.choosePrey(world);
    }

    /**
     * Asks the pack object to assign the new prey to all the wolves in the pack.
     * @param edible The thing to prey on.
     */
    public void chooseNextPrey(Object edible) {
        pack.assignPrey(edible);
    }

    /**
     * Sets this wolf to target this object.
     * This includes, but is not limited to: Carcasses, Rabbits and Bears.
     * @param edible The thing to prey on.
     */
    public void assignPrey(Object edible){
        targetPrey = edible;
    }

    /**
     * Digs a burrow for the Wolfs and assigns it to all the wolfs in the pack, with the pack.assignBurrow function.
     * @param world Providing details of the position on which the actor is currently located and much more.
     * @param l the location of the burrow.
     */
    @Override
    public void diggyHole(World world, Location l){
        burrow = new WolfBurrow();
        world.setTile(l, burrow);
        pack.assignBurrow(burrow);
    }

    /**
     * Sets this wolfs burrow to be equal to the parameter burrow.
     * Mainly used by the pack to assign it to all wolfs in the pack.
     * @param burrow of the WolfBurrow type.
     */
    public void assignBurrow(WolfBurrow burrow){
        this.burrow = burrow;
    }

    /**
     * Gets the Pack object and returns it.
     * @return Pack
     */
    public Pack getPack(){
        return pack;
    }

    public void enterHole(World world){
        if(burrow.isBurrowFull()){
            burrow.unAssign(this);
            assignBurrow(null);
            return;
        }
        burrow.enter(this);
        world.remove(this);
        world.setCurrentLocation(world.getLocation(burrow));
    }

    /**
     * Checks if this animal is in the burrow
     * @return true if it is its burrow.
     */
    public boolean isInBurrow(){
        if(burrow == null)return false;
        return (burrow.isInBurrow(this));
    }

    /**
     * Provides info on how this object should be displayed in game world.
     * @return DisplayInformation with its name and if it is infected.
     */
    @Override
    public DisplayInformation getInformation() {
        return new DisplayInformation(Color.cyan,
                "wolf"
                        +(isInfected ? "-fungi" : ""));
    }
}
