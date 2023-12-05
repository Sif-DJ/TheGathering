import Tema1.*;
import Tema2.*;
import itumulator.executable.*;
import itumulator.world.*;
import java.awt.*;
import java.util.*;
import java.io.*;
import javax.swing.JOptionPane;

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
        // Reads all input files in ./data/* and creates dropdown to select.
        String fileInput;

        /*  This section is dedicated to loading any file in the ./data/ folder.
            It does this by first creating a list of all files in said folder.
            Then it creates a JOptionPane that has a dropdown with the aforementioned list that the user can select.
            This it saves to load during proper setup. */
        try {
            String[] allInputFiles = new File("./data/").list();
            fileInput = (String) JOptionPane.showInputDialog(
                    null, "Choose a file: ", "Choose file to load",
                    JOptionPane.QUESTION_MESSAGE, null, allInputFiles, allInputFiles[0]);
        } catch(NullPointerException e) {
            System.out.println("You have no files in the data folder.\nMake sure you have all files placed correctly.");
            return;
        }

        // Setup variables
        String[] input;
        try{
            input = readFile(fileInput);
        }catch(FileNotFoundException e){
            System.out.println(e.getMessage());
            return;
        }

        int size = Integer.parseInt(input[0]); // Get the integer from the first line of the
        int delay = 100;
        int display_size = 800;

        // Setup objects
        Program p = new Program(size, display_size, delay);
        World world = p.getWorld();

        // Create entities for world with try-catch in case of faulty type submission.
        try{
            // Read all entities from file
            for(int i = 1; i < input.length; i++){
                System.out.println(input[i]);
                String[] line = input[i].split(" ");

                String type;
                if(line[0].equals("cordyceps")){
                    type = line[1];
                    // Remove
                }
                else{
                    type = line[0];
                }

                if(line.length > 2){
                    String[] placeInfo = line[2].split(",");
                    placeInfo[0] = placeInfo[0].substring(1);
                    placeInfo[1] = placeInfo[1].substring(0,placeInfo[1].length() - 1);
                    System.out.println(placeInfo[0] + "," + placeInfo[1]);
                    Location l = new Location(
                            Integer.parseInt(placeInfo[0]),
                            Integer.parseInt(placeInfo[1])
                    );
                    if(type.equals("grass"))createEntityAtLocation(world, new Grass(), l);
                    if(type.equals("rabbit"))createEntityAtLocation(world, new Rabbit(), l);
                    if(type.equals("burrow"))createEntityAtLocation(world, new RabbitBurrow(), l);
                    if(type.equals("wolf"))createEntityAtLocation(world, new Wolf(new Pack()), l);
                    if(type.equals("bear"))createEntityAtLocation(world, new Bear(), l);
                    if(type.equals("berry"))createEntityAtLocation(world, new BerryBush(), l);
                } else {
                    int[] nums;
                    String possibleRange = line[1];
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
                    if(type.equals("burrow"))createEntities(world, new RabbitBurrow(), nums); // Burrows in random positions
                    if(type.equals("wolf"))createEntities(world, new Wolf(new Pack()), nums); // Wolfs in random positions
                    if(type.equals("bear"))createEntities(world, new Bear(), nums); // Bears in random positions
                    if(type.equals("berry"))createEntities(world, new BerryBush(), nums); // BerryBushes in random positions
                }

            }
        }catch(Exception e){
            System.out.println("Error in creation of new entity:");
            System.out.println(e.getMessage());
        }

        // Setup DisplayInfo for individual classes
        setDisplay(p);

        // Run simulation
        p.show();
        while(true){
            try {
                p.simulate();
            } catch (Exception e) {
                return;
            }
        }
    }

    /**
     * Sets the display information of the different classes
     * @param p the program object
     */
    public static void setDisplay(Program p) {
        p.setDisplayInformation(RabbitBurrow.class, new DisplayInformation(Color.orange, "hole-small", false));
        p.setDisplayInformation(WolfBurrow.class, new DisplayInformation(Color.orange, "hole", false));
    }

    /**
     * Creates a specified amount of objects in a world object.
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

        // Some animals are part pack
        Pack pack = new Pack();

        // Instantiate an amount of entities.
        for (int i = 0; i < amount; i++) {

            entity = instantiateCorrectEntity(type, pack);

            // Gets and saves a location depending on if the entity is Blocking or NonBlocking
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
     * Is designed to take in any object that is placeable within the world and returns a new object of that type.
     * @param entity the entity to create a new instance of.
     * @param pack if it has a pack, such as a wolfPack, then place here.
     * @return returns an object of the same type as the one input.
     * @throws Exception if the type could not be recognized. Don't wanna place anything that should exist.
     */
    public static Object instantiateCorrectEntity(Object entity, Pack pack) throws Exception{
        // Apply the right object type to entity
        if (entity instanceof Rabbit) {
            return new Rabbit();
        } else if (entity instanceof Burrow) {
            return new RabbitBurrow();
        } else if (entity instanceof Grass) {
            return new Grass();
        } else if (entity instanceof Wolf) {
            Wolf wolf = new Wolf(pack);
            pack.add(wolf);
            return wolf;
        } else if (entity instanceof Bear) {
            return new Bear();
        } else if (entity instanceof BerryBush) {
            return new BerryBush();
        } else{
            throw new Exception("Cannot recognize entity");
        }
    }

    /**
     * Creates an object in world at a specific location.
     * @param world The world the entity is in.
     * @param type The type of entity.
     * @param location The location to place the entity at.
     * @throws Exception If the location is already occupied by a Blocking entity.
     */
    public static void createEntityAtLocation(World world, Object type, Location location) throws Exception{
        if(!world.isTileEmpty(location))throw new Exception("Spot has been taken");
        Pack pack = new Pack();
        Object entity = instantiateCorrectEntity(type, pack);
        world.setTile(location, entity);
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