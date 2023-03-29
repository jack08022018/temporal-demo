package com.camel.config.extensions;

import lombok.Getter;
import lombok.Setter;
import org.apache.camel.Component;
import org.apache.camel.component.jms.JmsComponent;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

public abstract class Connections {
    @Getter @Setter
    protected String camelName;

    protected abstract ConnectionFactory connectionFactory() throws Exception;

    public Component getComponent() throws Exception {
        return JmsComponent.jmsComponentAutoAcknowledge(connectionFactory());
    }
}
