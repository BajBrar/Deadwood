import java.util.List;

public class Card {
    private String name;
    private int budget;
    private List<Role> roles; // Use a list to store multiple roles for now?
    private boolean shown;
    private int sceneNum;
    private String sceneDesc;
    private String img;
    
    // Constructor
    public Card(String name, String img, int budget, int sceneNum, String sceneDesc, List<Role> roles) {
        this.name = name;
        this.img = img;
        this.budget = budget;
        this.sceneNum = sceneNum;
        this.sceneDesc = sceneDesc;
        this.roles = roles;

        this.shown = false; // Initially the card is  face-down
  
    }

     // Methods
     

    public void showCard() {
        this.shown = true;
    }

    

    // Getters
    public String getName() {
        return this.name;
    }

    public int getBudget() {
        return this.budget;
    }

    public List<Role> getRoles() {
        return this.roles;
    }

    public boolean isShown() {
        return shown;
    }

   

}

