package com.capella.guice.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
public class RegisterNodeType {
    @JsonProperty("parentNodeType")
    public String parentNodeType;
    @JsonProperty("nodeType")
    public String nodeType;
    @JsonProperty("queryable")
    public boolean queryable;
    @Singular
    public List<Attribute> attributes;
}