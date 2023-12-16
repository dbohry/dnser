package com.danielbohry.dnser.service;

import com.danielbohry.dnser.client.CloudflareClient;
import com.danielbohry.dnser.exception.NotFoundException;
import com.danielbohry.dnser.repository.DomainRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DomainService {

    private final DomainRepository repository;
    private final CloudflareClient client;

    public List<Domain> getAll() {
        return repository.findAll();
    }

    public Domain get(String name) {
        return repository.findByName(name).orElseThrow(() -> new NotFoundException("No domain found for name " + name));
    }

    public Domain save(String name) {
        if (repository.existsByName(name)) {
            return get(name);
        }
        return repository.save(Domain.builder().name(name).build());
    }

    public Domain addSubdomain(String domainName, String subDomainName, String target, boolean proxied) {
        Domain domain = get(domainName);

        if (subDomainName == null) {
            subDomainName = RandomStringUtils.randomAlphanumeric(8);
        }

        String externalId = domain.hasSubdomain(subDomainName)
                ? client.update(domain.getSubdomainByName(subDomainName))
                : client.create(new Subdomain(subDomainName, target, proxied));

        Subdomain subdomain = new Subdomain(externalId, subDomainName, target, proxied);
        domain.addSubdomain(subdomain);
        return repository.save(domain);
    }

    public Domain updateSubdomain(String domainName, String subDomainName, String target, boolean proxied) {
        Domain domain = get(domainName);

        if (!domain.hasSubdomain(subDomainName)) {
            throw new NotFoundException("No subdomain found for " + subDomainName);
        }

        Subdomain subdomain = domain.getSubdomainByName(subDomainName);
        subdomain.setName(subDomainName);
        subdomain.setTarget(target);
        subdomain.setProxied(proxied);

        domain.addSubdomain(subdomain);
        return repository.save(domain);
    }

    public Domain removeSubdomain(String domainName, String subdomainName) {
        Domain domain = get(domainName);
        Subdomain subdomain = domain.getSubdomainByName(subdomainName);
        client.delete(subdomain);
        domain.removeSubdomain(subdomainName);
        return repository.save(domain);
    }

}
