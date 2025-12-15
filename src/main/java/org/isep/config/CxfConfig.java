package org.isep.config;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.isep.web.soap.ProfesseurServiceWsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import jakarta.xml.ws.Endpoint;

@Configuration
public class CxfConfig {

    @Autowired
    private Bus bus;

    @Autowired
    private ProfesseurServiceWsImpl professeurServiceImpl;

    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, professeurServiceImpl);
        // Cela va publier le service à l'adresse /services/professeurs
        endpoint.publish("/professeurs");
        return endpoint;
    }
}