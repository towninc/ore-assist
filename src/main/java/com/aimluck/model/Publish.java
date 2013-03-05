package com.aimluck.model;

import java.io.Serializable;

import com.google.appengine.api.datastore.Key;
import java.util.Date;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;

@Model(schemaVersion = 1, kind = "p", schemaVersionName = "sV")
public class Publish implements Serializable {

    private static final long serialVersionUID = 1L;
    @Attribute(primaryKey = true, name = "k")
    private Key key;
    @Attribute(version = true, name = "v")
    private Long version;
    @Attribute(name = "n")
    private String name;
    @Attribute(name = "cT")
    private String contentType;
    @Attribute(name = "c", lob = true)
    private String content;
    @Attribute(name = "iP")
    private boolean isPublished;
    @Attribute(name = "uDR")
    private ModelRef<UserData> userDataRef = new ModelRef<UserData>(UserData.class);
    @Attribute(name = "cA")
    private Date createdAt;
    @Attribute(name = "uA")
    private Date updatedAt;

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public String getContentType() {
        return contentType;
    }

    /**
     *
     * @param contentType
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     *
     * @return
     */
    public String getContent() {
        return content;
    }

    /**
     *
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     *
     * @return
     */
    public boolean isPublished() {
        return isPublished;
    }

    /**
     *
     * @param isPublished
     */
    public void setPublished(boolean isPublished) {
        this.isPublished = isPublished;
    }

    /**
     * 
     * @return
     */
    public ModelRef<UserData> getUserDataRef() {
        return userDataRef;
    }

    /**
     * 
     * @return
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * 
     * @param createdAt
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     *
     * @return
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     *
     * @param updatedAt
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * Returns the key.
     *
     * @return the key
     */
    public Key getKey() {
        return key;
    }

    /**
     * Sets the key.
     *
     * @param key
     *            the key
     */
    public void setKey(Key key) {
        this.key = key;
    }

    /**
     * Returns the version.
     *
     * @return the version
     */
    public Long getVersion() {
        return version;
    }

    /**
     * Sets the version.
     *
     * @param version
     *            the version
     */
    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Publish other = (Publish) obj;
        if (key == null) {
            if (other.key != null) {
                return false;
            }
        } else if (!key.equals(other.key)) {
            return false;
        }
        return true;
    }
}
