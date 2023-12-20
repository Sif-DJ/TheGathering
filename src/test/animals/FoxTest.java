package test.animals;

import Animals.Bear;
import Animals.Fox;
import Animals.Rabbit;
import Animals.Wolf;
import Dubious.Pack;
import NonBlockables.Carcass;
import NonBlockables.FoxBurrow;
import NonBlockables.RabbitBurrow;
import itumulator.world.Location;
import itumulator.world.World;
import org.junit.jupiter.api.Test;
import test.testSuper;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FoxTest extends testSuper {


    @Test
    void foxSleepInTheDay(){
        Fox f = new Fox(false);
        world.setTile(new Location(4,4),f);
        for(int i = 0 ; i < 9 ; i++){
            p.simulate();
        }
        assertTrue(f.isInBurrow());
    }


    @Test
    void foxHuntsRabbitInThereBurrow(){
        Rabbit r = new Rabbit(false);
        RabbitBurrow rb = new RabbitBurrow();
        Fox f = new Fox(false);
        world.setTile(new Location(1,1),rb);
        world.setTile(new Location(1,1),r);
        r.assignHole(rb);
        r.enterHole(world);
        world.setNight();
        world.setTile(new Location(3,3),f);
        assertEquals(1,rabbitAmount());
        f.addEnergy(-200); //removes energy to make to fox hungry
        f.age(world); f.age(world); f.age(world); //age the fox up so it can hunt
        for(int i = 0 ; i < 10 ; i++){
            p.simulate();
        }
        assertEquals(0,rabbitAmount());
    }

    @Test
    void foxesDigBurrowsTest(){
        Fox f = new Fox(false);
        world.setTile(new Location(5,5),f);
        assertEquals(0,foxBurrowAmount());
        for(int i = 0 ; i < 10 ; i++){
            p.simulate();
        }
        assertEquals(1,foxBurrowAmount());
    }

    @Test
    void foxesPlaceCarcassesClosesToHome(){
        FoxBurrow fb = new FoxBurrow();
        Fox f = new Fox(false);
        world.setTile(new Location(1,1),fb);
        world.setTile(new Location(5,5),f);
        f.carryCarcass(new Rabbit(false));
        f.assignBurrow(fb);
        f.age(world);f.age(world);f.age(world);
        for(int i = 0 ; i < 10; i++){
            p.simulate();
            if(!f.isCarrying())break;
        }
        System.out.println(f.isCarrying());
        Carcass c = f.getAnyCarcass(world);
        assertTrue(CloseToBurrow(f, world.getLocation(c)));
    }

    @Test
    void foxesEatsCarcasses(){
        Fox f = new Fox(false);
        Carcass c = new Carcass(false,0);
        world.setTile(new Location(1,1),f);
        world.setTile(new Location(2,2),c);
        f.chooseNextTarget(world);
        f.addEnergy(-20);
        assertEquals(380,f.getEnergy());
        f.attack();
        assertTrue(f.getEnergy() > 380);
    }
    @Test
    void foxCanReproduceTest(){
        Location l = new Location(r.nextInt(worldSize), r.nextInt(worldSize));
        Fox f = new Fox(false);
        FoxBurrow fb = new FoxBurrow();
        world.setTile(l, fb);
        world.setTile(l, f);
        f.age(world); f.age(world); f.age(world);
        f.assignBurrow(fb);
        f.enterHole(world);
        Fox fo = new Fox(false);
        world.setTile(l, fo);
        fo.age(world); fo.age(world); fo.age(world);
        fo.assignBurrow(fb);
        fo.enterHole(world);
        assertEquals(2,foxAmount());
        f.reproduce(world,fo);
        assertTrue(foxAmount() > 2);
    }

    @Test
    void foxGrowAndFoxDeath(){
        Fox f = new Fox(false);
        world.setTile(new Location(5,5), f);
        assertTrue(f.isBaby());
        assertEquals(1, foxAmount());
        for (int i = 0; i < 60; i++){
            p.simulate();
        }
        assertFalse(f.isBaby());
        for (int i = 0; i < 340; i++){
            p.simulate();
        }
        assertEquals(0,foxAmount());
    }



    @Test
    void wolfEatFox(){
        Wolf wolf = new Wolf(false, new Pack());
        Fox f = new Fox(false);
        world.setTile(new Location(0,0), wolf);
        world.setTile(new Location(5,5), f);
        wolf.addEnergy(-100);
        wolf.age(world);wolf.age(world);wolf.age(world);
        f.addEnergy(-100);
        f.age(world);f.age(world);f.age(world);
        assertEquals(1,foxAmount());
        assertEquals(1,wolfAmount());
        for(int i = 0 ; i < 60; i++){
            p.simulate();
        }
        assertEquals(0,foxAmount());
        assertEquals(1,wolfAmount());
    }


    @Test
    void bearEatFox(){
        Bear bear = new Bear(false);
        Fox f = new Fox(false);
        world.setTile(new Location(0,0), bear);
        world.setTile(new Location(5,5), f);
        bear.addEnergy(-150);
        bear.age(world);bear.age(world);bear.age(world);
        f.addEnergy(-100);
        f.age(world);f.age(world);f.age(world);
        assertEquals(1,foxAmount());
        assertEquals(1,bearAmount());
        for(int i = 0 ; i < 60; i++){
            p.simulate();
        }
        assertEquals(0,foxAmount());
        assertEquals(1,bearAmount());
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
    int foxBurrowAmount(){
        Object[] list = world.getEntities().keySet().toArray(new Object[0]);
        ArrayList<FoxBurrow> grassArrayList = new ArrayList<>();
        for(Object thing : list){
            if(thing instanceof FoxBurrow)
                grassArrayList.add((FoxBurrow)thing);
        }
        return grassArrayList.size();
    }
    int foxAmount(){
        Object[] list = world.getEntities().keySet().toArray(new Object[0]);
        ArrayList<Fox> grassArrayList = new ArrayList<>();
        for(Object thing : list){
            if(thing instanceof Fox)
                grassArrayList.add((Fox)thing);
        }
        return grassArrayList.size();
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
    int bearAmount(){
        Object[] list = world.getEntities().keySet().toArray(new Object[0]);
        ArrayList<Bear> arrList = new ArrayList<>();
        for(Object thing : list){
            if(thing instanceof Bear)
                arrList.add((Bear)thing);
        }
        return arrList.size();
    }
    boolean CloseToBurrow(Fox fox, Location location){
        double lengthToBurrow = fox.getLengthBetweenTiles(location, world.getLocation(fox.getBurrow()));
        return lengthToBurrow < 3;
    }
}