package com.danielbohry.dnser.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateRecordResponse {

    Result result;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Result {
        private String id;
        private String name;
        private String type;
        private boolean proxied;
        private int ttl;
        private String comment;

        @JsonProperty("zone_id")
        private String zoneId;

        @JsonProperty("content")
        private String target;
    }

}