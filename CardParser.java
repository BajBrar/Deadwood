import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.*;
import org.w3c.dom.*;


class Scene {
    int number;
    String description;
    int x, y, w, h;
    
    public Scene(int number, String description) {
        this.number = number;
        this.description = description;
    }
}

public class CardParser {
    public static ArrayList<Card> parseCards(String filename) {
        ArrayList<Card> cards = new ArrayList<>();
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
                int x = Integer.parseInt(cardElement.getAttribute("x"));
                int y = Integer.parseInt(cardElement.getAttribute("y"));
                int h = Integer.parseInt(cardElement.getAttribute("h"));
                int w = Integer.parseInt(cardElement.getAttribute("w"));
                
                Element sceneElement = (Element) cardElement.getElementsByTagName("scene").item(0);
                int sceneNumber = Integer.parseInt(sceneElement.getAttribute("number"));
                String sceneDesc = sceneElement.getTextContent().trim();
                Scene scene = new Scene(sceneNumber, sceneDesc);
                
                ArrayList<Role> roles = new ArrayList<>();
                NodeList partNodes = cardElement.getElementsByTagName("part");
                for (int j = 0; j < partNodes.getLength(); j++) {
                    Element partElement = (Element) partNodes.item(j);
                    String partName = partElement.getAttribute("name");
                    int level = Integer.parseInt(partElement.getAttribute("level"));
                    String line = partElement.getElementsByTagName("line").item(0).getTextContent().trim();
                    roles.add(new Role(partName, level, line));
                }
                
                cards.add(new Card(name, img, budget, x, y, h, w, scene.number, scene.description, roles));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cards;
    }
}
