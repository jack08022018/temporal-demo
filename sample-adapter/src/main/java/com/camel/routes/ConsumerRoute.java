package com.camel.routes;

import com.camel.process.ConsumerExceptionHandler;
import com.camel.process.ConsumerSuccessHandler;
import com.camel.service.ActorService;
import com.camel.service.ApiService;
import com.camel.service.CityService;
import com.camel.service.ServiceBean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConsumerRoute extends RouteBuilder {
	final ServiceBean serviceBean;
	final ApiService apiService;
	final ConsumerExceptionHandler consumerExceptionHandler;
	final ConsumerSuccessHandler consumerSuccessHandler;
	final CityService cityService;
	final ActorService actorService;
	final IbmComsumeFilter ibmComsumeFilter;

	@Value("${queues.queueDev1}")
	private String queueDev1;

	@Value("${queues.queueDev2}")
	private String queueDev2;

	@Value("${queue.selector.key}")
	private String selectorKey;

	@Value("${queue.selector.camel}")
	private String selectorCamel;

	@Override
	@Transactional
	public void configure() throws Exception {
//		df.setModuleClassNames("com.fasterxml.jackson.datatype.jsr310.JavaTimeModule");
//		JacksonDataFormat jsonDf = new JacksonDataFormat(ActorEntity.class);
//		jsonDf.setPrettyPrint(true);

		onException(Exception.class)
//				.maximumRedeliveries(2)
				.process(consumerExceptionHandler)
				.handled(true)
				.markRollbackOnlyLast()
				.end();

		String postfix = " 9";
//		from("ibmmq:queue:DEV.QUEUE.1?selector=ADAPTER='CAMEL'")
//		@JmsListener(destination = "${queues.queueDev1}", selector = "JMSCorrelationID = 'JMSCorrelationID_CONSUMER'")
//		from("ibmmq:queue:" + queueDev1)
//				.transacted()
//				.filter(header(selectorKey).isEqualTo(selectorCamel))
//				.log("Receive DEV.QUEUE.1: ${body}")
//				.process(exchange -> cityService.saveCity("Ziguinchor" + postfix))
//				.process(exchange -> {
//					System.out.println("aaa: " + exchange.getIn().getBody().toString());
//					int a = 1/0;
//					exchange.getIn().setBody("success");
//				})
//				.process(consumerSuccessHandler)
//				.log("CAMEL: ${body}");

		from("ibmmq:queue:" + queueDev1 + "?selector=JMSCorrelationID LIKE 'CAMEL%'")
//		from("ibmmq:queue:" + queueDev1 + "?selector=ADAPTER='CAMEL'&transacted=true")
//				.transacted()
//				.filter(header("ADAPTER").isEqualTo("CAMEL"))
//				.filter(header("JMSCorrelationID").contains("CAMEL"))
//				.filter().method(ibmComsumeFilter, "isCamelAdapter")
//				.process(consumerSuccessHandler)
				.to("ibmmq:queue:" + queueDev2)
				.log("Receive DEV.QUEUE.1: ${body}");

	}

}
