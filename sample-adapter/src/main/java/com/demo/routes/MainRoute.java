package com.demo.routes;

import com.demo.config.exeptions.AdapterException;
import com.demo.constant.AllFunction;
import com.demo.dto.AdapterDto;
import com.demo.process.AdapterExceptionProcess;
import com.demo.process.ExceptionProcess;
import com.demo.process.RequestProcess;
import com.demo.service.ApiService;
import com.demo.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class MainRoute extends RouteBuilder {
	final ApiService apiService;
	final AdapterExceptionProcess adapterExceptionProcess;
	final ExceptionProcess exceptionProcess;
	final RequestProcess requestProcess;
    final CommonUtils commonUtils;

	@Override
	@Transactional
	public void configure() throws Exception {
//		onException(Exception.class)
////				.maximumRedeliveries(2)
//				.process(consumerExceptionHandler)
//				.handled(true)
//				.markRollbackOnlyLast()
//				.end();

        String LMID = "lmid=${exchange.properties['lmid'] ";
        var function = AllFunction.GET_CARD_INFO;
        from( "activemq-jds:queue:" + commonUtils.getQueueRequest(function) + "?asyncConsumer=true")
                .doTry()
                    .process(requestProcess)
                    .bean(apiService, "getCardLinkageInquiry")
                    .log(LMID + "frtRay body=S{body} ")
                .doCatch(AdapterException.class).process(adapterExceptionProcess)
                .doCatch(Exception.class).process(exceptionProcess)
                .doFinally()
                    .log(LMID + "finally response to " + function.getFunctionName() + " via queue " + commonUtils.getQueueResponse(function) + " body=${body}")
                    .to("activemq-jds:queue:" + commonUtils.getQueueResponse(function))
                .end();

    }

}
