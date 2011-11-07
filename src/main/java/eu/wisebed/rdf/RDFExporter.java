package eu.wisebed.rdf;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import eu.wisebed.wiseml.controller.WiseMLController;
import eu.wisebed.wiseml.model.WiseML;
import org.apache.log4j.Logger;

import java.io.FileInputStream;

public class RDFExporter {

    private static Logger LOGGER = Logger.getLogger(RDFExporter.class);

    private FileInputStream fileML = null;
    private Model wiseModel;
    private String uri;
    private String inwiseml;
    private String outrdf;

    public RDFExporter(String theURI, String inputWiseml, String outputRDF) {
        uri = theURI;
        wiseModel = ModelFactory.createDefaultModel();
        wiseModel.setNsPrefix("rdfs", "http://www.w3.org/2000/01/rdf-schema#");
        wiseModel.setNsPrefix("wiserdf", uri);
        this.inwiseml = inputWiseml;
        this.outrdf = outputRDF;

    }


    public void createRDF() {
        try {
            fileML = new FileInputStream(inwiseml);
            WiseMLController wmlcontroller = new WiseMLController();
            WiseML wml = wmlcontroller.loadWiseMLFromFile(fileML);
            WiseML2RDF wmll = new WiseML2RDF(wml);
            wmll.exportRDF(wiseModel, uri);
            wmll.dump(outrdf);
        } catch (Exception e) {
            LOGGER.error(e);
        }
    }

    public Model modelRDF() {
        try {
            fileML = new FileInputStream(inwiseml);
            WiseMLController wmlcontroller = new WiseMLController();
            WiseML wml = wmlcontroller.loadWiseMLFromFile(fileML);
            WiseML2RDF wmll = new WiseML2RDF(wml);
            wmll.exportRDF(wiseModel, uri);
        } catch (Exception e) {
            LOGGER.error(e);
        }
        return wiseModel;
    }

}
