package com.danielbohry.dnser.service;

import com.danielbohry.dnser.client.CloudflareClient;
import com.danielbohry.dnser.repository.DomainRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class DomainServiceTest {

    @Autowired
    private DomainRepository repository;

    @MockBean
    private CloudflareClient client;

    private DomainService service;

    @BeforeEach
    public void setup() {
        service = new DomainService(repository, client);
    }

    @AfterEach
    public void teardown() {
        repository.deleteAll();
    }

    @Test
    public void shouldSaveNewDomain() {
        //given
        String name = "mydomain.com";

        //when
        Domain response = service.save(name);

        //then
        assertEquals(response.getName(), name);
    }

    @Test
    public void shouldGetAllDomains() {
        //given
        insertSomeDomain("domain1.com");
        insertSomeDomain("domain2.com");
        insertSomeDomain("domain3.com");

        //when
        List<Domain> response = service.getAll();

        //then
        assertEquals(response.size(), 3);
    }

    @Test
    public void shouldAddSubdomain() {
        //given
        insertSomeDomain("domain1.com");

        //and
        when(client.create(any())).thenReturn("1");

        //when
        Domain response = service.addSubdomain("domain1.com", "test", "0.0.0.0", true);

        //then
        assertEquals(Set.of(new Subdomain("1", "test", "0.0.0.0", true)), response.getSubdomains());
    }

    @Test
    public void shouldAddSubdomainWithRandomName() {
        //given
        insertSomeDomain("domain1.com");

        //and
        when(client.create(any())).thenReturn("1");

        //when
        Domain response = service.addSubdomain("domain1.com", null, "0.0.0.0", true);

        //then
        assertEquals(1, response.getSubdomains().size());
        assertEquals("domain1.com", response.getName());

        if (response.getSubdomains().stream().findFirst().isPresent()) {
            assertEquals("0.0.0.0", response.getSubdomains().stream().findFirst().get().getTarget());
            assertNotNull(response.getSubdomains().stream().findFirst().get().getName());
        }
    }

    @Test
    public void shouldRemoveSubdomain() {
        //given
        String id = "domain1.com";
        insertSomeDomain(id);
        String subdomain1 = "test";
        String subdomain2 = "test2";
        service.addSubdomain(id, subdomain1, "0.0.0.0", true);
        service.addSubdomain(id, subdomain2, "0.0.0.0", true);

        //when
        Domain response = service.removeSubdomain(id, subdomain1);

        //then
        assertEquals(response.getSubdomains(), Set.of(new Subdomain("test2", "0.0.0.0", true)));
    }

    private void insertSomeDomain(String domain) {
        repository.save(Domain.builder().name(domain).build());
    }

}
