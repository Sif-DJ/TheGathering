package test.NonBlockables;

import Animals.Animal;
import Animals.Rabbit;
import NonBlockables.Grass;
import itumulator.executable.Program;
import itumulator.world.Location;
import itumulator.world.World;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import test.testSuper;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class GrassTest extends testSuper {

    @Test
    void spreadTest() {
        Location l = new Location(r.nextInt(worldSize), r.nextInt(worldSize));
        Grass grass = new Grass();
        world.setTile(l, grass);
        assertEquals(1,GrassAmount());
        grass.spread(world);
        assertEquals(2,GrassAmount());

    }

    @Test
    void animalOnGrassTest(){
        Location l = new Location(r.nextInt(worldSize), r.nextInt(worldSize));
        Rabbit r = new Rabbit(false);
        world.setTile(l, new Grass());
        world.setTile(l, r);
        assertTrue(standingOnGrass(r));
    }

    int GrassAmount(){
        Object[] list = world.getEntities().keySet().toArray(new Object[0]);
        ArrayList<Grass> grassArrayList = new ArrayList<>();
        for(Object thing : list){
            if(thing instanceof Grass)
                grassArrayList.add((Grass)thing);
        }
        return grassArrayList.size();
    }

    boolean standingOnGrass(Animal animal){
        if (world.containsNonBlocking(world.getLocation(animal))) {
            return world.getNonBlocking(world.getLocation(animal)) instanceof Grass;
        }
        return false;
    }
}