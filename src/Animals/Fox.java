package Animals;

import Dubious.DeathException;
import Dubious.Pack;
import itumulator.executable.DisplayInformation;
import itumulator.world.Location;
import itumulator.world.World;
import NonBlockables.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class Fox extends Predator {
    //variables
    Carcass carcass;
    FoxBurrow burrow;
    private final ArrayList<Animal> fleeFrom = new ArrayList<>();

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
        fleeFrom.add(new Bear(false));
        fleeFrom.add(new Wolf(false ,new Pack()));
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

        // This animal has two movement step per tick.
        for(int i = 0; i < 2; i++){
            if(!isInBurrow()) {
                doMove(world);
            }
        }
        if(burrow == null && world.isDay()){
            digBurrow(world);
        }
    }

    @Override
    public void doMove(World world) {
        if(world.getCurrentLocation() == null) return;
        ArrayList<Location> f = searchForAnimals(world, fleeFrom);
        if (!(f == null) && !f.isEmpty()) {
            Location closest = getClosestLocation(world, f);
            if (burrow != null) {
                flee(world, closest, burrow);
            } else {
                flee(world, closest);
            }
            if (world.containsNonBlocking(world.getLocation(this))) {
                if (world.getNonBlocking(world.getLocation(this)) instanceof FoxBurrow) {
                    assignBurrow((FoxBurrow) world.getNonBlocking(world.getLocation(this)));
                    enterHole(world);
                }
            }
            return;
        }

        try{
            // Check if it is standing on a foxBurrow
            if(world.getNonBlocking(world.getLocation(this)) instanceof FoxBurrow)
                assignBurrow((FoxBurrow) world.getNonBlocking(world.getLocation(this)));

        }catch(Exception e){
            // Keep going
        }
        if(isCarrying() && burrow != null){
            headTowards(world, world.getLocation(burrow));
            // Check if it can place its carcass
            double length = getLengthBetweenTiles(world.getLocation(this), world.getLocation(burrow));
            if(length < 2 && length >= 1){
                System.out.println(getLengthBetweenTiles(world.getLocation(this), world.getLocation(burrow)));
                placeCarcass(world);
            }
            return;
        }

        if(!isHunting() && world.isDay() && burrow != null){
            try{
                headTowards(world, world.getLocation(burrow));
                if(world.getLocation(burrow).equals(world.getLocation(this))){
                    enterHole(world);
                }
            }catch(Exception e){
                wandering(world);
            }
            return;
        }

        if(!isHunting()){
            try {
                ArrayList<Carcass> list = carcassAtBurrow(world);
                if(isBaby && getLengthBetweenTiles(world.getCurrentLocation(), world.getLocation(burrow)) >= 3){
                    headTowards(world,world.getLocation(burrow));
                }else if(isBaby && !list.isEmpty()){
                    chooseNextPrey(list.get(r.nextInt(list.size())));
                }else{
                    wandering(world);
                }
            }catch (Exception e){
                wandering(world);
            }

        }else{
            if(targetPrey == null)return;
            headTowards(world, world.getLocation(targetPrey));
            if(!isCarrying()){
                attemptAttack(world);
            }
        }
    }

    @Override
    public void age(World world) throws DeathException {
        super.age(world);
        if(age > 2)
            isBaby = false;
    }

    /**
     * Sets this foxes burrow to be equal to the parameter burrow.
     * Mainly used by the pack to assign it to all wolves in the pack.
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

    public ArrayList<Carcass> carcassAtBurrow(World world){
        ArrayList<Location> list = new ArrayList<>(world.getSurroundingTiles(world.getLocation(burrow),2));
        ArrayList<Carcass> locs = new ArrayList<>(); // Create empty list for additions
        for(Location l : list){
            // Does this contain a NonBlocking object? No? Move to next location then.
            if(!world.containsNonBlocking(l)) continue;
            // Checks if the NonBlocking object is a Carcass. If yes, add it to the list.
            if (world.getNonBlocking(l) instanceof Carcass) {
                locs.add((Carcass) world.getNonBlocking(l));
            }
        }
        return locs;
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
        System.out.println(this + " has has pounced on " + caughtRabbit);
    }
    /**
     * Creates a Carcass on the fox that it can then place where ever.
     * @param rabbit The rabbit that has been killed.
     */
    public void carryCarcass(Rabbit rabbit){
        carcass = new Carcass(rabbit.isInfected, rabbit.getEnergy());

    }

    /**
     * Places the Carcass that the fox is carrying on the ground.
     * @param world Providing details of the position on which the actor is currently located and much more.
     */
    public void placeCarcass(World world){
        if(carcass == null) return;
        try{
            Object nonBlocking = world.getNonBlocking(world.getCurrentLocation());
            if(nonBlocking instanceof Burrow || nonBlocking instanceof BerryBush)
                return;
            if(nonBlocking instanceof Carcass){
                ((Carcass)nonBlocking).addEnergy(carcass.getEnergy());
                carcass = null;
                return;
            }
            world.delete(nonBlocking);
        }catch(Exception e){
            // There was no nonblocking element
        }
        world.setTile(world.getCurrentLocation(), carcass);
        System.out.println(this + " has placed " + carcass);
        chooseNextPrey(carcass);
        carcass = null;
    }

    /**
     * If this fox is running around with a carcass, then true.
     * @return boolean of true when it has a carcass, false when it doesn't have a carcass.
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
        Set<Object> objs = world.getEntities().keySet();
        double foxCounter = 0.0;
        for(Object obj : objs){
            if(obj instanceof Fox) foxCounter += 1.0;
        }
        if(3.0 / foxCounter < r.nextDouble() * 2.0) return;
        if(isInBurrow() && burrow != null){
            Random r = new Random();
            ArrayList<Animal> rabbits = burrow.getAnimals();
            rabbits.remove(this);
            if(!rabbits.isEmpty()){
                reproduce(world, rabbits.get(r.nextInt(rabbits.size())));
            }
        }
    }

    @Override
    public void reproduce(World world, Animal animal) {
        if(energy < 90 || isBaby || animal.getEnergy() < 90 || animal.isBaby) return;
        Fox fox = (Fox) this.createNewSelf();
        world.add(fox);
        fox.assignBurrow(burrow);
        this.burrow.addToList(this);
        burrow.enter(fox);
        energy -= 90;
        animal.addEnergy(-90);
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
        world.setTile(l, new FoxBurrow());
        assignBurrow((FoxBurrow) world.getNonBlocking(world.getLocation(this)));
    }

    @Override
    public void chooseNextTarget(World world) {
        ArrayList<RabbitBurrow> burrows = new ArrayList<>();
        for(Object obj : world.getEntities().keySet()){
            try{
                if(world.getLocation(obj) == null)
                    continue;
            }catch(Exception e){
                continue;
            }
            if(obj instanceof RabbitBurrow)
                burrows.add((RabbitBurrow)obj);
        }
        if(burrows.isEmpty())return;
        targetPrey = burrows.get(r.nextInt(burrows.size()));
        System.out.println(this + " has found and is hunting " + targetPrey);
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
