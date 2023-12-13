package Animals;

import Dubious.DeathException;
import itumulator.executable.DisplayInformation;
import itumulator.world.Location;
import itumulator.world.World;
import NonBlockables.*;
import java.awt.*;
import java.util.ArrayList;

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
        this.maxEnergy = 400;
        this.energy = maxEnergy;
        this.age = 0;
        this.ageMax = 20;
        this.power = 3;
        this.health = 10;
    }

    /**
     * The method called whenever the actor needs to simulate its actions.
     * @param world Providing details of the position on which the actor is currently located and much more.
     */
    @Override
    public void act(World world) throws DeathException {
        try{
            super.act(world);
        }catch(DeathException e){
            System.out.println(e);
            return;
        }

        if(!isInBurrow())
            doMove(world);
        if(burrow == null && world.isDay()){
            digBurrow(world);
        }
    }

    @Override
    public void doMove(World world) {
        try{
            if(world.getNonBlocking(world.getLocation(this)) instanceof FoxBurrow)
                assignBurrow((FoxBurrow) world.getNonBlocking(world.getLocation(this)));
        }catch(Exception e){
            // Keep going
        }

        if(world.isDay() && burrow != null){
            try{
                headTowards(world, world.getLocation(burrow));
                if(world.getLocation(burrow).equals(world.getLocation(this))){
                    enterHole(world);
                    return;
                }
            }catch(Exception e){
                wandering(world);
            }
        }

        if(!isHunting()){
            wandering(world);
        }else{
            if(targetPrey == null)return;
            headTowards(world, world.getLocation(targetPrey));
            attemptAttack(world);
        }
    }

    /**
     * Sets this foxes burrow to be equal to the parameter burrow.
     * Mainly used by the pack to assign it to all wolfs in the pack.
     * @param burrow of the WolfBurrow type.
     */
    public void assignBurrow(FoxBurrow burrow){
        this.burrow = burrow;
    }

    /**
     * Makes this fox enter the burrow
     * @param world Providing details of the position on which the actor is currently located and much more.
     */
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
     * This makes the fox take a random rabbit from the burrow provided and carry the carcass of that rabbit.
     * @param burrow The Rabbit burrow that the fox is hunting in;
     */
    public void pounce(RabbitBurrow burrow) throws DeathException{
        if(burrow.getAnimals().isEmpty()) return;
        ArrayList<Rabbit> rabbits = new ArrayList<>();
        for(Animal a : burrow.getAnimals()){rabbits.add((Rabbit)a);}
        Rabbit caughtRabbit = rabbits.get(r.nextInt(rabbits.size()));
        burrow.forceExit(caughtRabbit);
        carryCarcass(caughtRabbit);
        caughtRabbit.health = 0;
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
        ArrayList<Location> listOfBurrows = new ArrayList<>();
        for(Object obj : world.getEntities().keySet()){
            if(obj instanceof RabbitBurrow)
                listOfBurrows.add(world.getLocation(obj));
        }
        if(listOfBurrows.isEmpty())return;
        targetPrey = world.getLocation(getClosestLocation(world, listOfBurrows));
    }

    /**
     *
     */
    @Override
    public void attack() {
        if(targetPrey instanceof RabbitBurrow)
            pounce((RabbitBurrow)targetPrey);
        if(targetPrey instanceof Carcass)
            eat((Carcass)targetPrey);
    }

    /**
     * Check if this fox is in a burrow.
     * @return true if it is. Otherwise, false.
     */
    public boolean isInBurrow(){
        if(burrow == null)return false;
        return burrow.isInBurrow(this);
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
                        +(isCarrying() ? "-carrying" : ""));
    }
}
