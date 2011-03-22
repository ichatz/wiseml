package eu.wisebed.wiseml.test.uth;

import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.JiBXException;
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


public class ReadFileData {

    static WiseML rootNode;

    public WiseML getRootNode() {
        return rootNode;
    }

    /**
     * Default construstor.
     */
    public ReadFileData() {
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
    public void constructData(final String key, String value, final List<Data> list) {
        // construct new data for each value of node...
        Data thisData = new Data();
        value=value.replace(',','.');
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
        origin.setX(0f);
        origin.setY(0f);
        origin.setZ(0f);
        origin.setPhi(0);
        origin.setTheta(0);

        // create node position...
        Position position = new Position();
        position.setX(0);
        position.setY(0);
        position.setZ(0);
        position.setPhi(0);
        position.setTheta(0);

        // create setup timeinfo..
        TimeInfo timeInfo = new TimeInfo();
        timeInfo.setDuration(2 * 24);
        timeInfo.setUnit("hours");
        timeInfo.setStart("22/4/2008 12:16:06");
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
        cap1.setName("urn:wisebed:node:capability:Temperature");
        cap1.setUnit("Temperature");
        cap1.setDatatype("C");
        Capability cap2 = new Capability();
        cap2.setName("urn:wisebed:node:capability:Humidity");
        cap2.setUnit("Humidity");
        cap2.setDatatype("%");
        Capability cap3 = new Capability();
        cap3.setName("urn:wisebed:node:capability:Voltage");
        cap3.setUnit("Voltage");
        cap3.setDatatype("V");
        Capability cap4 = new Capability();
        cap4.setName("urn:wisebed:node:capability:Nh3");
        cap4.setUnit("nh3");
        cap4.setDatatype("%");
        Capability cap5 = new Capability();
        cap5.setName("urn:wisebed:node:capability:mic");
        cap5.setUnit("mic");
        cap5.setDatatype("%");
        Capability cap6 = new Capability();
        cap6.setName("urn:wisebed:node:capability:mic_env");
        cap6.setUnit("mic");
        cap6.setDatatype("%");
        list.add(cap1);
        list.add(cap2);
        list.add(cap3);
        list.add(cap4);
        list.add(cap5);
        list.add(cap6);        


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
            File file = new File("C:/Code/flemming.csv");
            FileInputStream fstream = new FileInputStream(file);

            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));


            String line;
            boolean new_timestamp = false;
            List<Node> listnodes = null;
            String last_timestamp = "";
            String last_node = "";

            String node_id = null;
            Node node = null;
            Timestamp ts = null;
            List<Data> listdatan = new ArrayList<Data>();
            int cnt = 0;
            String getTimestampValue = "";
            boolean first_time = true;

            String[] tmp = null;

            while ((line = br.readLine()) != null && cnt < 10000) {
                //cnt++;
                Scanner scanner = new Scanner(line);
                scanner.useDelimiter(";");

                getTimestampValue = scanner.next();
                tmp = getTimestampValue.split(" ")[0].split("/");
                if (tmp[1].length() == 1)
                    tmp[1] = "0" + tmp[1];
                if (tmp[0].length() == 1)
                    tmp[0] = "0" + tmp[0];
                getTimestampValue = tmp[2] + "-" + tmp[1] + "-" + tmp[0] + " " + getTimestampValue.split(" ")[1] + ".0";
                new_timestamp = false;


                if (last_timestamp.equals(getTimestampValue) == false) {
                    if (first_time == true) {
                        first_time = false;
                    } else {
                        node.setData(listdatan);
                        listnodes.add(node);
                        ts.setNode(listnodes);
                        listTmstmps.add(ts);
                    }
                    ts = new Timestamp();
                    ts.setValue(getTimestampValue);
                    new_timestamp = true;
                    listnodes = new ArrayList<Node>();

                }

                last_timestamp = getTimestampValue;
                node_id = "urn:wisebed:node:node" + scanner.next();

                // check if this node is different with previous node...
                if (last_node.equals(node_id) == false && new_timestamp == false) {
                    node.setData(listdatan);
                    listnodes.add(node);
                    node = new Node();
                    node.setId(node_id);
                    last_node = node_id;
                    listdatan = new ArrayList<Data>();

                } else if (new_timestamp == true) {
                    node = new Node();
                    node.setId(node_id);
                    last_node = node_id;
                    listdatan = new ArrayList<Data>();
                }
                constructData("urn:wisebed:node:capability:" + scanner.next(), scanner.next(), listdatan);
                scanner.close();
            }  //while

            node.setData(listdatan);
            listnodes.add(node);
            ts.setNode(listnodes);
            listTmstmps.add(ts);

            // close the input stream...
            in.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {


        try {
            IBindingFactory bfact = BindingDirectory.getFactory(WiseML.class);


            ReadFileData fData = new ReadFileData();

            // marshal object back out to file (with nice indentation, as UTF-8)...
            IMarshallingContext mctx = bfact.createMarshallingContext();
            mctx.setIndent(5);
            FileOutputStream out = new FileOutputStream("flemming.xml");
            mctx.setOutput(out, null);
            mctx.marshalDocument(fData.getRootNode());


        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);

        } catch (JiBXException e) {
            e.printStackTrace();
            System.exit(1);
        }

    }
}


