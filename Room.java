import java.util.List;

public class Room {
    private String name;
    private List<String> adjacentRooms;
    private Card card;
    private List<Player> occupants;
    private List<Role> extras;
    private int maxTakes;
    private int accTakes;
   

    public Room(String name, List<String> adjacentRooms, List<Role> extras, int maxTakes) {
        this.name = name;
        this.adjacentRooms = adjacentRooms;
       
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

    public List<String> getAdjacent() {
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
                r.leaveRole();
            }
        }
        for (Role r : this.extras) {
            if (r.isTaken()) {
                addOccupant(r.getPlayer());
                r.leaveRole();
            }
        }
        card.hideCard();
    }

    public void remTake() {
        if (this.accTakes > 0) {
            this.accTakes = this.accTakes - 1;
        } else sceneFinished();
    }

    public int remainingTake() {
        return this.maxTakes - this.accTakes;
    }
    public List<Role> getExtras()  {
        return this.extras;
    }
}
