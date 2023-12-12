package Animals;

import itumulator.executable.DisplayInformation;
import itumulator.world.Location;
import itumulator.world.World;

import java.awt.*;

public class Fox extends Predator {

    public Fox(boolean isInfected) {
        super(isInfected);
    }

    @Override
    public void tryReproduce(World world) {

    }

    @Override
    public Animal createNewSelf() {
        return null;
    }

    @Override
    public void diggyHole(World world, Location l) {

    }

    @Override
    public void chooseNextTarget(World world) {

    }

    @Override
    public DisplayInformation getInformation() {
        return new DisplayInformation(Color.gray,
                "fox"
                        +(isBaby ? "-small" : ""));
    }
}
