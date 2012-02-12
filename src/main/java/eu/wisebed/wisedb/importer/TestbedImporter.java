package eu.wisebed.wisedb.importer;

import eu.wisebed.wisedb.controller.TestbedController;
import eu.wisebed.wisedb.model.Testbed;
import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.TimeZone;

/**
 * Imports testbed entities into the peristence store.
 */
public final class TestbedImporter extends AbstractImporter<Testbed> {

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
     * Web URL of Testbed.
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
     * Testbed time zone.
     */
    private TimeZone timeZone;

    /**
     * Default constructor.
     */
    public TestbedImporter() {
        // empty constructor.
    }

    /**
     * Returns testbed name.
     *
     * @return testbed name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets testbed name.
     *
     * @param name testbed name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Returns testbed's urn prefix.
     *
     * @return testbed's urn prefix.
     */
    public String getUrnPrefix() {
        return urnPrefix;
    }

    /**
     * Sets urn prefix.
     *
     * @param urnPrefix a urn prefix for testbed.
     */
    public void setUrnPrefix(final String urnPrefix) {
        this.urnPrefix = urnPrefix;
    }

    /**
     * Returns web page url.
     *
     * @return web page url.
     */
    public String getWebPageUrl() {
        return webPageUrl;
    }

    /**
     * Sets web page url.
     *
     * @param webPageUrl , a web page url
     */
    public void setWebPageUrl(final String webPageUrl) {
        this.webPageUrl = webPageUrl;
    }

    /**
     * Returns decription.
     *
     * @return description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description , a description.
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * Sets true/false whether the testbed is federated or not.
     *
     * @param federated boolean variable.
     */
    public void setFederated(final boolean federated) {
        this.federated = federated;
    }

    /**
     * Returns true if the testbed is federated or not.
     *
     * @return true/false.
     */
    public boolean isFederated() {
        return federated;
    }

    /**
     * Returns SNAA url.
     *
     * @return SNAA url.
     */
    public String getSnaaUrl() {
        return snaaUrl;
    }

    /**
     * Sets SNAA url.
     *
     * @param snaaUrl a SNAA url.
     */
    public void setSnaaUrl(final String snaaUrl) {
        this.snaaUrl = snaaUrl;
    }

    /**
     * Returns RS url.
     *
     * @return rs url.
     */
    public String getRsUrl() {
        return rsUrl;
    }

    /**
     * Sets RS url.
     *
     * @param rsUrl a RS url
     */
    public void setRsUrl(final String rsUrl) {
        this.rsUrl = rsUrl;
    }

    /**
     * Returns Session Management url.
     *
     * @return Session Management url.
     */
    public String getSessionUrl() {
        return sessionUrl;
    }

    /**
     * Sets Session Management url.
     *
     * @param sessionUrl a Session Management url.
     */
    public void setSessionUrl(final String sessionUrl) {
        this.sessionUrl = sessionUrl;
    }

    /**
     * Returns testbed's timezone.
     *
     * @return get testbed's timezone.
     */
    public TimeZone getTimeZone() {
        return timeZone;
    }

    /**
     * Sets testbed's timezone.
     *
     * @param timeZone testbed's timezone
     */
    public void setTimeZone(final TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    /**
     * Convert the WiseML document describing the records of the testbed into wisedb records.
     */
    public void convert() {

        // Setting up the testbed entity
        final Testbed testbed = new Testbed();
        testbed.setName(getName());
        testbed.setUrnPrefix(getUrnPrefix());
        testbed.setUrl(getWebPageUrl());
        testbed.setDescription(getDescription());
        testbed.setFederated(isFederated());
        testbed.setRsUrl(getRsUrl());
        testbed.setSnaaUrl(getSnaaUrl());
        testbed.setSessionUrl(getSessionUrl());
        testbed.setTimeZone(getTimeZone());

        // persisting testbed
        TestbedController.getInstance().add(testbed);
        LOGGER.debug("Testbed '" + testbed.getName() + "' imported to DB (1)");
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
