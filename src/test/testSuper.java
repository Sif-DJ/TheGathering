package test;
import NonBlockables.FoxBurrow;
import NonBlockables.RabbitBurrow;
import NonBlockables.WolfBurrow;
import itumulator.executable.*;
import itumulator.world.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.awt.*;
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
        setDisplay(p);
    }

    @AfterEach
    void tearDown() {
        world = null;
        p = null;
    }

    public static void setDisplay(Program p) {
        p.setDisplayInformation(RabbitBurrow.class, new DisplayInformation(Color.orange, "hole-small", false));
        p.setDisplayInformation(FoxBurrow.class, new DisplayInformation(Color.orange, "hole-small", false));
        p.setDisplayInformation(WolfBurrow.class, new DisplayInformation(Color.orange, "hole", false));
    }

}
