package eu.wisebed.wiseml.test.uth;

import eu.wisebed.wiseml.model.scenario.Timestamp;
import eu.wisebed.wiseml.model.setup.Data;
import eu.wisebed.wiseml.model.setup.Node;
import eu.wisebed.wiseml.model.trace.Trace;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Reader {

    public static void main(String[] args) {

        List<Timestamp> listTmstmps = new ArrayList<Timestamp>();
        List<Node> listnodes = new ArrayList<Node>();
        Timestamp ts = new Timestamp();

        Trace trace = new Trace();
        trace.setId(1);
        trace.setTimestamp(listTmstmps);

        try {

            File file = new File("metriseis.csv");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                scanner.useDelimiter(";");
                System.out.println(scanner.nextLine());
                ts.setValue(scanner.next());
                System.out.println("Date = " + ts.getValue());

                // Construct new data for each value of probe1 node...
                Data data1n1 = new Data();
                data1n1.setKey("urn:wisebed:node:capability:humidity-10");
                data1n1.setValue(scanner.next());
                System.out.println("probe1.humidity-10 = " + data1n1.getValue());
                Data data2n1 = new Data();
                data2n1.setKey("urn:wisebed:node:capability:humidity-20");
                data2n1.setValue(scanner.next());
                System.out.println("probe1.humidity-20 = " + data2n1.getValue());
                Data data3n1 = new Data();
                data3n1.setKey("urn:wisebed:node:capability:humidity-30");
                data3n1.setValue(scanner.next());
                System.out.println("probe1.humidity-30 = " + data3n1.getValue());
                Data data4n1 = new Data();
                data4n1.setKey("urn:wisebed:node:capability:humidity-50");
                data4n1.setValue(scanner.next());
                System.out.println("probe1.humidity-50 = " + data4n1.getValue());
                Data data5n1 = new Data();
                data5n1.setKey("urn:wisebed:node:capability:humidity-90");
                data5n1.setValue(scanner.next());
                System.out.println("probe1.humidity-90 = " + data5n1.getValue());

                // Construct new data for each value of probe2 node...
                Data data1n2 = new Data();
                data1n2.setKey("urn:wisebed:node:capability:humidity-10");
                data1n2.setValue(scanner.next());
                System.out.println("probe2.humidity-10 = " + data1n2.getValue());
                Data data2n2 = new Data();
                data2n2.setKey("urn:wisebed:node:capability:humidity-20");
                data2n2.setValue(scanner.next());
                System.out.println("probe2.humidity-20 = " + data2n2.getValue());
                Data data3n2 = new Data();
                data3n2.setKey("urn:wisebed:node:capability:humidity-30");
                data3n2.setValue(scanner.next());
                System.out.println("probe2.humidity-30 = " + data3n2.getValue());
                Data data4n2 = new Data();
                data4n2.setKey("urn:wisebed:node:capability:humidity-50");
                data4n2.setValue(scanner.next());
                System.out.println("probe2.humidity-50 = " + data4n2.getValue());
                Data data5n2 = new Data();
                data5n2.setKey("urn:wisebed:node:capability:humidity-90");
                data5n2.setValue(scanner.next());
                System.out.println("probe2.humidity-90 = " + data5n2.getValue());

                // Construct new data for each value of tmote2 node...
                Data data1n3 = new Data();
                data1n3.setKey("urn:wisebed:node:capability:humidity-0-15");
                data1n3.setValue(scanner.next());
                System.out.println("tmote2.humidity-0-15 = " + data1n3.getValue());
                Data data2n3 = new Data();
                data2n3.setKey("urn:wisebed:node:capability:humidity-15-30");
                data2n3.setValue(scanner.next());
                System.out.println("tmote2.humidity-15-30 = " + data2n3.getValue());
                Data data3n3 = new Data();
                data3n3.setKey("urn:wisebed:node:capability:airhumidity");
                data3n3.setValue(scanner.next());
                System.out.println("tmote2.airhumidity = " + data3n3.getValue());
                Data data4n3 = new Data();
                data4n3.setKey("urn:wisebed:node:capability:temperature");
                data4n3.setValue(scanner.next());
                System.out.println("tmote2.temperature = " + data4n3.getValue());

                // Construct new data for each value of tmote6 node...
                Data data1n4 = new Data();
                data1n4.setKey("urn:wisebed:node:capability:humidity-0-15");
                data1n4.setValue(scanner.next());
                System.out.println("tmote6.humidity-0-15 = " + data1n4.getValue());
                Data data2n4 = new Data();
                data2n4.setKey("urn:wisebed:node:capability:humidity-15-30");
                data2n4.setValue(scanner.next());
                System.out.println("tmote6.humidity-15-30 = " + data2n4.getValue());
                Data data3n4 = new Data();
                data3n4.setKey("urn:wisebed:node:capability:airhumidity");
                data3n4.setValue(scanner.next());
                System.out.println("tmote6.airhumidity = " + data3n4.getValue());
                Data data4n4 = new Data();
                data4n4.setKey("urn:wisebed:node:capability:temperature");
                data4n4.setValue(scanner.next());
                System.out.println("tmote6.temperature = " + data4n4.getValue());

                // Construct new data for each value of sensor2 node...
                Data data1n5 = new Data();
                data1n5.setKey("urn:wisebed:node:capability:humidity-0-15");
                data1n5.setValue(scanner.next());
                System.out.println("sensor2.humidity-0-15 = " + data1n5.getValue());
                Data data2n5 = new Data();
                data2n5.setKey("urn:wisebed:node:capability:humidity-15-30");
                data2n5.setValue(scanner.next());
                System.out.println("sensor2.humidity-15-30 = " + data2n5.getValue());

                // Construct new data for each value of sensor6 node...
                Data data1n6 = new Data();
                data1n6.setKey("urn:wisebed:node:capability:humidity-0-15");
                data1n6.setValue(scanner.next());
                System.out.println("sensor6.humidity-0-15 = " + data1n6.getValue());
                Data data2n6 = new Data();
                data2n6.setKey("urn:wisebed:node:capability:humidity-15-30");
                data2n6.setValue(scanner.next());
                System.out.println("sensor6.humidity-15-30 = " + data2n6.getValue());


                // Add data of probe1 to the list...
                List<Data> listdatan1 = new ArrayList<Data>();
                listdatan1.add(data1n1);
                listdatan1.add(data2n1);
                listdatan1.add(data3n1);
                listdatan1.add(data4n1);
                listdatan1.add(data5n1);

                // Add data of probe2 to the list...
                List<Data> listdatan2 = new ArrayList<Data>();
                listdatan2.add(data1n2);
                listdatan2.add(data2n2);
                listdatan2.add(data3n2);
                listdatan2.add(data4n2);
                listdatan2.add(data5n2);

                // Add data of tmote2 to the list...
                List<Data> listdatan3 = new ArrayList<Data>();
                listdatan3.add(data1n3);
                listdatan3.add(data2n3);
                listdatan3.add(data3n3);
                listdatan3.add(data4n3);

                // Add data of tmote6 to the list...
                List<Data> listdatan4 = new ArrayList<Data>();
                listdatan4.add(data1n4);
                listdatan4.add(data2n4);
                listdatan4.add(data3n4);
                listdatan4.add(data4n4);

                // Add data of sensor2 to the list...
                List<Data> listdatan5 = new ArrayList<Data>();
                listdatan5.add(data1n5);
                listdatan5.add(data2n5);

                // Add data of sensor6 to the list...
                List<Data> listdatan6 = new ArrayList<Data>();
                listdatan6.add(data1n6);
                listdatan6.add(data2n6);


                // Construct node for probe1
                Node n1 = new Node();
                n1.setId("urn:wisebed:node:probe1");
                n1.setData(listdatan1);

                // Construct node for probe2
                Node n2 = new Node();
                n2.setId("urn:wisebed:node:uth:probe2");
                n2.setData(listdatan2);

                // Construct node for tmote2
                Node n3 = new Node();
                n3.setId("urn:wisebed:node:uth:tmote2");
                n3.setData(listdatan3);

                // Construct node for tmote6
                Node n4 = new Node();
                n4.setId("urn:wisebed:node:uth:tmote6");
                n4.setData(listdatan4);

                // Construct node for sensor2
                Node n5 = new Node();
                n5.setId("urn:wisebed:node:uth:sensor2");
                n5.setData(listdatan5);


                // Construct node for sensor6
                Node n6 = new Node();
                n6.setId("urn:wisebed:node:uth:sensor6");
                n6.setData(listdatan6);

                // Add nodes to the list
                listnodes.add(n1);
                listnodes.add(n2);
                listnodes.add(n3);
                listnodes.add(n4);
                listnodes.add(n5);
                listnodes.add(n6);

                // Construct new Timestamp for this line of values
                ts.setNode(listnodes);

                // Add timestamp to the list
                listTmstmps.add(ts);

            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

}



