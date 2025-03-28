package com.traclabs.biosim.util;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class XMLUtils {
    public static String getTextofChildNode(Node parentNode, String nameOfChildToFind) {
        Node nodeToFind = getChildNodebyName(parentNode, nameOfChildToFind);
        if (nodeToFind != null)
            return nodeToFind.getTextContent();
        else
            return "";
    }

    public static Node getChildNodebyName(Node parentNode, String nameOfChildToFind) {
        NodeList childNodes = parentNode.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node currentChildNode = childNodes.item(i);
            String childName = currentChildNode.getLocalName();
            if (childName != null)
                if (childName.equals(nameOfChildToFind))
                    return currentChildNode;
        }
        return null;
    }

    public static List<Node> getChildrenbyNodeName(Node parentNode, String nameOfChildrenToFind) {
        NodeList childNodes = parentNode.getChildNodes();
        List<Node> listToReturn = new ArrayList<Node>();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node currentChildNode = childNodes.item(i);
            if (currentChildNode.getLocalName().equals(nameOfChildrenToFind))
                listToReturn.add(currentChildNode);
        }
        return listToReturn;
    }

    public static boolean getBooelanAttribute(Node node, String attributeName) {
        return node.getAttributes().getNamedItem(attributeName).getNodeValue().equals("true");
    }

    public static int getIntAttribute(Node node, String attributeName) {
        return Integer.parseInt(node.getAttributes().getNamedItem(attributeName).getNodeValue());
    }
}
