package eu.wisebed.wisedb.importer;

import eu.wisebed.wisedb.controller.TestbedController;
import eu.wisebed.wisedb.model.Testbed;
import org.apache.log4j.Logger;

import java.util.Collection;

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
    private String name;

    /**
     * Testbed URN prefix.
     */
    private String urnPrefix;

    /**
     * Web URL of Testbed
     */
    private String webPageUrl;

    /**
     * URL of Testbed.
     */
    private String description;

    /**
     * True if testbed is federated.
     */
    private boolean federated;

    /**
     * SNAA URL of Testbed.
     */
    private String snaaUrl;

    /**
     * RS URL of Testbed.
     */
    private String rsUrl;

    /**
     * SessionManagment URL of Testbed.
     */
    private String sessionUrl;

    /**
     * Default constructor.
     */
    public TestbedImporter() {
        // empty constructor.
    }

    /**
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return
     */
    public String getUrnPrefix() {
        return urnPrefix;
    }

    /**
     * @param urnPrefix
     */
    public void setUrnPrefix(String urnPrefix) {
        this.urnPrefix = urnPrefix;
    }

    /**
     * @return
     */
    public String getWebPageUrl() {
        return webPageUrl;
    }

    /**
     * @param webPageUrl
     */
    public void setWebPageUrl(final String webPageUrl) {
        this.webPageUrl = webPageUrl;
    }

    /**
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * Sets true/false whether the testbed is federated or not.
     * @return
     */
    public void setFederated(final boolean federated) {
        this.federated = federated;
    }

    /**
     * Returns true if the testbed is federated or not.
     * @return true/false.
     */
    public boolean isFederated() {
        return federated;
    }

    /**
     * @return
     */
    public String getSnaaUrl() {
        return snaaUrl;
    }

    /**
     * @param snaaUrl
     */
    public void setSnaaUrl(String snaaUrl) {
        this.snaaUrl = snaaUrl;
    }

    /**
     * @return
     */
    public String getRsUrl() {
        return rsUrl;
    }

    /**
     * @param rsUrl
     */
    public void setRsUrl(String rsUrl) {
        this.rsUrl = rsUrl;
    }

    /**
     * @return
     */
    public String getSessionUrl() {
        return sessionUrl;
    }

    /**
     * @param sessionUrl
     */
    public void setSessionUrl(String sessionUrl) {
        this.sessionUrl = sessionUrl;
    }

    /**
     * Convert the WiseML document describing the records of the testbed into wisedb records.
     */
    public void convert() {

        // Setting up the testbed entity
        final Testbed testbed = new Testbed();
        testbed.setName(getName());
        testbed.setUrnPrefix(getWebPageUrl());
        testbed.setUrl(getWebPageUrl());
        testbed.setDescription(getDescription());
        testbed.setFederated(isFederated());
        testbed.setRsUrl(getRsUrl());
        testbed.setSnaaUrl(getSnaaUrl());
        testbed.setSessionUrl(getSessionUrl());

        // persisting testbed
        TestbedController.getInstance().add(testbed);
        LOGGER.debug("Testbed imported to DB (1)");
    }


    /**
     * Convert the WiseML document describing the records of the testbed into wisedb records.
     *
     * @param name        , Name of testbed.
     * @param urnPrefix   , URN prefix of testbed.
     * @param url         , URL of testbed.
     * @param description , Description of testbed.
     * @param federated   , Federated testbed (true/false)
     * @param rsUrl       , RS service endpoint url.
     * @param snaaUrl     , SNAA service endpoint url.
     * @param sessionUrl  , Session service endpoint url.
     */
    public void convert(final String name, final String urnPrefix, final String url,
                        final String description, final boolean federated,
                        final String rsUrl, final String snaaUrl, final String sessionUrl) {

        // use setter to pass those params
        setName(name);
        setUrnPrefix(urnPrefix);
        setWebPageUrl(url);
        setDescription(description);
        setFederated(federated);
        setRsUrl(rsUrl);
        setSnaaUrl(snaaUrl);
        setSessionUrl(sessionUrl);

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
        for (Testbed testbed : getEntities()) {
            TestbedController.getInstance().add(testbed);
        }
        LOGGER.debug("Testbeds imported to DB(" + collection.size() + ").");
    }
}
