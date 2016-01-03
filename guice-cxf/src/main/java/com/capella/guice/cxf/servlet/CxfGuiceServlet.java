package com.capella.guice.cxf.servlet;

import com.capella.guice.cxf.resources.impl.GreetingsResourceImpl;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSBindingFactory;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.provider.JAXBElementProvider;
import org.apache.cxf.jaxrs.provider.json.JSONProvider;
import org.apache.cxf.transport.servlet.CXFNonSpringServlet;

import javax.servlet.ServletConfig;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ramesh
 */
public class CxfGuiceServlet extends CXFNonSpringServlet {

    @Override
    protected void loadBus(ServletConfig sc) {
        super.loadBus(sc);
        JAXRSServerFactoryBean jaxrsServerFactoryBean = new JAXRSServerFactoryBean();
        jaxrsServerFactoryBean.setResourceClasses(GreetingsResourceImpl.class);
        jaxrsServerFactoryBean.setBindingId(JAXRSBindingFactory.JAXRS_BINDING_ID);
        jaxrsServerFactoryBean.setProviders(getProviders());
        jaxrsServerFactoryBean.setAddress("/greetings");

        Server myServer = jaxrsServerFactoryBean.create();
    }

    public Object getJaxbProvider() {
        JAXBElementProvider jaxbElementProvider = new JAXBElementProvider();

        return jaxbElementProvider;
    }

    public Object getJsonProvider() {
        JSONProvider jsonProvider = new JSONProvider();
        return jsonProvider;
    }

    public List<Object> getProviders() {
        List<Object> providers = new ArrayList<>();
        providers.add(getJaxbProvider());
        providers.add(getJsonProvider());
        return providers;
    }
}
