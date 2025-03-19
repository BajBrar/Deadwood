import java.util.ArrayList;
import javax.swing.*;

class GUIView extends GameView {
GameController gc;
ArrayList<JButton> buttons;
JFrame board;


    public GUIView(GameController gc) {
        // super(gc);
        board = new JFrame("Deadwood");
        // Will set up the visual part of the board
        // Uses data from the GameController




    }

    @Override
    public void displayTurnOptions(int num, ArrayList<String> opts) {
        // Create a list of buttons from the ArrayList
        // Listeners activate GameController methods
    }
    
    @Override
    public void displayWinner(int[] winners) {
        
    }
    
    @Override
    public void displayInvalidInput() {
        
    }
    
    @Override
    public void displayInvalidMove() {
        
    }
    
    @Override
    public void displayNewCard(String name, String desc, ArrayList<String> roles, ArrayList<String> extras) {
        
    }
    
    @Override
    public void displayMoveOptions(ArrayList<String> adjacents) {
        
    }
    
    @Override
    public void displayOptions(ArrayList<String> opts) {
        
    }
    
    @Override
    public void displayRankUp(int player, int rank, int newRank) {
        
    }
    
    @Override
    public void success() {
        
    }
    
    @Override
    public void fail() {
        
    }
    
    @Override
    public void sceneWrapped(String sceneName) {
        
    }
    
    @Override
    public void playerCount() {
        
    }
    
    @Override
    public void displayRehearse(int player, int chips) {
        
    }
    
    @Override
    public void displayActive(int player, String roomName, int credit, int dollar, String out) {
        
    }
    
    @Override
    public void displayPlayer(String out) {
        
    }    
}