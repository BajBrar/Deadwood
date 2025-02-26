public class Role {
    private String name;
    private int rankRequirement;
    private boolean taken;
    private Player player; // Store the player assigned to this role
    private String line;
    private boolean starring;
    private Card sceneCard;

    public Role(String name, int rankRequirement, String line) {
        this.name = name;
        this.rankRequirement = rankRequirement;
        this.taken = false;
        this.line = line;
        this.player = null; // No player assigned initially
        this.starring = starring;
        this.sceneCard = sceneCard;
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

    public void takeRole(Player player) {
        if (this.taken) {
            throw new IllegalStateException("Role is already taken!");
        }
        this.taken = true;
        this.player = player;
        player.takeRole(this); // Assign role to the player
    }

    public void leaveRole() {
        if (!this.taken) {
            throw new IllegalStateException("Role is not currently taken!");
        }
        this.taken = false;
        if (this.player != null) {
            this.player.leaveRole(); // Remove role from player
        }
        this.player = null;
    }
    public boolean isStarring() {
        return starring;
    }
    public int getSceneBudget() {
        return sceneCard.getSceneBudget(); 
    }
}

