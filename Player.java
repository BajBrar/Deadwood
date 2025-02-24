public class Player{
  //Also these should be private and have getters and setters cause I dint think we want to change these values
  public int playerNum;
  public int rank;
  public int dollars;
  public int credits;
  public int practiceChips;
  //public String status; changed to curAction
  //Possibly be changed for a different class such as space.
  //need a var to keep track of what action the player is doing\
  public String curAction;
  public String startPos;
  public String curPos;


//Set up the players based on the total number of players playing the game.
  public Player(int playerNum, int startRank, String startPos, int startCredit) {
    this.playerNum = playerNum;
    this.rank = startRank;
    this.dollars = 0;
    this.credits = startCredit;
    this.practiceChips = 0;
    this.curAction = "waiting";
    this.startPos = startPos;
  }

  //we need the player to move so make a simple move function, the actual controller class will implement the logic
  public void move(String newPosition) {
    if (!curAction.equals("acting")) {
        this.curPos = newPosition;
        curAction = "waiting";
    } else {
        throw new IllegalStateException("Cannot move while acting!");
    }
}

//Same with rehearse
public void rehearse() {
  if (curAction.equals("rehearsing")) {
      practiceChips++;
      curAction = "rehearsing";}
 
}

//Base act logic but this needs some tweak to account for the different spaces (whether your acting on starring role or extra)
public boolean act(int rollResult, int sceneBudget) {
  if (!curAction.equals("acting")) {
      throw new IllegalStateException("Can only act while on a role!");
  }
  int totalRoll = rollResult + practiceChips;
  boolean success = totalRoll >= sceneBudget;
  if (success) {
      credits += 2;
  } else {
      dollars += 1;  
  }
  return success;
}

//upgrade should go here but I don't know if were adding the upgrade logic in this class or another?
public void upgrade(int newRank, int costDollars, int costCredits) {
  
}

public void endTurn() {
  curAction = "waiting";
  practiceChips = 0;
}
public void setCurAction(String action) {
  this.curAction = action;
}

// Getters for Controller to use, if we make the vars private then well need these
public int getPlayerNumber() { return playerNum; }
public int getRank() { return rank; }
public int getDollars() { return dollars; }
public int getCredits() { return credits; }
public int getPracticeChips() { return practiceChips; }
public String getStatus() { return curAction; }
public String getPosition() { return curPos; }
  
}
