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

/**
 * WiseMLController utility class for marshaling/unmarshaling WiseML entities to/from xml.
 */
public final class WiseMLController {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(WiseMLController.class);

    /**
     * This method loads a wiseml from file.
     *
     * @param data a Reader instance.
     * @return a WiseML instance.
     * @throws JiBXException a JiBXException exception.
     */
    public WiseML loadWiseML(final Reader data) throws JiBXException {

        // unmarshal wiseml information from file...
        final IBindingFactory bfact = BindingDirectory.getFactory(WiseML.class);
        final IUnmarshallingContext uctx = bfact.createUnmarshallingContext();

        return (WiseML) uctx.unmarshalDocument(data, null);
    }

    /**
     * This method loads a wiseml from file.
     *
     * @param file a file InputStream instance.
     * @return wiseml a WiseML instance.
     * @throws JiBXException a JiBXException exception.
     */
    public WiseML loadWiseML(final InputStream file) throws JiBXException {
        return loadWiseML(new InputStreamReader(file));
    }

    /**
     * This method loads a wiseml from file.
     *
     * @param file a file InputStream instance.
     * @return the loaded wiseml.
     * @throws JiBXException a JiBXException exception.
     */
    public WiseML loadWiseMLFromFile(final InputStream file) throws JiBXException {
        try {
            // unmarshal  information from file...
            final IBindingFactory bfact = BindingDirectory.getFactory(WiseML.class);
            final IUnmarshallingContext uctx = bfact.createUnmarshallingContext();
            return (WiseML) uctx.unmarshalDocument(file, null);

        } catch (JiBXException e) {
            LOGGER.fatal(e);
            throw e;
        }
    }

    /**
     * This method loads setup from file.
     *
     * @param file a file InputStream instance.
     * @return a setup instance.
     * @throws JiBXException a JiBXException exception.
     */
    public Setup loadSetupFromFile(final InputStream file) throws JiBXException {
        try {
            // unmarshal setup information from file...
            final IBindingFactory bfact = BindingDirectory.getFactory(Setup.class);
            final IUnmarshallingContext uctx = bfact.createUnmarshallingContext();
            return (Setup) uctx.unmarshalDocument(file, null);

        } catch (JiBXException e) {
            LOGGER.fatal(e);
            throw e;
        }
    }

    /**
     * this method writes wiseml in a file.
     *
     * @param wiseml a WiseML instance.
     * @param file a File instance.
     * @throws FileNotFoundException a FileNotFoundException exception.
     * @throws JiBXException a JibXException exception.
     */
    public void writeWiseMLAsFile(final WiseML wiseml,final File file) throws FileNotFoundException, JiBXException {
        try {
            // marshal object back out to file (with nice indentation, as UTF-8)...
            final IBindingFactory bfact = BindingDirectory.getFactory(WiseML.class);

            final IMarshallingContext mctx = bfact.createMarshallingContext();
            mctx.setIndent(5);
            final FileOutputStream output = new FileOutputStream(file);
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
     * @param setup a setup instance.
     * @param file a File instance.
     * @throws FileNotFoundException a FileNotFoundException exception.
     * @throws JiBXException a JibXException exception.
     */
    public void writeSetupAsFile(final Setup setup,final File file) throws FileNotFoundException, JiBXException {

        try {
            // marshal object back out to file (with nice indentation, as UTF-8)...
            final IBindingFactory bfact = BindingDirectory.getFactory(Setup.class);

            final IMarshallingContext mctx = bfact.createMarshallingContext();
            mctx.setIndent(5);
            final FileOutputStream output = new FileOutputStream(file.getName());
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
     * @param wiseml a WiseML instance.
     * @return string of wiseml data.
     * @throws JiBXException a JibXException exception.
     */
    public String writeWiseMLAsSTring(final WiseML wiseml) throws JiBXException {

        final ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        try {
            // marshal object back out to file (with nice indentation, as UTF-8)...
            final IBindingFactory bfact = BindingDirectory.getFactory(WiseML.class);

            final IMarshallingContext mctx = bfact.createMarshallingContext();
            mctx.setIndent(5);

            mctx.setOutput(buffer, null);
            mctx.marshalDocument(wiseml);

            return buffer.toString();
        } catch (JiBXException e) {
            LOGGER.fatal(e);
            throw e;
        }
    }

    /**
     * This method writes setup as a string.
     *
     * @param setup a setup instance.
     * @return string of setup data
     * @throws JiBXException a JibXException exception.
     */
    public String writeSetupAsString(final Setup setup) throws JiBXException {

        final WiseML wiseml = new WiseML();
        wiseml.setSetup(setup);
        final ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        try {
            // marshal object back out to file (with nice indentation, as UTF-8)...
            final IBindingFactory bfact = BindingDirectory.getFactory(Setup.class);

            final IMarshallingContext mctx = bfact.createMarshallingContext();
            mctx.setIndent(5);

            // initialize the output stream
            mctx.setOutput(buffer, null);
            mctx.marshalDocument(wiseml.getSetup());

            return buffer.toString();
        }catch (JiBXException e) {
            LOGGER.fatal(e);
            throw e;
        }
    }

    /**
     * This method loads wiseml setup from a string and returns it's setup.
     *
     * @param stup a string instance.
     * @return a setup instance.
     * @throws JiBXException a JiBXException exception.
     */
    public Setup loadSetupFromString(final String stup) throws JiBXException {
        try {
            // unmarshal setup information from file...
            final IBindingFactory bfact = BindingDirectory.getFactory(Setup.class);
            final IUnmarshallingContext uctx = bfact.createUnmarshallingContext();
            final ByteArrayInputStream buffer = new ByteArrayInputStream(stup.getBytes());
            return (Setup) uctx.unmarshalDocument(buffer, null);
        } catch (JiBXException e) {
            LOGGER.fatal(e);
            throw e;
        }
    }

    /**
     * This method loads wiseml setup from a string and returns it's setup.
     *
     * @param wiseml a wiseml instance.
     * @return a setup instance.
     * @throws JiBXException a JiBXException exception.
     */
    public Setup loadSetupFromWiseMLString(final String wiseml) throws JiBXException {
        try {
            // unmarshal setup information from file...
            final IBindingFactory bfact = BindingDirectory.getFactory(WiseML.class);
            final IUnmarshallingContext uctx = bfact.createUnmarshallingContext();
            final ByteArrayInputStream buffer = new ByteArrayInputStream(wiseml.getBytes());
            return ((WiseML) uctx.unmarshalDocument(buffer, null)).getSetup();
        } catch (JiBXException e) {
            LOGGER.fatal(e);
            throw e;
        }
    }
}
