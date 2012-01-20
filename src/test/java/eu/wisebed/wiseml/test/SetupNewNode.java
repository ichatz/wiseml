package eu.wisebed.wiseml.test;

import eu.wisebed.wiseml.controller.WiseMLController;
import eu.wisebed.wiseml.model.WiseML;
import eu.wisebed.wiseml.model.scenario.DisableLink;
import eu.wisebed.wiseml.model.scenario.DisableNode;
import eu.wisebed.wiseml.model.scenario.EnableLink;
import eu.wisebed.wiseml.model.scenario.EnableNode;
import eu.wisebed.wiseml.model.scenario.Scenario;
import eu.wisebed.wiseml.model.setup.Capability;
import eu.wisebed.wiseml.model.setup.Data;
import eu.wisebed.wiseml.model.setup.Defaults;
import eu.wisebed.wiseml.model.setup.Link;
import eu.wisebed.wiseml.model.setup.Node;
import eu.wisebed.wiseml.model.setup.Origin;
import eu.wisebed.wiseml.model.setup.Position;
import eu.wisebed.wiseml.model.setup.Rssi;
import eu.wisebed.wiseml.model.setup.Setup;
import eu.wisebed.wiseml.model.setup.TimeInfo;
import eu.wisebed.wiseml.model.trace.Trace;
import org.jibx.runtime.JiBXException;
import org.joda.time.DateTime;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class setting up new nodes.
 */
public class SetupNewNode {

