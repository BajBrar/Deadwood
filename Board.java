import java.util.*;

public class Board {
    //I have a list but we don't have to store it as a list if we don't want to
    private ArrayList<Room> rooms;
    private ArrayList<Card> cards;

    public Board(ArrayList<Room> rooms, ArrayList<Card> cards) {
        this.rooms = rooms;
        this.cards = cards;
        for(Room room: rooms){
            if (!room.getName().equals("office") && !room.getName().equals("trailer") && !cards.isEmpty()) {
                room.setCard(cards.remove(0));
            }
        };
    }

    // Set the initial state of the board
    public void setBoard() {
        for(Room room: rooms){
            if (!room.getName().equals("office") && !room.getName().equals("trailer") && !cards.isEmpty()) {
                room.setCard(cards.remove(0));
            }
        };
    }

    
    // Update the board based on the game state
    public void updateBoard() {
        
    }

    // Getter for rooms
    public List<Room> getRooms() {
        return rooms;
    }
}

