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
    private ArrayList<Take> takes;
    //size and location variables
    private int x, y, w, h;
    
    //Constructor
    public Room(String name, ArrayList<String> adjacentRooms, ArrayList<Role> extras, ArrayList<Take> takes, int x, int y, int w, int h) {
        this.name = name;
        this.adjacentRooms = adjacentRooms;
        if (takes != null) {
            this.maxTakes = takes.size();
            this.accTakes = takes.size();
        } else {
            this.maxTakes = -1;
            this.accTakes = -1;
        }
        this.extras = extras;
        this.takes = takes;
        //I got null exception so adding this check here 
        this.extras = (extras != null) ? extras : new ArrayList<>();
        this.card = null;
        
        // Initialize position and size
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }
    
    
    //Used for parsing upgrade values from the xml file.
    public void addUpgrade(Upgrade upgrade) {
        upgrades.add(upgrade);
    }
    
    //Returns an ArrayList of the upgrade objects
    public ArrayList<Upgrade> getUpgrades() {
        return upgrades;
    }
    
    //Returns the name of the room
    public String getName() {
        return this.name;
    }
    
    //Replace the old card with a new one
    public void setCard(Card newCard) {
        this.card = newCard;
    }
    
    //Returns an ArrayList of the adjacent rooms
    public ArrayList<String> getAdjacent() {
        return this.adjacentRooms;
    }
    
    //This will add a specific player to the occupants ArrayList
    //(Represents the player in the room but not working on a role)
    public void addOccupant(Player player) {
        occupants.add(player);
    }
    
    //Removes player from occupants ArrayList
    public void remOccupant(Player player) {
        occupants.remove(player);
    }
    
    //Once a scene is finished it changes every players status to 
    //idle and moves them from the roles to the occupants ArrayList
    //Resets practice chips
    public void sceneFinished() {
        this.accTakes = -1;
        for(Role r: card.getRoles()) {
            if (r.isTaken()) {
                addOccupant(r.getPlayer());
                r.getPlayer().setCurAction("idle");
                r.getPlayer().setPracticeChips(0);
                r.leaveRole();
            }
        }
        for (Role r : this.extras) {
            if (r.isTaken()) {
                addOccupant(r.getPlayer());
                r.getPlayer().setCurAction("idle");
                r.getPlayer().setPracticeChips(0);
                r.leaveRole();
            }
        }
        card.hideCard();
    }
    
    //Removes a take from accTakes, if it is the final take then it triggers the sceneFinished function
    public void remTake() {
        if (this.accTakes - 1 == 0) {
            sceneFinished();
        } else this.accTakes = this.accTakes - 1;
    }
    
    //Returns the amount of takes left
    public int remainingTake() {
        return this.accTakes;
    }
    
    //Returns a list of the extra roles in the room
    public ArrayList<Role> getExtras()  {
        return this.extras;
    }
    
    //Returns the card in the room
    public Card getCard() {
        return this.card;
    }
    
    //Returns an ArrayList of the roles on the card but also the extras in the room
    public ArrayList<Role> getAllRoles() {
        ArrayList<Role> ret = new ArrayList<>();
        ret.addAll(this.getExtras());
        ret.addAll(getCard().getRoles());
        return ret;
    }
    
    //Returns an ArrayList of the occupants
    public ArrayList<Player> getOccupants() {
        return this.occupants;
    }
    
    //Resets the room for the next day
    public void reset() {
        this.accTakes = maxTakes;
        //this.occupants.clear();
        if (this.extras != null) {
            this.extras.clear();
        }
    }
    
    public ArrayList<Take> getTakes() {
        return takes;
    }
}

