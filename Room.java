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

    public void resetRoom() {
        this.accTakes = 0;
    }
    
}
