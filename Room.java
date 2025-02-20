import java.util.ArrayList;
import java.util.List;

public class Room {
    private String name;
    private List<Room> adjacentRooms;
    private Card card;
    private List<Player> occupants;

    public Room(String name) {
        this.name = name;
        this.adjacentRooms = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setCard(Card card) {
    }

    public void addAdjacentRoom(Room room) {
        adjacentRooms.add(room);
    }

    public boolean isAdjacent(Room room) {
        return adjacentRooms.contains(room);
    }
}
