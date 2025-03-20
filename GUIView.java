import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import java.util.HashMap;

class GUIView extends GameView {
    private GameController gc;
    private JFrame board;
    private JPanel buttonPanel;
    private JTextArea gameLog;
    private JLayeredPane bPane;
    private JLabel boardLabel;
    private JLabel mLabel;
    private JLabel scoreboard;
    ImageIcon clapper = new ImageIcon("shot.png");
    ImageIcon icon = new ImageIcon("board.jpg");
    HashMap<String, JLabel> allShots = new HashMap<String, JLabel>();
    HashMap<String, JLabel> cards = new HashMap<>();


    public GUIView(GameController gc, int playerCount) {
        // Will set up the visual part of the board
        // Uses data from the GameController
        this.gc = gc;
        board = new JFrame("Deadwood Game");
        scoreboard = new JLabel();
        bPane = new JLayeredPane();
        boardLabel = new JLabel();
        mLabel = new JLabel("MENU");
        board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        board.setLayout(new BorderLayout());
        boardLabel.setIcon(icon);
        boardLabel.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());
        bPane.add(boardLabel, 0);
        board.setSize(icon.getIconWidth() + 200, icon.getIconHeight() + 35);
        
        
        mLabel.setBounds(icon.getIconWidth()+80,0,200,20);
        bPane.add(mLabel,2);
        
        buttonPanel = new JPanel();
        buttonPanel.setBounds(icon.getIconWidth(), 25, 200, icon.getIconHeight() - 25);
        buttonPanel.setBackground(Color.LIGHT_GRAY);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, 1));
        bPane.add(buttonPanel, 2);
        scoreboard.setBounds(icon.getIconWidth(), icon.getIconHeight() - 200, 200, 200);
        scoreboard.setBackground(Color.BLUE);
        scoreboard.setVisible(true);
        scoreboard.setOpaque(true);
        scoreboard.setLayout(new BorderLayout());
        scoreboard.add(new JLabel(), BorderLayout.NORTH);
        bPane.add(scoreboard, Integer.valueOf(6));
        


        // Add components to the frame
        board.add(bPane, BorderLayout.CENTER);
        board.setVisible(true);
        
    }
    
    
    

    public void remShot(String roomName, int num) {
        JLabel shotLabel = allShots.get(roomName + num);
        if (shotLabel != null) {
            shotLabel.setVisible(false);   
            bPane.revalidate();
            bPane.repaint();
        }
    }


    
    public void setShots(String roomName, ArrayList<Take> takes) {
        for (Take t : takes) {
            String key = roomName + t.number;
            JLabel shotLabel = new JLabel();
            shotLabel.setIcon(new ImageIcon("shot.png"));
            shotLabel.setBounds(t.x, t.y, clapper.getIconWidth(), clapper.getIconHeight());
            shotLabel.setVisible(true);
            shotLabel.setOpaque(true);
            allShots.put(key, shotLabel);
            bPane.add(allShots.get(roomName + t.number), Integer.valueOf(3));
        }
        bPane.revalidate();
        bPane.repaint();
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
    }    
}