package eu.wisebed.wiseml.test;

import eu.wisebed.wiseml.controller.WiseMLController;
import eu.wisebed.wiseml.model.WiseML;
import eu.wisebed.wiseml.model.scenario.Timestamp;
import eu.wisebed.wiseml.model.setup.Link;
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
            FileOutputStream output = new FileOutputStream("telosB_short.wiseml");
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
            fileML = new FileInputStream("/home/evangelos/workspace/easysense/wiseml2rdf/WiseNew4/telosB_short.wiseml");
        } catch (Exception e) {
            System.err.println(e);
        }

        WiseMLController wmlcontroller = new WiseMLController();
        WiseML wml = wmlcontroller.loadWiseMLFromFile(fileML);
        Trace theTrace = wml.getTrace();
        List traceItems = theTrace.getChildren();

        System.out.println("Timestamp Size:" + traceItems.size());

        for (Object item : traceItems) {
            if (item.getClass().equals(Timestamp.class)) {
                Timestamp ts = (Timestamp) item;
                System.out.println("Timestamp" + ts.getValue());
            } else if (item.getClass().equals(Node.class)) {
                Node nd = (Node) item;
                System.out.println("Node" + nd.getId());
                System.out.println("Node" + nd.getData().get(0).getValue());
            } else if (item.getClass().equals(Link.class)) {
                Link ln = (Link) item;
                System.out.println("Link" + ln.getSource() + "-->" + ln.getTarget());
                System.out.println("Link" + ln.getRssi().getValue());
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