    public static void main(String[] args) throws JiBXException {


        try {

            //set the start and end values for nodes...
            DateTime start = new DateTime();
            DateTime end = new DateTime();

            //set the origin values for setup...
            Origin origin1 = new Origin();
            origin1.setX((float)0);
            origin1.setY((float)0);
            origin1.setZ((float)0);
            origin1.setPhi((float)5);
            origin1.setTheta((float)2);

            //set the timeinfo values for setup...
            TimeInfo timeInfo1 = new TimeInfo();
            timeInfo1.setDuration(12);
            timeInfo1.setUnit("seconds");
            //timeInfo1.setStart(start);
            //timeInfo1.setEnd(end);


            //set the position values for nodes...
            Position position = new Position();
            position.setX((float)0);
            position.setY((float)0);
            position.setZ((float)6);
            position.setPhi((float)12);
            position.setTheta((float)0);

            Position positionDef = new Position();
            positionDef.setX((float)1.0);
            positionDef.setY((float)2);
            positionDef.setZ((float)1.879);
            positionDef.setPhi((float)32);
            positionDef.setTheta((float)90);

            //set capabilities for nodes...
            Capability capability1 = new Capability();
            capability1.setName("ranger");
            capability1.setDatatype("decimal");
            capability1.setUnit("meters");

            Capability capability2 = new Capability();
            capability2.setName("noise");
            capability2.setDatatype("decimal");
            capability2.setUnit("dBm");

            Capability capability3 = new Capability();
            capability3.setName("noise");
            capability3.setDatatype("decimal");
            capability3.setUnit("dBm");

            Capability capability4 = new Capability();
            capability4.setName("temperature");
            capability4.setDatatype("decimal");
            capability4.setUnit("kelvin");

            Capability capability5 = new Capability();
            capability5.setName("light");
            capability5.setDatatype("integer");
            capability5.setUnit("lux");

            //add to list the capabilities...
            List<Capability> capabilities = new ArrayList<Capability>();
            capabilities.add(capability1);
            capabilities.add(capability2);
            capabilities.add(capability3);
            capabilities.add(capability4);
            capabilities.add(capability5);

            //set rssi for links...
            Rssi rssi = new Rssi();
            rssi.setDatatype("decimal");
            rssi.setUnit("dBm");

            //set link for nodes...
            Link link1 = new Link();
            link1.setSource("urn:wisebed:node:tud:330010");
            link1.setTarget("urn:wisebed:node:tud:330006");
            link1.setEncrypted(true);
            link1.setVirtual(false);
            link1.setRssi(rssi);
            link1.setCapabilities(capabilities);

            Link link2 = new Link();
            link2.setSource("urn:wisebed:node:tud:330020");
            link2.setTarget("urn:wisebed:node:tud:330003");
            link2.setEncrypted(true);
            link2.setVirtual(false);
            link2.setRssi(rssi);
            link2.setCapabilities(capabilities);


            //set all the values to node1...
            Node node1 = new Node();
            node1.setId("urn:wisebed:node:tud:M4A�P20V");
            node1.setPosition(position);
            node1.setGateway("1");
            node1.setProgramDetails("blinkfast.tnode");
            node1.setNodeType("TNode v4");
            node1.setDescription("A fast blinking TNode");
            node1.setCapabilities(capabilities);


            //set all the values to node2...
            Node node2 = new Node();
            node2.setId("urn:wisebed:node:tud:M4A�P50V");
            node2.setPosition(position);
            node2.setGateway("1");
            node2.setProgramDetails("blinkfast.tnode");
            node2.setNodeType("TNode v4");
            node2.setDescription("default description of a node");
            node2.setCapabilities(capabilities);


            //set all the values to node3...
            Node node3 = new Node();
            node3.setId("urn:wisebed:node:tud:M4A�P100V");
            node3.setPosition(position);
            node3.setGateway("1");
            node3.setProgramDetails("blinkfast.tnode");
            node3.setNodeType("TNode v4");
            node3.setDescription("default description of a node");
            node3.setCapabilities(capabilities);

            Node nodeDef = new Node();
            nodeDef.setPosition(positionDef);
            nodeDef.setGateway("1");
            nodeDef.setProgramDetails("blink.tnode");
            nodeDef.setNodeType("TNode v4");
            nodeDef.setDescription("default description of a node");
            nodeDef.setCapabilities(capabilities);


            //add to list the nodes...
            List<Node> list = new ArrayList<Node>();
            list.add(node1);
            list.add(node2);
            list.add(node3);

            //add to list the links...
            List<Link> listL = new ArrayList<Link>();
            listL.add(link1);
            listL.add(link2);

            //New setup values...
            Setup setup = new Setup();
            setup.setNodes(list);
            setup.setOrigin(origin1);
            setup.setInterpolation("cubic");
            setup.setDescription("this is an example WiseML with all the elements.");
            setup.setLink(listL);
            setup.setTimeinfo(timeInfo1);

            //enable node...
            EnableNode enableNode = new EnableNode();
            enableNode.setId(1);

            //disable node...
            DisableNode disableNode = new DisableNode();
            disableNode.setId(1);

            //enable link...
            EnableLink enableLink = new EnableLink();
            enableLink.setSource("urn:wisebed:node:tud:330020");
            enableLink.setTarget("urn:wisebed:node:tud:330003");

            //disable link...
            DisableLink disableLink = new DisableLink();
            disableLink.setSource("urn:wisebed:node:tud:330020");
            disableLink.setTarget("urn:wisebed:node:tud:330003");

            //add data value key...
            Data data1 = new Data();
            data1.setKey("50");
            Data data2 = new Data();
            data2.setKey("20");
            List<Data> listData = new ArrayList<Data>();
            listData.add(data1);
            listData.add(data2);
            node1.setData(listData);

            List<eu.wisebed.wiseml.model.scenario.Timestamp> listTmstmps = new ArrayList<eu.wisebed.wiseml.model.scenario.Timestamp>();


            eu.wisebed.wiseml.model.scenario.Timestamp timestamp1 = new eu.wisebed.wiseml.model.scenario.Timestamp();
            timestamp1.setValue("0");
            timestamp1.setEnableNode(enableNode);
            timestamp1.setDisableNode(disableNode);
            timestamp1.setEnableLink(enableLink);
            timestamp1.setDisableLink(disableLink);
            timestamp1.setNode(list);

            eu.wisebed.wiseml.model.scenario.Timestamp timestamp2 = new eu.wisebed.wiseml.model.scenario.Timestamp();
            timestamp2.setValue("1");
            timestamp2.setEnableNode(enableNode);
            timestamp2.setDisableNode(disableNode);
            timestamp2.setEnableLink(enableLink);
            timestamp2.setDisableLink(disableLink);
            timestamp2.setNode(list);

            listTmstmps.add(timestamp1);
            listTmstmps.add(timestamp2);

            //add scenario values...
            Scenario scen = new Scenario();
            scen.setId(1);
            scen.setTimestamp(listTmstmps);

            //add defaults values...
            Defaults defaults = new Defaults();
            defaults.setNode(nodeDef);
            defaults.setLink(link1);
            setup.setDefaults(defaults);

            Position positionTr = new Position();
            positionTr.setX((float)2);
            positionTr.setY((float)4);
            positionTr.setZ((float)6);
            positionTr.setPhi((float)0);
            positionTr.setTheta((float)6);

            Node nodeTr = new Node();
            nodeTr.setPosition(positionTr);
            nodeTr.setData(listData);

            //add trace...
            Trace trace = new Trace();
            trace.setId(1);
            trace.setTimestamp(listTmstmps);

            //set to rootnode setup, scenario, trace and defaults...
            WiseML rootNode = new WiseML();
            rootNode.setVersion("1.0");
            //rootNode.setXmlns("http://wisebed.eu/ns/wiseml/1.0");
            rootNode.setSetup(setup);
            rootNode.setScenario(scen);
            rootNode.setTrace(trace);

            WiseMLController wiseMLctrl = new WiseMLController();

            if (args[0].equals("unmarshal")) {
                // unmarshal node information from file...
                FileInputStream in = new FileInputStream("output.xml");
                rootNode = wiseMLctrl.loadWiseMLFromFile(in);
                System.out.println(scen.getId());
                System.out.println(timeInfo1.getStart());

            } else if (args[0].equals("marshal")) {
                // marshal object back out to file (with nice indentation, as UTF-8)...
                wiseMLctrl.writeWiseMLAsFile(rootNode, new File("output.xml"));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

}

