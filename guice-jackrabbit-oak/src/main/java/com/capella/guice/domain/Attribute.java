package com.capella.guice.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
public class Attribute {
    @JsonProperty("name")
    public final String name;
    @JsonProperty("dataType")
    public final String dataType;
}