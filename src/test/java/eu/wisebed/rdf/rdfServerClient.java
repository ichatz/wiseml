package eu.wisebed.rdf;


import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.sparql.core.DatasetGraph;
import com.hp.hpl.jena.sparql.core.DatasetImpl;
import com.hp.hpl.jena.tdb.TDB;
import com.hp.hpl.jena.tdb.TDBFactory;
import org.openjena.atlas.logging.Log;
import org.openjena.fuseki.Fuseki;
import org.openjena.fuseki.server.SPARQLServer;


public class rdfServerClient {

    public static void main (String args[])   {
        if (args.length<2) {
            System.out.println("input WiseML file path in needed");
            System.out.println("output RFD file path in needed");
           return;
        }

       RDFExporter rdfex = new RDFExporter("http://www.wisebed.eu/wiseml2rdf/", args[0], args[1]);
       Model m =  rdfex.modelRDF();



       String sparqlQueryString="SELECT * {?sub ?pre ?obj}";
       Query query = QueryFactory.create(sparqlQueryString) ;
       QueryExecution qexec = QueryExecutionFactory.create(query, m);
       ResultSet results = qexec.execSelect() ;
       ResultSetFormatter.out(results) ;




       String directory = "./tdb";
       Dataset dataset = TDBFactory.createDataset(directory);
       Model tdb = dataset.getDefaultModel();
       tdb.add(m);
       tdb.commit();

       qexec = QueryExecutionFactory.create(query, tdb);
       results = qexec.execSelect() ;
       ResultSetFormatter.out(results) ;


       TDB.sync(tdb);

        Dataset dset=new DatasetImpl(tdb);
        DatasetGraph dg=dset.asDatasetGraph();


        Log.logLevel(SPARQLServer.log.getName(), org.apache.log4j.Level.WARN, java.util.logging.Level.WARNING) ;
        Log.logLevel(Fuseki.serverlog.getName(), org.apache.log4j.Level.WARN, java.util.logging.Level.WARNING) ;
        Log.logLevel("org.eclipse.jetty", org.apache.log4j.Level.WARN, java.util.logging.Level.WARNING) ;

        SPARQLServer srv = new SPARQLServer(dg,"info",1281,true);
        srv.start();

        QueryExecution qExec = QueryExecutionFactory.sparqlService("http://127.0.0.1:1281/info/query", "SELECT * {?s ?p ?o}") ;
        ResultSet rs = qExec.execSelect() ;
        System.out.println(">>"+ ResultSetFormatter.asXMLString(rs));

    //    srv.stop();



      }
}