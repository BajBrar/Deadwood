
public class Role {
    private String name;
    private int rankRequirement;
    private boolean taken;
    private Player player;
    private String line;
    
    public Role(String name, int rankRequirement, String line) {
        this.name = name;
        this.rankRequirement = rankRequirement;
        this.taken = false;
        this.line = line;
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

    public void takeRole() {
        this.taken = true;
    }

    public void leaveRole() {
        this.taken = false;
    }

}

