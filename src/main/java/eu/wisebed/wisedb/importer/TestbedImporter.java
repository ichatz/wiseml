package eu.wisebed.wisedb.importer;

import eu.wisebed.testbed.api.wsn.v22.SessionManagement;
import eu.wisebed.wisedb.controller.CapabilityController;
import eu.wisebed.wisedb.controller.TestbedController;
import eu.wisebed.wisedb.model.Testbed;
import eu.wisebed.wiseml.controller.WiseMLController;
import eu.wisebed.wiseml.model.WiseML;
import eu.wisebed.wiseml.model.setup.Capability;
import eu.wisebed.wiseml.model.setup.Node;
import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;

/**
 * Retrieves testbed records from the Session management endpoint and imports them into the wisedb.
 */
public class TestbedImporter extends AbstractImporter<Testbed> {

    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = Logger.getLogger(TestbedImporter.class);

    /**
     * Testbed name.
     */
    private String testbedName;

    /**
     * Testbed URN prefix.
     */
    private String testbedUrnPrefix;

    /**
     * Web URL of Testbed
     */
    private String testbedUrl;

    /**
     * URL of Testbed.
     */
    private String testbedDescription;

    /**
     * True if testbed is federated.
     */
    private boolean isTestbedFederated;

    /**
     * SNAA URL of Testbed.
     */
    private String testbedSnaaUrl;

    /**
     * SNAA URL of Testbed.
     */
    private String testbedRsUrl;

    /**
     * SNAA URL of Testbed.
     */
    private String testbedSessionUrl;

    /**
     * Default constructor.
     */
    public TestbedImporter() {
        // empty constructor.
    }

    /**
     *
     * @return
     */
    public String getTestbedName() {
        return testbedName;
    }

    /**
     *
     * @param testbedName
     */
    public void setTestbedName(String testbedName) {
        this.testbedName = testbedName;
    }

    /**
     *
     * @return
     */
    public String getTestbedUrnPrefix() {
        return testbedUrnPrefix;
    }

    /**
     *
     * @param testbedUrnPrefix
     */
    public void setTestbedUrnPrefix(String testbedUrnPrefix) {
        this.testbedUrnPrefix = testbedUrnPrefix;
    }

    /**
     *
     * @return
     */
    public String getTestbedUrl() {
        return testbedUrl;
    }

    /**
     *
     * @param testbedUrl
     */
    public void setTestbedUrl(final String testbedUrl) {
        this.testbedUrl = testbedUrl;
    }

    /**
     *
     * @return
     */
    public String getTestbedDescription() {
        return testbedDescription;
    }

    /**
     *
     * @param testbedDescription
     */
    public void setTestbedDescription(final String testbedDescription) {
        this.testbedDescription = testbedDescription;
    }

    /**
     *
     * @return
     */
    public boolean isTestbedFederated() {
        return isTestbedFederated;
    }

    /**
     *
     * @param testbedFederated
     */
    public void setTestbedFederated(boolean testbedFederated) {
        isTestbedFederated = testbedFederated;
    }

    /**
     *
     * @return
     */
    public String getTestbedSnaaUrl() {
        return testbedSnaaUrl;
    }

    /**
     *
     * @param testbedSnaaUrl
     */
    public void setTestbedSnaaUrl(String testbedSnaaUrl) {
        this.testbedSnaaUrl = testbedSnaaUrl;
    }

    /**
     *
     * @return
     */
    public String getTestbedRsUrl() {
        return testbedRsUrl;
    }

    /**
     *
     * @param testbedRsUrl
     */
    public void setTestbedRsUrl(String testbedRsUrl) {
        this.testbedRsUrl = testbedRsUrl;
    }

    /**
     *
     * @return
     */
    public String getTestbedSessionUrl() {
        return testbedSessionUrl;
    }

    /**
     *
     * @param testbedSessionUrl
     */
    public void setTestbedSessionUrl(String testbedSessionUrl) {
        this.testbedSessionUrl = testbedSessionUrl;
    }

    /**
     * Convert the WiseML document describing the records of the testbed into wisedb records.
     */
    public void convert() {

        // Setting up the testbed entity
        Testbed testbed = new Testbed();
        testbed.setName(getTestbedName());
        testbed.setUrnPrefix(getTestbedUrl());
        testbed.setUrl(getTestbedUrl());
        testbed.setDescription(getTestbedDescription());
        testbed.setFederated(isTestbedFederated());
        testbed.setRsUrl(getTestbedRsUrl());
        testbed.setSnaaUrl(getTestbedSnaaUrl());
        testbed.setSessionUrl(getTestbedSessionUrl());

        // set this as it's entity
        setEntity(testbed);

        // persisting testbed
        TestbedController.getInstance().add(testbed);
        LOGGER.debug("Testbed imported to DB (1)");
    }


    /**
     * Convert the WiseML document describing the records of the testbed into wisedb records.
     *
     * @param name , Name of testbed.
     * @param urnPrefix , URN prefix of testbed.
     * @param url , URL of testbed.
     * @param description , Description of testbed.
     * @param federated , Federated testbed (true/false)
     * @param rsUrl , RS service endpoint url.
     * @param snaaUrl , SNAA service endpoint url.
     * @param sessionUrl , Session service endpoint url.
     */
    public void convert(final String name,final String urnPrefix, final String url,
                        final String description,final boolean federated,
                        final String rsUrl, final String snaaUrl, final String sessionUrl){

        // use setter to pass those params
        setTestbedName(name);
        setTestbedUrnPrefix(urnPrefix);
        setTestbedUrl(url);
        setTestbedDescription(description);
        setTestbedFederated(federated);
        setTestbedRsUrl(rsUrl);
        setTestbedSnaaUrl(snaaUrl);
        setTestbedSessionUrl(sessionUrl);

        // call params-less convert
        convert();
    }

    /**
     * Convert the WiseML Testbed entries collection to a WiseDB testbed records.
     *
     * @param collection , collection of testbed
     */
    public void convertCollection(final Collection<Testbed> collection) {

        // set entity collection
        setEntities(collection);

        // import records to db
        for(Testbed testbed : getEntities()) {
            TestbedController.getInstance().add(testbed);
        }
        LOGGER.debug("Testbeds imported to DB(" + collection.size() +").");
    }
}
