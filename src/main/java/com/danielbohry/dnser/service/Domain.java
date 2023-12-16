package com.danielbohry.dnser.service;

import com.danielbohry.dnser.exception.NotFoundException;
import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class Domain {

    private String id;
    private String name;
    private Set<Subdomain> subdomains;

    public boolean hasSubdomain(String name) {
        if (this.subdomains == null) {
            return false;
        }

        return this.subdomains.stream()
                .anyMatch(i -> i.getName().equals(name));
    }

    public Subdomain getSubdomainByName(String name) {
        return this.subdomains.stream()
                .filter(i -> i.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("No subdomain found with name " + name));
    }

    public void addSubdomain(Subdomain subdomain) {
        if (this.subdomains == null) {
            this.subdomains = new HashSet<>();
        }

        this.subdomains.stream()
                .filter(i -> subdomain.getName() != null)
                .filter(i -> subdomain.getName().equals(i.getName()))
                .findFirst()
                .ifPresent(sub -> {
                    removeSubdomain(subdomain.getName());
                    addSubdomain(subdomain);
                });

        this.subdomains.add(subdomain);
    }

    public void removeSubdomain(String subdomain) {
        if (this.subdomains == null) {
            return;
        }

        this.subdomains.stream()
                .filter(i -> subdomain.equals(i.getName()))
                .findFirst()
                .ifPresent(sub -> this.subdomains.remove(sub));
    }

}