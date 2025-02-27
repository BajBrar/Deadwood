import java.util.List;
import java.util.Set;
interface View {
    void displayMoveOptions(int num, Set<String> opts);
    void displayWinner(List<Player> players, int points);
    void displayInvalidInput();
    void displayInvalidMove();
    void displayNewCard(Card c);
}
