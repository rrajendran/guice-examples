package com.capella.arangodb.service.entity;

import com.arangodb.entity.DocumentField;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Ramesh Rajendran
 */
public class S3Document implements Serializable {
    @DocumentField(DocumentField.Type.KEY)
    private String documentId;
    private String documentName;
    private Map<String, String> properties;

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        S3Document that = (S3Document) o;
        return Objects.equals(getDocumentId(), that.getDocumentId()) &&
                Objects.equals(getDocumentName(), that.getDocumentName()) &&
                Objects.equals(getProperties(), that.getProperties());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDocumentId(), getDocumentName(), getProperties());
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("S3Document{");
        sb.append("documentId='").append(documentId).append('\'');
        sb.append(", documentName='").append(documentName).append('\'');
        sb.append(", properties=").append(properties);
        sb.append('}');
        return sb.toString();
    }

    public void addProperty(String key, String value) {
        if (properties == null) {
            properties = new HashMap<>();
        }
        properties.put(key, value);
    }
}
