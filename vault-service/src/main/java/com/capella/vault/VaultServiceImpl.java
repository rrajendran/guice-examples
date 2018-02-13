package com.capella.vault;

import java.io.IOException;
import java.util.Map;

import com.bettercloud.vault.Vault;
import com.bettercloud.vault.VaultException;
import com.capella.vault.client.VaultClient;
import com.capella.vault.domain.VaultResponse;
import com.google.inject.Inject;

/**
 * @author Ramesh Rajendran
 */
public class VaultServiceImpl implements VaultService {

    @Inject
    private Vault vault;

    @Inject
    private VaultClient vaultClient;

    @Override
    public void create(String path, Map<String, Object> secrets) throws VaultException, IOException {
        //vault.logical().write(path, secrets);
        vaultClient.create(path, secrets);
    }

    @Override
    public VaultResponse read(String path, String key) throws VaultException, IOException {
        return vaultClient.get(path, key);//vault.logical().read(path).getData().get(key);
    }
}
