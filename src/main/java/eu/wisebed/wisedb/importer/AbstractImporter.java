package eu.wisebed.wisedb.importer;

import eu.wisebed.api.sm.SessionManagement;
import eu.wisebed.testbed.api.wsn.WSNServiceHelper;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collection;

public abstract class AbstractImporter<E> {

    /**
     * Entity to import records.
     */
    private E entity;

    /**
     * Entities collection to import
     */
    private Collection<E> entities;

    /**
     * The URL of the Session Managment Service endpoint.
     */
    private String endpointUrl;

    /**
     * The Session Managment Service endpoint.
     */
    private SessionManagement sessionManagementService;

    /**
     * InputStream instance to retrieve WiseML records.
     */
    private InputStream wiseMlStream;

    /**
     * a path of a WiseML document
     */
    private String wiseMlPath;

    /**
     * Returns entity instance
     *
     * @return entity instance
     */
    public E getEntity() {
        return entity;
    }

    /**
     * Sets entity
     *
     * @param entity
     */
    public void setEntity(final E entity) {
        this.entity = entity;
    }

    /**
     * Returns entities instances.
     *
     * @return entities
     */
    public Collection<E> getEntities() {
        return entities;
    }

    /**
     * Set entities instances.
     *
     * @param entities
     */
    public void setEntities(final Collection<E> entities) {
        this.entities = entities;
    }

    /**
     * Returns the endpoint url of the Session Managment Service endpoint.
     *
     * @return
     */
    public String getEndpointUrl() {
        return endpointUrl;
    }

    /**
     * Returns the endpoint url of the Session Managment Service endpoint.
     *
     * @param endpointUrl , Session managment service endpoint url
     */
    public void setEndpointUrl(final String endpointUrl) {
        this.endpointUrl = endpointUrl;
    }

    /**
     * Returns a Session Managment Service instance.
     *
     * @return a Session Managment Service instance
     */
    public SessionManagement getSessionManagementService() {
        return sessionManagementService;
    }

    /**
     * Sets a Session Managment Service instance.
     *
     * @param sessionManagementService
     */
    public void setSessionManagementService(final SessionManagement sessionManagementService) {
        this.sessionManagementService = sessionManagementService;
    }

    /**
     * Returns the WiseML InputStream instance.
     *
     * @return a wiseml input stream instance
     */
    public InputStream getWiseMlStream() {
        return wiseMlStream;
    }

    /**
     * Sets the WiseMl InputStream instance.
     *
     * @param wiseMlStream , an input stream.
     */
    public void setWiseMlStream(final InputStream wiseMlStream) {
        this.wiseMlStream = wiseMlStream;
    }

    /**
     * Returns the path of the WiseML document.
     *
     * @return the path of the WiseML document.
     */
    public String getWiseMlPath() {
        return wiseMlPath;
    }

    /**
     * Sets the path of the WiseML document.
     *
     * @param wiseMlPath , the path of the WiseML document.
     */
    public void setWiseMlPath(String wiseMlPath) {
        this.wiseMlPath = wiseMlPath;
    }

    /**
     * Connect to the remote Session Managment Service.
     */
    public void connect() throws Exception {

        if (getEndpointUrl() == null) {
            throw new Exception("Remote Session Management Service endpoint is null");
        }

        // set session managementService
        setSessionManagementService(WSNServiceHelper.getSessionManagementService(getEndpointUrl()));

        // get wiseml in a string
        String wiseMl = getSessionManagementService().getNetwork();

        // setting input stream
        setWiseMlStream(new ByteArrayInputStream(wiseMl.getBytes()));
    }

    /**
     * Connect to the remote Session Managment Service via the url in params.
     *
     * @param url
     * @throws Exception
     */
    public void connect(final String url) throws Exception {

        // set endpoint url
        setEndpointUrl(url);
        connect();
    }

    /**
     * Open local WiseML document file in the path.
     *
     * @param path, a path to a WiseML document file.
     */
    public void open(final String path) throws Exception {

        // set path to open
        setWiseMlPath(path);
        open();
    }

    /**
     * Open local WiseML document file in the wiseMlpath.
     */
    public void open() throws Exception {
        try {
            wiseMlStream = new FileInputStream(getWiseMlPath());
        } catch (Exception e) {
            throw new Exception("Could not open input stream to open file from path (" + getWiseMlPath() + ")");
        }
    }

    /**
     * Abstract declaration of the convert method. Sub-classes must implement this method.
     */
    public abstract void convert();

    /**
     * Abstract declareation of the convertCollection method. Sub-class must implement this metnod
     */
    public abstract void convertCollection(Collection<E> records);
}
