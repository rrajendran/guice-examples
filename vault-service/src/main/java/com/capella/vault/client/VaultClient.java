package com.capella.vault.client;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.slf4j.Logger;

import com.bettercloud.vault.VaultException;
import com.capella.vault.domain.VaultResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Ramesh Rajendran
 */
public class VaultClient {
    public static final Logger LOGGER = getLogger(VaultClient.class);
    @Inject
    private Client httpClient;

    @Inject
    @Named("vault.url")
    private String vaultUrl;

    @Inject
    @Named("vault.token")
    private String token;

    @Inject
    private ObjectMapper objectMapper;

    public Response create(String path, Map<String, Object> data) throws VaultException, IOException {
        String jsonBody = objectMapper.writeValueAsString(data);
        URI uri = UriBuilder
                        .fromUri(vaultUrl)
                        .path("/v1" + path)
                        .build();
        LOGGER.info("Vault create url= {}", uri);
        Response response = httpClient.target(uri).request().header("X-Vault-Token", token).buildPost(Entity.entity(jsonBody, "application/json"))
                        .invoke();
        LOGGER.info("Response status = " + response.getStatus());
        if (response.getStatus() == 200 || response.getStatus() == 204) {
            return response;
        }
        throw new VaultException("Unable to create secrets: " + jsonBody);
    }

    public VaultResponse get(String path, String key) throws VaultException, IOException {

        URI uri = UriBuilder
                        .fromUri(vaultUrl)
                        .path("/v1" + path)
                        .build();
        LOGGER.info("Vault create url= {}", uri);
        Response response = httpClient.target(uri).request("application/json").header("X-Vault-Token", token).buildGet()
                        .invoke();
        LOGGER.info("Response status = " + response.getStatus());
        if (response.getStatus() == 200 || response.getStatus() == 204) {
            byte[] bytes = response.readEntity(byte[].class);
            return objectMapper.readValue(bytes, VaultResponse.class);
        }
        throw new VaultException("Unable to get secret key: " + key);
    }

}
