import itumulator.executable.*;
import itumulator.world.*;
import java.awt.*;
import java.util.*;
import Tema1.*;
import java.io.*;

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
        String[] input;
        try {
            input = readFile("t1-1a.txt");
        }catch(FileNotFoundException e){
            System.out.println(e.getMessage());
            return;
        }

        int size = Integer.parseInt(input[0]); // Get the integer from the first line

        int delay = 1000;
        int display_size = 800;

        // Setup objects
        Program p = new Program(size, display_size, delay);
        World world = p.getWorld();

        // Create entities for world with try-catch in case of faulty type submission.
        try{
            // Read all entities from file
            for(int i = 1; i < input.length; i++){
                System.out.println(input[i]);
                String type = input[i].split(" ")[0];
                int[] nums;
                String possibleRange = input[i].split(" ")[1];
                if(possibleRange.indexOf('-') > 0){
                    nums = new int[2];
                    nums[0] = Integer.parseInt(possibleRange.split("-")[0]);
                    nums[1] = Integer.parseInt(possibleRange.split("-")[1]);
                }else{
                    nums = new int[1];
                    nums[0] = Integer.parseInt(possibleRange);
                }

                if(type.equals("grass"))createEntities(world, new Grass(), nums); // Grass in random positions
                if(type.equals("rabbit"))createEntities(world, new Rabbit(), nums); // Rabbits in random positions
                if(type.equals("burrow"))createEntities(world, new Burrow(), nums); // Burrows in random positions

            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        // Setup DisplayInfo for individual classes
        p.setDisplayInformation(Rabbit.class, new DisplayInformation(Color.red, "rabbit-large", false));
        p.setDisplayInformation(Burrow.class, new DisplayInformation(Color.orange, "hole", false));
        p.setDisplayInformation(Grass.class, new DisplayInformation(Color.green, "grass", false));

        // Run simulation
        p.show();
        while (true) {
            p.simulate();
        }
    }

    /**
     * Creates a specified amount of objects in a world object. Currently registered entities include:
     * * Rabbit
     * * Burrow
     * * Grass
     * @param world The world to apply the entity to.
     * @param type Insert an empty of the object type you want multiple of.
     * @param amounts The amount of entities to be created. Only reads the first and second index as min and max.
     * @throws Exception If and only if an incompatible object was set as a parameter.
     */
    private static void createEntities(World world, Object type, int[] amounts) throws Exception{
        Random r = new Random();
        int size = world.getSize();
        Object entity;
        int amount;
        if(amounts.length > 1){
            amount = r.nextInt(amounts[1]-amounts[0])+amounts[0]; // Random(Max - Min) + Min
        }else{
            amount = amounts[0];
        }

        // Instantiate an amount of entities.
        for (int i = 0; i < amount; i++) {

            // Apply the right object type to entity
            if(type instanceof Rabbit){
                entity = new Rabbit();
            }else if(type instanceof Burrow){
                entity = new Burrow();
            }else if(type instanceof Grass){
                entity = new Grass();
            }else{
                throw new Exception("Cannot recognize entity");
            }

            Location l = new Location(r.nextInt(size), r.nextInt(size));
            if(entity instanceof NonBlocking){
                while (world.containsNonBlocking(l)) {
                    l = new Location(r.nextInt(size), r.nextInt(size));
                }
            }else{
                while (!world.isTileEmpty(l)) {
                    l = new Location(r.nextInt(size), r.nextInt(size));
                }
            }

            world.setTile(l, entity);
        }
    }

    /**
     * Reads files from directory "./data/fileName".
     * @param fileName Name of the file to be read.
     * @return String[] each line has own index.
     * @exception FileNotFoundException If the file does not exist.
     */
    private static String[] readFile(String fileName) throws FileNotFoundException {
        File file = new File("./data/"+fileName);
        Scanner s = new Scanner(file);
        ArrayList<String> list = new ArrayList<>();
        while(s.hasNextLine()){
            list.add(s.nextLine());
        }
        return list.toArray(new String[0]);
    }
}