package com.capella.guice.services;


import javax.inject.Inject;
import javax.jcr.*;
import javax.jcr.security.Privilege;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Objects;

import static javax.jcr.Property.JCR_DATA;
import static javax.jcr.nodetype.NodeType.NT_FILE;
import static javax.jcr.nodetype.NodeType.NT_RESOURCE;

/**
 * Second hop example. Stores, retrieves, and removes example content.
 */

public class JackRabbitOperations {
    @Inject
    private Repository repository;

    @Inject
    private Session session;

    /**
     * Add text to node
     *
     * @param message
     * @throws RepositoryException
     */
    public void addText(String path, String message) throws RepositoryException {
        if (Objects.isNull(path)) {
            throw new IllegalArgumentException("path is required");
        }

        Node rootNode = session.getRootNode();
        AccessControlModule.modifyNodeAccess(session, rootNode, Privilege.JCR_WRITE);
        // Store content
        final Node hello;
        if (!rootNode.hasNode(path)) {
            hello = rootNode.addNode(path);
        } else {
            hello = rootNode.getNode(path);
        }

        hello.setProperty("message", message);
        session.save();
    }

    /**
     * Retrive stored text
     *
     * @return
     * @throws RepositoryException
     */
    public String retrieveText(String path) throws RepositoryException {
        if (Objects.isNull(path)) {
            throw new IllegalArgumentException("path is required");
        }
        Node rootNode = session.getRootNode();
        // Retrieve content
        if (rootNode.hasNode(path)) {
            Node node = rootNode.getNode(path);
            return node.getProperty("message").getString();
        }
        return null;
    }

    /**
     * Remove Node
     *
     * @throws RepositoryException
     */
    public void removeNode(String path) throws RepositoryException {
        if (Objects.isNull(path)) {
            throw new IllegalArgumentException("path is required");
        }
        Node rootNode = session.getRootNode();
        if (rootNode.hasNode(path)) {
            Node node = rootNode.getNode(path);
            node.remove();
            session.save();
        }
    }

    /**
     * Store binary file
     *
     * @throws RepositoryException
     * @throws FileNotFoundException
     */
    public String writeBinaryFile(String path, InputStream inputStream) throws RepositoryException, FileNotFoundException {
        Node rootNode = session.getRootNode();
        Node folder;

        if (!rootNode.hasNode(path)) {
            folder = rootNode.addNode(path);
        } else {
            folder = rootNode.getNode(path);
        }

        Node nodeFile = folder.addNode("Article.pdf", NT_FILE);
        Node content = nodeFile.addNode(Property.JCR_CONTENT, NT_RESOURCE);
        Binary binary = session.getValueFactory().createBinary(inputStream);

        content.setProperty(Property.JCR_DATA, binary);
        content.setProperty(Property.JCR_MIMETYPE, "application/pdf");
        return content.getIdentifier();

    }

    /**
     * Read binary file
     *
     * @throws RepositoryException
     */
    public InputStream readBinaryFile(String identifier) throws RepositoryException {
        if (Objects.isNull(identifier)) {
            throw new IllegalArgumentException("Document identifier is required");
        }
        Node nodeByIdentifier = session.getNodeByIdentifier(identifier);
        Binary binary = nodeByIdentifier.getProperty(JCR_DATA).getBinary();
        return binary.getStream();

    }

    /**
     * Check if node exists or not
     *
     * @param nodePath
     * @return
     * @throws RepositoryException
     */
    public Boolean hasNode(String nodePath) throws RepositoryException {
        if (Objects.isNull(nodePath)) {
            throw new IllegalArgumentException("nodePath is required");
        }
        return session.getRootNode().hasNode(nodePath);
    }
}