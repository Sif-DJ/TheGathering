package test;
import itumulator.executable.*;
import itumulator.world.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
public class testSuper {
    protected World world;
    protected Program p;
    protected int worldSize = 10;
    int delay = 200;
    int display_size = 800;
    protected Random r = new Random();
    @BeforeEach
    void setUp() {
        p = new Program(worldSize, display_size, delay);
        world = p.getWorld();
    }

    @AfterEach
    void tearDown() {
        world = null;
    }



}
