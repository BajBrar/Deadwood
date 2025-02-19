//This class is the controller for the game. It will handle the game logic for MVC pattern.

import java.util.List;

public class GameController {
    private List<Player> players;
    private Board board;
    private Dice dice;
    private int currentPlayerIndex;

    public GameController(List<Player> players, Board board, Dice dice) {
        this.players = players;
        this.board = board;
        this.dice = dice;
        //need this var to keep track of what players turn it is
        this.currentPlayerIndex = 0; 
    }
    //we might need more methods to handle the game logic

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public void movePlayer(String newPosition) {
       
    }

    public void playerRehearse() {
       
    }

    public void playerAct() {
       
    }

    public void upgradePlayer(int newRank, int costDollars, int costCredits) {
       
    }

    public void endTurn() {
        
    }
}
