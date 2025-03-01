public class Deadwood {
    public static void main(String[] args) {
        GameController game = new GameController();
        game.StartGame("cards.xml", "board.xml");
    }
}
