package eu.wisebed.rdf;


import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.sparql.core.DatasetGraph;
import com.hp.hpl.jena.sparql.core.DatasetImpl;
import org.openjena.atlas.logging.Log;
import org.openjena.fuseki.Fuseki;
import org.openjena.fuseki.server.SPARQLServer;


public class rdfServer {

    public static void main (String args[])   {
        if (args.length<2) {
            System.out.println("input WiseML file path in needed");
            System.out.println("output RFD file path in needed");
           return;

        }

        RDFExporter rdfex = new RDFExporter("http://www.wisebed.eu/wiseml2rdf/", args[0], args[1]);
        Model m =  rdfex.modelRDF();
        Dataset dset=new DatasetImpl(m);
        DatasetGraph dg=dset.asDatasetGraph();

        Log.logLevel(SPARQLServer.log.getName(), org.apache.log4j.Level.WARN, java.util.logging.Level.WARNING) ;
        Log.logLevel(Fuseki.serverlog.getName(), org.apache.log4j.Level.WARN, java.util.logging.Level.WARNING) ;
        Log.logLevel("org.eclipse.jetty", org.apache.log4j.Level.WARN, java.util.logging.Level.WARNING) ;

        SPARQLServer srv = new SPARQLServer(dg,"info",1281,true);
        srv.start();

     }
}