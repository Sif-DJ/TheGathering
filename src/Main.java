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
     */
    public static void main(String[] args) {
        // Setup variables
        int size = 15;
        int delay = 1000;
        int display_size = 800;

        // Setup objects
        Program p = new Program(size, display_size, delay);
        World world = p.getWorld();

        // Create entities for world
        world.setTile(new Location(0,0), new Rabbit());

        // Setup DisplayInfo for individual classes
        p.setDisplayInformation(Rabbit.class, new DisplayInformation(Color.red, "rabbit-large", false));
        p.setDisplayInformation(RabbitHole.class, new DisplayInformation(Color.orange, "hole", false));
        p.setDisplayInformation(Grass.class, new DisplayInformation(Color.green, "grass", false));

        // Run simulation
        p.show();
        while(true) {
            p.simulate();
        }
    }
}