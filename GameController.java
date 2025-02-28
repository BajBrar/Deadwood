//This class is the controller for the game. It will handle the game logic for MVC pattern.

import java.util.ArrayList;
import java.util.Random;

public class GameController {
    private ArrayList<Player> players = new ArrayList<>();
    private LocationManager locMan;
    private TransactionManager tm;
    private Dice dice;
    private static boolean gameOver;
    private int dayNum = 1;
    private int maxDay;
    private GameView v = new GameView();
    private ConsoleInput i = new ConsoleInput();
    
/*
    public void upgradePlayer(int newRank, int costDollars, int costCredits) {
        Player player = getCurrentPlayer();

        //Check if the player has enough resources (dollars and credits) I don't think a player should be able to downgrade their rank
        if (newRank <= player.getRank()) {
            throw new IllegalArgumentException("Player must upgrade to a higher rank.");
        }

        //Check the cost based on the rank upgrade
        if (newRank == 2) {
            if (costDollars > player.getDollars() && costCredits > player.getCredits()) {
                throw new IllegalStateException("Not enough currency for upgrade.");
            }
        } else if (newRank == 3) {
            if (costDollars > player.getDollars() && costCredits > player.getCredits()) {
                throw new IllegalStateException("Not enough currency for upgrade.");
            }
        } else if (newRank == 4) {
            if (costDollars > player.getDollars() && costCredits > player.getCredits()) {
                throw new IllegalStateException("Not enough currency for upgrade.");
            }
        } else if (newRank == 5) {
            if (costDollars > player.getDollars() && costCredits > player.getCredits()) {
                throw new IllegalStateException("Not enough currency for upgrade.");
            }
            } else if (newRank == 6) {
                if (costDollars > player.getDollars() && costCredits > player.getCredits()) {
                    throw new IllegalStateException("Not enough currency for upgrade.");
                }
            } else {
                throw new IllegalArgumentException("Invalid rank specified.");
            }

            //Deduct the currency required for the upgrade
            if (costDollars > 0) {
                player.setDollars(player.getDollars() - costDollars);
            }
            if (costCredits > 0) {
                player.setCredits(player.getCredits() - costCredits);
            }

            //Upgrade the player's rank
            player.setRank(newRank);

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
    */

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
            this.players.add(new Player(i, startRank, "Trailer", startCredit));
        }
        ArrayList<Card> cards = CardParser.parseCards(cardFile);
        ArrayList<Room> rooms = BoardParser.parseBoard(boardFile);
        this.locMan = new LocationManager(new Board(rooms, cards, players));
        gameLoop();
    }


    public String inputOpts(ArrayList<String> opt) {
        String s = "";
        while (!opt.contains(s)) { 
            s = i.requestInput();
            if (!opt.contains(s)) {
                v.displayInvalidInput();
                v.displayOptions(opt);
            }
        }
        return s;
    }

    public void gameLoop() {
        Random r = new Random();
        int curIndex = r.nextInt(this.players.size());
        ArrayList<String> opts = new ArrayList<>();
        while(gameOver == false) {
            Player curPlayer = this.players.get(curIndex);
            opts.add("end turn");
            if (curPlayer.getPosition().equalsIgnoreCase("Office") && curPlayer.getRank() < 6) {
                opts.add("upgrade");
            }
            if (curPlayer.getStatus().equals("idle")) {
                opts.add("move");
            } else if (curPlayer.getStatus().equals("working") && curPlayer.getStartPosition().equalsIgnoreCase(curPlayer.getPosition())) {
                opts.add("act");
                if (curPlayer.getPracticeChips() < locMan.getRoom(curPlayer.getPosition()).getCard().getBudget()) {
                    opts.add("rehearse");
                }
            } 
            v.displayTurnOptions(curPlayer.getPlayerNumber(), opts);
            String s = inputOpts(opts);
            switch (s) {
                case "move":
                    opts.clear();
                    v.displayMoveOptions(locMan.getAdjRooms(curPlayer.getPosition()));
                    s = inputOpts(locMan.getAdjRooms(curPlayer.getPosition()));
                    if (s.equals("trailer") || s.equals("office")) {
                        locMan.move(s, curPlayer);
                        curPlayer.setCurAction("moved");
                        if (!s.equalsIgnoreCase("office")) {
                            break;
                        }
                        opts.add("upgrade");
                        opts.add("end turn");
                    } else {
                        Card c = locMan.getRoom(s).getCard();
                        if (!c.isShown() && locMan.getRoom(s).remainingTake() > 0) {
                            c.showCard();
                            ArrayList<String> starring = new ArrayList<>();
                            ArrayList<String> extras = new ArrayList<>();
                            for (Role roles : c.getRoles()) {
                                starring.add("" + roles.getName() + ", rank: " + roles.getRankRequirement() + ", occupied: " + roles.isTaken());
                            }
                            for (Role roles : locMan.getRoom(s).getExtras()) {
                                extras.add("" + roles.getName() + ", rank: " + roles.getRankRequirement() + ", occupied: " + roles.isTaken());
                            }
                            v.displayNewCard(c.getName(), c.getDesc(), starring, extras);
                        } 
                        for (Role roles : c.getRoles()) {
                            if (!roles.isTaken() && this.players.get(curIndex).getRank() >= roles.getRankRequirement()) {
                                opts.add(roles.getName());
                            }
                        }
                        for (Role extras : locMan.getRoom(s).getExtras()) {
                            if (!extras.isTaken() && this.players.get(curIndex).getRank() >= extras.getRankRequirement()) {
                                opts.add(extras.getName());
                            }
                        }
                        if (opts.isEmpty()) {
                            locMan.move(locMan.getRoom(s).getName(), curPlayer);
                            curPlayer.setCurAction("moved");
                        } else {
                            v.displayOptions(opts);
                            s = inputOpts(opts);
                            locMan.move(s, curPlayer);
                            curPlayer.updatePos(curPlayer.getPosition());
                            curPlayer.setCurAction("working");
                        }
                        break;
                    }
                case "upgrade":
                    opts.clear();
                    for (int i = 2; i <= 6; i++) {
                        if (i > curPlayer.getRank()) {
                            opts.add(String.valueOf(i));
                        }
                    }
                    v.displayOptions(opts);
                    s = inputOpts(opts);
                    opts.clear();
                    opts.add("Credits");
                    opts.add("Dollars");
                    v.displayOptions(opts);
                    boolean useDollars;
                    if (inputOpts(opts).equalsIgnoreCase("Dollars")) {
                        useDollars = true;
                    } else useDollars = false;
                    if (tm.rankUp(curPlayer, Integer.parseInt(s), useDollars, locMan.getRoom("Office"))) {
                        v.displayRankUp(curPlayer.getPlayerNumber(), curPlayer.getRank());
                    } else {
                        v.displayRankUp(curPlayer.getPlayerNumber(), 0);
                    }
                    continue;
                case "act":
                    int roll = dice.roll() + curPlayer.getPracticeChips();
                    Room curRoom =  locMan.getRoom(curPlayer.getPosition());
                    switch (tm.cashOut(curPlayer, locMan, roll)) {
                        case 1:
                        case 2:
                            v.success();
                            if (curRoom.remainingTake() == 1) {
                                v.sceneWrapped(curRoom.getCard().getName());
                                tm.bonusPayout(curRoom);
                            }  
                            curRoom.remTake();
                            break;
                        case 3:
                        case 4:
                            v.fail();
                            break;
                     } 
                     curPlayer.setCurAction("worked");
                    break;
                case "rehearse":
                    System.out.println("worked");
                    break;
                case "end turn":
                    break;
                default:
                    throw new AssertionError();
            }
            opts.clear();
            if (curPlayer.getStatus().equalsIgnoreCase("moved")) {
                curPlayer.setCurAction("idle");
            } else if (curPlayer.getStatus().equalsIgnoreCase("worked")) {
                curPlayer.setCurAction("working");
            }
            if (curIndex == this.players.size() - 1) {
                curIndex = 0;
            } else { curIndex += 1; }
        }
    }
}
