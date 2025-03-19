import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

class GUIView extends GameView {
    private GameController gc;
    private ArrayList<JButton> buttons;
    private JFrame board;
    private JPanel buttonPanel;
    private JTextArea gameLog;


    public GUIView(GameController gc) {
        // super(gc);
        // board = new JFrame("Deadwood");
        // Will set up the visual part of the board
        // Uses data from the GameController
        this.gc = gc;
        board = new JFrame("Deadwood Game");
        board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        board.setSize(800, 600);
        board.setLayout(new BorderLayout());

        // Panel for buttons
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 3, 5, 5)); 
        buttons = new ArrayList<>();

        // Text area to log game events
        gameLog = new JTextArea();
        gameLog.setEditable(false);
        JScrollPane logScroll = new JScrollPane(gameLog);
        
        // Add components to the frame
        board.add(buttonPanel, BorderLayout.SOUTH);
        board.add(logScroll, BorderLayout.CENTER);
        
        board.setVisible(true);

    }

    private void appendToLog(String text) {
        gameLog.append(text);
        gameLog.setCaretPosition(gameLog.getDocument().getLength());
    }

    @Override
    public void displayTurnOptions(int num, ArrayList<String> opts) {
        // Create a list of buttons from the ArrayList
        // Listeners activate GameController methods
        buttonPanel.removeAll(); 
        buttons.clear();
        
        for (String opt : opts) {
            JButton button = new JButton(opt);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    gc.inputOpts(new ArrayList<>(List.of(opt))); // Pass selected option to controller
                }
            });
            buttons.add(button);
            buttonPanel.add(button);
        }
        
        buttonPanel.revalidate();
        buttonPanel.repaint();
    }
    
    @Override
    public void displayWinner(int[] winners) {

    }
    
    @Override
    public void displayInvalidInput() {
        appendToLog("Invalid input. Try again.\n");
    }
    
    @Override
    public void displayInvalidMove() {
        appendToLog("Invalid move. Choose another action.\n");
    }
    
    @Override
    public void displayNewCard(String name, String desc, ArrayList<String> roles, ArrayList<String> extras) {
        appendToLog("New scene: " + name + " - " + desc + "\nRoles: " + roles + "\nExtras: " + extras + "\n");
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
        appendToLog(out + "\n");
    }    
}