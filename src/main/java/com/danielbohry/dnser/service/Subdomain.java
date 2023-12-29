package com.danielbohry.dnser.service;

public record Subdomain(String id, String name, String target, Boolean proxied) {

    public static Subdomain of(String name, String target, Boolean proxied) {
        return new Subdomain(null, name, target, proxied);
    }
}
