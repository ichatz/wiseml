package eu.wisebed.wiserdf.test;


import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;


public class rdfServerClientUpdate {

    public static void main(String args[]) {


        QueryExecution qExec = QueryExecutionFactory.sparqlService("http://127.0.0.1:1281/info/query", "SELECT * {?s ?p ?o}");
        ResultSet rs = qExec.execSelect();
        System.out.println(">>" + ResultSetFormatter.asXMLString(rs));


    }
}