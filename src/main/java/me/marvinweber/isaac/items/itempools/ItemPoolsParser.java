package me.marvinweber.isaac.items.itempools;

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
import java.util.HashMap;


public class ItemPoolsParser {
    public static String FILENAME = "item_pool/itempools.xml";

    public static HashMap<String, ItemPool> parse() {
        HashMap<String, ItemPool> itemPools = new HashMap<>();

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(new File(FILENAME));

            doc.getDocumentElement().normalize();

            NodeList list = doc.getElementsByTagName("Pool");

            for (int i = 0; i < list.getLength(); i++) {
                Node poolNode = list.item(i);

                if (poolNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element pool = (Element) poolNode;

                    ArrayList<ItemPool.Item> items = new ArrayList<>();
                    NodeList itemNodes = pool.getElementsByTagName("Item");

                    for (int j = 0; j < itemNodes.getLength(); j++) {
                        Node itemNode = itemNodes.item(j);
                        if (itemNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element item = (Element) itemNode;
                            items.add(new ItemPool.Item(item.getAttribute("Id"), item.getAttribute("Weight")));
                        }
                    }


                    ItemPool itemPool = new ItemPool(pool.getAttribute("Name"), items);
                    itemPools.put(itemPool.name, itemPool);

                }
            }

        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
        return itemPools;
    }
}
