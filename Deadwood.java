public class Deadwood {
    public static void main(String[] args) {
        GameController game = new GameController();
        game.StartGame(Integer.parseInt(args[0]), args[1], args[2]);
    }
}
