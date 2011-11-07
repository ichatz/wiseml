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

public class WiseConfigController {

    private static Logger LOGGER = Logger.getLogger(WiseConfigController.class);

    /**
     * this method loads a wiseml from file.
     *
     * @param file , InputStream instance.
     * @return testbed configuration instance.
     * @throws org.jibx.runtime.JiBXException , JiBX Exception.
     */
    public TestbedConfiguration loadWiseMLFromFile(InputStream file) throws JiBXException{
        TestbedConfiguration config = new TestbedConfiguration();
        try {
            // unmarshal wiseml information from file...
            IBindingFactory bfact = BindingDirectory.getFactory(TestbedConfiguration.class);
            IUnmarshallingContext uctx = bfact.createUnmarshallingContext();
            config = (TestbedConfiguration) uctx.unmarshalDocument(file, null);

        } catch (JiBXException e) {
            LOGGER.fatal(e);
            throw e;
        }
        return config;
    }


    /**
     * this method loads a TestbedConfiguration from file.
     *
     * @param config , a testbed configuration instance
     * @return wiseml , serialized wiseml.
     * @throws org.jibx.runtime.JiBXException
     */
    public String getConfigurationFile(TestbedConfiguration config) throws JiBXException {
        final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try {
            // unmarshal wiseml information from file...
            IBindingFactory bfact = BindingDirectory.getFactory(TestbedConfiguration.class);
            IMarshallingContext mctx = bfact.createMarshallingContext();
            mctx.setIndent(5);
            mctx.setOutput(buffer, null);
            mctx.marshalDocument(config);

        } catch (JiBXException e) {
            LOGGER.fatal(e);
            throw e;
        }
        return buffer.toString();
    }

}
