//This class is the controller for the game. It will handle the game logic for MVC pattern.

import java.util.ArrayList;
import java.util.Random;

public class GameController {
    private ArrayList<Player> players = new ArrayList<>();
    private LocationManager locMan;
    private TransactionManager tm = new TransactionManager();
    private Dice dice = new Dice();
    private static boolean gameOver;
    private int dayNum = 1;
    private int maxDay;
    private int curIndex;
    private GameView v = new GameView();
    private ConsoleInput i = new ConsoleInput();
    private int availableScene;
    ArrayList<String> helpOpt = new ArrayList<>();
    
    //Starts the game and sets all necessary values before beginning the game loop
    public void StartGame(String cardFile, String boardFile) {
        int playerCount;
        v.playerCount();
        ArrayList<String> pCount = new ArrayList<>();
        pCount.add("2");
        pCount.add("3");
        pCount.add("4");
        pCount.add("5");
        pCount.add("6");
        pCount.add("7");
        pCount.add("8");
        String input = inputOpts(pCount);
        playerCount = Integer.parseInt(input);
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
        helpOpt.add("active?");
        helpOpt.add("info");
        helpOpt.add("end game");
        helpOpt.add("help");
        gameLoop();
    }
    
    //Based on an ArrayList of strings it requests an input from the user (must be in opt)
    //There are extra commands that you are able to call at any time and the current players turn is maintained
    public String inputOpts(ArrayList<String> opt) {
        String input = "";
        while (!opt.contains(input)) { 
            input = i.requestInput();
            if (helpOpt.contains(input)) { 
                switch (input) {
                    case "active?":
                    Player player = this.players.get(curIndex);
                    String out = "";
                    if (player.getStatus().equalsIgnoreCase("working") || player.getStatus().equalsIgnoreCase("worked")) {
                        out = out.concat(locMan.playersRoom(player).getName() + " working as " + locMan.getRole(player).getName() + ", \"" + locMan.getRole(player).getLine() + "\"");
                    } else {
                        out = out.concat(locMan.playersRoom(player).getName() + ".");
                    }
                    v.displayActive(player.getPlayerNumber(), locMan.playersRoom(player).getName(), player.getCredits(), player.getDollars(), out);
                    break;
                    case "info":
                    for (Player p: players) {
                        if (curIndex == p.getPlayerNumber()) {
                            v.displayPlayer("(Active) Player " + p.getPlayerNumber() + ":\n\tRoom: " + locMan.playersRoom(p).getName());
                        } else {
                            v.displayPlayer("Player " + p.getPlayerNumber() + ":\n\tRoom: " + locMan.playersRoom(p).getName());
                        }
                        
                    }
                    break;
                    case "end game":
                    dayNum = maxDay;
                    endDay();
                    break;
                }
                v.displayOptions(opt);  
                continue;
            }
            if (!opt.contains(input)) {
                v.displayInvalidInput();
                v.displayOptions(opt);
            }
        }
        return input;
    }
    
    
    //This method will end the day but first check that it is not the last day
    //If it is the last day it will calculate the scores and display the winners.
    public void endDay() {
        if (dayNum == maxDay) {
            v.displayWinner(tm.finalScore(players));
            gameOver = true;
        } else {
            locMan.newDay();
            this.availableScene = 10;
            for (Player player : players) {
                player.freshDay();
            }
            for (Room room : locMan.allRooms()) {
                room.reset();
            }
        }
    }
    
