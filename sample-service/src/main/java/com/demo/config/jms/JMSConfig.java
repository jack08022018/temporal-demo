package com.demo.config.jms;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;
import javax.jms.Session;

@EnableJms
@Configuration
@ComponentScan
public class JMSConfig {
	@Value("${activemq.listener.auto-startup}")
	Boolean autoStartup;

	@Value("${activemq.url}")
	String url;

	@Bean
	public ConnectionFactory connectionFactory() {
		var connectionFactory = new ActiveMQConnectionFactory(url);
		connectionFactory.setUseAsyncSend(true);
		connectionFactory.setTrustAllPackages(true);
		return connectionFactory;
	}

	@Bean
	public JmsTemplate activeMqTemplate() {
		var template = new JmsTemplate(new CachingConnectionFactory(connectionFactory()));
		template.setDeliveryPersistent(false);
		return template;
	}

//	@Bean
//	public JmsListenerContainerFactory jmsListenerContainerFactory() {
//		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
//		factory.setConnectionFactory(connectionFactory());
//		//core poll size=4 threads and max poll size 8 threads
//		factory.setConcurrency("1-1");
//		return factory;
//	}
	@Bean(name = "jmsListenerContainerFactory")
	public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
		var factory = new DefaultJmsListenerContainerFactory();
		factory.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
		factory.setSessionTransacted(true);
		factory.setConnectionFactory(connectionFactory());
    	factory.setAutoStartup(autoStartup);
		return factory;
	}

}
