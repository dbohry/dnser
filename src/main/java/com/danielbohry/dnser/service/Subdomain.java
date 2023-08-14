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

    public Subdomain(String name, String target) {
        this.name = name;
        this.target = target;
    }
}
