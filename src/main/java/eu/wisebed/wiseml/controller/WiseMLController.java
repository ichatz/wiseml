package eu.wisebed.wiseml.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.JiBXException;

import eu.wisebed.wiseml.model.WiseML;
import eu.wisebed.wiseml.model.setup.Setup;

public class WiseMLController {

    /**
     * This method loads a wiseml from file.
     * 
     * @param file
     * @return wiseml
     */
    public WiseML loadWiseML(Reader data) {
        WiseML wiseml = new WiseML();
        try {
            // unmarshal wiseml information from file...
            IBindingFactory bfact = BindingDirectory.getFactory(WiseML.class);
            IUnmarshallingContext uctx = bfact.createUnmarshallingContext();
            wiseml = (WiseML) uctx.unmarshalDocument(data, null);

        } catch (JiBXException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return wiseml;
    }

    /**
     * this method loads a wiseml from file.
     * 
     * @param file
     * @return wiseml
     */
    public WiseML loadWiseML(InputStream file) {
        return loadWiseML(new InputStreamReader(file));
    }

    /**
     * this method loads a wiseml from file.
     * 
     * @param file
     * @return wiseml
     */
    public WiseML loadWiseMLFromFile(InputStream file) {
        WiseML wiseml = new WiseML();
        try {
            // unmarshal wiseml information from file...
            IBindingFactory bfact = BindingDirectory.getFactory(WiseML.class);
            IUnmarshallingContext uctx = bfact.createUnmarshallingContext();
            wiseml = (WiseML) uctx.unmarshalDocument(file, null);

        } catch (JiBXException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return wiseml;
    }

    /**
     * this method loads setup from file.
     * 
     * @param file
     * @return setup
     */
    public Setup loadSetupFromFile(InputStream file) {
        Setup setup = new Setup();
        try {
            // unmarshal setup information from file...
            IBindingFactory bfact = BindingDirectory.getFactory(Setup.class);
            IUnmarshallingContext uctx = bfact.createUnmarshallingContext();
            setup = (Setup) uctx.unmarshalDocument(file, null);

        } catch (JiBXException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return setup;

    }

    /**
     * this method writes wiseml in a file.
     * 
     * @param wiseml
     * @param file
     */
    public void writeWiseMLAsFile(WiseML wiseml, File file) {
        try {
            // marshal object back out to file (with nice indentation, as UTF-8)...
            IBindingFactory bfact = BindingDirectory.getFactory(WiseML.class);

            IMarshallingContext mctx = bfact.createMarshallingContext();
            mctx.setIndent(5);
            FileOutputStream output = new FileOutputStream(file);
            mctx.setOutput(output, null);
            mctx.marshalDocument(wiseml);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (JiBXException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * this method writes setup in a file.
     * 
     * @param setup
     * @param file
     */
    public void writeSetupAsFile(Setup setup, File file) {

        try {
            // marshal object back out to file (with nice indentation, as UTF-8)...
            IBindingFactory bfact = BindingDirectory.getFactory(Setup.class);

            IMarshallingContext mctx = bfact.createMarshallingContext();
            mctx.setIndent(5);
            FileOutputStream output = new FileOutputStream(file.getName());
            mctx.setOutput(output, null);
            mctx.marshalDocument(setup);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (JiBXException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * this method writes wiseml as a string.
     * 
     * @param wiseml
     * @return string of wiseml data
     */
    public String writeWiseMLAsSTring(WiseML wiseml) {

        final ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        try {
            // marshal object back out to file (with nice indentation, as UTF-8)...
            IBindingFactory bfact = BindingDirectory.getFactory(WiseML.class);

            IMarshallingContext mctx = bfact.createMarshallingContext();
            mctx.setIndent(5);

            mctx.setOutput(buffer, null);
            mctx.marshalDocument(wiseml);

        } catch (JiBXException e) {
            e.printStackTrace();

        }
        return buffer.toString();
    }

    /**
     * this method writes setup as a string.
     * 
     * @param setup
     * @return string of setup data
     */
    public String writeSetupAsString(Setup setup) {

        WiseML wiseml = new WiseML();
        wiseml.setSetup(setup);
        final ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        try {
            // marshal object back out to file (with nice indentation, as UTF-8)...
            IBindingFactory bfact = BindingDirectory.getFactory(Setup.class);

            IMarshallingContext mctx = bfact.createMarshallingContext();
            mctx.setIndent(5);

            // initialize the output stream
            mctx.setOutput(buffer, null);
            mctx.marshalDocument(wiseml.getSetup());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer.toString();

    }

    /**
     * this method loads setup from a string.
     * 
     * @param stup
     * @return setup
     */
    public Setup loadSetupFromString(String stup) {
        Setup setup = new Setup();
        final ByteArrayInputStream buffer = new ByteArrayInputStream(stup.getBytes());

        try {
            // unmarshal setup information from file...
            IBindingFactory bfact = BindingDirectory.getFactory(Setup.class);
            IUnmarshallingContext uctx = bfact.createUnmarshallingContext();
            setup = (Setup) uctx.unmarshalDocument(buffer, null);

        } catch (JiBXException e) {
            e.printStackTrace();
        }
        return setup;
    }

    /**
     * this method loads wiseml from a string.
     * 
     * @param wisml
     * @return wiseml
     */
    public Setup loadSetupFromWiseMLString(String wisml) {
        WiseML wsml = new WiseML();
        final ByteArrayInputStream buffer = new ByteArrayInputStream(wisml.getBytes());

        try {
            // unmarshal setup information from file...
            IBindingFactory bfact = BindingDirectory.getFactory(WiseML.class);
            IUnmarshallingContext uctx = bfact.createUnmarshallingContext();
            wsml = (WiseML) uctx.unmarshalDocument(buffer, null);

        } catch (JiBXException e) {
            e.printStackTrace();
        }
        return wsml.getSetup();
    }

}
