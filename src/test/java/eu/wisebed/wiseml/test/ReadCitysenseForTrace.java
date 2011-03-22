package eu.wisebed.wiseml.test;

import eu.wisebed.wiseml.controller.WiseMLController;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.JiBXException;
import eu.wisebed.wiseml.model.WiseML;
import eu.wisebed.wiseml.model.scenario.Timestamp;
import eu.wisebed.wiseml.model.setup.*;
import eu.wisebed.wiseml.model.trace.Trace;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class setting up new nodes and creates a trace
 * of timestamps for these nodes in a xml file.
 */
public class ReadCitysenseForTrace {

    private static WiseML rootNode;

    /**
     * Value of start record.
     */
    private static int startRecord;
    /**
     * Value of end record.
     */
    private static int endRecord;
    /**
     * List for VAISALA_WXT510 capabilities.
     */
    public List<Capability> list_for_VAISALA_WXT510 = new ArrayList<Capability>();
    /**
     * List for BK732A_SPL capabilities.
     */
    public List<Capability> list_for_BK732A_SPL = new ArrayList<Capability>();
    /**
     * List for DYLOS_DC1100_PRO capabilities.
     */
    public List<Capability> list_for_DYLOS_DC1100_PRO = new ArrayList<Capability>();
    /**
     * List for TSI_3781_WCPC capabilities.
     */
    public List<Capability> list_for_TSI_3781_WCPC = new ArrayList<Capability>();
    /**
     * List for VAISALA_GMP343 capabilities.
     */
    public List<Capability> list_for_VAISALA_GMP343 = new ArrayList<Capability>();
    /**
     * List for ECOCHEM_PAS2000 capabilities.
     */
    public List<Capability> list_for_ECOCHEM_PAS2000 = new ArrayList<Capability>();
    /**
     * List for ECOCHEM_PAS2000CE capabilities.
     */
    public List<Capability> list_for_ECOCHEM_PAS2000CE = new ArrayList<Capability>();

    /**
     * Default construstor.
     */
    public ReadCitysenseForTrace(int fromRecord, int toRecord) {
        prepareCapabilitiesForVAISALAWXT510();
        prepareCapabilitiesForBK732ASPL();
        prepareCapabilitiesForDYLOSDC1100PRO();
        prepareCapabilitiesForTSI3781WCPC();
        prepareCapabilitiesForVAISALAGMP343();
        prepareCapabilitiesForECOCHEMPAS2000();
        prepareCapabilitiesForECOCHEMPAS2000CE();

        rootNode = new WiseML();
        constructSetup();
        constructTrace();

    }

    public WiseML getRootNode() {
        return rootNode;
    }

