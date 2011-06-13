package eu.wisebed.wisedb.test;

import eu.wisebed.testbed.api.wsn.v22.SessionManagement;
import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wiseml.controller.WiseMLController;
import eu.wisebed.wiseml.model.WiseML;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Retrieves Testbed data and inserts them in the db.
 */
public class ImportTestbedEntries {

    public static void main(String[] args) {
        // Initialize hibernate
        HibernateUtil.connectEntityManagers();

        String endpointUrl = "http://wisebed.itm.uni-luebeck.de:8888/sessions";

        SessionManagement sessionManagementService =
                eu.wisebed.testbed.api.wsn.WSNServiceHelper
                        .getSessionManagementService(endpointUrl);

        String wiseml = sessionManagementService.getNetwork();

        System.err.println("Got WiseML from URI: " + endpointUrl);
        System.err.println(wiseml);
        System.err.println("---");
        InputStream is = new ByteArrayInputStream(wiseml.getBytes());
        try {
            WiseMLController cnt = new WiseMLController();
            WiseML stp = cnt.loadWiseMLFromFile(is);
            System.out.println(stp.getVersion());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
