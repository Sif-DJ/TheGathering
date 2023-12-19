package NonBlockables;

import Animals.*;
import Dubious.*;
import itumulator.world.Location;
import org.junit.jupiter.api.Test;
import test.testSuper;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CarcassTest extends testSuper {

    @Test
    void animalDropCarcassTest(){
        Rabbit rabbit = new Rabbit(false);
        Location l = new Location(2,2);
        world.setTile(l, rabbit);
        assertEquals(0, getCarcassAmount());
        try{
            rabbit.die(world);
        }catch(DeathException e){
            // The rabbit is dead
        }
        assertEquals(1,getCarcassAmount());
    }

    @Test
    void animalEatCarcassTest(){
        Wolf wolf = new Wolf(false, new Pack());
        Rabbit rabbit = new Rabbit(false);
        Location l = new Location(2,2);
        world.setTile(l, rabbit);
        assertEquals(0, getCarcassAmount());
        try{
            rabbit.die(world);
        }catch(DeathException e){
            // The rabbit is dead
        }
        assertEquals(1,getCarcassAmount());
        wolf.chooseNextPrey(wolf.getAnyCarcass(world));
        wolf.addEnergy(-50);
        assertEquals(350, wolf.getEnergy());
        wolf.attack();
        assertTrue(wolf.getEnergy() > 350);
    }

    @Test
    void carcassRotTest(){
        Carcass carcass = new Carcass(false, 0);
        assertEquals(3, carcass.getRotTimer());
        carcass.rot();
        carcass.rot();
        carcass.rot();
        assertEquals(0, carcass.getRotTimer());
        try{
            carcass.die(world);
        }catch(DeathException e){
            // The carcass is dead
        }
        // Insert more here
    }

    int getCarcassAmount(){
        Object[] list = world.getEntities().keySet().toArray(new Object[0]);
        ArrayList<Carcass> arrList = new ArrayList<>();
        for(Object thing : list){
            if(thing instanceof Carcass)
                arrList.add((Carcass)thing);
        }
        return arrList.size();
    }

}