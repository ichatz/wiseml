package eu.wisebed.wiseml.controller;

import eu.wisebed.wiseml.model.trace.Trace;
import org.apache.log4j.Logger;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.JiBXException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class TraceController {

    private static final Logger LOGGER = Logger.getLogger(TraceController.class);


    public Trace writeTraceAsFile(final Trace trace,final File file) throws FileNotFoundException, JiBXException {


        final HashMap<Integer, Set<String>> mapTr = new HashMap<Integer, Set<String>>();
        final HashMap<String, HashMap<Integer, Set<String>>> map = new HashMap<String, HashMap<Integer, Set<String>>>();
        final Set<String> messages = new TreeSet<String>();

//        for (String message : messages) {
//            messages.add(trace.getNode().getMessage().getReading());
//        }
//
//        mapTr.put(trace.getTimestamp(), messages);
//        map.put(trace.getNode().getId(), mapTr);

        final List<Integer> listTimestmps = new ArrayList<Integer>();
        listTimestmps.addAll(mapTr.keySet());
        Collections.sort(listTimestmps);

        final List<String> finalList = new ArrayList<String>();
        finalList.addAll(map.keySet());
        Collections.sort(finalList);


        try {
            // marshal object back out to file (with nice indentation, as UTF-8)...
            final IBindingFactory bfact = BindingDirectory.getFactory(Trace.class);
            final IMarshallingContext mctx = bfact.createMarshallingContext();
            mctx.setIndent(5);
            final FileOutputStream output = new FileOutputStream(file);
            mctx.setOutput(output, null);
            mctx.marshalDocument(trace);

        } catch (FileNotFoundException e) {
            LOGGER.fatal(e);
            throw e;
        } catch (JiBXException e) {
            LOGGER.fatal(e);
            throw e;
        }

        return trace;
    }
}
