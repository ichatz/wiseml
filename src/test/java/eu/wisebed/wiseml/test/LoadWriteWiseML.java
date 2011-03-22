package eu.wisebed.wiseml.test;

import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.JiBXException;
import eu.wisebed.wiseml.controller.WiseMLController;
import eu.wisebed.wiseml.model.WiseML;
import eu.wisebed.wiseml.model.setup.Setup;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;


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
        String newNetwork = "";
        File file = new File("trace-citysense.xml");
        FileInputStream fis = null;


         fis = new FileInputStream(file);

         WiseMLController cnt = new WiseMLController();
         WiseML stp = cnt.loadWiseMLFromFile(fis);
         System.out.println(stp.getTrace().getTimestamp().size());



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
