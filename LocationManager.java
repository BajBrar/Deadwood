import java.util.ArrayList;
public class LocationManager{
    private Board board;

    public LocationManager(Board board){
        this.board = board;
    }

    public void move(String destination, Player player) {
        if (board.getRoomByName(destination) != null) {
            player.updatePos(destination);
            getRoom(destination).addOccupant(player);
            getRoom(player.getStartPosition()).remOccupant(player);
        } else {
            for (Room room: board.getRooms()) { 
                if (!room.getName().equalsIgnoreCase("trailer") && !room.getName().equalsIgnoreCase("office")) {
                    for (Role role : room.getExtras()) {
                        if (destination.equals(role.getName())) {
                            player.updatePos(destination);
                            role.takeRole(player);
                            getRoom(player.getStartPosition()).remOccupant(player);
                        }
                    }
                    for (Role role : room.getCard().getRoles()) {
                        if (destination.equals(role.getName())) {
                            player.updatePos(destination);
                            role.takeRole(player);
                            getRoom(player.getStartPosition()).remOccupant(player);
                        }
                    }
                }
            }
        }
    }

    public ArrayList<String> getAdjRooms(String roomName) {
        if (board.getRoomByName(roomName) != null) {
            return board.getRoomByName(roomName).getAdjacent();
        } else return null;
    }

    public Role getRole(Player player){
        for (Room room: board.getRooms()) {
            for (Role role : room.getAllRoles()) {
                if (role.isTaken() && role.getPlayer().equals(player)) {
                    return role;
                }
            }
        }
        return null;
    }

    public void newDay(){
        board.setBoard();
    }

    public Room getRoom(String roomName){
        return board.getRoomByName(roomName);
    }
}
