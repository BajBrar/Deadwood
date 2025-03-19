import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

class GUIView extends GameView {
    private GameController gc;
    private JFrame board;
    private JPanel buttonPanel;
    private JTextArea gameLog;
    private JLayeredPane bPane;
    private JLabel boardLabel;
    private JLabel mLabel;
    private JTable scoreboard;

    public GUIView(GameController gc, int playerCount) {
        // Will set up the visual part of the board
        // Uses data from the GameController
        this.gc = gc;
        int pCount = playerCount();
        board = new JFrame("Deadwood Game");
        board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        bPane = board.getLayeredPane();
        boardLabel = new JLabel();
        ImageIcon icon = new ImageIcon("board.jpg");
        boardLabel.setIcon(icon);
        boardLabel.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());
        bPane.add(boardLabel, 0);
        board.setSize(icon.getIconWidth() + 200, icon.getIconHeight() + 35);
        
        
        mLabel = new JLabel("MENU");
        mLabel.setBounds(icon.getIconWidth()+80,0,200,20);
        bPane.add(mLabel,2);
        
    
        buttonPanel = new JPanel();
        buttonPanel.setBounds(icon.getIconWidth(), 25, 200, icon.getIconHeight() - 25);
        buttonPanel.setBackground(Color.green);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, 1));
        bPane.add(buttonPanel, 2);
        
        
        
        
        
        // Text area to log game events
        gameLog = new JTextArea();
        gameLog.setEditable(false);
        JScrollPane logScroll = new JScrollPane(gameLog);
        
        // Add components to the frame

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
        for (String opt : opts) {
            JButton button = new JButton(opt);
            button.setPreferredSize(new Dimension(200, 25));
            button.setMaximumSize(new Dimension(200,25));
            button.setMinimumSize(new Dimension(200,25));
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    gc.turn(opt); // Pass selected option to controller
                }
            });
            
            buttonPanel.add(button);
        }
        
        buttonPanel.revalidate();
        buttonPanel.validate();
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
    public String displayOptions(ArrayList<String> opts) {
        String input = null;
        while (input == null) { 
            Object[] optArr = opts.toArray();
            input = (String) JOptionPane.showInputDialog(null, "Choose one of the following:", "Input" , JOptionPane.INFORMATION_MESSAGE, null, optArr , optArr[0]);
        }
        return input;
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
    public int playerCount() {
        return 0;
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