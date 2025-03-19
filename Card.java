import java.util.ArrayList;

public class Card {
    private String name;
    private int budget;
    private ArrayList<Role> roles; // Use a list to store multiple roles for now?
    private boolean shown;
    private int sceneNum;
    private String sceneDesc;
    private String img;
    private int x, y, w, h;
    
    // Constructor
    public Card(String name, String img,  int x, int y, int w, int h, int budget, int sceneNum, String sceneDesc, ArrayList<Role> roles) {
        this.name = name;
        this.img = img;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
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
    
    public void hideCard() {
        this.shown = false;
    }
    
    // Getters
    public String getDesc() {
        return this.sceneDesc;
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getBudget() {
        return this.budget;
    }
    
    public ArrayList<Role> getRoles() {
        return this.roles;
    }
    
    public boolean isShown() {
        return shown;
    }
    
    
    
}

