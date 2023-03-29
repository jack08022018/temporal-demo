package com.camel.config.extensions;

import com.ibm.mq.jms.MQConnectionFactory;
import com.ibm.msg.client.wmq.compat.jms.internal.JMSC;
import lombok.Setter;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.connection.UserCredentialsConnectionFactoryAdapter;

import javax.jms.ConnectionFactory;

@Setter
public class IBMMQ extends Connections {
    private String host;
    private Integer port;
    private String queueManager;
    private String channel;
    private String user;
    private String password;

    @Override
    protected ConnectionFactory connectionFactory() throws Exception {
        var factory = new MQConnectionFactory();
        factory.setHostName(host);
        factory.setPort(port);
        factory.setQueueManager(queueManager);
        factory.setChannel(channel);
        factory.setTransportType(JMSC.MQJMS_TP_CLIENT_MQ_TCPIP);
        var adapter = new UserCredentialsConnectionFactoryAdapter();
        adapter.setUsername(user);
        adapter.setPassword(password);
        adapter.setTargetConnectionFactory(factory);
        var caching = new CachingConnectionFactory();
        caching.setTargetConnectionFactory(adapter);
        caching.setSessionCacheSize(500);
        caching.setReconnectOnException(true);
        return caching;
    }
}
