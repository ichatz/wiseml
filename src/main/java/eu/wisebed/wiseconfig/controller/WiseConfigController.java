package eu.wisebed.wiseconfig.controller;

import eu.wisebed.wiseconfig.model.TestbedConfiguration;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.JiBXException;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class WiseConfigController {

    /**
     * this method loads a wiseml from file.
     *
     * @param file
     * @return wiseml
     */
    public TestbedConfiguration loadWiseMLFromFile(InputStream file) {
        TestbedConfiguration config = new TestbedConfiguration();
        try {
            // unmarshal wiseml information from file...
            IBindingFactory bfact = BindingDirectory.getFactory(TestbedConfiguration.class);
            IUnmarshallingContext uctx = bfact.createUnmarshallingContext();
            config = (TestbedConfiguration) uctx.unmarshalDocument(file, null);

        } catch (JiBXException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return config;
    }


    /**
     * this method loads a TestbedConfiguration from file.
     *
     * @param config
     * @return wiseml
     */
    public String getConfigurationFile(TestbedConfiguration config) {
        final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try {
            // unmarshal wiseml information from file...
            IBindingFactory bfact = BindingDirectory.getFactory(TestbedConfiguration.class);
            IMarshallingContext mctx = bfact.createMarshallingContext();
            mctx.setIndent(5);
            mctx.setOutput(buffer, null);
            mctx.marshalDocument(config);

        } catch (JiBXException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return buffer.toString();
    }

}
