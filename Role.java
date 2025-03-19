public class Role {
    private String name;
    private int rankRequirement;
    private boolean taken;
    private Player player; // Store the player assigned to this role
    private String line;
    private int x, y, w, h;
    
    
    //Role constructor
    public Role(String name, int rankRequirement, String line, int x, int y, int w, int h) {
        this.name = name;
        this.rankRequirement = rankRequirement;
        this.taken = false;
        this.line = line;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.player = null; // No player assigned initially
    }
    
    public String getName() {
        return name;
    }
    
    public int getRankRequirement() {
        return rankRequirement;
    }
    
    public boolean isTaken() {
        return taken;
    }
    
    public String getLine() {
        return line;
    }
    
    public Player getPlayer() {
        return player;
    }
    
    //Puts the player in the role
    public void takeRole(Player player) {
        if (this.taken) {
            throw new IllegalStateException("Role is already taken!");
        }
        this.taken = true;
        this.player = player;
    }
    
    //Removes the player from the role
    public void leaveRole() {
        this.taken = false;
        player.setCurAction("idle");
        this.player = null;
    }
}