    /**
     * This method sets capabilities for Sensor Type VAISALA_WXT510.
     */
    public final void prepareCapabilitiesForVAISALAWXT510() {
        // node capabilities...
        Capability cap1 = new Capability();
        Capability cap2 = new Capability();
        Capability cap3 = new Capability();
        Capability cap4 = new Capability();
        Capability cap5 = new Capability();
        Capability cap6 = new Capability();
        Capability cap7 = new Capability();
        Capability cap8 = new Capability();
        Capability cap9 = new Capability();
        Capability cap10 = new Capability();
        Capability cap11 = new Capability();
        cap1.setName("urn:wisebed:node:capability:minWindDirection");
        cap1.setUnit("Degree");
        cap1.setDatatype("deg");
        cap2.setName("urn:wisebed:node:capability:maxWindDirection");
        cap2.setUnit("Degree");
        cap2.setDatatype("deg");
        cap3.setName("urn:wisebed:node:capability:avgWindDirection");
        cap3.setUnit("Degree");
        cap3.setDatatype("deg");
        cap4.setName("urn:wisebed:node:capability:minWindSpeed");
        cap4.setUnit("Meters_Per_Second");
        cap4.setDatatype("m/s");
        cap5.setName("urn:wisebed:node:capability:maxWindSpeed");
        cap5.setUnit("Meters_Per_Second");
        cap5.setDatatype("m/s");
        cap6.setName("urn:wisebed:node:capability:avgWindSpeed");
        cap6.setUnit("Meters_Per_Second");
        cap6.setDatatype("m/s");
        cap7.setName("urn:wisebed:node:capability:airPressure");
        cap7.setUnit("Hundred_Pascal");
        cap7.setDatatype("hPa");
        cap8.setName("urn:wisebed:node:capability:airTemperature");
        cap8.setUnit("Degree_Celsius");
        cap8.setDatatype("C");
        cap9.setName("urn:wisebed:node:capability:relativeHumidity");
        cap9.setUnit("Percent");
        cap9.setDatatype("%");
        cap10.setName("urn:wisebed:node:capability:rainDuration");
        cap10.setUnit("Second");
        cap10.setDatatype("s");
        cap11.setName("urn:wisebed:node:capability:rainIntensity");
        cap11.setUnit("Inches_Per_Hour");
        cap11.setDatatype("in/h");
        list_for_VAISALA_WXT510.add(cap1);
        list_for_VAISALA_WXT510.add(cap2);
        list_for_VAISALA_WXT510.add(cap3);
        list_for_VAISALA_WXT510.add(cap4);
        list_for_VAISALA_WXT510.add(cap5);
        list_for_VAISALA_WXT510.add(cap6);
        list_for_VAISALA_WXT510.add(cap7);
        list_for_VAISALA_WXT510.add(cap8);
        list_for_VAISALA_WXT510.add(cap9);
        list_for_VAISALA_WXT510.add(cap10);
        list_for_VAISALA_WXT510.add(cap11);
    }

    /**
     * Set capabilities for Sensor Type BK732A_SPL.
     */
    public final void prepareCapabilitiesForBK732ASPL() {
        Capability cap1 = new Capability();
        cap1.setName("urn:wisebed:node:capability:sounLevel");
        cap1.setUnit("Decibel");
        cap1.setUnit("dB");
        list_for_BK732A_SPL.add(cap1);
    }

    /**
     * This method sets capabilities for Sensor Type DYLOS_DC1100_PRO.
     */
    public final void prepareCapabilitiesForDYLOSDC1100PRO() {
        Capability cap1 = new Capability();
        cap1.setName("urn:wisebed:node:capability:smallParticles");
        cap1.setUnit("Count");
        cap1.setDatatype("cnt");
        Capability cap2 = new Capability();
        cap2.setName("urn:wisebed:node:capability:largeParticles");
        cap2.setUnit("Count");
        cap2.setDatatype("cnt");
        list_for_DYLOS_DC1100_PRO.add(cap1);
        list_for_DYLOS_DC1100_PRO.add(cap2);

    }

    /**
     * This method sets capabilities for Sensor Type TSI_3781_WCPC.
     */
    public final void prepareCapabilitiesForTSI3781WCPC() {
        Capability cap1 = new Capability();
        cap1.setName("urn:wisebed:node:capability:particleConcentration");
        cap1.setUnit("Particles_per_cubic_cm");
        cap1.setDatatype("ppccm");
        Capability cap2 = new Capability();
        cap2.setName("urn:wisebed:node:capability:status");
        cap2.setUnit("Status_Flags");
        cap2.setDatatype("bitfield");
        list_for_TSI_3781_WCPC.add(cap1);
        list_for_TSI_3781_WCPC.add(cap2);
    }

    /**
     * This method sets capabilities for Sensor Type VAISALA_GMP343.
     */
    public final void prepareCapabilitiesForVAISALAGMP343() {
        Capability cap1 = new Capability();
        cap1.setName("urn:wisebed:node:capability:co2Density");
        cap1.setUnit("Parts_Per_Million_Vol");
        cap1.setDatatype("ppmv");
        Capability cap2 = new Capability();
        cap2.setName("urn:wisebed:node:capability:co2Temp");
        cap2.setUnit("Degree_Celsius");
        cap2.setDatatype("C");
        list_for_VAISALA_GMP343.add(cap1);
        list_for_VAISALA_GMP343.add(cap2);

    }

