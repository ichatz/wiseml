package eu.wisebed.wisedb.test;

import eu.wisebed.wisedb.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Properties;

public class ListHibernateProperties {
    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = Logger.getLogger(ListHibernateProperties.class);

    public static void main(String[] args){
        // get properties
        Properties props = HibernateUtil.getInstance().getConfiguration().getProperties();
        props.list(System.out);
    }
}
