import java.util.*;

public class Role {
    private String name;
    private int rankRequirement;
    private boolean taken;

    public Role(String name, int rankRequirement) {
        this.name = name;
        this.rankRequirement = rankRequirement;
        this.taken = false;
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

