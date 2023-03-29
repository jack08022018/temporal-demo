package com.camel.config.extensions;

import lombok.Setter;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.connection.CachingConnectionFactory;

import javax.jms.ConnectionFactory;

@Setter
@EnableJms
public class ActiveMq extends Connections {
    private String host;

    @Override
    public ConnectionFactory connectionFactory() {
        var factory = new ActiveMQConnectionFactory();
        factory.setBrokerURL(host);
        factory.setUseAsyncSend(true);
        factory.setTrustAllPackages(true);
        factory.setDispatchAsync(false);
        factory.setOptimizeAcknowledge(true);
        factory.setAlwaysSessionAsync(true);

        var caching = new CachingConnectionFactory();
        caching.setTargetConnectionFactory(factory);
        caching.setSessionCacheSize(500);
        caching.setReconnectOnException(true);
        return caching;
    }
}
