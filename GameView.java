//Anything that needs to be printed needs to go in this class to make it easier for interface implementation later on and its easier for the controller to call the view to print things.

//The example thats in assignment 2 description all of those system.out.println's should be in this class.
import java.util.ArrayList;

class GameView implements View{
    
    @Override
    public void displayTurnOptions(int num, ArrayList<String> opts) {
        try {
            System.out.println("Player " + num + "'s turn!");
            System.out.println("Choose an option: " + String.join(", ", opts));
        } catch (Exception e) {
            System.out.println(e);
            System.exit(-1);
        }
    };
    @Override
    public void displayWinner(ArrayList<Player> players, int points) {
        System.out.println();
        System.out.print("Player(s) ");
        for(Player p: players) {
            System.out.print(p.getPlayerNumber() + ", ");
        }
        System.out.print("win with " + points + "points.");
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
    public void displayOptions(ArrayList<String> opts) {
        try {
            System.out.println("Type one of the following options: " + String.join(", ", opts));
        } catch (Exception e) {
            System.out.println(e);
            System.exit(-1);
        }
    }
    @Override
    public void displayRankUp(int player, int rank) {
        if (rank == 0) {
            System.out.println("Player " + player + "could not afford the rank.");
        } else {
            System.out.println("Player " + player + "is now rank " + rank + ".");
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
}
