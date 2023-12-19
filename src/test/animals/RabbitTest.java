package test.animals;

import Animals.*;
import Dubious.*;
import NonBlockables.*;
import itumulator.world.*;
import org.junit.jupiter.api.Test;
import test.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class RabbitTest extends testSuper {

    @Test
    void rabbitDeathTest(){
        Location l = new Location(r.nextInt(worldSize), r.nextInt(worldSize));
        Rabbit r = new Rabbit(false);
        world.setTile(l, r);
        assertEquals(1,rabbitAmount());
        try {
            r.die(world);
        }catch (DeathException e){
           //this exception is not relavent here but relavent in for other things
        }
        assertEquals(0,rabbitAmount());
    }
    @Test
    void ageLessEnergyTest(){
        Location l = new Location(r.nextInt(worldSize), r.nextInt(worldSize));
        Rabbit r = new Rabbit(false);
        world.setTile(l, r);
        assertEquals(100,r.getMaxEnergy());
        r.age(world);
        assertNotEquals(100,r.getMaxEnergy());
    }
    @Test
    void rabbitLosesEnergyIfItMoves(){
        Location l = new Location(r.nextInt(worldSize), r.nextInt(worldSize));
        Rabbit r = new Rabbit(false);
        world.setTile(l, r);
        assertEquals(100,r.getEnergy());
        r.wandering(world);
        assertNotEquals(100,r.getEnergy());
    }
    @Test
    void rabbitEatGrassAndGetsEnergy(){
        Location l = new Location(r.nextInt(worldSize), r.nextInt(worldSize));
        Rabbit r = new Rabbit(false);
        Grass g = new Grass();
        world.setTile(l, g);
        world.setTile(l, r);
        r.addEnergy(-20);
        assertEquals(80,r.getEnergy());
        r.eat(g);
        assertTrue(80 < r.getEnergy());
    }
    @Test
    void ifRabbitHasNoEnergyItDies(){
        Location l = new Location(r.nextInt(worldSize), r.nextInt(worldSize));
        Rabbit r = new Rabbit(false);
        world.setTile(l, r);
        assertEquals(1,rabbitAmount());
        r.addEnergy(-r.getEnergy());
        r.act(world);
        assertEquals(0,rabbitAmount());
    }
    @Test
    void rabbitCanDigBurrowTest(){
        Location l = new Location(r.nextInt(worldSize), r.nextInt(worldSize));
        Rabbit r = new Rabbit(false);
        world.setTile(l, r);
        assertEquals(0,rabbitBurrowAmount());
        r.digBurrow(world);
        assertEquals(1,rabbitBurrowAmount());
    }

    @Test
    void multipleRabbitInBurrowTest(){
        Location l = new Location(r.nextInt(worldSize), r.nextInt(worldSize));
        Rabbit r = new Rabbit(false);
        world.setTile(l, r);
        r.digBurrow(world);
        r.enterHole(world);
        Rabbit ra = new Rabbit(false);
        world.setTile(l, ra);
        ra.assignHole(world);
        ra.enterHole(world);
        RabbitBurrow burrow = (RabbitBurrow) world.getNonBlocking(l);
        assertEquals(2,burrow.getAnimals().size());
    }

    @Test
    void rabbitCanReproduceTest(){
        Location l = new Location(r.nextInt(worldSize), r.nextInt(worldSize));
        Rabbit r = new Rabbit(false);
        world.setTile(l, r);
        r.age(world); r.age(world); r.age(world);
        r.digBurrow(world);
        r.enterHole(world);
        Rabbit ra = new Rabbit(false);
        world.setTile(l, ra);
        ra.age(world); ra.age(world); ra.age(world);
        ra.assignHole(world);
        ra.enterHole(world);
        assertEquals(2,rabbitAmount());
        r.reproduce(world,ra);
        assertNotEquals(2,rabbitAmount());
    }

    @Test
    void rabbitsHaveOnlyOneBurrowTest(){
        Location l = new Location(r.nextInt(worldSize), r.nextInt(worldSize));
        Location l2 = null;
        while(l2 == null || l.equals(l2)){
            l2 = new Location(r.nextInt(worldSize), r.nextInt(worldSize));
        }
        Rabbit r = new Rabbit(false);
        RabbitBurrow rb1 = new RabbitBurrow();
        RabbitBurrow rb2 = new RabbitBurrow();
        world.setTile(l,r);
        world.setTile(l,rb1);
        world.setTile(l2,rb2);
        r.assignHole(world);
        assertEquals(rb1,r.getBurrow());
        r.assignHole(rb2);
        assertEquals(rb2,r.getBurrow());
        assertNotEquals(rb1,r.getBurrow());
    }

    @Test
    void rabbitGoHomeAtNightTest(){
        Location l = new Location(4,4);
        Rabbit r = new Rabbit(false);
        RabbitBurrow rb = new RabbitBurrow();
        world.setTile(l,rb);
        l = new Location(0,0);
        world.setTile(l,r);
        r.assignHole(rb);
        world.setNight();
        r.act(world);
        assertTrue(isAtCloserLocation(l,r));
    }


    @Test
    void rabbitFleesFromWolf(){
        Location l = new Location(3,3);
        Location l2 = new Location(2,2);
        Rabbit r = new Rabbit(false);
        world.setTile(l,r);
        world.setTile(l2,new Wolf(false,new Pack()));
        r.act(world);
        assertTrue(isAtFutherLocation(l,l2,r));
    }

    @Test
    void rabbitFleesFromBear(){
        Location l = new Location(3,3);
        Location l2 = new Location(2,2);
        Rabbit r = new Rabbit(false);
        world.setTile(l,r);
        world.setTile(l2,new Bear(false));
        r.act(world);
        assertTrue(isAtFutherLocation(l,l2,r));
    }


    boolean isAtCloserLocation(Location lastLocation, Rabbit r){
        double distanceFromBurrow1 = r.getLengthBetweenTiles(world.getLocation(r.getBurrow()),lastLocation);
        double distanceFromBurrow2 = r.getLengthBetweenTiles(world.getLocation(r.getBurrow()),world.getLocation(r));
        return distanceFromBurrow1 > distanceFromBurrow2;
    }

    boolean isAtFutherLocation(Location lastLocation,Location toFutherFrom, Rabbit r){
        double distanceFromBurrow1 = r.getLengthBetweenTiles(toFutherFrom, lastLocation);
        double distanceFromBurrow2 = r.getLengthBetweenTiles(toFutherFrom, world.getLocation(r));
        return distanceFromBurrow1 < distanceFromBurrow2;
    }

    int rabbitAmount(){
        Object[] list = world.getEntities().keySet().toArray(new Object[0]);
        ArrayList<Rabbit> grassArrayList = new ArrayList<>();
        for(Object thing : list){
            if(thing instanceof Rabbit)
                grassArrayList.add((Rabbit)thing);
        }
        return grassArrayList.size();
    }
    int rabbitBurrowAmount(){
        Object[] list = world.getEntities().keySet().toArray(new Object[0]);
        ArrayList<RabbitBurrow> grassArrayList = new ArrayList<>();
        for(Object thing : list){
            if(thing instanceof RabbitBurrow)
                grassArrayList.add((RabbitBurrow)thing);
        }
        return grassArrayList.size();
    }


}