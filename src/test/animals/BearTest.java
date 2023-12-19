package test.animals;

import Animals.*;
import Dubious.DeathException;
import Dubious.Pack;
import NonBlockables.BerryBush;
import NonBlockables.Food;
import itumulator.world.Location;
import org.junit.jupiter.api.Test;
import test.testSuper;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BearTest extends testSuper {

    @Test
    void bearsHuntAndEatTest(){
        Location l1 = new Location(2,2);
        Location l2 = new Location(4,4);
        Bear bear = new Bear(false);
        Rabbit rabbit = new Rabbit(false);
        world.setTile(l1, bear);
        world.setTile(l2, rabbit);
        bear.chooseNextPrey(rabbit);
        assertTrue(bear.isHunting()); // Proves that it is hunting an animal
        bear.attack(); // Deal 8, killing it
        try{
            rabbit.die(world); // Simulate death during act
        }catch(DeathException e){
            // It is very dead now
            bear.chooseNextPrey(null);
        }
        assertFalse(bear.isHunting()); // Proves that the Bear is done hunting
        bear.chooseNextPrey(bear.getAnyCarcass(world)); // Simulate find a carcass
        assertTrue(bear.isHunting());
        bear.addEnergy(-50); // Remove energy to prove that eating restores energy
        assertEquals(550, bear.getEnergy());
        bear.attack();
        assertTrue(bear.getEnergy() > 550);
    }

    @Test
    void bearAlsoEastsBerries(){
        Location l1 = new Location(2,2);
        Location l2 = new Location(4,4);
        Bear bear = new Bear(false);
        BerryBush berryBush = new BerryBush();
        world.setTile(l1, bear);
        world.setTile(l2, berryBush);
        berryBush.grow();
        bear.chooseNextTarget(world);
        assertEquals(berryBush,bear.getTargetPrey());
        BerryBush b = (BerryBush) bear.getTargetPrey();
        bear.addEnergy(-50); // Remove energy to prove that eating restores energy
        assertEquals(550, bear.getEnergy());
        bear.attack();
        assertTrue(bear.getEnergy() > 550);
    }

    @Test
    void bearEatenByeEnoughWolfs(){
        Pack pack = new Pack();
        Bear bear = new Bear(false);
        world.setTile(new Location(4,4), bear);
        Wolf wolf1 = new Wolf(false, pack), wolf2 = new Wolf(false, pack);
        world.setTile(new Location(0,0), wolf1);
        world.setTile(new Location(0,1), wolf2);
        wolf1.chooseNextTarget(world);
        assertNotEquals(bear , wolf1.getTargetPrey());
        Wolf wolf3 = new Wolf(false, pack);
        world.setTile(new Location(2,0), wolf3);
        wolf1.chooseNextTarget(world);
        assertEquals(bear,wolf1.getTargetPrey());
        assertEquals(1,bearAmount());
        wolf1.attack();
        wolf2.attack();
        wolf3.attack();
        try{
            bear.die(world); // Simulate death during act
        }catch(DeathException e){
            // It is very dead now
            wolf1.chooseNextPrey(null);
        }
        assertEquals(0,bearAmount());
    }




    @Test
    void rarelyLeavesTerritoryTest(){
        Bear bear = new Bear(false);
        world.setTile(new Location(4,4), bear);
        for(int i=0; i<30;i++){
            bear.act(world);
            assertTrue(outOfTerritory(bear));
        }
    }

    boolean outOfTerritory(Bear bear){
        double lengthToCenter = bear.getLengthBetweenTiles(bear.getTerritoryCenter(),world.getLocation(bear));
        return lengthToCenter < 4.3;
    }


    int bearAmount(){
        Object[] list = world.getEntities().keySet().toArray(new Object[0]);
        ArrayList<Bear> arrList = new ArrayList<>();
        for(Object thing : list){
            if(thing instanceof Bear)
                arrList.add((Bear)thing);
        }
        return arrList.size();
    }

}