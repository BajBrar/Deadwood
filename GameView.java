//Anything that needs to be printed needs to go in this class to make it easier for interface implementation later on and its easier for the controller to call the view to print things.

//The example thats in assignment 2 description all of those system.out.println's should be in this class.
import java.util.ArrayList;

class GameView implements View{
    
    @Override
    public void displayTurnOptions(int num, ArrayList<String> opts) {
        try {
            System.out.println("Player " + num + "'s turn!");
            System.out.println("Choose an option: " + String.join(", ", opts) + ", active?, info");
        } catch (Exception e) {
            System.out.println(e);
            System.exit(-1);
        }
    };
    @Override
    public void displayWinner(int[] winners) {
        System.out.println();
        System.out.print("Player(s) ");
        int points = 0;
        for(int i = 0; i < winners.length; i++) {
            if (winners[i] != 0) {
                points = winners[i];
                System.out.print(i + ", ");
            }
        }
        System.out.print("win with " + points + " points.");
    };
    @Override
    public void displayInvalidInput() {
        System.out.println("Invalid input, try again!");
    };
    @Override
    public void displayInvalidMove() {
        System.out.println("You can only move to adjacent rooms");
    };
    @Override
    public void displayNewCard(String name, String desc, ArrayList<String> roles, ArrayList<String> extras) {
        System.out.println("New scene revealed!");
        System.out.println("Name: " + name);
        System.out.println("Description: " + desc);
        System.out.println("Roles: ");
        for (String s : roles) {
            System.out.println("\t" + s);
        }
        System.out.println("Extras: ");
        for (String s : extras) {
            System.out.println("\t" + s);
        }
    };
    @Override
    public void displayMoveOptions(ArrayList<String> adjacents) {
        try {
            System.out.println("Where will you move? (Choices: " + String.join(", ", adjacents) + ")");
        } catch (Exception e) {
            System.out.println(e);
            System.exit(-1);
        }
    }
    @Override
    public String displayOptions(ArrayList<String> opts) {
        try {
            System.out.println("Type one of the following options: " + String.join(", ", opts) + ", active?, info");
        } catch (Exception e) {
            System.out.println(e);
            System.exit(-1);
        }
        return null;
    }
    @Override
    public void displayRankUp(int player, int rank, int newRank) {
        if (rank == 0) {
            System.out.println("Player " + player + " could not afford the rank.");
        } else {
            System.out.println("Player " + player + " was rank " + rank + " is now rank " + newRank + ".");
        }
    }
    @Override
    public void success() {
        System.out.println("Success! One take down!");
    }
    @Override
    public void fail() {
        System.out.println("Everybody makes mistakes! Try again next time!");
    }
    @Override
    public void sceneWrapped(String sceneName) {
        System.out.println("" + sceneName + " has wrapped! Great job everyone!");
    }
    @Override
    public int playerCount() {
        System.out.println("How many players will be playing? [2-8] (inclusive)");
        return 0;
    }
    @Override
    public void displayRehearse(int player, int chips) {
        System.out.println("Player " + player + " has " + chips + " practice chips.");
    }
    @Override
    public void displayActive(int player, String roomName, int credit, int dollar, String out) {
        System.out.println("Player " + player + " has " + credit + " credit(s) and $" + dollar + ". They are in the " + out);
    }
    @Override 
    public void displayPlayer(String out) {
        System.out.println(out);
    }

    @Override
    public void displayDayEnd(int n) {
        System.out.println("Day " + n + "has ended.");
    }
}
