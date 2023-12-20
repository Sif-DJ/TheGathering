package test.NonBlockables;

import NonBlockables.Carcass;
import NonBlockables.Mushroom;
import itumulator.world.Location;
import org.junit.jupiter.api.Test;
import test.testSuper;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MushroomTest extends testSuper {

    @Test
    void mushroomInCarcass(){
        Carcass c1 = new Carcass(true,0);//this carcass starts with mushrooms in it
        Carcass c2 = new Carcass(false,0);//this carcass starts without mushrooms in it
        world.setTile(new Location(5,5),c1);
        world.setTile(new Location(3,3),c2);
        assertEquals(2,getCarcassAmount());
        assertEquals(0,getmushroomAmount());
        for(int i = 0;i < 20; i++){
            p.simulate();
        }
        c2.setIsInfected();//this carcass now gets infected with mushrooms
        for(int i = 0;i < 40; i++){
            p.simulate();
        }
        assertEquals(0,getCarcassAmount());
        assertEquals(2,getmushroomAmount());
        for(int i = 0;i < 40; i++){
            p.simulate();
        }
        assertEquals(1,getmushroomAmount());
        for(int i = 0;i < 20; i++){
            p.simulate();
        }
        assertEquals(0,getmushroomAmount());

    }

    @Test
    void mushroomSpreadToSurvive(){
        Mushroom m = new Mushroom(60);
        Carcass c = new Carcass(false,0);//this carcass starts without mushrooms in it
        world.setTile(new Location(1,1),m);
        for(int i = 0;i < 40; i++){
            p.simulate();
        }
        world.setTile(new Location(4,4),c);
        assertEquals(1,getmushroomAmount());
        assertEquals(1,getCarcassAmount());
        for(int i = 0;i < 20; i++){
            p.simulate();
        }
        assertEquals(0,getmushroomAmount());
        assertEquals(1,getCarcassAmount());
        for(int i = 0;i < 40; i++){
            p.simulate();
        }
        assertEquals(1,getmushroomAmount());
        assertEquals(0,getCarcassAmount());
        for(int i = 0;i < 60; i++){
            p.simulate();
        }
        assertEquals(0,getmushroomAmount());
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
    int getmushroomAmount(){
        Object[] list = world.getEntities().keySet().toArray(new Object[0]);
        ArrayList<Mushroom> arrList = new ArrayList<>();
        for(Object thing : list){
            if(thing instanceof Mushroom)
                arrList.add((Mushroom)thing);
        }
        return arrList.size();
    }
}