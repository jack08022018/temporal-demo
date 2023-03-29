package com.camel.routes;

import com.camel.process.RestExceptionHandler;
import com.camel.dto.User;
import com.camel.service.ApiService;
import com.camel.service.ServiceBean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RestRoute extends RouteBuilder {
	final ServiceBean serviceBean;
	final ApiService apiService;
	final RestExceptionHandler restExceptionHandler;

	@Value("${rest.camel.context-path}")
	private String contextPath;

	@Value("${rest.camel.port}")
	private String port;

	@Override
	public void configure() throws Exception {
//		restConfiguration().component("servlet").bindingMode(RestBindingMode.auto);
		restConfiguration().component("netty-http")
				.apiProperty("api.title", "Camel REST API")
				.apiProperty("api.version", "1.0")
				.enableCORS(true)
				.apiProperty("cors", "true")
				.contextPath(contextPath)
				.port(port)
//				.dataFormatProperty("moduleClassNames", "com.fasterxml.jackson.datatype.jsr310.JavaTimeModule")
//				.dataFormatProperty("disableFeatures", "WRITE_DATES_AS_TIMESTAMPS")
				.bindingMode(RestBindingMode.json)
				.dataFormatProperty("prettyPrint", "true")
				.dataFormatProperty("enableFeatures","ACCEPT_CASE_INSENSITIVE_PROPERTIES");

		onException(Exception.class)
//				.maximumRedeliveries(2)
				.process(restExceptionHandler)
				.handled(true)
				.markRollbackOnlyLast()
				.end();
		rest("/api")
				.get("/hello").type(User.class)
				.to("direct:hello")

				.post("/getUser").type(User.class)//.outType(String.class)
				.to("bean:serviceBean?method=getUser")

				.post("/getActor")
				.to("direct:getActor")

				.post("/toUpper").type(User.class)//.outType(String.class)
				.to("bean:serviceBean?method=toUpper")

				.post("/handleTransactional").type(User.class)//.outType(String.class)
				.to("direct:handleTransactional")

				.post("/importExcel")
//				.consumes("multipart/form-data")
//				.param().name("file")
//					.type(RestParamType.header)
////					.defaultValue("false")
////					.description("Verbose order details")
//				.endParam()
				.to("direct:importExcel");

	}

}
