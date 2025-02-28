public class Role {
    private String name;
    private int rankRequirement;
    private boolean taken;
    private Player player; // Store the player assigned to this role
    private String line;

    public Role(String name, int rankRequirement, String line) {
        this.name = name;
        this.rankRequirement = rankRequirement;
        this.taken = false;
        this.line = line;
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

    public void takeRole(Player player) {
        if (this.taken) {
            throw new IllegalStateException("Role is already taken!");
        }
        this.taken = true;
        this.player = player;
    }

    public void leaveRole() {
        this.taken = false;
        this.player = null;
    }
}

