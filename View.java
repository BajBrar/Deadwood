import java.util.ArrayList;
interface View {
    void displayTurnOptions(int num, ArrayList<String> opts);
    void displayWinner(ArrayList<Player> players, int points);
    void displayInvalidInput();
    void displayInvalidMove();
    void displayNewCard(String name, String desc, ArrayList<String> roles, ArrayList<String> extras);
    void displayMoveOptions(ArrayList<String> adjacents);
    void displayOptions(ArrayList<String> opts);
    void displayRankUp(int player, int rank);
    void success();
    void fail();
    void sceneWrapped(String sceneName);
}

