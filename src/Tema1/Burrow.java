package Tema1;

import itumulator.world.Location;
import itumulator.world.NonBlocking;
import itumulator.world.World;
import itumulator.simulator.*;

import java.util.ArrayList;

public class Burrow implements NonBlocking, Actor {

    // Rabbits in hole
    private ArrayList<Rabbit> rabbits;
    // RabbitHole konstruktere en liste af rabbits
    public Burrow(){rabbits = new ArrayList<>();}

    @Override
    public void act(World world){
        if(world.isDay() && world.isTileEmpty(world.getLocation(this))){
            exit(world);
        }
    }
    public void enter(Rabbit rabbit){
        this.rabbits.add(rabbit);
    }

    public void exit(World world){
        if(rabbits.isEmpty())return;
        world.setTile(world.getLocation(this), rabbits.get(0));
        rabbits.remove(0);
    }

    public boolean isRabbitInHole(Rabbit rabbit){
        return (rabbits.contains(rabbit));
    }
}
