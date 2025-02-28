public class Player{
  //Also these should be private and have getters and setters cause I dint think we want to change these values
  private int playerNum;
  private int rank;
  private int dollars;
  private int credits;
  private int practiceChips;
  //curAction can be [idle, working, worked, moved]
  private String curAction;
  private String startPos;
  private String curPos;


//Set up the players based on the total number of players playing the game.
  public Player(int playerNum, int startRank, String startPos, int startCredit) {
    this.playerNum = playerNum;
    this.rank = startRank;
    this.dollars = 0;
    this.credits = startCredit;
    this.practiceChips = 0;
    this.curAction = "idle";
    this.startPos = startPos;
    this.curPos = startPos;
  }

  //we need the player to move so make a simple move function, the actual controller class will implement the logic
  public void updatePos(String newPosition) {
        this.startPos = this.curPos;
        this.curPos = newPosition;
        curAction = "moved";
}


public void setCurAction(String action) {
  this.curAction = action;
}

public void setFunds(int dol, int cred) {
  this.dollars = dol;
  this.credits = cred;
}

public void freshDay() {
  setCurAction("idle");
  this.curPos = "Trailer";
  this.startPos = "Trailer";
}

// Getters for Controller to use, if we make the vars private then well need these
public int getPlayerNumber() { return playerNum; }
public int getRank() { return rank; }
public int getDollars() { return dollars; }
public int getCredits() { return credits; }
public int getPracticeChips() { return practiceChips; }
public String getStatus() { return curAction; }
public String getPosition() { return curPos; }
public String getStartPosition() { return startPos; }
public void setDollars(int dollars){this.dollars = dollars;}
public void setRank(int rank){this.rank = rank;}
public void setCredits(int credits){this.credits = credits;}
public void setPracticeChips(int num) {this.practiceChips = num;}
}
