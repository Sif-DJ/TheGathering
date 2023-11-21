import itumulator.executable.Program;
import itumulator.world.Location;
import itumulator.world.World;
import Tema1.*;

public class Main {

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

        // Run simulation
        p.show();
        while(true) {
            p.simulate();
        }
    }
}