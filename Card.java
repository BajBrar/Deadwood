import java.util.List;

public class Card {
    private String name;
    private int budget;
    private List<Role> roles; // Use a list to store multiple roles for now?
    private boolean shown;
    private int shotCount;
    
    // Constructor
    public Card(String name, int budget, List<Role> roles, int shotCount) {
        this.name = name;
        this.budget = budget;
        this.roles = roles;
        this.shown = false; // Initially the card is  face-down
        this.shotCount = shotCount;
    }

     // Methods
     public void updateShot() {
        if (shotCount > 0) {
            shotCount--;
        }
    }

    public void showCard() {
        this.shown = true;
    }

    public boolean isCompleted() {
        return shotCount == 0;
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getBudget() {
        return budget;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public boolean isShown() {
        return shown;
    }

    public int getShotCount() {
        return shotCount;
    }

}

