import java.util.ArrayList;
import java.util.Collections;

public class TransactionManager {
    // Cash out earnings for a player (e.g., after completing a scene)
    // need cases for scene finishing then for players off and on cards and when nobody is on card but there are extras.
    public int cashOut(Player player, LocationManager locMan, int roll) {
       if (roll >= locMan.getRoom(player.getPosition()).getCard().getBudget()) {
           if (locMan.getRoom(player.getPosition()).getExtras().contains(locMan.getRole(player))) {
               addFund(player, 1, 1);
               return 1;
           } else {
                addFund(player, 0, 2);
                return 2;
           }
       } else {
            if (locMan.getRoom(player.getPosition()).getExtras().contains(locMan.getRole(player))) {
                addFund(player, 1, 0);
                return 3;
            } else { return 4; }
       }
    }

    public void bonusPayout(Room room) {
        boolean bonus = false;
        Dice dice = new Dice();
        for (Role r: room.getCard().getRoles()) {
            if (r.isTaken()) {
                bonus = true;
            }
        }
        if (bonus) {
            for (Role r : room.getExtras()) {
                if (r.isTaken()) {
                    addFund(r.getPlayer(), r.getRankRequirement(), 0);
                }
            }
            ArrayList<Integer> rolls = new ArrayList<>();
            for (int i = 0; i < room.getCard().getBudget(); i++) {
                rolls.add(dice.roll());
            }
            Collections.sort(rolls, (a, b) -> b - a);
            int[] totals = new int[room.getCard().getRoles().size()];
            int max = room.getCard().getRoles().size() - 1;
            while (!rolls.isEmpty()) { 
                totals[max] += rolls.remove(0);
                if (max == 0) {
                    max = room.getCard().getRoles().size() - 1;
                } else max--;
            }
        } 
    }

    // Attempt to rank up the player's level if they have enough currency
    public boolean rankUp(Player player, int newRank, boolean useDollars, Room room) {
        boolean retVal = false; 
        for (Upgrade upgrade : room.getUpgrades()) {
            if (useDollars) {
                if (upgrade.level == newRank && upgrade.currency.equalsIgnoreCase("Dollar") && upgrade.amount <= player.getDollars()) {
                    player.setRank(newRank);
                    addFund(player, 0 - upgrade.amount, 0);
                    retVal = true;
                    break;
                } else {
                    retVal = false;
                }
            } else {
                if (upgrade.level == newRank && upgrade.currency.equalsIgnoreCase("Credit") && upgrade.amount <= player.getCredits()) {
                    player.setRank(newRank);
                    addFund(player, 0, 0 - upgrade.amount);
                    retVal = true;
                    break;
                } else {
                    retVal = false;
                }
            }
        }
        return retVal;
    }

    public void addFund(Player player, int dollars, int credits) {
        player.setFunds(player.getDollars() + dollars, player.getCredits() + credits);
    }

      
}
