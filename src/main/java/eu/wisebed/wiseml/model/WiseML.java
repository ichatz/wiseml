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
