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
        // need this var to keep track of what players turn it is
        this.currentPlayerIndex = 0;
    }
    // we might need more methods to handle the game logic

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public void movePlayer(String newPosition) {
        Player player = getCurrentPlayer();
        // Make sure the player isn't acting or else they can't move
        if (player.getStatus().equals("acting")) {
            throw new IllegalStateException("Cannot move while acting!");
        }
        String currentPosition = player.getPosition();
        // Check if the new position is a valid adjacent room
        Room currentRoom = board.getRoomByName(currentPosition);
        if (currentRoom == null) {
            throw new IllegalStateException("Invalid current position.");
        }
        List<String> adjacentRooms = currentRoom.getAdjacent();
        if (!adjacentRooms.contains(newPosition)) {
            throw new IllegalStateException("Invalid move! Must move to an adjacent location.");
        }
        // then move the player
        player.move(newPosition);
    }

    public void playerRehearse() {
        Player player = getCurrentPlayer();

        // Ensure the player is not already acting because they cant act and rehearse at
        // the same time
        if (player.getStatus().equals("acting")) {
            throw new IllegalStateException("Can only rehearse while acting!");
        }
        player.setCurAction("rehearsing");
        player.rehearse();

    }

    public void playerAct() {
        Player player = getCurrentPlayer();

        if (!player.getStatus().equals("acting")) {
            throw new IllegalStateException("Player must be acting to perform an action!");
        }

        Role playerRole = player.getRole(); // Get the player's role

        if (playerRole == null) {
            throw new IllegalStateException("Player must have a role to act!");
        }

        int rollResult = dice.roll(); // Simulate dice roll
        boolean isStarring = playerRole.isStarring(); // Check if starring role
        boolean success = player.act(rollResult, playerRole.getSceneBudget(), isStarring);

        System.out.println("Player " + player.getPlayerNumber() + (success ? " succeeded!" : " failed!"));
    }

    public void upgradePlayer(int newRank, int costDollars, int costCredits) {

    }

    public void endTurn() {
        // Move to the next player
        currentPlayerIndex++;

        // If we've reached the end of the player list, reset the index
        if (currentPlayerIndex >= players.size()) {
            currentPlayerIndex = 0;
        }

        // update day or game status if necessary
        if (currentPlayerIndex == 0) {
            dayNum++;
        }

        // Check if the game has reached the maximum days
        if (dayNum > maxDay) {
            gameOver = true;
            return;
        }

        // Set the current player to the next player
        Player currentPlayer = getCurrentPlayer();
        currentPlayer.endTurn();
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
        for (int i = 0; i < playerCount; i++) {
            this.players.add(new Player(i, startRank, "trailer", startCredit));
        }
        // call card/board parser into list
        List<Card> cards = CardParser.parseCards(cardFile);
        BoardParser bP = new BoardParser();
        List<Room> rooms = bP.parseBoard(boardFile);
        ArrayList<String> tadjRooms = new ArrayList<>();
        ArrayList<String> oadjRooms = new ArrayList<>();
        for (Room r : rooms) {
            if (r.getAdjacent().contains("trailer")) {
                tadjRooms.add(r.getName());
            }
            if (r.getAdjacent().contains("office")) {
                oadjRooms.add(r.getName());
            }
        }
        // NEED TO EDIT PARSER TO GET ALL INFORMATION!!!!!
        rooms.add(new Room("trailer", tadjRooms, null, -1));
        rooms.add(new Room("office", oadjRooms, null, -1));

        GameView v = new GameView();
        for (Card c : cards) {
            v.displayNewCard(c);
        }
        // call board constructor
        // call gameLoop
    }

    public void gameLoop() {
        // while(gameOver == false) {

        // }
    }

}
