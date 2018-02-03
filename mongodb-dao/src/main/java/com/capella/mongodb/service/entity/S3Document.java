package com.capella.mongodb.service.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
