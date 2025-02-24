import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

class Set {
    String name;
    List<String> neighbors = new ArrayList<>();
    List<Take> takes = new ArrayList<>();
    List<Part> parts = new ArrayList<>();
}

class Take {
    int number;
}

class Part {
    String name;
    int level;
    String line;
}

public class BoardParser {
    public static void main(String[] args) {
        try {
            File inputFile = new File("board.xml");
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
                        set.parts.add(part);
                    }
                    
                    // Print parsed information
                    System.out.println("Set: " + set.name);
                    System.out.println("  Neighbors: " + set.neighbors);
                    System.out.println("  Takes: " + set.takes.size());
                    System.out.println("  Parts: " + set.parts.size());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

