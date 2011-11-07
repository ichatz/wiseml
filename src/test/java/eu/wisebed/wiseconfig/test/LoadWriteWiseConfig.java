package eu.wisebed.wiseconfig.test;

import eu.wisebed.wiseconfig.controller.WiseConfigController;
import eu.wisebed.wiseconfig.model.TestbedConfiguration;
import org.jibx.runtime.JiBXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class LoadWriteWiseConfig {

    public static void main(String[] args) throws JiBXException {

        try {
            System.out.println(args[0]);
            String newNetwork = "";
            File file = new File(args[0]);
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            WiseConfigController cnt = new WiseConfigController();
            TestbedConfiguration stp = cnt.loadWiseMLFromFile(fis);
            System.out.println(stp.getNodes().get(0).getApplications().size());
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
}
