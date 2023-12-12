package Animals;

import Dubious.DeathException;
import itumulator.executable.DisplayInformation;
import itumulator.world.Location;
import itumulator.world.World;
import NonBlockables.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class Fox extends Predator {
    //variable
    Carcass carcass;
    FoxBurrow burrow;


    /**
     * The Fox constructor
     * @param isInfected A boolean that determines if the Fox is infected by spores of mushrooms.
     */
    public Fox(boolean isInfected) {
        super(isInfected);
    }

    /**
     * The method called whenever the actor needs to simulate its actions.
     * @param world Providing details of the position on which the actor is currently located and much more.
     */
    @Override
    public void act(World world) throws DeathException {
        super.act(world);
        if(world.isNight()){

        }
    }

    /**
     * Creates a Carcass on the fox that it can then place where ever.
     * @param rabbit The rabbit that has been killed.
     */
    public void carryCarcass(Rabbit rabbit){
        carcass = new Carcass(rabbit.isInfected,rabbit.getEnergy());

    }

    /**
     * Places the Carcass that the fox is carrying on the ground.
     * @param world Providing details of the position on which the actor is currently located and much more.
     */
    public void placeCarcass(World world){
        if(carcass == null) return;
        world.setTile(world.getCurrentLocation(), carcass);
        carcass = null;
    }

    /**
     * If this fox is running around with a carcass, then true.
     * @return boolean of true when have, false when it doesn't have.
     */
    public boolean isCarrying(){
        return (carcass != null);
    }

    /**
     * Defines how a Fox reproduces.
     * @param world Providing details of the position on which the actor is currently located and much more.
     */
    @Override
    public void tryReproduce(World world) {

    }

    /**
     * Creates a new instance of its type of animal.
     * Mainly used in the reproduce() function.
     * @return returns a new instance of itself.
     */
    @Override
    public Animal createNewSelf() {
        return new Fox(false);
    }

    /**
     * Digs a burrow to place in the world and assigns itself to it.
     * @param world providing details of the position on which the actor is currently located and much more.
     * @param l the location of the burrow.
     */
    @Override
    public void diggyHole(World world, Location l) {

    }

    @Override
    public void chooseNextTarget(World world) {

    }

    /**
     *
     */
    @Override
    public void attack() {

    }

    /**
     * Is used to save a burrow in the foxes storage.
     * @param burrow
     */
    public void assignHole(FoxBurrow burrow){
        this.burrow = burrow;
    }

    /**
     * Provides info on how this object should be displayed in game world.
     * @return DisplayInformation with its name and if it is infected.
     */
    @Override
    public DisplayInformation getInformation() {
        return new DisplayInformation(Color.gray,
                "fox"
                        +(isBaby ? "-small" : "")
                        +(carcass != null ? "-carrying" : ""));
    }
}
