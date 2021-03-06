package com.capella.guice.services;

import com.capella.guice.domain.OakContentStream;
import com.capella.guice.domain.OakDocument;
import com.capella.guice.exceptions.DocumentManagementException;
import org.apache.jackrabbit.JcrConstants;
import org.apache.jackrabbit.commons.JcrUtils;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.jcr.*;
import javax.jcr.nodetype.NodeType;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static javax.jcr.Property.*;
import static javax.jcr.nodetype.NodeType.NT_FILE;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Ramesh Rajendran
 */
public class OakFileRepository {
    public static final Logger LOGGER = getLogger(OakFileRepository.class);
    private Session session;

    @Inject
    public OakFileRepository(Session session) {
        this.session = session;
    }


    /**
     * Store binary file
     *
     * @throws RepositoryException
     * @throws FileNotFoundException
     */
    public String writeBinaryFile(String path, InputStream inputStream, String documentName, String mimeType) throws RepositoryException {
        Node folder;
        Node rootNode = session.getRootNode();
        if (!rootNode.hasNode(path)) {
            folder = rootNode.addNode(path);
        } else {
            folder = rootNode.getNode(path);
        }
        Node nodeFile;
        if (!folder.hasNode(documentName)) {
            nodeFile = folder.addNode(documentName, NT_FILE);
        } else {
            nodeFile = folder.getNode(documentName);
        }

        nodeFile.addMixin(NodeType.MIX_CREATED);
        nodeFile.addMixin(NodeType.MIX_LAST_MODIFIED);
        nodeFile.addMixin(NodeType.MIX_TITLE);

        Node content = nodeFile.addNode(Property.JCR_CONTENT, "nt:hoFile");



        content.setProperty(Property.JCR_MIMETYPE, mimeType);
        String currentDatTime = LocalDateTime.now().toString();
        //content.setProperty(Property.JCR_CREATED, currentDatTime);
        content.setProperty("jcr:serviceDeliveryId", UUID.randomUUID().toString());
        content.setProperty(JCR_CREATED, currentDatTime);
        content.setProperty(JCR_LAST_MODIFIED, currentDatTime);
        content.setProperty(JCR_NAME, documentName);
        content.setProperty(JCR_ID, UUID.randomUUID().toString());


        Binary binary = session.getValueFactory().createBinary(inputStream);
        content.setProperty(Property.JCR_DATA, binary);
        session.save();
        return content.getIdentifier();

    }

    /**
     * Read binary file
     *
     * @throws RepositoryException
     */

    public OakDocument readBinaryFile(String identifier) throws RepositoryException {
        Node nodeByIdentifier = session.getNodeByIdentifier(identifier);
        Binary binary = nodeByIdentifier.getProperty(JCR_DATA).getBinary();
        String fileName = null;


        return OakDocument.builder()
                .id(identifier)
                .oakContentStream(OakContentStream.builder()
                        .fileName(fileName)
                        .inputStream(binary.getStream())
                        .length(BigInteger.valueOf(binary.getSize()))
                        .build())
                .build();
    }


    public Map<String, String> getProperties(String uuid) throws RepositoryException {
        Node nodeByIdentifier = session.getNodeByIdentifier(uuid);
        PropertyIterator properties = nodeByIdentifier.getProperties();
        Map<String, String> map = new HashMap<>();
        while (properties.hasNext()) {
            Property property = properties.nextProperty();
            System.out.println(property.getName());
            if(property.getName() != "jcr:content") {
                map.put(property.getName(), property.getString());
            }
        }
        return map;
    }


    public void updateProperties(Map<String, String> mappedProperties, String documentId) throws RepositoryException {
        Node nodeByIdentifier = session.getNodeByIdentifier(documentId);

        mappedProperties.entrySet().stream().forEach((entry) -> {
            try {
                nodeByIdentifier.setProperty(entry.getKey(), entry.getValue());
            } catch (RepositoryException e) {
                throw new DocumentManagementException("Failed updating node properties", e);
            }
        });

        session.save();
    }

    public void removeNodeByIdentifier(String documentId) throws RepositoryException {
        Node fileNode = session.getNodeByIdentifier(documentId);
        fileNode.getParent().remove();
        session.save();

    }
}