    //This is the game loop and has the main portion of the logic necessary during gameplay.
    public void gameLoop() {
        Random r = new Random();
        this.curIndex = r.nextInt(this.players.size());
        ArrayList<String> opts = new ArrayList<>();
        while(gameOver == false) {
            Player curPlayer = this.players.get(this.curIndex);
            if (curPlayer.getPosition().equalsIgnoreCase("Office") && curPlayer.getRank() < 6) {
                opts.add("upgrade");
            }
            if (curPlayer.getStatus().equals("idle")) {
                opts.add("move");
            } else if (curPlayer.getStatus().equals("working")) {
                opts.add("act");
                if (curPlayer.getPracticeChips() < locMan.playersRoom(curPlayer).getCard().getBudget()) {
                    opts.add("rehearse");
                }
            } 
            opts.add("end turn");
            v.displayTurnOptions(curPlayer.getPlayerNumber(), opts);
            String s = inputOpts(opts);
            switch (s) {
                case "move":
                opts.clear();
                v.displayMoveOptions(locMan.playersRoom(curPlayer).getAdjacent());
                s = inputOpts(locMan.playersRoom(curPlayer).getAdjacent());
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
                        for (Role extraR : locMan.getRoom(s).getExtras()) {
                            extras.add("" + extraR.getName() + ", rank: " + extraR.getRankRequirement() + ", occupied: " + extraR.isTaken());
                        }
                        v.displayNewCard(c.getName(), c.getDesc(), starring, extras);
                    } 
                    for (Role roles : c.getRoles()) {
                        if (!roles.isTaken() && curPlayer.getRank() >= roles.getRankRequirement() && locMan.playersRoom(curPlayer).remainingTake() > 0) {
                            opts.add(roles.getName());
                        }
                    }
                    for (Role extraRole : locMan.getRoom(s).getExtras()) {
                        if (!extraRole.isTaken() && (curPlayer.getRank() >= extraRole.getRankRequirement()) && locMan.getRoom(s).remainingTake() > 0) {
                            opts.add(extraRole.getName());
                        }
                    }
                    opts.add("Pass");
                    if (opts.size() == 1) {
                        locMan.move(locMan.getRoom(s).getName(), curPlayer);
                        curPlayer.setCurAction("moved");
                    } else {
                        v.displayOptions(opts);
                        String inp = inputOpts(opts);
                        if (inp.equalsIgnoreCase("Pass")) {
                            locMan.move(locMan.getRoom(s).getName(), curPlayer);
                            curPlayer.setCurAction("moved");
                        } else {
                            locMan.move(inp, curPlayer);
                            curPlayer.updatePos(curPlayer.getPosition());
                            curPlayer.setCurAction("working");
                        }
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
                int oldRank = curPlayer.getRank();
                if (inputOpts(opts).equalsIgnoreCase("Dollars")) {
                    useDollars = true;
                } else useDollars = false;
                if (tm.rankUp(curPlayer, Integer.parseInt(s), useDollars, locMan.getRoom("Office"))) {
                    v.displayRankUp(curPlayer.getPlayerNumber(), oldRank, curPlayer.getRank());
                } else {
                    v.displayRankUp(curPlayer.getPlayerNumber(), 0, 0);
                }
                continue;
                case "act":
                int roll = dice.roll() + curPlayer.getPracticeChips();
                Room curRoom =  locMan.playersRoom(curPlayer);
                switch (tm.cashOut(curPlayer, locMan, roll)) {
                    case 1:
                    case 2:
                    v.success();
                    if (curRoom.remainingTake() == 1) {
                        curPlayer.setCurAction("idle");
                        v.sceneWrapped(curRoom.getCard().getName());
                        tm.bonusPayout(curRoom);
                        availableScene--;
                        if (availableScene == 1) {
                            endDay();
                        }
                    }  
                    curRoom.remTake();
                    curPlayer.setCurAction("worked");
                    break;
                    case 3:
                    case 4:
                    curPlayer.setCurAction("worked");
                    v.fail();
                    break;
                } 
                break;
                case "rehearse":
                curPlayer.setPracticeChips(curPlayer.getPracticeChips() + 1);
                v.displayRehearse(curPlayer.getPlayerNumber(), curPlayer.getPracticeChips());
                curPlayer.setCurAction("worked");
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
            if (this.curIndex == this.players.size() - 1) {
                this.curIndex = 0;
            } else { this.curIndex += 1; }
        }
    }
}
