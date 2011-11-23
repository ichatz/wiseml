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
 *
 */
public final class NodeController {

    /**
     * Logger
     */
    private static final Logger LOGGER = Logger.getLogger(NodeController.class);

    /**
     *
     * @param file
     * @return
     * @throws JiBXException
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
     *
     * @param stup
     * @return
     * @throws JiBXException
     */
    public Node load(final String stup) throws JiBXException{
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
     *
     * @param node
     * @param file
     * @return
     * @throws JiBXException
     * @throws FileNotFoundException
     */
    public Node write(final Node node, final File file) throws JiBXException, FileNotFoundException {
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
