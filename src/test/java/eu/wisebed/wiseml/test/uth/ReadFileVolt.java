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
 * This class reads a file that has volt data of nodes, timestamps and capabilities values and
 * creates an xml file of nodes setup and a trace of these timestamps.
 */
public class ReadFileVolt {

    static WiseML rootNode;

    public WiseML getRootNode() {
        return rootNode;
    }

    /**
     * Default construstor.
     */
    public ReadFileVolt() {
        rootNode = new WiseML();
        constructSetup();
        constructTrace();
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

    public void constructSetup() {

        // initialize new list of nodes...
        List<Node> listNodes = new ArrayList<Node>();

        // create setup origin...
        Origin origin = new Origin();
        origin.setX((float)0);
        origin.setY((float)0);
        origin.setZ((float)0);
        origin.setPhi((float)0);
        origin.setTheta((float)0);

        // create node position...
        Position position = new Position();
        position.setX((float)0);
        position.setY((float)0);
        position.setZ((float)0);
        position.setPhi((float)0);
        position.setTheta((float)0);

        // create setup timeinfo..
        TimeInfo timeInfo = new TimeInfo();
        timeInfo.setDuration(2 * 24);
        timeInfo.setUnit("hours");
        timeInfo.setStart("22/4/2008 12:16:00");
        timeInfo.setEnd("24/4/2008 14:36:43 ");

        // create setup and add it to wiseml...
        Setup setup = new Setup();
        setup.setNodes(listNodes);
        setup.setOrigin(origin);
        setup.setInterpolation("metrics");
        setup.setDescription("Read flemming volt data");
        setup.setTimeinfo(timeInfo);
        rootNode.setSetup(setup);

        // initialize new list of capabilities...
        List<Capability> list = new ArrayList<Capability>();
        Capability cap1 = new Capability();
        cap1.setName("urn:wisebed:node:capability:Voltage");
        cap1.setUnit("Voltage");
        cap1.setDatatype("V");
        list.add(cap1);

        // create node 1...
        Node node1 = new Node();
        node1.setDescription("NODE_1");
        node1.setCapabilities(list);
        node1.setId("urn:wisebed:node:uth:node1");
        node1.setPosition(position);
        node1.setGateway("0");
        node1.setProgramDetails("Metriseis");
        node1.setNodeType("node1");

        // create node 2...
        Node node2 = new Node();
        node2.setDescription("NODE_2");
        node2.setCapabilities(list);
        node2.setId("urn:wisebed:node:uth:node2");
        node2.setPosition(position);
        node2.setGateway("0");
        node2.setProgramDetails("Metriseis");
        node2.setNodeType("node2");

        // create node 3...
        Node node3 = new Node();
        node3.setDescription("NODE_3");
        node3.setCapabilities(list);
        node3.setId("urn:wisebed:node:uth:node3");
        node3.setPosition(position);
        node3.setGateway("0");
        node3.setProgramDetails("Metriseis");
        node3.setNodeType("node3");

        // create node 4...
        Node node4 = new Node();
        node4.setDescription("NODE_4");
        node4.setCapabilities(list);
        node4.setId("urn:wisebed:node:uth:node4");
        node4.setPosition(position);
        node4.setGateway("0");
        node4.setProgramDetails("Metriseis");
        node4.setNodeType("node4");

        // create node 5...
        Node node5 = new Node();
        node5.setDescription("NODE_5");
        node5.setCapabilities(list);
        node5.setId("urn:wisebed:node:uth:node5");
        node5.setPosition(position);
        node5.setGateway("0");
        node5.setProgramDetails("Metriseis");
        node5.setNodeType("node5");

        listNodes.add(node1);
        listNodes.add(node2);
        listNodes.add(node3);
        listNodes.add(node4);
        listNodes.add(node5);
    }


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
            File file = new File("C:/Code/int_volt_table.csv");
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

                Node node = new Node();
                node.setId("urn:wisebed:node:node" + scanner.next());
                List<Data> listdatan = new ArrayList<Data>();
                node.setData(listdatan);

                scanner.next();

                if (scanner.hasNext()) {
                    constructData("urn:wisebed:node:capability:Voltage", scanner.next(), listdatan);
                } else {
                    break;
                }


                // add node if we have at least one data item...
                if (listdatan.size() > 0) {
                    listnodes.add(node);
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


            ReadFileVolt fVolt = new ReadFileVolt();

            // marshal object back out to file (with nice indentation, as UTF-8)...
            IMarshallingContext mctx = bfact.createMarshallingContext();
            mctx.setIndent(5);
            FileOutputStream out = new FileOutputStream("flemming-volt.xml");
            mctx.setOutput(out, null);
            mctx.marshalDocument(fVolt.getRootNode());


        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);

        } catch (JiBXException e) {
            e.printStackTrace();
            System.exit(1);
        }

    }
}
