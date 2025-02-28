import java.util.Random;

public class Dice {
    //I don't think well need die bigger then 6 sides?
    //Made it final so it can't be changed
    private static final int SIDES = 6; 
    private final Random random = new Random();

    //Only return a # that we can use later in other classes
    public int roll() {
        return random.nextInt(SIDES) + 1;
    }

}

