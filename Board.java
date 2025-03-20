import java.util.*;

public class Board {
    //I have a list but we don't have to store it as a list if we don't want to
    private ArrayList<Room> rooms;
    private ArrayList<Card> cards;
    
    public Board(ArrayList<Room> rooms, ArrayList<Card> cards, ArrayList<Player> players) {
        this.rooms = rooms;
        this.cards = cards;
        Collections.shuffle(cards);
        for(Player player: players) {
            getRoomByName("Trailer").addOccupant(player);
        }
        for(Room room: rooms){
            if (!room.getName().equalsIgnoreCase("Office") && !room.getName().equalsIgnoreCase("Trailer") && !cards.isEmpty()) {
                room.setCard(cards.remove(0));
            }
        }
    }
    
    // Set the initial state of the board
    public void setBoard() {
        for(Room room: rooms){
            room.reset();
            if (!room.getName().equalsIgnoreCase("Office") && !room.getName().equalsIgnoreCase("Trailer") && !cards.isEmpty()) {
                room.setCard(cards.remove(0));
            }
        }
    }
    
    // Get a room by name
    public Room getRoomByName(String roomName) {
        for (Room room : rooms) {  
            if (room.getName().equalsIgnoreCase(roomName)) {
                return room;
            } 
        }
        return null; 
    }
    
    public Room getRoomByPlayer(Player player) {
        Room retRoom = null;
        for (Room room : rooms) {  
            if (room.getOccupants().contains(player)) {
                retRoom = room;
            } else if (!room.getName().equalsIgnoreCase("Office") && !room.getName().equalsIgnoreCase("Trailer")) {
                for (Role role : room.getAllRoles()) {
                    if (role.isTaken() && role.getPlayer() == player) {
                        retRoom = room;
                    }
                }
            }
        }
        return retRoom; 
    }
    
    
    // Getters
    public ArrayList<Room> getRooms() {
        return this.rooms;
    }
    public ArrayList<Card> getCards() {
        return this.cards;
    }
}

