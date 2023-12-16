package com.danielbohry.dnser.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Subdomain {

    private String id;
    private String name;
    private String target;
    private Boolean proxied;

    public Subdomain(String name, String target, Boolean proxied) {
        this.name = name;
        this.target = target;
        this.proxied = proxied;
    }
}
