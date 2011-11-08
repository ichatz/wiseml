package eu.wisebed.wiseml.controller;

import eu.wisebed.wiseml.model.setup.Node;
import org.apache.log4j.Logger;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.JiBXException;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;


public class NodeController {

    private static final Logger LOGGER = Logger.getLogger(NodeController.class);

    public Node load(FileInputStream file) throws JiBXException {

        try {
            // unmarshal node information from file...
            final IBindingFactory bfact = BindingDirectory.getFactory(Node.class);
            final IUnmarshallingContext uctx = bfact.createUnmarshallingContext();
            return (Node) uctx.unmarshalDocument(file, null);

        } catch (JiBXException e) {
            LOGGER.fatal(e);
            throw e;
        }
    }

    public Node load(String stup) throws JiBXException{

        final ByteArrayInputStream buffer = new ByteArrayInputStream(stup.getBytes());
        try {
            final IBindingFactory bfact = BindingDirectory.getFactory(Node.class);
            final IUnmarshallingContext uctx = bfact.createUnmarshallingContext();
            return (Node) uctx.unmarshalDocument(buffer, null);
        } catch (JiBXException e) {
            LOGGER.fatal(e);
            throw e;
        }
    }

    public Node write(Node node, File file) throws JiBXException, FileNotFoundException {

        try {
            // marshal object back out to file (with nice indentation, as UTF-8)...
            final IBindingFactory bfact = BindingDirectory.getFactory(Node.class);
            final IMarshallingContext mctx = bfact.createMarshallingContext();
            mctx.setIndent(5);
            final FileOutputStream output = new FileOutputStream(file);
            mctx.setOutput(output, null);
            mctx.marshalDocument(node);

        } catch (FileNotFoundException e) {
            LOGGER.fatal(e);
            throw e;
        } catch (JiBXException e) {
            LOGGER.fatal(e);
            throw e;
        }
        return node;
    }

}
