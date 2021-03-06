package unit.com.capella.consult.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;

import com.bettercloud.vault.VaultException;
import com.capella.consult.service.VaultService;
import com.capella.consult.service.domain.VaultResponse;
import com.capella.consult.service.guice.modules.VaultModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Copyright 2018 (c) Mastek UK Ltd
 * <p>
 * Created on : 10/02/2018
 *
 * @author Ramesh Rajendran
 */
public class VaultServiceImplTest {
    private VaultService vaultService;
    private String path = "/secret/hello1";

    @Before
    public void setUp() {
        Injector injector = Guice.createInjector(new VaultModule());
        vaultService = injector.getInstance(VaultService.class);
    }

    @Test
    public void create() throws VaultException, IOException {
        Map<String, Object> secrets = new HashMap<>();
        secrets.put("user", "ramesh");
        secrets.put("pass", "passwd");
        vaultService.create(path, secrets);
        VaultResponse user = vaultService.read(path, "user");
        assertThat(user.getData().get("user"), CoreMatchers.is("ramesh"));
    }
}