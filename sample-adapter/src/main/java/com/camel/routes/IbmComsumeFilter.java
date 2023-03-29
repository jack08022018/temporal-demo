package com.camel.routes;

import org.apache.camel.Header;
import org.springframework.stereotype.Component;

@Component
public class IbmComsumeFilter {
    public boolean isCamelAdapter(@Header("JMSCorrelationID") ConsumeFilterDto dto) {
        return dto.adapterName.equals("ABC");
//        return jMSCorrelationID.contains("CAMEL");
    }
}
