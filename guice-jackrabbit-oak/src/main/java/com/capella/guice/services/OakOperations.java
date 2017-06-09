package com.capella.guice.services;


import com.capella.guice.domain.OakDocument;

import java.io.InputStream;
import java.util.Map;

/**
 * @author ramesh.rajendran
 */
public interface OakOperations {


    OakDocument getDocumentById(String documentId);


    String saveDocument(InputStream inputStream, String documentName, String mimeType);

    void updateDocumentMetaData(Map<String, String> documentMetaData, String documentId);

    Map<String, String> getDocumentMetadataById(String documentId);


    void deleteDocumentById(String documentId);

}
