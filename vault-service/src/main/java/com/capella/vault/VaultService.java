package com.capella.vault;

import java.io.IOException;
import java.util.Map;

import com.bettercloud.vault.VaultException;
import com.capella.vault.domain.VaultResponse;

/**
 * @author Ramesh Rajendran
 */
public interface VaultService {

    /**
     * Create secrets
     *
     * @param path
     * @param secrets
     */
    void create(String path, Map<String, Object> secrets) throws VaultException, IOException;

    /**
     * Read value
     *
     * @param path
     * @param key
     * @return
     * @throws VaultException
     */
    VaultResponse read(String path, String key) throws VaultException, IOException;
}
