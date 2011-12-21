package eu.wisebed.wiseconfig.test;

import eu.wisebed.wiseconfig.controller.WiseConfigController;
import eu.wisebed.wiseconfig.model.TestbedConfiguration;
import org.apache.log4j.Logger;
import org.jibx.runtime.JiBXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class LoadWriteWiseConfig {

    public static Logger LOGGER = Logger.getLogger(LoadWriteWiseConfig.class);

    public static void main(String[] args) throws JiBXException {

        try {
            LOGGER.info(args[0]);
            String newNetwork = "";
            File file = new File(args[0]);
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            WiseConfigController cnt = new WiseConfigController();
            TestbedConfiguration stp = cnt.loadWiseMLFromFile(fis);
            LOGGER.info(stp.getNodes().get(0).getApplications().size());
        } catch (FileNotFoundException e) {
            LOGGER.error(e);
        }
    }
}
