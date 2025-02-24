import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

class Card {
    String name, img;
    int budget;
    Scene scene;
    List<Part> parts;

    public Card(String name, String img, int budget, Scene scene, List<Part> parts) {
        this.name = name;
        this.img = img;
        this.budget = budget;
        this.scene = scene;
        this.parts = parts;
    }
}

class Scene {
    int number;
    String description;

    public Scene(int number, String description) {
        this.number = number;
        this.description = description;
    }
}

class Part {
    String name, line;
    int level;

    public Part(String name, int level, String line) {
        this.name = name;
        this.level = level;
        this.line = line;
    }
}

public class CardParser {
    public static List<Card> parseCards(String filename) {
        List<Card> cards = new ArrayList<>();
        try {
            File file = new File(filename);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList cardNodes = doc.getElementsByTagName("card");
            for (int i = 0; i < cardNodes.getLength(); i++) {
                Element cardElement = (Element) cardNodes.item(i);
                String name = cardElement.getAttribute("name");
                String img = cardElement.getAttribute("img");
                int budget = Integer.parseInt(cardElement.getAttribute("budget"));
                
                Element sceneElement = (Element) cardElement.getElementsByTagName("scene").item(0);
                int sceneNumber = Integer.parseInt(sceneElement.getAttribute("number"));
                String sceneDesc = sceneElement.getTextContent().trim();
                Scene scene = new Scene(sceneNumber, sceneDesc);
                
                List<Part> parts = new ArrayList<>();
                NodeList partNodes = cardElement.getElementsByTagName("part");
                for (int j = 0; j < partNodes.getLength(); j++) {
                    Element partElement = (Element) partNodes.item(j);
                    String partName = partElement.getAttribute("name");
                    int level = Integer.parseInt(partElement.getAttribute("level"));
                    String line = partElement.getElementsByTagName("line").item(0).getTextContent().trim();
                    parts.add(new Part(partName, level, line));
                }
                
                cards.add(new Card(name, img, budget, scene, parts));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cards;
    }
    
    public static void main(String[] args) {
        List<Card> cards = parseCards("cards.xml");
        for (Card card : cards) {
            System.out.println("Card: " + card.name + ", Budget: " + card.budget);
        }
    }
}
