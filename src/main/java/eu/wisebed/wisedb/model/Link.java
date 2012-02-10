package eu.wisebed.wisedb.model;

import org.hibernate.annotations.Entity;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * This is a persistant class for the object link that has the
 * properties of a link. In the class there are
 * getter and setter methods for the properties.
 */
@Entity
@Table(name = "links")
public class Link implements Serializable {

    /**
     * Serial Unique Version ID.
     */
    private static final long serialVersionUID = -393203811928650579L;

    public Link() {
        linkPK = new LinkPK();
    }

    /**
     * The link primary key.
     */
    @Id
    @Basic(fetch = FetchType.LAZY)
    private LinkPK linkPK;

//    /**
//     * a boolean value indicating if the link is encrypted or not.
//     */
//    @Column(name = "encrypted", nullable = false)
//    @Basic(fetch = FetchType.LAZY)
//    private Boolean encrypted;
//
//    /**
//     * a boolean value if the link is virtual or not.
//     */
//    @Column(name = "virtual", nullable = false)
//    @Basic(fetch = FetchType.LAZY)
//    private Boolean virtual;

    /**
     * this link belongs to a setup.
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @Column(name = "setup_id")
    @Basic(fetch = FetchType.LAZY)
    private Setup setup;

    /**
     * this method returns the source of the link.
     *
     * @return the source of the link.
     */
    public String getSource() {
        return linkPK.getSource();
    }

    /**
     * this method sets the source of the link.
     *
     * @param source the source of the link.
     */
    public void setSource(final String source) {
        linkPK.setSource(source);
    }

    /**
     * this method returns the target of the link.
     *
     * @return the target of the link.
     */
    public String getTarget() {
        return linkPK.getTarget();
    }

    /**
     * this method sets the target of the link.
     *
     * @param target the target of the link.
     */
    public void setTarget(final String target) {
        linkPK.setTarget(target);

    }

//    /**
//     * this method returns the boolean value encrypted of the link.
//     *
//     * @return the encrypted of the link.
//     */
//    public Boolean isEncrypted() {
//        return encrypted;
//    }
//
//    /**
//     * this method returns the boolean value encrypted of the link.
//     *
//     * @return the encrypted of the link.
//     */
//    public Boolean getEncrypted() {
//        return isEncrypted();
//    }
//
//    /**
//     * this method sets the boolean value encrypted of the link.
//     *
//     * @param encrypted the encrypted of the link.
//     */
//    public void setEncrypted(final Boolean encrypted) {
//        this.encrypted = encrypted;
//    }
//
//    /**
//     * this method returns the boolean value virtual of the link.
//     *
//     * @return the virtual of the link.
//     */
//    public Boolean isVirtual() {
//        return virtual;
//    }
//
//    /**
//     * this method returns the boolean value virtual of the link.
//     *
//     * @return the virtual of the link.
//     */
//    public Boolean getVirtual() {
//        return isVirtual();
//    }
//
//    /**
//     * this method sets the boolean value virtual of the link.
//     *
//     * @param virtual the virtual of the link.
//     */
//    public void setVirtual(final Boolean virtual) {
//        this.virtual = virtual;
//    }

    /**
     * Returns a collection of setups.
     *
     * @return the related setup instance
     */
    public Setup getSetup() {
        return setup;
    }

    /**
     * sets the setup this link belongs to setups.
     *
     * @param setup , a setup instance
     */
    public void setSetup(final Setup setup) {
        this.setup = setup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Link)) return false;

        Link link = (Link) o;

        if (!linkPK.equals(link.linkPK)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = linkPK.hashCode();
        return result;
    }

    @Embeddable
    private class LinkPK implements Serializable {

        /**
         * the source of an object Link.
         */
        @ManyToOne(targetEntity = Node.class)
        @Column(name = "link_source", nullable = false)
        private String source;

        /**
         * the target of an object Link.
         */
        @ManyToOne(targetEntity = Node.class)
        @Column(name = "link_target", nullable = false)
        private String target;

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            LinkPK linkPK = (LinkPK) o;

            if (!source.equals(linkPK.source)) return false;
            if (!target.equals(linkPK.target)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = source.hashCode();
            result = 31 * result + target.hashCode();
            return result;
        }

        public String getTarget() {
            return target;
        }

        public void setTarget(String target) {
            this.target = target;
        }
    }
}
