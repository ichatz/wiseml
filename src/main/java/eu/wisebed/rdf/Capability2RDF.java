package eu.wisebed.rdf;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;
import eu.wisebed.wiseml.model.setup.Capability;

public class Capability2RDF extends Capability {



    public     Capability2RDF(Capability ca){
        this.setDatatype(ca.getDatatype());
        this.setDefaultvalue(ca.getDefaultvalue());
        this.setLinkReadings(ca.getLinkReadings());
        this.setLinks(ca.getLinks());
        this.setName(ca.getName());
        this.setNodeReadings(ca.getNodeReadings());
        this.setNodes(ca.getNodes());
        this.setUnit(ca.getUnit());
    }

    private Model model;

    public Resource exportRDF(Model theModel, String uri)
    {
       // set the Jena model reference
       model = theModel;

       // initialize resource and properties
       Resource resCapability = model.createResource(uri + "Capability");

       Property capability_Name = model.createProperty(uri + "capability_Name");
       Property capability_Datatype = model.createProperty(uri + "capability_Datatype");
       Property capability_Unit = model.createProperty(uri + "capability_Unit");
       Property capability_Default = model.createProperty(uri + "capability_Default");



       model.add(resCapability, RDF.type, RDFS.Class);
       Resource newCap = model.createResource(uri + "Capability" + "/" + this.getName().replace(" ", "_").replace("(", "_").replace(")", "_"));

       newCap.addLiteral(capability_Datatype, this.getDatatype());
       newCap.addLiteral(capability_Default, this.getDefaultvalue() );
       newCap.addLiteral(capability_Unit, this.getUnit());

       model.add(newCap, RDF.type, resCapability);

       return newCap;

    }

}
