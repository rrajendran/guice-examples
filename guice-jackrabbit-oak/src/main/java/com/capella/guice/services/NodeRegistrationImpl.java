package com.capella.guice.services;

import com.capella.guice.domain.Attribute;
import com.capella.guice.domain.RegisterNodeType;
import com.capella.guice.exceptions.DocumentManagementException;
import com.capella.guice.utils.JsonUtils;
import com.google.inject.Inject;
import org.slf4j.Logger;

import javax.jcr.PropertyType;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Workspace;
import javax.jcr.nodetype.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Ramesh Rajendran
 */

public class NodeRegistrationImpl implements NodeRegistration {

    public static final Logger LOGGER = getLogger(OakFileRepository.class);


    private Session session;

    @Inject
    public NodeRegistrationImpl(Session session) throws RepositoryException {
        this.session = session;
    }

    @Override
    public void register() throws RepositoryException, IOException {
        LOGGER.info("Registering custom properties");
        Workspace workspace = session.getWorkspace();
        NodeTypeManager nodeTypeManager = workspace.getNodeTypeManager();
        InputStream inputStream = NodeRegistrationImpl.class.getClassLoader().getResourceAsStream("node_types.json");
        List<RegisterNodeType> registerNodeType = JsonUtils.jsonToGenericList(inputStream, RegisterNodeType.class);
        registerNodeType.stream().forEach(nt -> registerNewNodeType(nodeTypeManager, nt));
    }

    private void registerNewNodeType(NodeTypeManager nodeTypeManager, RegisterNodeType registerNodeTypes) {
        LOGGER.info("Registering new node type = " + registerNodeTypes.getNodeType());
        try {
            NodeTypeTemplate ndt = nodeTypeManager.createNodeTypeTemplate();


            List<PropertyDefinition> serviceDeliveryIdTemplate = getPropertyDefinitionTemplate(nodeTypeManager, registerNodeTypes.getAttributes());
            ndt.getPropertyDefinitionTemplates().addAll(serviceDeliveryIdTemplate);

            ndt.setName(registerNodeTypes.getNodeType());
            ndt.setQueryable(registerNodeTypes.isQueryable());

            //heritates from nt:resource
            String[] str = {registerNodeTypes.getParentNodeType()};
            ndt.setDeclaredSuperTypeNames(str);
            nodeTypeManager.registerNodeType(ndt, true);
            session.save();

        } catch (Exception e) {
            throw new DocumentManagementException("Error registering nodeType '" + registerNodeTypes.getParentNodeType() + "''", e);
        }
    }

    private boolean hasNodeTypeRegistered(RegisterNodeType registerNodeType, NodeTypeManager nodeTypeManager) {
        NodeType allNodeTypes = null;
        try {
            allNodeTypes = nodeTypeManager.getNodeType(registerNodeType.getParentNodeType());
        } catch (RepositoryException e) {
            throw new DocumentManagementException("Error registering nodeType '" + registerNodeType.getParentNodeType() + "''", e);
        }
        NodeTypeIterator declaredSubtypes = allNodeTypes.getSubtypes();
        while (declaredSubtypes.hasNext()) {
            NodeType nodeType = declaredSubtypes.nextNodeType();
            if (nodeType.getName().equals(registerNodeType.getNodeType())) {
                LOGGER.warn("Node already registered = " + nodeType.getName());
                return true;
            }
        }
        return false;
    }

    private List<PropertyDefinition> getPropertyDefinitionTemplate(NodeTypeManager nodeTypeManager, final List<Attribute> attributes) throws RepositoryException {
        return attributes.stream().map(p -> getPropertyDefinition(p, nodeTypeManager)).collect(Collectors.toList());
    }

    private PropertyDefinition getPropertyDefinition(Attribute attribute, NodeTypeManager nodeTypeManager) {
        LOGGER.info("Registering attributes : " + attribute);
        try {
            PropertyDefinitionTemplate serviceDeliveryIdTemplate = nodeTypeManager.createPropertyDefinitionTemplate();
            serviceDeliveryIdTemplate.setName(attribute.getName());
            serviceDeliveryIdTemplate.setRequiredType(PropertyType.valueFromName(attribute.getDataType()));
            return serviceDeliveryIdTemplate;
        } catch (Exception e) {
            throw new DocumentManagementException("Error registering attributes '" + attribute + "''", e);
        }

    }
}
