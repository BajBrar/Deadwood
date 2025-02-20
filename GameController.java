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

        Player player = getCurrentPlayer();

        //Make sure the player isn't acting or else they cant move
       if (player.getStatus().equals("acting")) {
           throw new IllegalStateException("Cannot move while acting!");
       }
       //Make sure that the move is valid so check the neighboring positions
       //this needs to be changed isAdjacent isn't done in board yet
    //    if (!board.isAdjacent(player.getPosition(), newPosition)) {
    //     throw new IllegalStateException("Invalid move! Must move to an adjacent location.");
    //    }
        //Move player and update the position
        player.move(newPosition);

    }

    public void playerRehearse() {
        Player player = getCurrentPlayer();

        //Ensure the player is not already acting because they cant act and rehearse at the same time
        if (player.getStatus().equals("acting")){
            throw new IllegalStateException("Can only rehearse while acting!");
        }
        player.setCurAction("rehearsing");
        player.rehearse();

       
    }

    public void playerAct() {
       
    }

    public void upgradePlayer(int newRank, int costDollars, int costCredits) {
       
    }

    public void endTurn() {
        
    }
}
