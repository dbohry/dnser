package com.danielbohry.dnser.api.dto;

public record SubdomainRequest(String name, String target, Boolean proxied) {

    public SubdomainRequest(String name, String target, Boolean proxied) {
        this.name = name;
        this.target = (target == null || target.isBlank()) ? "0.0.0.0" : target;
        this.proxied = (proxied != null) && proxied;
    }

}
