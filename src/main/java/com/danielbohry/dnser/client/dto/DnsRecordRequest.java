package com.danielbohry.dnser.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DnsRecordRequest {

    @JsonProperty("content")
    private String target;
    private String name;
    private boolean proxied = false;
    private String type = "A";
    private String comment = "Auto generated";
    private List<String> tags = new ArrayList<>();
    private int ttl = 3600;

    public DnsRecordRequest(String name, String target) {
        this.name = name;
        this.target = target;
    }
}
