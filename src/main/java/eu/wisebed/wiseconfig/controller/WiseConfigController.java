package eu.wisebed.wiseconfig.controller;

import eu.wisebed.wiseconfig.model.TestbedConfiguration;
import org.apache.log4j.Logger;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.JiBXException;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * This is a a utility class for persisting and loading instances of classes to/from xml file so as to provide
 * configuration.
 */
public final class WiseConfigController {

    /**
     * Logger instance.
     */
    private static final Logger LOGGER = Logger.getLogger(WiseConfigController.class);

    /**
     * this method loads a wiseml from file.
     *
     * @param file InputStream instance.
     * @return testbed configuration instance.
     * @throws JiBXException JiBXException Exception.
     */
    public TestbedConfiguration loadWiseMLFromFile(final InputStream file) throws JiBXException {

        try {
            // unmarshal wiseml information from file...
            final IBindingFactory bfact = BindingDirectory.getFactory(TestbedConfiguration.class);
            final IUnmarshallingContext uctx = bfact.createUnmarshallingContext();
            return (TestbedConfiguration) uctx.unmarshalDocument(file, null);
        } catch (JiBXException e) {
            LOGGER.fatal(e);
            throw e;
        }
    }


    /**
     * this method loads a TestbedConfiguration from file.
     *
     * @param config a testbed configuration instance
     * @return serialized wiseml in a String instance.
     * @throws JiBXException a JiBXException exception.
     */
    public String getConfigurationFile(final TestbedConfiguration config) throws JiBXException {
        try {
            // unmarshal wiseml information from file...
            final IBindingFactory bfact = BindingDirectory.getFactory(TestbedConfiguration.class);
            final IMarshallingContext mctx = bfact.createMarshallingContext();
            final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            mctx.setIndent(5);
            mctx.setOutput(buffer, null);
            mctx.marshalDocument(config);
            return buffer.toString();
        } catch (JiBXException e) {
            LOGGER.fatal(e);
            throw e;
        }
    }
}
