public class Space{
    private boolean occupied;
    private Player player;
    
    public Space() {
        this.occupied = false;
    }
    public void setSpace(Player newPlayer) {
        this.player = newPlayer;
    }
    public void setSpace() {
        this.occupied = false;
        this.player = null;
    }
    public void getSpace() {
    }
}
//Not sure this class is really needed