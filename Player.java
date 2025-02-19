public class Player{
  public int playerNum;
  public int rank;
  public int dollars;
  public int credits;
  public int practiceChips;
  public String status;
  //Possibly be changed for a different class such as space.
  public String startPos;
  public String curPos;


//Set up the players based on the total number of players playing the game.
  public Player(int pNum, int pCount) {
    this.playerNum = pNum;
  }


  
}
