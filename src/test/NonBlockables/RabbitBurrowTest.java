package test.NonBlockables;

import Animals.*;
import NonBlockables.*;
import itumulator.world.Location;
import org.junit.jupiter.api.Test;
import test.testSuper;

import static org.junit.jupiter.api.Assertions.*;

class RabbitBurrowTest extends testSuper {

    @Test
    void animalOnBurrowTest(){
        Location l = new Location(r.nextInt(worldSize), r.nextInt(worldSize));
        Rabbit r = new Rabbit(false);
        world.setTile(l, new RabbitBurrow());
        world.setTile(l, r);
        assertTrue(standingOnBurrow(r)); // Proves that an animal can be on a burrow, any burrow even
    }

    boolean standingOnBurrow(Animal animal){
        if (world.containsNonBlocking(world.getLocation(animal))) {
            return world.getNonBlocking(world.getLocation(animal)) instanceof Burrow;
        }
        return false;
    }

}