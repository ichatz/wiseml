package eu.wisebed.wiseml.test.uth;

import eu.wisebed.wiseml.model.WiseML;
import eu.wisebed.wiseml.model.scenario.Timestamp;
import eu.wisebed.wiseml.model.setup.Capability;
import eu.wisebed.wiseml.model.setup.Data;
import eu.wisebed.wiseml.model.setup.Node;
import eu.wisebed.wiseml.model.setup.Origin;
import eu.wisebed.wiseml.model.setup.Position;
import eu.wisebed.wiseml.model.setup.Setup;
import eu.wisebed.wiseml.model.setup.TimeInfo;
import eu.wisebed.wiseml.model.trace.Trace;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.JiBXException;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class reads a file that has nodes, timestamps and capabilities values and
 * creates an xml file of setup these nodes and a trace of these timestamps.
 */
public class ReadFileAsTrace {

    static WiseML rootNode;

    /**
     * Default construstor.
     */
    public ReadFileAsTrace() {
        rootNode = new WiseML();
        constructSetup();
        constructTrace();
    }

    public WiseML getRootNode() {
        return rootNode;
    }

    /**
     * Construct new setup.
     */
    public void constructSetup() {

        // initialize new list of nodes...
        List<Node> listNodes = new ArrayList<Node>();

        // create new node origin...
        Origin origin1 = new Origin();
        origin1.setX((float)0);
        origin1.setY((float)0);
        origin1.setZ((float)0);
        origin1.setPhi(0);
        origin1.setTheta(0);

        // create new setup timeinfo..
        TimeInfo timeInfo = new TimeInfo();
        timeInfo.setDuration(85 * 24);
        timeInfo.setUnit("hours");
        timeInfo.setStart("2/7/2009 23:00");
        timeInfo.setEnd("24/9/2009 13:00");

        // create new setup and add it to wiseml...
        Setup setup = new Setup();
        setup.setNodes(listNodes);
        setup.setOrigin(origin1);
        setup.setInterpolation("metrics");
        setup.setDescription("this is an example WiseML with humidity metrics.");
        setup.setTimeinfo(timeInfo);
        rootNode.setSetup(setup);

        // create new node position...
        Position position = new Position();
        position.setX((float)0);
        position.setY((float)0);
        position.setZ((float)0);
        position.setPhi(0);
        position.setTheta(0);

        // initialize new list of capabilities...
        List<Capability> list1 = new ArrayList<Capability>();
        Capability cap1 = new Capability();
        Capability cap2 = new Capability();
        Capability cap3 = new Capability();
        Capability cap4 = new Capability();
        Capability cap5 = new Capability();
        cap1.setName("urn:wisebed:node:capability:humidity-10");
        cap1.setUnit("Humidity");
        cap1.setDatatype("%");
        cap2.setName("urn:wisebed:node:capability:humidity-20");
        cap2.setUnit("Humidity");
        cap2.setDatatype("%");
        cap3.setName("urn:wisebed:node:capability:humidity-30");
        cap3.setUnit("Humidity");
        cap3.setDatatype("%");
        cap4.setName("urn:wisebed:node:capability:humidity-50");
        cap4.setUnit("Humidity");
        cap4.setDatatype("%");
        cap5.setName("urn:wisebed:node:capability:humidity-90");
        cap5.setUnit("Humidity");
        cap5.setDatatype("%");
        list1.add(cap1);
        list1.add(cap2);
        list1.add(cap3);
        list1.add(cap4);
        list1.add(cap5);

        // initialize new list of capabilities...
        List<Capability> list2 = new ArrayList<Capability>();
        Capability capability1 = new Capability();
        Capability capability2 = new Capability();
        Capability capability3 = new Capability();
        Capability capability4 = new Capability();
        Capability capability5 = new Capability();
        capability1.setName("urn:wisebed:node:capability:humidity-0-15");
        capability1.setUnit("Humidity");
        capability1.setDatatype("%");
        capability2.setName("urn:wisebed:node:capability:humidity-15-30");
        capability2.setUnit("Humidity");
        capability2.setDatatype("%");
        capability3.setName("urn:wisebed:node:capability:airhumidity");
        capability3.setUnit("Humidity");
        capability3.setDatatype("%");
        capability4.setName("urn:wisebed:node:capability:temperature");
        capability4.setUnit("C");
        capability4.setDatatype("%");
        capability5.setName("urn:wisebed:node:capability:battery");
        capability5.setUnit("V");
        capability5.setDatatype("%");
        list2.add(capability1);
        list2.add(capability2);
        list2.add(capability3);
        list2.add(capability4);
        list2.add(capability5);

        // create node Cropsense probe 1...
        Node node1 = new Node();
        node1.setDescription("Cropsense probe 1");
        node1.setCapabilities(list1);
        node1.setId("urn:wisebed:node:uth:probe1");
        node1.setPosition(position);
        node1.setGateway("0");
        node1.setProgramDetails("Metriseis Igrasias");
        node1.setNodeType("cropsense");

        // create node Cropsense probe 2...
        Node node2 = new Node();
        node2.setDescription("Cropsense probe 2");
        node2.setCapabilities(list1);
        node2.setId("urn:wisebed:node:uth:probe2");
        node2.setPosition(position);
        node2.setGateway("0");
        node2.setProgramDetails("Metriseis Igrasias");
        node2.setNodeType("cropsense");

        // create node Cropsense Tmote sensor 2...
        Node node3 = new Node();
        node3.setDescription("Tmote sensor 2");
        node3.setCapabilities(list2);
        node3.setId("urn:wisebed:node:uth:tmote2");
        node3.setPosition(position);
        node3.setGateway("0");
        node3.setProgramDetails("Metriseis Igrasias");
        node3.setNodeType("tmote");

        // create node Cropsense Tmote sensor 6...
        Node node4 = new Node();
        node4.setDescription("Tmote sensor 6");
        node4.setCapabilities(list2);
        node4.setId("urn:wisebed:node:uth:tmote6");
        node4.setPosition(position);
        node4.setGateway("0");
        node4.setProgramDetails("Metriseis Igrasias");
        node4.setNodeType("tmote");

        // add nodes to the list...
        listNodes.add(node1);
        listNodes.add(node2);
        listNodes.add(node3);
        listNodes.add(node4);
    }