    /**
     * This method sets capabilities for Sensor Type ECOCHEM_PAS2000.
     */
    public final void prepareCapabilitiesForECOCHEMPAS2000() {
        Capability cap1 = new Capability();
        cap1.setName("urn:wisebed:node:capability:relativeConcentration");
        cap1.setUnit("Count");
        cap1.setDatatype("cnt");
        list_for_ECOCHEM_PAS2000.add(cap1);
    }

    /**
     * This method sets capabilities for Sensor Type ECOCHEM_PAS2000CE.
     */
    public final void prepareCapabilitiesForECOCHEMPAS2000CE() {
        Capability cap1 = new Capability();
        cap1.setName("urn:wisebed:node:capability:relativeConcentration");
        cap1.setUnit("Count");
        cap1.setDatatype("cnt");
        list_for_ECOCHEM_PAS2000CE.add(cap1);
    }

    /**
     * This method adds node capabilities in one list.
     *
     * @param list1 node capabilities
     * @param list2 new node capabilities
     */
    public final void constructCapabilities(final List<Capability> list1, final List<Capability> list2) {
        list1.addAll(list2);

    }

    /**
     * Construct new Setup for nodes.
     */
    public final void constructSetup() {

        List<Node> thisSetupNodes = new ArrayList<Node>();

        // create new setup and add it to wiseml...
        Setup setup = new Setup();
        setup.setNodes(thisSetupNodes);
        rootNode.setSetup(setup);

        try {
            // connect to the database...
            String connectionURL = "jdbc:mysql://black.cti.gr/citysense";
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection connection = DriverManager.getConnection(connectionURL, "webdust", "web333");
            Statement statement1 = connection.createStatement();


            // retrieve data for setup...
            ResultSet rs1 = statement1.executeQuery(" SELECT Nodes.id, Nodes.Name, Nodes.Longitude, Nodes.Latitude, Nodes.Elevation, Nodes.ip, Sensors.Type " +
                    " FROM Nodes, Sensors " +
                    " WHERE Nodes.id = Sensors.node_id " +
                    " AND Sensors.id >= 11 " +
                    " AND Sensors.id <= 38 " +
                    " AND Sensors.id <> 31 " +
                    " AND Sensors.id <> 36 " +
                    " ORDER BY Nodes.id");

            // create new node...
            Node prevNode = null;
            while (rs1.next()) {

                final String nodeID = "urn:wisebed:node:citysense:" + rs1.getString("Nodes.id");
                Node node = prevNode;

                // check if the node is not the same with previous node...
                if ((prevNode == null) || (!nodeID.equals(prevNode.getId()))) {

                    // create new node...
                    node = new Node();
                    thisSetupNodes.add(node);
                    node.setId(nodeID);
                    node.setDescription(rs1.getString("Nodes.Name"));

                    // create new position and add it to the node...
                    Position position = new Position();
                    position.setX(rs1.getFloat("Nodes.Latitude"));
                    position.setY(rs1.getFloat("Nodes.Longitude"));
                    position.setZ(rs1.getFloat("Nodes.Elevation"));
                    node.setPosition(position);

                    // initiazize list of capability...
                    List<Capability> initList = new ArrayList<Capability>();

                    // create new capability for ip address and add it to the list...
                    Capability ipcap = new Capability();
                    ipcap.setName("urn:wisebed:node:capability:IP_address");
                    ipcap.setDatatype("String");
                    ipcap.setUnit("ip_address");
                    initList.add(ipcap);

                    // initialize list of data...
                    List<Data> dataList = new ArrayList<Data>();

                    // create new data for ip address and add it to the list...
                    Data data = new Data();
                    data.setKey("urn:wisebed:node:capability:IP_address");
                    data.setValue(rs1.getString("Nodes.ip"));
                    dataList.add(data);

                    // add to node ip address and its data...
                    node.setCapabilities(initList);
                    node.setData(dataList);
                }

                // check what type is sensor node and add a list of capabilities of this type of sensor to node...
                if (rs1.getString("Sensors.Type").equals("BK732A SPL")) {
                    constructCapabilities(node.getCapabilities(), list_for_BK732A_SPL);
                } else if (rs1.getString("Sensors.Type").equals("DYLOS DC1100 PRO")) {
                    constructCapabilities(node.getCapabilities(), list_for_DYLOS_DC1100_PRO);
                } else if (rs1.getString("Sensors.Type").equals("TSI 3781 WCPC")) {
                    constructCapabilities(node.getCapabilities(), list_for_TSI_3781_WCPC);
                } else if (rs1.getString("Sensors.Type").equals("VAISALA GMP343")) {
                    constructCapabilities(node.getCapabilities(), list_for_VAISALA_GMP343);
                } else if (rs1.getString("Sensors.Type").equals("VAISALA WXT510")) {
                    constructCapabilities(node.getCapabilities(), list_for_VAISALA_WXT510);
                } else if (rs1.getString("Sensors.Type").equals("ECOCHEM PAS2000")) {
                    constructCapabilities(node.getCapabilities(), list_for_ECOCHEM_PAS2000);
                } else if (rs1.getString("Sensors.Type").equals("ECOCHEM PAS2000CE")) {
                    constructCapabilities(node.getCapabilities(), list_for_ECOCHEM_PAS2000CE);
                }

                // keep record of previous node...
                prevNode = node;
            }

            rs1.close();


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }


    }

