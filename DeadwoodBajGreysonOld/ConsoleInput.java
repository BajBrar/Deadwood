import java.util.*;

class ConsoleInput implements Input {
    Scanner scan = new Scanner(System.in);
    
    @Override
    public String requestInput() {
        return scan.nextLine();
    }
}
