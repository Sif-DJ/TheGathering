import itumulator.executable.Program;
import itumulator.world.World;

public class Main {

    public static void main(String[] args) {
        // Setup variables
        int size = 15;
        int delay = 1000;
        int display_size = 800;

        // 
        Program p = new Program(size, display_size, delay);
        World world = p.getWorld();


    }
}