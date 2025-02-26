import java.util.List;
import java.util.Set;
interface View {
    void displayMoveOptions(String name, Set<String> opts);
    void displayWinner(List<Player> players, int points);
    void displayInvalidInput();
    void displayInvalidMove();
    void displayNewCard(Card c);
}