    /**
     * Construct new Trace for nodes.
     */
    public final void constructTrace() {

        // create new trace and add it to wiseml...
        Trace trace = new Trace();
        List<Timestamp> listTimestps = new ArrayList<Timestamp>();
        trace.setTimestamp(listTimestps);
        rootNode.setTrace(trace);

        try {
            // connect to the database...
            String connectionURL = "jdbc:mysql://black.cti.gr/citysense";
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection connection = DriverManager.getConnection(connectionURL, "webdust", "web333");
            Statement statement2 = connection.createStatement();
            Statement statement3 = connection.createStatement();
            Statement statement4 = connection.createStatement();
               int cnt=0;
            // retrieve timestamps for trace...
            ResultSet rs2 = statement2.executeQuery(" SELECT id, Timestamp, sensors_id " +
                    " FROM SensorOutputTable" +
                    " WHERE year(Timestamp) >=2010 and month(Timestamp)=1 order by Timestamp") ;//+



            while (rs2.next()) {

                   if (cnt%100==0) System.out.println(cnt);
                cnt++;
                // Create new Timestamp...
                Timestamp timest = new Timestamp();
                timest.setValue(rs2.getString("Timestamp"));

                // create new list for nodes included in the timestamp...
                List<Node> thisTimestampNodes = new ArrayList<Node>();
                timest.setNode(thisTimestampNodes);

                // add timestamp to trace...
                listTimestps.add(timest);

                // retrieve sensors nodes...
                ResultSet rs3 = statement3.executeQuery("SELECT node_id " +
                        " FROM Sensors" +
                        " WHERE Sensors.id = " + rs2.getString("sensors_id"));

                if (!rs3.next()) {
                    continue;
                }

                // create new Node and add it inside the timestamp...
                Node node = new Node();
                thisTimestampNodes.add(node);

                node.setId("urn:wisebed:node:citysense:" + rs3.getString("node_id"));

                // create new list of data and add it to node...
                List<Data> listData = new ArrayList<Data>();
                node.setData(listData);
                rs3.close();

                // retrieve data values...
                ResultSet rs4 = statement4.executeQuery(" SELECT RealMeasurements.Name, RealMeasurements.Value" +
                        " FROM RealMeasurements" +
                        " WHERE sensor_output_id = " + rs2.getString("id"));


                // add data to the list...
                while (rs4.next()) {
                    Data data1 = new Data();
                    data1.setKey("urn:wisebed:node:capability:" + rs4.getString("RealMeasurements.Name"));
                    data1.setValue(rs4.getString("RealMeasurements.Value"));
                    listData.add(data1);
                }

                rs4.close();
            }

            rs2.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    public static void main(final String[] args) {
        ReadCitysenseForTrace readCFT = new ReadCitysenseForTrace(startRecord, endRecord);

        try {
            if (true) {
                WiseMLController wiseMLctrl = new WiseMLController();
                wiseMLctrl.writeWiseMLAsFile(readCFT.getRootNode(), new File("trace-citysense.xml"));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}



