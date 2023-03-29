package com.camel.routes;

import com.camel.dto.CardInfoDto;
import com.camel.process.RestExceptionHandler;
import com.camel.repository.CountryRepository;
import com.camel.service.ActorService;
import com.camel.service.ApiService;
import com.camel.service.CityService;
import com.camel.service.ServiceBean;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.util.json.JsonObject;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DirectRoute extends RouteBuilder {
	final ServiceBean serviceBean;
	final ApiService apiService;
	final RestExceptionHandler restExceptionHandler;
	final CityService cityService;
	final ActorService actorService;
	final CountryRepository countryRepository;
	final JmsTemplate jmsTemplate;

	final ObjectMapper customObjectMapper;

	@Override
	public void configure() throws Exception {
//		df.setModuleClassNames("com.fasterxml.jackson.datatype.jsr310.JavaTimeModule");
//		JacksonDataFormat jsonDf = new JacksonDataFormat(ActorEntity.class);
//		jsonDf.setPrettyPrint(true);

		onException(Exception.class)
//				.handled(true)
//				.maximumRedeliveries(2)
				.process(restExceptionHandler)
				.handled(true)
				.markRollbackOnlyLast()
				.end();

		from("direct:hello")
				.process(exchange -> {
//					var country = countryRepository.findById(1).get();
//					exchange.getIn().setBody(country);
					String xmlString = """
							<EBK_INT_CMS_0001_REQ>
								<HEADER>
									<MTI>41210</MTI>
								</HEADER>
								<BODY>
									<CIFID></CIFID>
									<STANID></STANID>
									<LOCALDATETINE>20236206212625</LOCALDATETINE>
									<RESCODE>12</RESCODE>
									<RESFIELD></RESFIELD>
									<CARDSINFO>
										<CARDCODE>123456</CARDCODE>
									</CARDSINFO>
									<CARDSINFO>
										<CARDCODE>123422</CARDCODE>
									</CARDSINFO>
								</BODY>
							</EBK_INT_CMS_0001_REQ>""";
//									<CARDSINFO></CARDSINFO>
					var objectNode = new XmlMapper().readValue(xmlString, ObjectNode.class);
					var cardInfos = objectNode.get("BODY").get("CARDSINFO");
					List<CardInfoDto> data = cardInfos.isEmpty() ? new ArrayList<>() : customObjectMapper.readValue(cardInfos.traverse(), new TypeReference<>(){});
					exchange.getIn().setBody(data);
				});
//				.transform(simple("Random number ${random(0,100)}"))
//				.transform().constant("Hello World direct")
//				.to("ibmmq:queue:DEV.QUEUE.1");

		from("direct:getActor")
//				.bean(apiService, "getActor")
				.process(exchange -> {
					String body = "ping!";
					jmsTemplate.convertAndSend("PRINT_NAME", body);
//					jmsTemplate.convertAndSend("PRINT_NAME", body, messagePostProcessor -> {
//						messagePostProcessor.setStringProperty(selectorKey, selectorConsumer);
//						return messagePostProcessor;
//					});
				});
//				.to("ibmmq:queue:DEV.QUEUE.2?selector=ADAPTER='CONSUMER'");
//				.unmarshal().json(Object.class);
//				.marshal(jsonDf);

		String postfix = " 8";
		from("direct:handleTransactional")
				.transacted()
//				.bean(apiService, "handleTransactional")
				.process(exchange -> cityService.saveCity("Ziguinchor" + postfix))
//				.process(exchange -> actorService.saveActor("THORA" + postfix))
				.process(exchange -> {
					System.out.println("aaa: " + exchange.getIn().getBody().toString());
					int a = 1/0;
					exchange.getIn().setBody("success");
				});

		from("direct:importExcel")
//				.unmarshal().mimeMultipart()
//				.setHeader(Exchange.CONTENT_TYPE, constant("multipart/form-data"))
				.transacted()
				.bean(apiService, "importExcel")
				.process(exchange -> {
					System.out.println("aaa: " + exchange.getIn().getBody().toString());
//					int a = 1/0;
					exchange.getIn().setBody("success");
				});

//		from("timer:timer1?period={{timer.period}}")
//				.process(myProcessor)
////			.to("direct:hello")
//			.log("${body}");

	}

}
