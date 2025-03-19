import java.util.ArrayList;
interface View {
    void displayTurnOptions(int num, ArrayList<String> opts);
    void displayWinner(int[] winners);
    void displayInvalidInput();
    void displayInvalidMove();
    void displayNewCard(String name, String desc, ArrayList<String> roles, ArrayList<String> extras);
    void displayMoveOptions(ArrayList<String> adjacents);
    String displayOptions(ArrayList<String> opts);
    void displayRankUp(int player, int rank, int newRank);
    void success();
    void fail();
    void sceneWrapped(String sceneName);
    int playerCount();
    void displayRehearse(int player, int chips);
    void displayActive(int player, String roomName, int credit, int dollar, String out);
    void displayPlayer(String out);
    void displayDayEnd(int n);
}

