package com.capella.guice.services;

import com.capella.guice.domain.OakDocument;
import com.capella.guice.exceptions.DocumentManagementException;
import com.capella.guice.mappers.PropertyIdMapper;
import com.google.inject.Inject;
import org.slf4j.Logger;

import javax.inject.Named;
import javax.jcr.ItemNotFoundException;
import javax.jcr.RepositoryException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Ramesh Rajendran
 */

public class OakOperationsImpl implements OakOperations {
    public static final Logger LOGGER = getLogger(OakOperationsImpl.class);
    @Inject
    private OakFileRepository oakFileRepository;

    @Inject
    @Named("oak.content.path")
    private String documentPath;

    @Override
    public OakDocument getDocumentById(String documentId) {
        try {
            return oakFileRepository.readBinaryFile(documentId);
        } catch (ItemNotFoundException ex) {
            LOGGER.error("Document not found " + documentId);
            return null;
        } catch (Exception ex) {
            throw new DocumentManagementException("Fetch document failed -" + documentId, ex);
        }
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
        try {
            Map<String, String> mappedProperties = PropertyIdMapper.map(documentMetaData);
            oakFileRepository.updateProperties(mappedProperties, documentId);
        } catch (Exception ex) {
            throw new DocumentManagementException("Update of metadata failed:", ex);
        }

    }

    @Override
    public OakDocument getDocumentMetadataById(String documentId) {
        return null;
    }

    @Override
    public void deleteDocumentById(String documentId) {
        LOGGER.info("Deleting document = " + documentId);
        try {
            oakFileRepository.removeNodeByIdentifier(documentId);
        } catch (Exception ex) {
            throw new DocumentManagementException("Retrieving metadata faile:", ex);
        }

    }

    @Override
    public void setProperties(List<Map<String, String>> properties) {

    }

    @Override
    public Map<String, String> getProperty(String documentId) {
        try {
            return oakFileRepository.getProperties(documentId);
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
        return null;
    }
}
