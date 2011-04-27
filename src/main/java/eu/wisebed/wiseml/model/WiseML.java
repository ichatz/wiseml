package eu.wisebed.wiseml.model;

import eu.wisebed.wiseml.model.scenario.Scenario;
import eu.wisebed.wiseml.model.setup.Setup;
import eu.wisebed.wiseml.model.trace.Trace;

/**
 * This is a persistent class for the object wiseml that has the
 * properties of a wiseml. In the class there are
 * getter and setter methods for the properties.
 */
public class WiseML {

    /**
     * the version of the wiseml file.
     */
    private String version;

    /**
     * the url of the xmlns.
     */
    private String xmlns;

    /**
     * setup nodes for the object Wiseml.
     */
    private Setup setup;

    /**
     * set scenario for the object Wiseml.
     */
    private Scenario scenario;

    /**
     * set trace for the object Wiseml.
     */
    private Trace trace;

    /**
     * this method returns the version of the wiseml.
     *
     * @return the version of the wiseml.
     */
    public String getVersion() {
        return version;
    }

    /**
     * this method sets the version of the wiseml.
     *
     * @param value the value of the wiseml.
     */
    public void setVersion(String value) {
        this.version = value;
    }

    /**
     * this method returns the url of the xmlns.
     *
     * @return a url.
     */
    public String getXmlns() {
        return xmlns;
    }

    /**
     * this method sets the url of the xmlns.
     *
     * @param value a url.
     */
    public void setXmlns(String value) {
        this.xmlns = value;
    }

    /**
     * this method returns the setup of a wiseml.
     *
     * @return the setup of the wiseml.
     */
    public Setup getSetup() {
        return setup;
    }

    /**
     * this method sets a setup of a wiseml.
     *
     * @param setup the wiseml setup.
     */
    public void setSetup(Setup setup) {
        this.setup = setup;
    }

    /**
     * this method returns the scenario of a wiseml.
     *
     * @return the scenario of the wiseml.
     */
    public Scenario getScenario() {
        return scenario;
    }

    /**
     * this method sets a scenario of a wiseml.
     *
     * @param scenario the wiseml scenario.
     */
    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    /**
     * this method returns the trace of a wiseml.
     *
     * @return the trace of the wiseml.
     */
    public Trace getTrace() {
        return trace;
    }

    /**
     * this method sets a trace of a wiseml.
     *
     * @param trace the wiseml trace.
     */
    public void setTrace(Trace trace) {
        this.trace = trace;
    }

}
