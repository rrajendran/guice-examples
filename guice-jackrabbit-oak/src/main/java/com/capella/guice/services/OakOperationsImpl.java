package com.capella.guice.services;

import com.capella.guice.domain.OakDocument;
import com.capella.guice.exceptions.DocumentManagementException;
import com.google.inject.Inject;

import javax.inject.Named;
import javax.jcr.RepositoryException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @author Ramesh Rajendran
 */

public class OakOperationsImpl implements OakOperations {

    @Inject
    private OakFileRepository oakFileRepository;

    @Inject
    @Named("oak.content.path")
    private String documentPath;

    @Override
    public OakDocument getDocumentById(String documentId) {
        try {
            return oakFileRepository.readBinaryFile(documentId);
        } catch (Exception ex) {
            // do something with your exception
        }
        return null;
    }

    @Override
    public String saveDocument(InputStream inputStream, String documentName, String mimeType) {
        try {
            return oakFileRepository.writeBinaryFile(documentPath, inputStream, documentName, mimeType);
        } catch (Exception e) {
            throw new DocumentManagementException("Save document failed:", e);
        }
    }

    @Override
    public void updateDocumentMetaData(Map<String, String> documentMetaData, String documentId) {

    }

    @Override
    public OakDocument getDocumentMetadataById(String documentId) {
        return null;
    }

    @Override
    public void deleteDocumentById(String documentId) {

    }

    @Override
    public void setProperties(List<Map<String, String>> properties) {

    }

    @Override
    public Map<String, String> getProperty(String propertyKey) {
        try {
            return oakFileRepository.getProperties(propertyKey);
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
        return null;
    }
}
