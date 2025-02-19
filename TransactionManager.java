import java.util.Random;

public class TransactionManager {
    private Random diceRoller;

    public TransactionManager() {
        diceRoller = new Random();
    }

    // Cash out earnings for a player (e.g., after completing a scene)
    public void cashOut(Player player, int dollars, int credits) {
       
    }

    // Attempt to rank up the player's level if they have enough currency
    public boolean rankUp(Player player, int newRank, boolean useDollars) {
        return true; //just a placeholder return
    }

    // Rolls a die for acting
    public int rollDice() {
        return diceRoller.nextInt(6) + 1; 
    }

    // Update player's game state (e.g., reset status at the end of turn)
    public void updatePlayer(Player player) {
       
    }

    // Returns the cost for ranking up based on rank level
    private int getRankUpCost(int rank) {
        return 0; //just a placeholder return
    }   
}
