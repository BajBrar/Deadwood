//This class is the controller for the game. It will handle the game logic for MVC pattern.

import java.util.ArrayList;
import java.util.List;

public class GameController {
    private ArrayList<Player> players = new ArrayList<>();
    private Board board;
    private Dice dice;
    private int currentPlayerIndex;
    private static boolean gameOver;
    private int dayNum;
    private int maxDay;

    public GameController() {
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
        //increment player index/set to 0
    }

    public void StartGame(int playerCount, String cardFile, String boardFile) {

        int startRank;
        int startCredit = 0;
        switch (playerCount) {
            case 2:
            case 3:
                this.maxDay = 3;
                startRank = 1;
                break;
            case 4:
                this.maxDay = 4;
                startRank = 1;
                startCredit = 0;
                break;
            case 5:
                this.maxDay = 4;
                startRank = 1;
                startCredit = 2;
                break;
            case 6:
                this.maxDay = 4;
                startRank = 1;
                startCredit = 4;
                break;
            case 7:
            case 8:
                this.maxDay = 4;
                startRank = 2;
                startCredit = 0;
                break;
            default:
                throw new AssertionError();
        }
        for(int i = 0; i < playerCount; i++) {
            this.players.add(new Player(i, startRank, "trailer", startCredit));
        }
        //call card/board parser into list
        List<Card> cards = CardParser.parseCards(cardFile);
        System.out.println("Card size: " + cards.size());
        BoardParser bP = new BoardParser();
        List<Room> rooms = bP.parseBoard(boardFile);
        ArrayList<String> tadjRooms = new ArrayList<>();
        ArrayList<String> oadjRooms = new ArrayList<>();
        for (Room r: rooms) {
            if (r.getAdjacent().contains("trailer")) {
                tadjRooms.add(r.getName());
            }
            if (r.getAdjacent().contains("office")) {
                oadjRooms.add(r.getName());
            }
        }
        rooms.add(new Room("trailer", tadjRooms, null, -1));
        rooms.add(new Room("office", oadjRooms, null, -1));

        System.out.println("Rooms size: " + rooms.size());
        for (Room r : rooms) {
            System.out.println("Room: " + r.getName() + ", AdjRooms: " + r.getAdjacent());
        }
        for (Player p : players) {
            System.out.println("Player " + p.getPlayerNumber() + ", Rank " + p.getRank() + ", Credits " + p.getCredits());
        }
        //call board constructor
        //call gameLoop
    }

    public void gameLoop() {
        //while(gameOver == false) {
            
       // }
    }

    
}
