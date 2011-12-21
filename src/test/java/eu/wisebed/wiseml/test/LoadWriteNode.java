package eu.wisebed.wiseml.test;

import eu.wisebed.wiseml.controller.NodeController;
import eu.wisebed.wiseml.model.setup.Node;
import org.apache.log4j.Logger;
import org.jibx.runtime.JiBXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class LoadWriteNode {

    private static final Logger LOGGER = Logger.getLogger(LoadWriteNode.class);

    public static void main(String[] args) throws JiBXException, FileNotFoundException {

        NodeController nodeC = new NodeController();
        Node node = new Node();

        node.setId("urn:wisebed:node:tud:M4A�P20V");
        node.setGateway("1");
        node.setDescription("A fast blinking TNode");
        node.setNodeType("TNode v4");
        node.setProgramDetails("blinkfast.tnode");

        File file = new File("data.xml");

        FileInputStream fileIn = null;


        try {
            fileIn = new FileInputStream(file);
            nodeC.write(node, file);
            nodeC.load(fileIn);
        } catch (FileNotFoundException e) {
            LOGGER.error(e);
        }
    }
}
