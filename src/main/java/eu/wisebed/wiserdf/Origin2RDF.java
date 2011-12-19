package eu.wisebed.wiserdf;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;
import eu.wisebed.wiseml.model.setup.Origin;

public class Origin2RDF extends Origin {
    private Model model;

    public Origin2RDF(Origin or) {
        this.setPhi(or.getPhi());
        this.setPhi(or.getPhi());
        this.setTheta(or.getTheta());
        this.setTheta(or.getTheta());
        this.setX(or.getX());
        this.setY(or.getY());
        this.setZ(or.getZ());

    }

    public Resource exportRDF(Model theModel, String uri) {
        // set the Jena model reference
        model = theModel;
        Property hasPhiOrigin = model.createProperty(uri + "hasPhiOrigin");
        Property hasThetaOrigin = model.createProperty(uri + "hasThetaOrigin");
        Property hasXOrigin = model.createProperty(uri + "hasXOrigin");
        Property hasYOrigin = model.createProperty(uri + "hasYOrigin");
        Property hasZOrigin = model.createProperty(uri + "hasZOrigin");

        Resource resOrigin = model.createResource(uri + "Origin");
        model.add(resOrigin, RDF.type, RDFS.Class);
        Resource newOrigin = model.createResource(uri + "Origin" + "/" + this.toString());

        newOrigin.addProperty(hasXOrigin, Float.toString(this.getX()));
        newOrigin.addProperty(hasYOrigin, Float.toString(this.getY()));
        newOrigin.addProperty(hasZOrigin, Float.toString(this.getZ()));
        newOrigin.addProperty(hasPhiOrigin, Float.toString(this.getPhi()));
        newOrigin.addProperty(hasThetaOrigin, Float.toString(this.getTheta()));

        model.add(newOrigin, RDF.type, resOrigin);

        return newOrigin;

    }
}
