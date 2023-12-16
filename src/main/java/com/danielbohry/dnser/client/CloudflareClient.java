package com.danielbohry.dnser.client;

import com.danielbohry.dnser.client.dto.CreateRecordResponse;
import com.danielbohry.dnser.client.dto.DnsRecordRequest;
import com.danielbohry.dnser.client.dto.UpdateRecordResponse;
import com.danielbohry.dnser.service.Subdomain;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class CloudflareClient {

    private static final RestTemplate rest = new RestTemplate();
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final Logger log = LogManager.getLogger(CloudflareClient.class);

    @Value("${cloudflare.url}")
    private String url;

    @Value("${cloudflare.zone}")
    private String zone;

    @Value("${cloudflare.token}")
    private String token;

    @SneakyThrows
    public String create(Subdomain subdomain) {
        var request = new DnsRecordRequest(subdomain.getName(), subdomain.getTarget(), subdomain.getProxied());

        log.info("Create request {}", request);

        HttpEntity<String> requestEntity = new HttpEntity<>(mapper.writeValueAsString(request), defaultHeaders());
        ResponseEntity<CreateRecordResponse> response = rest.exchange(
                url + "zones/" + zone + "/dns_records",
                HttpMethod.POST,
                requestEntity,
                CreateRecordResponse.class
        );

        return Objects.requireNonNull(response.getBody()).getResult().getId();
    }

    @SneakyThrows
    public String update(Subdomain subdomain) {
        var request = new DnsRecordRequest(subdomain.getName(), subdomain.getTarget(), subdomain.getProxied());

        log.info("Update request {}", request);

        HttpEntity<String> requestEntity = new HttpEntity<>(mapper.writeValueAsString(request), defaultHeaders());
        ResponseEntity<UpdateRecordResponse> response = rest.exchange(
                url + "zones/" + zone + "/dns_records/" + subdomain.getId(),
                HttpMethod.PUT,
                requestEntity,
                UpdateRecordResponse.class
        );

        return Objects.requireNonNull(response.getBody()).getResult().getId();
    }

    public void delete(Subdomain subdomain) {
        HttpEntity<String> requestEntity = new HttpEntity<>(null, defaultHeaders());

        log.info("Delete request {}", subdomain);

        rest.exchange(
                url + "zones/" + zone + "/dns_records/" + subdomain.getId(),
                HttpMethod.DELETE,
                requestEntity,
                String.class
        );
    }

    private HttpHeaders defaultHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(APPLICATION_JSON);

        return headers;
    }

}
