package com.camel.config;

import com.camel.config.extensions.Connections;
import lombok.RequiredArgsConstructor;
import org.apache.camel.CamelContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

//@Component
@RequiredArgsConstructor
public class ConnectionInittializer {
    final List<Connections> connections;
    final CamelContext camelContext;

    @Bean
    public void loadCamelComponents() throws Exception {
        if(connections != null) {
            for (Connections s : connections) {
                camelContext.addComponent(s.getCamelName(), s.getComponent());
            }
        }
    }
}
