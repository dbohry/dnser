package com.danielbohry.dnser.api.dto;

public record UpdateSubdomainRequest(String target, Boolean proxied) {

    public UpdateSubdomainRequest(String target, Boolean proxied) {
        this.target = (target == null || target.isBlank()) ? "0.0.0.0" : target;
        this.proxied = (proxied != null) && proxied;
    }

}
