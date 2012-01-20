package eu.wisebed.wiseml.test;

import eu.wisebed.wiseml.model.setup.Capability;
import eu.wisebed.wiseml.model.setup.Defaults;
import eu.wisebed.wiseml.model.setup.Link;
import eu.wisebed.wiseml.model.setup.Node;
import eu.wisebed.wiseml.model.setup.Position;
import eu.wisebed.wiseml.model.setup.Rssi;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.JiBXException;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * This class set up defaults for wiseML.
 */

public class SetupWiseMLDefaults {

    public static void main(String[] args) {

        try {

            //set the position values for node...
            Position position = new Position();
            position.setX((float) 0);
            position.setY((float) 0);
            position.setZ((float) 6);
            position.setPhi((float)12);
            position.setTheta((float)0);

            //set capabilities for node...
            Capability capability = new Capability();
            capability.setName("ranger");
            capability.setDatatype("decimal");
            capability.setUnit("meters");

            List<Capability> capabilities = new ArrayList<Capability>();
            capabilities.add(capability);

            //set all the values to node...
            Node node = new Node();
            node.setId("urn:wisebed:node:tud:M4A�P20V");
            node.setPosition(position);
            node.setGateway("1");
            node.setProgramDetails("blinkfast.tnode");
            node.setNodeType("TNode v4");
            node.setDescription("A fast blinking TNode");
            node.setCapabilities(capabilities);

            //set rssi for link...
            Rssi rssi = new Rssi();
            rssi.setDatatype("decimal");
            rssi.setUnit("dBm");

            //set link...
            Link link = new Link();
            link.setSource("urn:wisebed:node:tud:330010");
            link.setTarget("urn:wisebed:node:tud:330006");
            link.setEncrypted(true);
            link.setVirtual(false);
            link.setRssi(rssi);
            link.setCapabilities(capabilities);

            Defaults defaults = new Defaults();
            defaults.setNode(node);
            defaults.setLink(link);

            IBindingFactory bfact = BindingDirectory.getFactory(Defaults.class);
            IMarshallingContext mctx = bfact.createMarshallingContext();
            mctx.setIndent(5);
            FileOutputStream out = new FileOutputStream("output2.xml");
            mctx.setOutput(out, null);
            mctx.marshalDocument(defaults);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (
                JiBXException e) {
            e.printStackTrace();
            System.exit(1);
        }


    }
}
