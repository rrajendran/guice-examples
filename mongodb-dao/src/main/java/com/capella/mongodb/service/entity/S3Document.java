package com.capella.mongodb.service.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Ramesh Rajendran
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class S3Document implements Serializable{
    @JsonProperty("_id")
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
