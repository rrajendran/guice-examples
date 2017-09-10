package com.capella.guice.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Attribute {
    @JsonProperty("name")
    public String name;
    @JsonProperty("dataType")
    public String dataType;
}