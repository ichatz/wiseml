package eu.wisebed.wiseml.controller;

import eu.wisebed.wiseml.model.WiseML;
import eu.wisebed.wiseml.model.setup.Setup;
import org.apache.log4j.Logger;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.JiBXException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class WiseMLController {

    private static final Logger LOGGER = Logger.getLogger(WiseMLController.class);


    /**
     * This method loads a wiseml from file.
     *
     * @param data , a Reader instance.
     * @return wiseml , returns a WiseML instance
     * @throws JiBXException exception
     */
    public WiseML loadWiseML(final Reader data) throws JiBXException {
        WiseML wiseml = new WiseML();

        // unmarshal wiseml information from file...
        IBindingFactory bfact = BindingDirectory.getFactory(WiseML.class);
        IUnmarshallingContext uctx = bfact.createUnmarshallingContext();
        wiseml = (WiseML) uctx.unmarshalDocument(data, null);

        return wiseml;
    }

    /**
     * this method loads a wiseml from file.
     *
     * @param file
     * @return wiseml
     * @throws JiBXException
     */
    public WiseML loadWiseML(final InputStream file) throws JiBXException {
        return loadWiseML(new InputStreamReader(file));
    }

    /**
     * this method loads a wiseml from file.
     *
     * @param file
     * @return wiseml
     */
    public WiseML loadWiseMLFromFile(final InputStream file) throws JiBXException {
        WiseML wiseml = new WiseML();
        try {
            // unmarshal wiseml information from file...
            IBindingFactory bfact = BindingDirectory.getFactory(WiseML.class);
            IUnmarshallingContext uctx = bfact.createUnmarshallingContext();
            wiseml = (WiseML) uctx.unmarshalDocument(file, null);

        } catch (JiBXException e) {
            LOGGER.fatal(e);
            throw e;
        }
        return wiseml;
    }

    /**
     * this method loads setup from file.
     *
     * @param file
     * @return setup
     */
    public Setup loadSetupFromFile(final InputStream file) throws JiBXException {
        Setup setup = new Setup();
        try {
            // unmarshal setup information from file...
            IBindingFactory bfact = BindingDirectory.getFactory(Setup.class);
            IUnmarshallingContext uctx = bfact.createUnmarshallingContext();
            setup = (Setup) uctx.unmarshalDocument(file, null);

        } catch (JiBXException e) {
            LOGGER.fatal(e);
            throw e;
        }
        return setup;

    }

    /**
     * this method writes wiseml in a file.
     *
     * @param wiseml
     * @param file
     */
    public void writeWiseMLAsFile(final WiseML wiseml,final File file) throws FileNotFoundException, JiBXException {
        try {
            // marshal object back out to file (with nice indentation, as UTF-8)...
            IBindingFactory bfact = BindingDirectory.getFactory(WiseML.class);

            IMarshallingContext mctx = bfact.createMarshallingContext();
            mctx.setIndent(5);
            FileOutputStream output = new FileOutputStream(file);
            mctx.setOutput(output, null);
            mctx.marshalDocument(wiseml);

        } catch (FileNotFoundException e) {
            LOGGER.fatal(e);
            throw e;
        } catch (JiBXException e) {
            LOGGER.fatal(e);
            throw e;
        }
    }

    /**
     * this method writes setup in a file.
     *
     * @param setup
     * @param file
     */
    public void writeSetupAsFile(final Setup setup,final File file) throws FileNotFoundException, JiBXException {

        try {
            // marshal object back out to file (with nice indentation, as UTF-8)...
            IBindingFactory bfact = BindingDirectory.getFactory(Setup.class);

            IMarshallingContext mctx = bfact.createMarshallingContext();
            mctx.setIndent(5);
            FileOutputStream output = new FileOutputStream(file.getName());
            mctx.setOutput(output, null);
            mctx.marshalDocument(setup);

        } catch (FileNotFoundException e) {
            LOGGER.fatal(e);
            throw e;
        } catch (JiBXException e) {
            LOGGER.fatal(e);
            throw e;
        }
    }

    /**
     * this method writes wiseml as a string.
     *
     * @param wiseml
     * @return string of wiseml data
     */
    public String writeWiseMLAsSTring(final WiseML wiseml) throws JiBXException {

        final ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        try {
            // marshal object back out to file (with nice indentation, as UTF-8)...
            IBindingFactory bfact = BindingDirectory.getFactory(WiseML.class);

            IMarshallingContext mctx = bfact.createMarshallingContext();
            mctx.setIndent(5);

            mctx.setOutput(buffer, null);
            mctx.marshalDocument(wiseml);

        } catch (JiBXException e) {
            LOGGER.fatal(e);
            throw e;


        }
        return buffer.toString();
    }

    /**
     * this method writes setup as a string.
     *
     * @param setup
     * @return string of setup data
     */
    public String writeSetupAsString(final Setup setup) throws Exception {

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
            LOGGER.fatal(e);
            throw e;
        }
        return buffer.toString();

    }

    /**
     * this method loads setup from a string.
     *
     * @param stup
     * @return setup
     */
    public Setup loadSetupFromString(final String stup) throws JiBXException {
        Setup setup = new Setup();
        final ByteArrayInputStream buffer = new ByteArrayInputStream(stup.getBytes());

        try {
            // unmarshal setup information from file...
            IBindingFactory bfact = BindingDirectory.getFactory(Setup.class);
            IUnmarshallingContext uctx = bfact.createUnmarshallingContext();
            setup = (Setup) uctx.unmarshalDocument(buffer, null);

        } catch (JiBXException e) {
            LOGGER.fatal(e);
            throw e;
        }
        return setup;
    }

    /**
     * this method loads wiseml from a string.
     *
     * @param wisml
     * @return wiseml
     */
    public Setup loadSetupFromWiseMLString(final String wisml) throws JiBXException {
        WiseML wsml = new WiseML();
        final ByteArrayInputStream buffer = new ByteArrayInputStream(wisml.getBytes());

        try {
            // unmarshal setup information from file...
            IBindingFactory bfact = BindingDirectory.getFactory(WiseML.class);
            IUnmarshallingContext uctx = bfact.createUnmarshallingContext();
            wsml = (WiseML) uctx.unmarshalDocument(buffer, null);

        } catch (JiBXException e) {
            LOGGER.fatal(e);
            throw e;
        }
        return wsml.getSetup();
    }

}
