package com.capella.guice.enums;

/**
 * Created by Ramesh Rajendran on 4/24/16.
 */
public enum PropertyIdsEnum {
    SOURCE_NAME("sourceSystem", "jcr:sourceSystem"),
    SERVICE_DELIVERY_ID("serviceDeliveryId", "jcr:serviceDeliveryId"),
    DEBUG_CORRELATION_ID("debugCorrelationId", "jcr:debugCorrelationId"),
    DOCUMENT_TYPE("documentType", "jcr:documentType"),
    DOCUMENT_NAME("documentName", "jcr:documentName"),
    SOURCE_UNIQUE_REFERENCE("sourceUniqueReference", "jcr:sourceUniqueReference");

    private final String localName;
    private final String id;

    PropertyIdsEnum(String localName, String id) {
        this.localName = localName;
        this.id = id;
    }

    public String getLocalName() {
        return localName;
    }

    public String getId() {
        return id;
    }
}
