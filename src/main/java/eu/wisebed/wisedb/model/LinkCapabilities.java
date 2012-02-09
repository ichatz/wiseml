package eu.wisebed.wisedb.model;

import org.hibernate.annotations.Entity;

import javax.persistence.Embeddable;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: amaxilatis
 * Date: 2/9/12
 * Time: 2:48 PM
 */
@Entity
@Table(name = "link_capabilities")
public class LinkCapabilities implements Serializable {

    private static final long serialVersionUID = -1690217775134693179L;

    @Id
    private PrivateKey privateKey;

    public void setCapabilityId(final String id) {
        privateKey.setCapability_id(id);
    }

    public void setSource(final String source) {
        privateKey.setLink_source(source);
    }

    public void setTarget(final String target) {
        privateKey.setLink_target(target);
    }

    public String getCapabilityId() {
        return privateKey.getCapability_id();
    }

    public String setSource() {
        return privateKey.getLink_source();
    }

    public String getTarget() {
        return privateKey.getLink_target();
    }

    @Embeddable
    private class PrivateKey {
        private String capability_id;
        private String link_source;
        private String link_target;

        public String getCapability_id() {
            return capability_id;
        }

        public void setCapability_id(String capability_id) {
            this.capability_id = capability_id;
        }

        public String getLink_source() {
            return link_source;
        }

        public void setLink_source(String link_source) {
            this.link_source = link_source;
        }

        public String getLink_target() {
            return link_target;
        }

        public void setLink_target(String link_target) {
            this.link_target = link_target;
        }
    }
}
