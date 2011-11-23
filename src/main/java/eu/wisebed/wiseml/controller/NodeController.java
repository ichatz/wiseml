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


/**
 *  NodeController utility class for marshaling/unmarshaling Node entities to/from xml.
 */
public final class NodeController {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(NodeController.class);

    /**
     * Loads a node from input file.
     *
     * @param file a FileInputStream instance.
     * @return a Node instance.
     * @throws JiBXException a JiBXException exception.
     */
    public Node load(final FileInputStream file) throws JiBXException {

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

    /**
     * Loads a node from a marshaled string.
     *
     * @param stup a String instance.
     * @return a Node instance.
     * @throws JiBXException a JiBXException exception.
     */
    public Node load(final String stup) throws JiBXException {
        try {
            final IBindingFactory bfact = BindingDirectory.getFactory(Node.class);
            final IUnmarshallingContext uctx = bfact.createUnmarshallingContext();
            final ByteArrayInputStream buffer = new ByteArrayInputStream(stup.getBytes());
            return (Node) uctx.unmarshalDocument(buffer, null);
        } catch (JiBXException e) {
            LOGGER.fatal(e);
            throw e;
        }
    }

    /**
     * Writes a node to a file.
     *
     * @param node a node instance.
     * @param file a file instance.
     * @return returns node instance given as input.
     * @throws JiBXException a JiBXException exception.
     * @throws FileNotFoundException a FileNotFoundException exception.
     */
    public Node write(final Node node, final File file) throws JiBXException , FileNotFoundException {
        try {
            // marshal object back out to file (with nice indentation, as UTF-8)...
            final IBindingFactory bfact = BindingDirectory.getFactory(Node.class);
            final IMarshallingContext mctx = bfact.createMarshallingContext();
            mctx.setIndent(5);
            final FileOutputStream output = new FileOutputStream(file);
            mctx.setOutput(output, null);
            mctx.marshalDocument(node);
            return node;
        } catch (FileNotFoundException e) {
            LOGGER.fatal(e);
            throw e;
        } catch (JiBXException e) {
            LOGGER.fatal(e);
            throw e;
        }
    }
}
