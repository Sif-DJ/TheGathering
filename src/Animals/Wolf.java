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
        this.ageMax = 15;
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

        doMove(world);
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

    /**
     * Defines how a wolf reproduces.
     * @param world Providing details of the position on which the actor is currently located and much more.
     */
    @Override
    public void tryReproduce(World world) {

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
