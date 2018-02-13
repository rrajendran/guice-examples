package com.capella.consult.service.guice.modules;

import java.util.Properties;

import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.slf4j.Logger;

import com.bettercloud.vault.Vault;
import com.bettercloud.vault.VaultConfig;
import com.bettercloud.vault.VaultException;
import com.capella.consult.service.VaultService;
import com.capella.consult.service.VaultServiceImpl;
import com.capella.consult.service.client.VaultClient;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import static org.slf4j.LoggerFactory.getLogger;

import static com.capella.consult.service.guice.modules.PropertiesProvider.getProperties;
import static com.google.inject.name.Names.bindProperties;

/**
 * @author Ramesh Rajendran
 */
public class VaultModule extends AbstractModule {
    public static final Logger LOGGER = getLogger(VaultModule.class);

    @Override
    protected void configure() {
        bind(VaultService.class).to(VaultServiceImpl.class);
        bind(VaultClient.class);
        binder().bind(Properties.class).toProvider(PropertiesProvider.class).in(Singleton.class);
        bindProperties(binder(), getProperties());
    }

    @Provides
    public VaultConfig getVaultConfig(@Named("vault.url") String vaultUrl,
                    @Named("vault.token") String token) throws VaultException {
        LOGGER.info("Connection to {}, with token {}", vaultUrl, token);
        return new VaultConfig()
                        .address(vaultUrl)
                        .token(token)
                        //.sslConfig(new SslConfig().verify(false).build())
                        .build();

    }

    @Provides
    public Vault getVault(VaultConfig config) {
        return new Vault(config);
    }

    @Provides
    public Client getHttpClient() {
        return ClientBuilder.newClient();
    }

    @Provides
    public ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = (new ObjectMapper()).configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).configure(
                        JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true)
                        .configure(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
        objectMapper.registerModule(new Jdk8Module());
        return objectMapper;
    }

}
