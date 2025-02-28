import java.util.ArrayList;

public class Room {
    private String name;
    private ArrayList<String> adjacentRooms = new ArrayList<>();
    private Card card;
    private ArrayList<Player> occupants = new ArrayList<>();
    private ArrayList<Role> extras = new ArrayList<>();
    private int maxTakes;
    private int accTakes;
    private ArrayList<Upgrade> upgrades = new ArrayList<>();
   

    public Room(String name, ArrayList<String> adjacentRooms, ArrayList<Role> extras, int maxTakes) {
        this.name = name;
        this.adjacentRooms = adjacentRooms;
        this.maxTakes = maxTakes;
        this.accTakes = maxTakes;
        this.extras = extras;
        this.card = null;
    }

    public void addUpgrade(Upgrade upgrade) {
        upgrades.add(upgrade);
    }

    public ArrayList<Upgrade> getUpgrades() {
        return upgrades;
    }

    public String getName() {
        return this.name;
    }

    public void setCard(Card newCard) {
        this.card = newCard;
    }

    

    public boolean isAdjacent(Room room) {
        return adjacentRooms.contains(room.getName());
    }

    public ArrayList<String> getAdjacent() {
        return this.adjacentRooms;
    }

    public void addOccupant(Player player) {
        occupants.add(player);
    }

    public void remOccupant(Player player) {
        occupants.remove(player);
    }
    
    public void sceneFinished() {
        for(Role r: card.getRoles()) {
            if (r.isTaken()) {
                addOccupant(r.getPlayer());
                r.getPlayer().setPracticeChips(0);
                r.leaveRole();
            }
        }
        for (Role r : this.extras) {
            if (r.isTaken()) {
                addOccupant(r.getPlayer());
                r.getPlayer().setPracticeChips(0);
                r.leaveRole();
            }
        }
        card.hideCard();
    }

    public void remTake() {
        if (this.accTakes - 1 > 0) {
            this.accTakes = this.accTakes - 1;
        } else sceneFinished();
    }

    public int remainingTake() {
        return this.accTakes;
    }
    public ArrayList<Role> getExtras()  {
        return this.extras;
    }
    public Card getCard() {
        return this.card;
    }

    public ArrayList<Role> getAllRoles() {
        ArrayList<Role> ret = getExtras();
        ret.addAll(getCard().getRoles());
        return ret;
    }

    public ArrayList<Player> getOccupants() {
        return this.occupants;
    }

    public void reset() {
        this.accTakes = maxTakes;
        this.occupants.clear();
        this.extras.clear();
    }
}
