//This class is the controller for the game. It will handle the game logic for MVC pattern.

import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;

public class GameController {
    private ArrayList<Player> players = new ArrayList<>();
    private LocationManager locMan;
    private TransactionManager tm = new TransactionManager();
    private Dice dice = new Dice();
    private static boolean gameOver;
    private int dayNum = 1;
    private int maxDay;
    private int curIndex;
    private GUIView v;
    private ConsoleInput i = new ConsoleInput();
    private int availableScene = 10;
    ArrayList<String> helpOpt = new ArrayList<>();
    ArrayList<String> opts = new ArrayList<>();
    Player curPlayer;
    
    
    //Starts the game and sets all necessary values before beginning the game loop
    public void StartGame(String cardFile, String boardFile) {
        int playerCount = 0;
        String input = null;
        while (input == null && playerCount == 0) { 
            String[] pCount = {"2", "3", "4", "5", "6", "7", "8"};
            input = (String) JOptionPane.showInputDialog(null, "How many players will play?", "Input" , JOptionPane.INFORMATION_MESSAGE, null, pCount , pCount[0]);
        }
        playerCount = Integer.parseInt(input);
        v = new GUIView(this, playerCount);
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
        for (Room r : rooms) {
            if (r.getTakes() != null) {
                v.setShots(r.getName(), r.getTakes());
            }
        }
        //v.update();
        Random r = new Random();
        curIndex = r.nextInt(players.size());
        gameLoop();
    }
    
    
    
    public void startTurn() {
        curPlayer = this.players.get(this.curIndex);
        if (locMan.playersRoom(curPlayer).getName().equalsIgnoreCase("Office") && curPlayer.getRank() < 6) {
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
    }
    
    public void endTurn() {
        if (curPlayer.getStatus().equalsIgnoreCase("moved")) {
            curPlayer.setCurAction("idle");
        } else if (curPlayer.getStatus().equalsIgnoreCase("worked")) {
            curPlayer.setCurAction("working");
        }
        if (this.curIndex == this.players.size() - 1) {
            this.curIndex = 0;
        } else { this.curIndex += 1; }
        opts.clear();
        gameLoop();
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
                locMan.move("trailer", player);
                player.freshDay();
            }
        }
    }
    
    //This is the game loop and has the main portion of the logic necessary during gameplay.
    public void gameLoop() {
        if(gameOver == false) {
            startTurn();
            opts.clear();
        }
    }
    
    public ArrayList<Room> getRooms() {
        return locMan.allRooms();
    }
    
    
    
    public void rehearse() {
        curPlayer.setPracticeChips(curPlayer.getPracticeChips() + 1);
        v.displayRehearse(curPlayer.getPlayerNumber(), curPlayer.getPracticeChips());
        curPlayer.setCurAction("worked");
        endTurn();
    }
    
    
    public void move() {
        opts.clear();
        String s = v.displayOptions(locMan.playersRoom(curPlayer).getAdjacent());
        if (s.equalsIgnoreCase("trailer") || s.equalsIgnoreCase("office")) {
            locMan.move(s, curPlayer);
            curPlayer.setCurAction("moved");
            if (!s.equalsIgnoreCase("office")) {
                return;
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
                
                String inp = v.displayOptions(opts);
                if (inp.equalsIgnoreCase("Pass")) {
                    locMan.move(locMan.getRoom(s).getName(), curPlayer);
                    curPlayer.setCurAction("moved");
                } else {
                    locMan.move(inp, curPlayer);
                    curPlayer.updatePos(curPlayer.getPosition());
                    curPlayer.setCurAction("working");
                }
            }
        }
        endTurn();
    }
    
    
    public void turn(String choice) {
        switch (choice) {
            case "act":
            act();
            break;
            case "rehearse":
            rehearse();
            break;
            case "move":
            move();
            break;
            case "upgrade":
            upgrade();
            break;
            case "end turn":
            endTurn();
            break;
        }
    }
    
    
    public void act() {
        int roll = dice.roll() + curPlayer.getPracticeChips();
        Room curRoom =  locMan.playersRoom(curPlayer);
        switch (tm.cashOut(curPlayer, locMan, roll)) {
            case 1:
            case 2:
            v.success();
            if (curRoom.remainingTake() <= 1) {
                v.remShot(curRoom.getName(), curRoom.remainingTake());
                curPlayer.setCurAction("idle");
                v.sceneWrapped(curRoom.getCard().getName());
                tm.bonusPayout(curRoom);
                availableScene--;
                if (availableScene <= 1) {
                    endDay();
                    v.displayDayEnd(dayNum);
                } 
                
            }  else {
                curPlayer.setCurAction("worked");
                v.remShot(curRoom.getName(), curRoom.remainingTake());
                curRoom.remTake();
            }
            break;
            case 3:
            case 4:
            curPlayer.setCurAction("worked");
            v.fail();
            break;
        } 
        endTurn();
    }
    
    public void upgrade() {
        opts.clear();
        for (int i = 2; i <= 6; i++) {
            if (i > curPlayer.getRank()) {
                opts.add(String.valueOf(i));
            }
        }
        
        String s = v.displayOptions(opts);
        opts.clear();
        opts.add("Credits");
        opts.add("Dollars");
        
        boolean useDollars;
        int oldRank = curPlayer.getRank();
        if (v.displayOptions(opts).equalsIgnoreCase("Dollars")) {
            useDollars = true;
        } else useDollars = false;
        if (tm.rankUp(curPlayer, Integer.parseInt(s), useDollars, locMan.getRoom("Office"))) {
            v.displayRankUp(curPlayer.getPlayerNumber(), oldRank, curPlayer.getRank());
        } else {
            v.displayRankUp(curPlayer.getPlayerNumber(), 0, 0);
        }
        endTurn();
    }
    
}
