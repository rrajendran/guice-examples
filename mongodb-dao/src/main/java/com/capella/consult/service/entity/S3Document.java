package com.capella.consult.service.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ramesh Rajendran
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class S3Document implements Serializable{
    private String documentId;
    private String documentName;
    private Map<String, String> properties;

    public void addProperty(String key, String value) {
        if (properties == null) {
            properties = new HashMap<>();
        }
        properties.put(key, value);
    }
}
