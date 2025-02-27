//Anything that needs to be printed needs to go in this class to make it easier for interface implementation later on and its easier for the controller to call the view to print things.

//The example thats in assignment 2 description all of those system.out.println's should be in this class.
import java.util.List;
import java.util.Set;

class GameView implements View{
    
    @Override
    public void displayMoveOptions(int num, Set<String> opts) {
        try {
            System.out.println("Player " + num + "'s turn!");
            System.out.println("Choose an option: " + String.join(", ", opts));
        } catch (Exception e) {
            System.out.println(e);
            System.exit(-1);
        }
    };
    @Override
    public void displayWinner(List<Player> players, int points) {
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
    public void displayNewCard(Card c) {
        System.out.println("New scene revealed!");
        System.out.println("Name: " + c.getName());
        System.out.println("Description: " + c.getDesc());
        for (Role r : c.getRoles()) {
            System.out.println("  Role: " + r.getName());
            System.out.println("  Rank: " + r.getRankRequirement());
        }
    };
    

   
}
