package eu.wisebed.wiserdf;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;
import eu.wisebed.wiseml.model.setup.Capability;

public class Capability2RDF extends Capability {


    public Capability2RDF(Capability ca) {
        this.setDatatype(ca.getDatatype());
        this.setDefaultvalue(ca.getDefaultvalue());
        this.setLinkReadings(ca.getLinkReadings());
        this.setLinks(ca.getLinks());
        this.setName(ca.getName());
        this.setNodeReadings(ca.getNodeReadings());
        this.setNodes(ca.getNodes());
        this.setUnit(ca.getUnit());
    }

    public Resource exportRDF(final Model model, final String uri) {
        // initialize resource and properties
        Resource resCapability = model.createResource(uri + "Capability");

        model.createProperty(uri + "capability_Name");
        final Property capDatatype = model.createProperty(uri + "capability_Datatype");
        final Property capUnit = model.createProperty(uri + "capability_Unit");
        final Property capDefault = model.createProperty(uri + "capability_Default");

        model.add(resCapability, RDF.type, RDFS.Class);
        final Resource newCap = model.createResource(uri + "Capability" + "/" + this.getName().replace(" ", "_").replace("(", "_").replace(")", "_"));

        newCap.addLiteral(capDatatype, this.getDatatype());
        newCap.addLiteral(capDefault, this.getDefaultvalue());
        newCap.addLiteral(capUnit, this.getUnit());

        model.add(newCap, RDF.type, resCapability);

        return newCap;

    }

}
