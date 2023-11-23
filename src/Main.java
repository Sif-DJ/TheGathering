import itumulator.executable.Program;
import itumulator.world.Location;
import itumulator.world.World;
import itumulator.executable.*;
import java.awt.*;
import java.util.*;
import Tema1.*;

/**
 * Main class to contain all setup and main running.
 */
public class Main {

    /**
     * Main function to run the program.
     * Sets up relevant variables and objects to be run in the simulation.
     * @param args exists out of necessity, leave an empty array here.
     */
    public static void main(String[] args) {
        // Setup variables
        int size = 15;
        int delay = 1000;
        int display_size = 800;
        int amountOfGrass = 5;
        int amountOfRabbits = 0;
        int amountOfBurrows = 0;
        Random r = new Random();
        // Setup objects
        Program p = new Program(size, display_size, delay);
        World world = p.getWorld();

        // Create entities for world
        // creating grass in random pos
        for(int i = 0; i < amountOfGrass; i++){
            Location l = new Location(r.nextInt(size),r.nextInt(size));
            while(world.containsNonBlocking(l)) {
                l = new Location(r.nextInt(size),r.nextInt(size));
            }
            world.setTile(l, new Grass());
        }

        // creating rabbits in random positions
        for(int i = 0; i < amountOfRabbits; i++){
            Location l = new Location(r.nextInt(size),r.nextInt(size));
            while(world.containsNonBlocking(l)) {
                l = new Location(r.nextInt(size),r.nextInt(size));
            }
            world.setTile(l, new Rabbit());
        }

        // creating Burrows in random pos
        for(int i = 0; i < amountOfBurrows; i++){
            Location l = new Location(r.nextInt(size),r.nextInt(size));
            while(world.containsNonBlocking(l)) {
                l = new Location(r.nextInt(size),r.nextInt(size));
            }
            world.setTile(l, new Burrow());
        }
        // Setup DisplayInfo for individual classes
        p.setDisplayInformation(Rabbit.class, new DisplayInformation(Color.red, "rabbit-large", false));
        p.setDisplayInformation(Burrow.class, new DisplayInformation(Color.orange, "hole", false));
        p.setDisplayInformation(Grass.class, new DisplayInformation(Color.green, "grass", false));

        // Run simulation
        p.show();
        while(true) {
            p.simulate();
        }
    }
}