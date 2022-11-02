package me.marvinweber.isaac.mapgen.rooms.layouts;

import me.marvinweber.isaac.mapgen.Room;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class RoomsParser {
    public static ArrayList<RoomLayout> parse(STB filename) {
        ArrayList<RoomLayout> roomLayouts = new ArrayList<>();

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(new File(filename.path));

            doc.getDocumentElement().normalize();

            NodeList list = doc.getElementsByTagName("room");

            for (int i = 0; i < list.getLength(); i++) {

                Node roomNode = list.item(i);

                if (roomNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element room = (Element) roomNode;

                    ArrayList<RoomLayout.Door> doors = new ArrayList<>();
                    NodeList doorNodes = room.getElementsByTagName("door");

                    for (int j = 0; j < doorNodes.getLength(); j++) {
                        Node doorNode = doorNodes.item(j);
                        if (doorNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element door = (Element) doorNode;
                            Room.Face pos;

                            if (Objects.equals(door.getAttribute("x"), "6") && Objects.equals(door.getAttribute("y"), "-1")) {
                                pos = Room.Face.NORTH;
                            } else if (Objects.equals(door.getAttribute("x"), "6") && Objects.equals(door.getAttribute("y"), "7")) {
                                pos = Room.Face.SOUTH;
                            } else if (Objects.equals(door.getAttribute("x"), "-1") && Objects.equals(door.getAttribute("y"), "3")) {
                                pos = Room.Face.WEST;
                            } else if (Objects.equals(door.getAttribute("x"), "13") && Objects.equals(door.getAttribute("y"), "3")) {
                                pos = Room.Face.EAST;
                            } else {
                                pos = Room.Face.NORTH;
                            }
                            doors.add(new RoomLayout.Door(Boolean.parseBoolean(door.getAttribute("exists")), pos));
                        }
                    }

                    ArrayList<RoomLayout.Spawn> spawns = new ArrayList<>();
                    NodeList spawnNodes = room.getElementsByTagName("spawn");

                    for (int k = 0; k < spawnNodes.getLength(); k++) {
                        Node spawnNode = spawnNodes.item(k);
                        if (spawnNode == null) continue;
                        if (spawnNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element spawn = (Element) spawnNode;

                            Node entityNode = spawn.getElementsByTagName("entity").item(0);

                            Element entityElement = (Element) entityNode;
                            RoomLayout.Entity entity = new RoomLayout.Entity(entityElement.getAttribute("type"), entityElement.getAttribute("variant"), entityElement.getAttribute("subtype"), entityElement.getAttribute("weight"));

                            spawns.add(new RoomLayout.Spawn(spawn.getAttribute("x"), spawn.getAttribute("y"), entity));


                        }
                    }

                    RoomLayout roomLayout = new RoomLayout(
                            room.getAttribute("variant"),
                            room.getAttribute("name"),
                            room.getAttribute("type"),
                            room.getAttribute("subtype"),
                            room.getAttribute("shape"),
                            room.getAttribute("width"),
                            room.getAttribute("height"),
                            room.getAttribute("difficulty"),
                            room.getAttribute("weight"),
                            doors,
                            spawns

                    );
                    roomLayouts.add(roomLayout);

                }
            }

        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
        return roomLayouts;
    }

    public enum STB {
        SPECIAL_ROOMS("room_data/00.special rooms.xml"),
        BASEMENT_ROOMS("room_data/01.basement.xml");

        private final String path;

        STB(String path) {
            this.path = path;
        }
    }
}
