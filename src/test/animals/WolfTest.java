package test.animals;

import itumulator.world.Location;
import org.junit.jupiter.api.Test;
import test.testSuper;
import Animals.*;
import Dubious.*;
import NonBlockables.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class WolfTest extends testSuper {

    @Test
    void wolfDeathTest(){
        Location l = new Location(3,3);
        Wolf wolf = new Wolf(false, new Pack());
        world.setTile(l, wolf);
        assertEquals(1,wolfAmount());
        try{
            wolf.die(world);
        }catch(DeathException e){
            // This is here, because die() throws DeathException
        }
        assertEquals(0, wolfAmount());
    }

    @Test
    void wolfHuntPreyTest(){
        Location l1 = new Location(2,2);
        Location l2 = new Location(4,4);
        Wolf wolf = new Wolf(false, new Pack());
        Rabbit rabbit = new Rabbit(false);
        world.setTile(l1, wolf);
        world.setTile(l2, rabbit);
        wolf.chooseNextPrey(rabbit);
        assertTrue(wolf.isHunting()); // Proves that it is hunting an animal
        wolf.attack(); // Deal 5, not killing it
        wolf.attack(); // Deal 5, should die at next act call
        try{
            rabbit.die(world); // Simulate death during act
        }catch(DeathException e){
            // It is very dead now
            wolf.chooseNextPrey(null);
        }
        assertFalse(wolf.isHunting()); // Proves that the wolf is done hunting
        wolf.chooseNextPrey(wolf.getAnyCarcass(world)); // Simulate find a carcass
        assertTrue(wolf.isHunting());
        wolf.addEnergy(-50); // Remove energy to prove that eating restores energy
        assertEquals(350, wolf.getEnergy());
        wolf.attack();
        assertTrue(wolf.getEnergy() > 350);
    }

    @Test
    void isOfSamePackTest(){
        Pack pack = new Pack();
        for(int i = 0; i < 5; i++){
            world.add(new Wolf(false, pack));
        }
        for(Wolf wolf : pack.getPack()){
            assertEquals(pack, wolf.getPack());
        }
    }

    @Test
    void digBurrowAndReproduce(){
        Pack pack = new Pack();
        Wolf wolf1 = new Wolf(false, pack), wolf2 = new Wolf(false, pack);
        world.setTile(new Location(0,0), wolf1);
        world.setTile(new Location(0,1), wolf2);
        wolf1.digBurrow(world);
        assertNotNull(wolf1.getBurrow());
        assertNotNull(wolf2.getBurrow());
        wolf1.enterHole(world);
        wolf2.enterHole(world);
        assertEquals(2, wolfAmount());
        wolf1.reproduce(world, wolf2);
        assertEquals(3, wolfAmount());
    }

    @Test
    void wolfHuntOpponentWolf(){
        Wolf wolf1 = new Wolf(false, new Pack()), wolf2 = new Wolf(false, new Pack());
        world.setTile(new Location(0,2), wolf1);
        world.setTile(new Location(4,2), wolf2);
        assertNotEquals(wolf1.getPack(),wolf2.getPack());
        wolf1.chooseNextTarget(world);
        wolf2.chooseNextTarget(world);
        assertEquals(wolf1, wolf2.getTargetPrey()); // wolf2 is targeting wolf1, and will attack it
        assertEquals(wolf2, wolf1.getTargetPrey()); // wolf1 is targeting wolf2, and will attack it
    }

    int wolfAmount(){
        Object[] list = world.getEntities().keySet().toArray(new Object[0]);
        ArrayList<Wolf> arrList = new ArrayList<>();
        for(Object thing : list){
            if(thing instanceof Wolf)
                arrList.add((Wolf)thing);
        }
        return arrList.size();
    }

}