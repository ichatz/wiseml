package eu.wisebed.wiseml.test;

import eu.wisebed.wiseml.controller.WiseMLController;
import eu.wisebed.wiseml.model.WiseML;
import eu.wisebed.wiseml.model.scenario.Timestamp;
import eu.wisebed.wiseml.model.setup.Data;
import eu.wisebed.wiseml.model.setup.Node;
import eu.wisebed.wiseml.model.setup.Setup;
import eu.wisebed.wiseml.model.trace.Trace;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.JiBXException;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;


public class LoadWriteWiseML {

    public void testWriteToFile() {
        WiseML wiseml = new WiseML();
        Setup setup = new Setup();
        setup.setInterpolation("cubic");
        setup.setDescription("this is an example WiseML with all the elements.");
        wiseml.setSetup(setup);

        try {
            // marshal object back out to file (with nice indentation, as UTF-8)...
            IBindingFactory bfact = BindingDirectory.getFactory(Setup.class);

            IMarshallingContext mctx = bfact.createMarshallingContext();
            mctx.setIndent(5);
            FileOutputStream output = new FileOutputStream("temp-setup.xml");
            mctx.setOutput(output, null);
            mctx.marshalDocument(wiseml.getSetup());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);

        } catch (JiBXException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void testWriteToCharArray() {
        WiseML wiseml = new WiseML();
        Setup setup = new Setup();
        setup.setInterpolation("cubic");
        setup.setDescription("this is an example WiseML with all the elements.");
        wiseml.setSetup(setup);

        try {
            // marshal object back out to file (with nice indentation, as UTF-8)...
            IBindingFactory bfact = BindingDirectory.getFactory(Setup.class);

            IMarshallingContext mctx = bfact.createMarshallingContext();
            mctx.setIndent(5);

            // initialize the output stream
            final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            mctx.setOutput(buffer, null);
            mctx.marshalDocument(wiseml.getSetup());

            System.out.println(buffer.toString());

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void doAnotherTest() throws FileNotFoundException {
        FileInputStream fileML = null;
           try {
               fileML = new FileInputStream("webdust_short.wiseml");
           } catch(Exception e){
               System.err.println(e);
           }

           WiseMLController wmlcontroller = new WiseMLController();
           WiseML wml = wmlcontroller.loadWiseMLFromFile(fileML);
          Trace theTrace = wml.getTrace();
          List<Timestamp> timestamps = theTrace.getTimestamp();
          System.out.println("Timestamp Size:"+timestamps.size());

          for(Timestamp timestamp : timestamps)
          {
              System.out.println("Timstamp:"+timestamp.getValue());
               List<Node> nodes = timestamp.getNode();
               System.out.println("Size:"+nodes.size());
               for(Object msg : nodes.toArray()){
                   Node msgg= (Node) msg;
                   System.out.println("NodeID:"+msgg.getId());
                   for (int i=0;i<msgg.getData().size();i++)
                       System.out.println("\t"+( (Data) msgg.getData().get(i)).getValue() );
               }

          }

    }

    public static void main(String[] args) {
        LoadWriteWiseML testMe = new LoadWriteWiseML();


//        testMe.testWriteToFile();
//        testMe.testWriteToCharArray();
        try {
            testMe.doAnotherTest();
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
}
