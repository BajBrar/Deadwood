public class LocationManager{
    private Board board;

    public LocationManager(Board board){
        this.board = board;
    }

    public boolean validMove(String destination, Board board, Player player) {
        return true;
    }

    public void move(String destination, Board board, Player player) {
    }

    public void getAdjRooms(Board board) {
    }

    public Role getSpace(String curPos){
        return new Role("", 0, "");
    }

    public void newDay(){
        //set all player location to trailer
    }

    public boolean getRoom(Player player, Board board, String destination){
        return true;
    }

    
}
//Having some trouble with this without first parsing the XML file