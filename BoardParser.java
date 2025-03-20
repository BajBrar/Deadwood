import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;

class Set {
    String name;
    ArrayList<String> neighbors = new ArrayList<>();
    ArrayList<Take> takes = new ArrayList<>();
    ArrayList<Role> parts = new ArrayList<>();
    int x, y, w, h;
}

class Take {
    int number;
    int x, y, w, h;
}

class Part {
    String name;
    int level;
    String line;
    int x, y, w, h;
}

public class BoardParser {
    public static ArrayList<Room> parseBoard(String fileName) {
        ArrayList<Room> rooms = new ArrayList<>();
        try {
            File inputFile = new File(fileName);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(inputFile);
            doc.getDocumentElement().normalize();
            
            NodeList setList = doc.getElementsByTagName("set");
            
            for (int i = 0; i < setList.getLength(); i++) {
                Node setNode = setList.item(i);
                if (setNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element setElement = (Element) setNode;
                    Set set = new Set();
                    set.name = setElement.getAttribute("name");
                    
                    // Parse neighbors
                    NodeList neighbors = setElement.getElementsByTagName("neighbor");
                    for (int j = 0; j < neighbors.getLength(); j++) {
                        Element neighborElement = (Element) neighbors.item(j);
                        set.neighbors.add(neighborElement.getAttribute("name"));
                    }
                    
                    // Parse takes
                    NodeList takes = setElement.getElementsByTagName("take");
                    for (int j = 0; j < takes.getLength(); j++) {
                        Element takeElement = (Element) takes.item(j);
                        Take take = new Take();
                        take.number = Integer.parseInt(takeElement.getAttribute("number"));
                        
                        Element takeArea = (Element) takeElement.getElementsByTagName("area").item(0);
                        if (takeArea != null) {
                            take.x = Integer.parseInt(takeArea.getAttribute("x"));
                            take.y = Integer.parseInt(takeArea.getAttribute("y"));
                            take.w = Integer.parseInt(takeArea.getAttribute("w"));
                            take.h = Integer.parseInt(takeArea.getAttribute("h"));
                        }
                        set.takes.add(take);
                    }
                    
                    // Parse parts
                    NodeList parts = setElement.getElementsByTagName("part");
                    for (int j = 0; j < parts.getLength(); j++) {
                        Element partElement = (Element) parts.item(j);
                        Part part = new Part();
                        part.name = partElement.getAttribute("name");
                        part.level = Integer.parseInt(partElement.getAttribute("level"));
                        part.line = partElement.getElementsByTagName("line").item(0).getTextContent();
                        
                        Element partArea = (Element) partElement.getElementsByTagName("area").item(0);
                        if (partArea != null) {
                            part.x = Integer.parseInt(partArea.getAttribute("x"));
                            part.y = Integer.parseInt(partArea.getAttribute("y"));
                            part.w = Integer.parseInt(partArea.getAttribute("w"));
                            part.h = Integer.parseInt(partArea.getAttribute("h"));
                        }
                        
                        set.parts.add(new Role(part.name, part.level, part.line, part.x, part.y, part.w, part.h));
                    }
                    
                    // Parse x, y, w, h for the set itself
                    Element areaElement = (Element) setElement.getElementsByTagName("area").item(0);
                    if (areaElement != null) {
                        set.x = Integer.parseInt(areaElement.getAttribute("x"));
                        set.y = Integer.parseInt(areaElement.getAttribute("y"));
                        set.w = Integer.parseInt(areaElement.getAttribute("w"));
                        set.h = Integer.parseInt(areaElement.getAttribute("h"));
                    }
                    
                    rooms.add(new Room(set.name, set.neighbors, set.parts, set.takes, set.x, set.y, set.w, set.h));
                }
            }
            
            // Parse trailer
            NodeList trailerList = doc.getElementsByTagName("trailer");
            for (int i = 0; i < trailerList.getLength(); i++) {
                Node trailerNode = trailerList.item(i);
                if (trailerNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element trailerElement = (Element) trailerNode;
                    ArrayList<String> neighbors = new ArrayList<>();
                    NodeList neighborList = trailerElement.getElementsByTagName("neighbor");
                    for (int j = 0; j < neighborList.getLength(); j++) {
                        neighbors.add(((Element) neighborList.item(j)).getAttribute("name"));
                    }
                    rooms.add(new Room("Trailer", neighbors, null, null, 0, 0,0, 0));
                }
            }
            
            // Parse Office
            NodeList officeList = doc.getElementsByTagName("office");
            for (int i = 0; i < officeList.getLength(); i++) {
                Node officeNode = officeList.item(i);
                if (officeNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element officeElement = (Element) officeNode;
                    ArrayList<String> neighbors = new ArrayList<>();
                    ArrayList<Upgrade> upgrades = new ArrayList<>();
                    
                    NodeList neighborNodes = officeElement.getElementsByTagName("neighbor");
                    for (int j = 0; j < neighborNodes.getLength(); j++) {
                        Element neighborElement = (Element) neighborNodes.item(j);
                        neighbors.add(neighborElement.getAttribute("name"));
                    }
                    
                    // Parse Upgrades
                    NodeList upgradeNodes = officeElement.getElementsByTagName("upgrade");
                    for (int j = 0; j < upgradeNodes.getLength(); j++) {
                        Element upgradeElement = (Element) upgradeNodes.item(j);
                        int level = Integer.parseInt(upgradeElement.getAttribute("level"));
                        String currency = upgradeElement.getAttribute("currency");
                        int amount = Integer.parseInt(upgradeElement.getAttribute("amt"));
                        upgrades.add(new Upgrade(level, currency, amount));
                    }
                    
                    Room office = new Room("Office", neighbors, null, null, 0, 0, 0, 0);
                    for (Upgrade upgrade : upgrades) {
                        office.addUpgrade(upgrade);
                    }
                    
                    rooms.add(office);
                }
            } 
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rooms; 
    }
}
