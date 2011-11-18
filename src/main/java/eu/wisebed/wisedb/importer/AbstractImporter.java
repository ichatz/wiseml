package eu.wisebed.wisedb.importer;

import eu.wisebed.api.sm.SessionManagement;
import eu.wisebed.testbed.api.wsn.WSNServiceHelper;
import org.jibx.runtime.JiBXException;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

/**
 * This class implements an abstract manager which is responsible for all the
 * basic unmarshaling and importing from a wiseml document into the database. Other
 * sub-classes inherit from this one. This means that every sub-class can use
 * all the methods that are implemented here and consequently.
 *
 * @param <E> Generic type of AbstractImporter
 */
public abstract class AbstractImporter<E> {

    /**
     * Entities collection to import.
     */
    private Collection<E> entities;

    /**
     * The URL of the Session Management Service endpoint.
     */
    private String endpointUrl;

    /**
     * The Session Management Service endpoint.
     */
    private SessionManagement sessionManagement;

    /**
     * InputStream instance to retrieve WiseML records.
     */
    private InputStream wiseMlStream;

    /**
     * a path of a WiseML document.
     */
    private String wiseMlPath;

    /**
     * Returns entities instances.
     *
     * @return entities
     */
    public final Collection<E> getEntities() {
        return entities;
    }

    /**
     * Set entities instances.
     *
     * @param entities , collection of E instances.
     */
    public final void setEntities(final Collection<E> entities) {
        this.entities = entities;
    }

    /**
     * Returns the endpoint URL of the Session Managment Service endpoint.
     *
     * @return  returns endpoint URL.
     */
    public final String getEndpointUrl() {
        return endpointUrl;
    }

    /**
     * Returns the endpoint url of the Session Managment Service endpoint.
     *
     * @param endpointUrl , Session managment service endpoint url
     */
    public final void setEndpointUrl(final String endpointUrl) {
        this.endpointUrl = endpointUrl;
    }

    /**
     * Returns a Session Managment Service instance.
     *
     * @return a Session Managment Service instance
     */
    public final SessionManagement getSessionManagement() {
        return sessionManagement;
    }

    /**
     * Sets a Session Managment Service instance.
     *
     * @param sessionManagement , session managment service instance
     */
    public final void setSessionManagement(final SessionManagement sessionManagement) {
        this.sessionManagement = sessionManagement;
    }

    /**
     * Returns the WiseML InputStream instance.
     *
     * @return a wiseml input stream instance
     */
    public final InputStream getWiseMlStream() {
        return wiseMlStream;
    }

    /**
     * Sets the WiseMl InputStream instance.
     *
     * @param value , an input stream.
     */
    public final void setWiseMlStream(final InputStream value) {
        this.wiseMlStream = value;
    }

    /**
     * Returns the path of the WiseML document.
     *
     * @return the path of the WiseML document.
     */
    public final String getWiseMlPath() {
        return wiseMlPath;
    }

    /**
     * Sets the path of the WiseML document.
     *
     * @param wiseMlPath , the path of the WiseML document.
     */
    public final void setWiseMlPath(final String wiseMlPath) {
        this.wiseMlPath = wiseMlPath;
    }

    /**
     * Connect to the remote Session Managment Service.
     * */
    public final void connect() {

        // set session managementService
        setSessionManagement(WSNServiceHelper.getSessionManagementService(getEndpointUrl()));

        // get wiseml in a string
        final String wiseMl = getSessionManagement().getNetwork();

        // setting input stream
        setWiseMlStream(new ByteArrayInputStream(wiseMl.getBytes()));
    }

    /**
     * Connect to the remote Session Managment Service via the URL in params.
     *
     * @param url , session managment service URL
     */
    public final void connect(final String url) {

        // set endpoint url
        setEndpointUrl(url);
        connect();
    }

    /**
     * Open local WiseML document file in the path.
     *
     * @param path a path to a WiseML document file.
     * @throws IOException exception
     */
    public final void open(final String path) throws IOException {

        // set path to open
        setWiseMlPath(path);
        open();
    }

    /**
     * Open local WiseML document file in the wiseMlpath.
     *
     * @throws IOException exception
     */
    public final void open() throws IOException {
        wiseMlStream = new FileInputStream(getWiseMlPath());
    }

    /**
     * Abstract declaration of the convert method. Sub-classes must implement this method.
     * @throws JiBXException an exception
     */
    public abstract void convert() throws JiBXException;

    /**
     * Abstract declareation of the convertCollection method. Sub-class must implement this metnod
     *
     * @param records , a collection of E instances.
     * @throws JiBXException an exception
     */
    public abstract void convertCollection(final Collection<E> records) throws JiBXException;
}
