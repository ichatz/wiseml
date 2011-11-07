package eu.wisebed.rdf;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;
import eu.wisebed.wiseml.model.WiseML;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;

public class WiseML2RDF extends WiseML {


    private Model model;

    public WiseML2RDF(WiseML wml) {
        this.setSetup(wml.getSetup());
        this.setTrace(wml.getTrace());
        this.setVersion(wml.getVersion());
    }

    public Resource exportRDF(Model theModel, String uri) {
        model = theModel;


        // initialize resource and propertiesProperty hasScenario = model.createProperty(uri + "hasScenario");
        Resource resWISEML = model.createResource(uri + "WISEML");
        Property hasScenario = model.createProperty(uri + "hasScenario");
        Property hasSetup = model.createProperty(uri + "hasSetup");
        Property hasTrace = model.createProperty(uri + "hasTrace");
        Property theVersion = model.createProperty(uri + "wiseml_version");
        Property theXmlns = model.createProperty(uri + "xmlns");

        model.add(resWISEML, RDF.type, RDFS.Class);

        Resource newWISEML = model.createResource(uri + "WISEML" + "/" + this.getVersion().replace(" ", "_")); //this.getXmlns().replace(" ", "_"));


        //newWISEML.addProperty(theXmlns, this.getXmlns());
        newWISEML.addProperty(theVersion, this.getVersion());

        // need to communicate the nodes of the experiment between the setup
        // and the trace

        // adding the setup
        Setup2RDF newSetup = new Setup2RDF(this.getSetup());
        Resource resSetup = newSetup.exportRDF(model, uri);
        newWISEML.addProperty(hasSetup, resSetup);

        // adding the trace
        Trace2RDF newTrace = new Trace2RDF(this.getTrace());
        Resource resTrace = newTrace.exportRDF(model, uri, newSetup);
        newWISEML.addProperty(hasTrace, resTrace);

        // adding the wiseml to the model
        model.add(newWISEML, RDF.type, resWISEML);

        return newWISEML;
    }

    public String dump(String wisemlFile) {
        // creates an rdf dump file with the name "wisemlfile_dump.rdf" - returns the name
        String dumpFileName = wisemlFile;
        try {
            model.write(new PrintWriter(new PrintStream(new FileOutputStream(wisemlFile))), "N-TRIPLE");

            System.out.println("File dumped succesfully, filename: " + wisemlFile);
        } catch (Exception e) {
            System.out.println("Error in dumping to rdf file: " + e);
        }

        return wisemlFile;
    }


}
