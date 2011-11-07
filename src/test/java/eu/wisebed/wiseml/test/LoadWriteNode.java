package eu.wisebed.wiseml.test;

import eu.wisebed.wiseml.controller.NodeController;
import eu.wisebed.wiseml.model.setup.Node;
import org.jibx.runtime.JiBXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class LoadWriteNode {

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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        nodeC.write(node, file);
        nodeC.load(fileIn);
        System.out.println(node.getId());
    }


}
