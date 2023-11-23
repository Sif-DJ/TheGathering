package Tema1;

import itumulator.world.Location;
import itumulator.world.World;

import java.util.*;

public class Grass extends Food {
    //variables for grass
    int spreadchance = 5; // There is a 1:spreadchance chance of spreading.
    int grassLifeTimer = 8;
    Random r = new Random();
    //Grass konstrukter
    public Grass(){}
    //override function
    @Override
    public int eat(int amount) {
        return 0;
    }

    @Override
    public void act(World world) {
        grassLifeTimer--;
        if (grassLifeTimer <= 0){
            world.delete(this);
        }
        if(spreadchance == r.nextInt(spreadchance+1)){
            spread(world);
        }
    }
    @Override
    public void die(World world) {

    }
    //function for grass to spread
    public void spread(World world){
        Set<Location> neighbours  = world.getSurroundingTiles();
        List<Location> list = new ArrayList<>(neighbours);
        Iterator<Location> it = list.iterator();
        while (it.hasNext()) {
            Location tile = it.next();
            if (world.containsNonBlocking(tile))
                it.remove();
        }
        if(list.isEmpty()) return;
        Location l = list.get(r.nextInt(list.size()));
        world.setTile(l, new Grass());
    }
    public void spreadFunny(World world){
        Set<Location> neighbours  = world.getEmptySurroundingTiles();
        List<Location> list = new ArrayList<>(neighbours);
        if(list.isEmpty()) return;
        Location l = list.get(r.nextInt(list.size()));
        if(!world.isTileEmpty(l)) return;
        try{
            world.setTile(l, new Grass());
        }catch (Exception e){
            System.out.println(e.getMessage());
            world.setTile(l, new Rabbit());
        }

    }



}
