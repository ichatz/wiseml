package eu.wisebed.wiseml.controller;

import eu.wisebed.wiseml.model.setup.Node;
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

    public Node load(FileInputStream file) {

        Node node = new Node();

        try {

            // unmarshal node information from file...
            IBindingFactory bfact = BindingDirectory.getFactory(Node.class);
            IUnmarshallingContext uctx = bfact.createUnmarshallingContext();
            node = (Node) uctx.unmarshalDocument(file, null);

        } catch (JiBXException e) {
            e.printStackTrace();
            System.exit(1);
        }

        return node;

    }

    public Node load(String stup) {

        Node node = new Node();
        final ByteArrayInputStream buffer = new ByteArrayInputStream(stup.getBytes());
        try {

            IBindingFactory bfact = BindingDirectory.getFactory(Node.class);
            IUnmarshallingContext uctx = bfact.createUnmarshallingContext();
            node = (Node) uctx.unmarshalDocument(buffer, null);
        } catch (JiBXException e) {
            e.printStackTrace();
        }

        return node;

    }

    public Node write(Node node, File file) {

        try {

            // marshal object back out to file (with nice indentation, as UTF-8)...
            IBindingFactory bfact = BindingDirectory.getFactory(Node.class);
            IMarshallingContext mctx = bfact.createMarshallingContext();
            mctx.setIndent(5);
            FileOutputStream output = new FileOutputStream(file);
            mctx.setOutput(output, null);
            mctx.marshalDocument(node);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (JiBXException e) {
            e.printStackTrace();
            System.exit(1);
        }

        return node;
    }

}
