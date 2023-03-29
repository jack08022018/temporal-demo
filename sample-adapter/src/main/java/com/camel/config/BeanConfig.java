package com.camel.config;

import com.arjuna.ats.jta.TransactionManager;
import com.camel.config.extensions.ActiveMq;
import com.camel.config.extensions.IBMMQ;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import com.ibm.mq.jms.MQConnectionFactory;
import com.ibm.mq.jms.MQConnectionFactory;
import com.ibm.msg.client.wmq.compat.jms.internal.JMSC;
import lombok.RequiredArgsConstructor;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.apache.camel.spring.spi.SpringTransactionPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.jms.connection.UserCredentialsConnectionFactoryAdapter;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.QueueConnectionFactory;
import javax.sql.DataSource;
import javax.transaction.UserTransaction;

@EnableJms
@Configuration
@RequiredArgsConstructor
public class BeanConfig {

    final Environment env;

    @Bean(name = "customObjectMapper")
    public ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper;
    }

//    @Bean
//    public JmsListenerContainerFactory<?> jmsListenerContainerFactory(
//            ConnectionFactory connectionFactory,
//            DefaultJmsListenerContainerFactoryConfigurer configurer) {
//        var factory = new DefaultJmsListenerContainerFactory();
//        configurer.configure(factory, connectionFactory);
//        factory.setTransactionManager(transactionManager());
//        return factory;
//    }
//
//    @Bean
//    public PlatformTransactionManager transactionManager() {
//        var transactionManager = new JmsTransactionManager();
//        transactionManager.setConnectionFactory(jmsConnectionFactory());
//        return transactionManager;
//    }
//
//    @Bean
//    public QueueConnectionFactory jmsConnectionFactory() {
//        var connectionFactory = new MQConnectionFactory("tcp://localhost:5672");
//        return connectionFactory;
//    }

//    @Bean(name = "ibmmq")
//    public IBMMQ ibmmq() {
//        var mq = new IBMMQ();
//        mq.setCamelName("ibmmq");
//        mq.setHost("localhost");
//        mq.setPort(1416);
//        mq.setQueueManager("QM1");
//        mq.setChannel("DEV.ADMIN.SVRCONN");
//        mq.setUser("admin");
//        mq.setPassword("passw0rd");
//        return mq;
//    }
//
//    @Bean(name = "activemq")
//    public ActiveMq activeMq() {
//        var mq = new ActiveMq();
//        mq.setHost("tcp://localhost:61616");
//        return mq;
//    }

//    @Bean
    public ConnectionFactory connectionFactory() throws JMSException {
        var factory = new MQConnectionFactory();
        factory.setHostName("localhost");
        factory.setPort(1414);
        factory.setQueueManager("QM1");
        factory.setChannel("DEV.ADMIN.SVRCONN");
        factory.setTransportType(JMSC.MQJMS_TP_CLIENT_MQ_TCPIP);
        var adapter = new UserCredentialsConnectionFactoryAdapter();
        adapter.setUsername("admin");
        adapter.setPassword("passw0rd");
        adapter.setTargetConnectionFactory(factory);
        var caching = new CachingConnectionFactory();
        caching.setTargetConnectionFactory(adapter);
        caching.setSessionCacheSize(500);
        caching.setReconnectOnException(true);
        return caching;
    }

//    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() throws JMSException {
        var factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
//        factory.setErrorHandler(sampleJmsErrorHandler);
        return factory;
    }

//    @Bean
    public JmsTemplate jmsTemplateIbm() throws JMSException {
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(connectionFactory());
        template.setPubSubDomain(false); // false for a Queue, true for a Topic
        return template;
    }

//    @Bean
//    public PlatformTransactionManager transactionManagerIbm() throws JMSException {
//        var transactionManager = new JmsTransactionManager();
//        transactionManager.setConnectionFactory(connectionFactory());
//        return transactionManager;
//    }
//
//    @Bean(name = "txPolicyIbm")
//    public SpringTransactionPolicy txPolicyIbm(
//            final @Qualifier("transactionManagerIbm") PlatformTransactionManager txManager) {
//        var policy = new SpringTransactionPolicy();
//        policy.setTransactionManager(txManager);
//        return policy;
//    }

//    @Bean(name = "ibmmq")
    public JmsComponent jmsComponent() throws JMSException {
        var jmsComponent = new JmsComponent();
//        jmsComponent.setTransactionManager(transactionManagerIbm());
        jmsComponent.setConnectionFactory(connectionFactory());
        return jmsComponent;
    }

    @Bean
    CamelContextConfiguration contextConfiguration() {
        return new CamelContextConfiguration() {
            @Override
            public void beforeApplicationStart(CamelContext context) {
                addIbmMq(context);
                System.setProperty("hawtio.authenticationEnabled", "false");
            }

            @Override
            public void afterApplicationStart(CamelContext context) {
            }
        };
    }

    private void addIbmMq(CamelContext context) {
        var factory = new MQConnectionFactory();
        try {
            factory.setQueueManager("QM1");
            factory.setTransportType(1);
            factory.setPort(1414);
            factory.setHostName("localhost");
            factory.setChannel("DEV.ADMIN.SVRCONN");
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
        var adapter = new UserCredentialsConnectionFactoryAdapter();
        adapter.setTargetConnectionFactory(factory);
        adapter.setUsername("admin");
        adapter.setPassword("passw0rd");
        var caching = new CachingConnectionFactory();
        caching.setTargetConnectionFactory(adapter);
        caching.setSessionCacheSize(500);
        caching.setReconnectOnException(true);

        var component = JmsComponent.jmsComponentAutoAcknowledge(caching);
//        JBossJtaTransactionManager transactionManager = new JBossJtaTransactionManager();
//        PlatformTransactionManager ptm = new DataSourceTransactionManager(dataSource);
//        component.setTransactionManager(ptm);
        var template = new JmsTemplate();
        template.setConnectionFactory(factory);
        template.setPubSubDomain(false); // false for a Queue, true for a Topic
        context.addComponent("ibmmq", component);
    }

//    @Bean(name = "txPolicyMariadb")
//    public SpringTransactionPolicy txPolicyMaria(
//            final @Qualifier("mariadbTransactionManager") PlatformTransactionManager txManager) {
//        var policy = new SpringTransactionPolicy();
//        policy.setTransactionManager(txManager);
//        return policy;
//    }
}
