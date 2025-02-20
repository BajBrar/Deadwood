//Anything that needs to be printed needs to go in this class to make it easier for interface implementation later on and its easier for the controller to call the view to print things.

//The example thats in assignment 2 description all of those system.out.println's should be in this class.
import java.util.Scanner;

public class GameView {
    private Scanner scanner;

    public GameView() {
        this.scanner = new Scanner(System.in);
    }

    //add all the display methods here
    public void displayGameState() {
    }

    public void playerPrompt() {
    }

    public void displayWinner() {
    }
    

    //close the scanner 
    public void closeScanner() {
        scanner.close();
    }
}
