package com.danielbohry.dnser.api;

import com.danielbohry.dnser.api.dto.SaveDomainRequest;
import com.danielbohry.dnser.api.dto.SubdomainRequest;
import com.danielbohry.dnser.api.dto.UpdateSubdomainRequest;
import com.danielbohry.dnser.service.Domain;
import com.danielbohry.dnser.service.DomainService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/domains")
@AllArgsConstructor
public class DomainController {

    private final DomainService service;

    @GetMapping
    public ResponseEntity<List<Domain>> get() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Domain> get(@PathVariable String id) {
        return ResponseEntity.ok(service.get(id));
    }

    @PostMapping
    public ResponseEntity<Domain> save(@RequestBody SaveDomainRequest request) {
        Domain response = service.save(request.name());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("{id}/subdomain")
    public ResponseEntity<Domain> addSubdomain(@PathVariable String id, @RequestBody SubdomainRequest request) {
        Domain response = service.addSubdomain(id, request.name(), request.target(), request.proxied());
        return ResponseEntity.ok(response);
    }

    @PutMapping("{id}/subdomain/{subdomain}")
    public ResponseEntity<Domain> updateSubdomain(@PathVariable String id, @PathVariable String subdomain, @RequestBody UpdateSubdomainRequest request) {
        Domain response = service.updateSubdomain(id, subdomain, request.target(), request.proxied());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}/subdomain/{subdomain}")
    public ResponseEntity<Domain> removeSubdomain(@PathVariable String id, @PathVariable String subdomain) {
        Domain response = service.removeSubdomain(id, subdomain);
        return ResponseEntity.ok(response);
    }

}
