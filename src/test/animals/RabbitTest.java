package test.animals;

import Animals.Rabbit;
import Dubious.DeathException;
import NonBlockables.Grass;
import NonBlockables.RabbitBurrow;
import itumulator.world.Location;
import org.junit.jupiter.api.Test;
import test.testSuper;

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
        assertNotEquals(80,r.getEnergy());
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
    void rabbitCanDigBurrow(){
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
        world.setTile(l, r);
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