    /**
     * Construct new Data.
     *
     * @param key   node capability key
     * @param value node capability value
     * @param list  a list of data.
     */
    public void constructData(final String key, final String value, final List<Data> list) {
        // construct new data for each value of node...
        Data thisData = new Data();
        thisData.setKey(key);
        thisData.setValue(value);
        if (value.length() > 0) {
            list.add(thisData);
        }
    }

    /**
     * Construct new trace.
     */
    public void constructTrace() {

        // initialize new list of timestamps...
        List<Timestamp> listTmstmps = new ArrayList<Timestamp>();
        int timeStampValue = 0;

        // Create new trace and add it to wiseml...
        Trace trace = new Trace();
        trace.setId(1);
        trace.setTimestamp(listTmstmps);
        rootNode.setTrace(trace);

        try {

            // create new file and read it...
            File file = new File("C:/Code/metriseis.csv");
            FileInputStream fstream = new FileInputStream(file);

            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                //read file line by line...
                Scanner scanner = new Scanner(line);
                scanner.useDelimiter(";");

                // construct new timestamp...
                Timestamp ts = new Timestamp();

                // initialize list of nodes for this timestamp...
                List<Node> listnodes = new ArrayList<Node>();
                ts.setNode(listnodes);

                // get and ignore...
                String getTimestampValue = scanner.next();

                // extract Date from input line...
                ts.setValue("" + timeStampValue);
                timeStampValue += 2;

                // add timestamp to the list...
                listTmstmps.add(ts);

                // --- Add nodes one - by - one and also the data for each capability of the nodes

                // PROBE-1
                Node n1 = new Node();
                n1.setId("urn:wisebed:node:probe1");
                List<Data> listdatan1 = new ArrayList<Data>();
                n1.setData(listdatan1);

                if (scanner.hasNext()) {
                    constructData("urn:wisebed:node:capability:humidity-10", scanner.next(), listdatan1);
                } else {
                    break;
                }

                if (scanner.hasNext()) {
                    constructData("urn:wisebed:node:capability:humidity-20", scanner.next(), listdatan1);
                } else {
                    break;
                }

                if (scanner.hasNext()) {
                    constructData("urn:wisebed:node:capability:humidity-30", scanner.next(), listdatan1);
                } else {
                    break;
                }

                if (scanner.hasNext()) {
                    constructData("urn:wisebed:node:capability:humidity-50", scanner.next(), listdatan1);
                } else {
                    break;
                }

                if (scanner.hasNext()) {
                    constructData("urn:wisebed:node:capability:humidity-90", scanner.next(), listdatan1);
                } else {
                    break;
                }

                // add node if we have at least one data item...
                if (listdatan1.size() > 0) {
                    listnodes.add(n1);
                }

                // PROBE-2
                Node n2 = new Node();
                n2.setId("urn:wisebed:node:probe2");
                List<Data> listdatan2 = new ArrayList<Data>();
                n2.setData(listdatan2);

                if (scanner.hasNext()) {
                    constructData("urn:wisebed:node:capability:humidity-10", scanner.next(), listdatan2);
                } else {
                    break;
                }

                if (scanner.hasNext()) {
                    constructData("urn:wisebed:node:capability:humidity-20", scanner.next(), listdatan2);
                } else {
                    break;
                }

                if (scanner.hasNext()) {
                    constructData("urn:wisebed:node:capability:humidity-30", scanner.next(), listdatan2);
                } else {
                    break;
                }

                if (scanner.hasNext()) {
                    constructData("urn:wisebed:node:capability:humidity-50", scanner.next(), listdatan2);
                } else {
                    break;
                }

                if (scanner.hasNext()) {
                    constructData("urn:wisebed:node:capability:humidity-90", scanner.next(), listdatan2);
                } else {
                    break;
                }

                // add node if we have at least one data item...
                if (listdatan2.size() > 0) {
                    listnodes.add(n2);
                }


                // TMOTE-2
                Node n3 = new Node();
                n3.setId("urn:wisebed:node:tmote2");
                List<Data> listdatan3 = new ArrayList<Data>();
                n3.setData(listdatan3);

                if (scanner.hasNext()) {
                    constructData("urn:wisebed:node:capability:humidity-0-15", scanner.next(), listdatan3);
                } else {
                    break;
                }

                if (scanner.hasNext()) {
                    constructData("urn:wisebed:node:capability:humidity-15-30", scanner.next(), listdatan3);
                } else {
                    break;
                }

                if (scanner.hasNext()) {
                    constructData("urn:wisebed:node:capability:airhumidity", scanner.next(), listdatan3);
                } else {
                    break;
                }

                if (scanner.hasNext()) {
                    constructData("urn:wisebed:node:capability:temperature", scanner.next(), listdatan3);
                } else {
                    break;
                }

                if (scanner.hasNext()) {
                    constructData("urn:wisebed:node:capability:battery", scanner.next(), listdatan3);
                } else {
                    break;
                }

                // add node if we have at least one data item...
                if (listdatan3.size() > 0) {
                    listnodes.add(n3);
                }


                // TMOTE-6
                Node n4 = new Node();
                n4.setId("urn:wisebed:node:tmote6");
                List<Data> listdatan4 = new ArrayList<Data>();
                n4.setData(listdatan4);

                if (scanner.hasNext()) {
                    constructData("urn:wisebed:node:capability:humidity-0-15", scanner.next(), listdatan4);
                } else {
                    break;
                }

                if (scanner.hasNext()) {
                    constructData("urn:wisebed:node:capability:humidity-15-30", scanner.next(), listdatan4);
                } else {
                    break;
                }

                if (scanner.hasNext()) {
                    constructData("urn:wisebed:node:capability:airhumidity", scanner.next(), listdatan4);
                } else {
                    break;
                }

                if (scanner.hasNext()) {
                    constructData("urn:wisebed:node:capability:temperature", scanner.next(), listdatan4);
                } else {
                    break;
                }

                if (scanner.hasNext()) {
                    constructData("urn:wisebed:node:capability:battery", scanner.next(), listdatan4);
                } else {
                    break;
                }

                // add node if we have at least one data item...
                if (listdatan4.size() > 0) {
                    listnodes.add(n4);
                }

                scanner.close();
            }

            // close the input stream...
            in.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {


        try {
            IBindingFactory bfact = BindingDirectory.getFactory(WiseML.class);


            ReadFileAsTrace rf = new ReadFileAsTrace();

            // marshal object back out to file (with nice indentation, as UTF-8)...
            IMarshallingContext mctx = bfact.createMarshallingContext();
            mctx.setIndent(5);
            FileOutputStream out = new FileOutputStream("trace-output.xml");
            mctx.setOutput(out, null);
            mctx.marshalDocument(rf.getRootNode());


        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);

        } catch (JiBXException e) {
            e.printStackTrace();
            System.exit(1);
        }

    }
}

