package eu.wisebed.wiserdf;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;
import eu.wisebed.wiseml.model.setup.TimeInfo;

public class TimeInfo2RDF extends TimeInfo {
    private Model model;

    public Resource exportRDF(Model theModel, String uri) {
        // set the Jena model reference
        model = theModel;
        Property hasDuration = model.createProperty(uri + "hasDuration");
        Property hasEnd = model.createProperty(uri + "hasEnd");
        Property hasStart = model.createProperty(uri + "hasStart");
        Property hasUnit = model.createProperty(uri + "hasUnit");


        Resource resTimeInfo = model.createResource(uri + "TimeInfo");
        model.add(resTimeInfo, RDF.type, RDFS.Class);
        Resource newTimeInfo = model.createResource(uri + "Position" + "/" + this.toString());

        newTimeInfo.addProperty(hasDuration, (this.getDuration() == 0) ? "No duration mentioned" : Integer.toString(this.getDuration()));
        newTimeInfo.addProperty(hasEnd, this.getEnd());
        newTimeInfo.addProperty(hasStart, this.getStart());
        newTimeInfo.addProperty(hasUnit, this.getUnit());


        model.add(newTimeInfo, RDF.type, resTimeInfo);

        return newTimeInfo;

    }
}
