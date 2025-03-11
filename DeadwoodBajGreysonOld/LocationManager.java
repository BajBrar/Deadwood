import java.util.ArrayList;
public class LocationManager{
    private Board board;
    
    //Constructor
    public LocationManager(Board board){
        this.board = board;
    }
    
    //Using the name of the room/role it updates the board and player to reflect that.
    public void move(String destination, Player player) {
        if (board.getRoomByName(destination) != null) {
            playersRoom(player).remOccupant(player);
            player.updatePos(destination);
            getRoom(destination).addOccupant(player);
        } else {
            for (Room room: board.getRooms()) { 
                if (!room.getName().equalsIgnoreCase("trailer") && !room.getName().equalsIgnoreCase("office")) {
                    for (Role role : room.getExtras()) {
                        if (destination.equals(role.getName())) {
                            player.updatePos(destination);
                            role.takeRole(player);
                            playersRoom(player).remOccupant(player);
                        }
                    }
                    for (Role role : room.getCard().getRoles()) {
                        if (destination.equals(role.getName())) {
                            player.updatePos(destination);
                            role.takeRole(player);
                            playersRoom(player).remOccupant(player);
                        }
                    }
                }
            }
        }
    }
    
    //Returns an ArrayList of the adjacent rooms by the rooms name
    public ArrayList<String> getAdjRooms(String roomName) {
        if (board.getRoomByName(roomName) != null) {
            return board.getRoomByName(roomName).getAdjacent();
        } else return null;
    }
    
    //Returns the role that the player is currently in
    public Role getRole(Player player){
        for (Room room: board.getRooms()) {
            if (!room.getName().equalsIgnoreCase("office") && !room.getName().equalsIgnoreCase("trailer")) {
                for (Role role : room.getAllRoles()) {
                    if (role.isTaken() && role.getPlayer().equals(player)) {
                        return role;
                    }
                }
            }
        }
        return null;
    }
    
    //Sets the board up for a new day
    public void newDay(){
        board.setBoard();
    }
    
    //Returns the room that the player is currently in
    public Room playersRoom(Player player) {
        return board.getRoomByPlayer(player);
    }
    
    //Returns a specific room by the rooms name
    public Room getRoom(String roomName){
        return board.getRoomByName(roomName);
    }
    
    //Returns an ArrayList of all the rooms on the board
    public ArrayList<Room> allRooms() {
        return board.getRooms();
    }
}
