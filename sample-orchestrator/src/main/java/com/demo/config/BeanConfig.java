package com.demo.config;

import com.demo.config.properties.TemporalProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;


@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties({TemporalProperties.class})
public class BeanConfig {

    final Environment env;

//    public static void main(String[] args) throws Exception {
//        String json = """
//                {"dateTime":"/Date(-62135596800000+0700)/"}""";
//        ObjectMapper objectMapper = getObjectMapper();
//        MyObject myObject = objectMapper.readValue(json, MyObject.class);
//        System.out.println(myObject.getDateTime());
//    }
//
//    public static class MyObject {
//        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "'/Date('SZZZZZ')/'")
//        private Date dateTime;
//
//        public Date getDateTime() {
//            return dateTime;
//        }
//
//        public void setDateTime(Date dateTime) {
//            this.dateTime = dateTime;
//        }
//    }

    @Bean(name = "customObjectMapper")
    public static ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper;
    }

    @Bean(name = "customRestTemplate")
    public RestTemplate getRestTemplate() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setReadTimeout(30000);
        requestFactory.setConnectTimeout(30000);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        restTemplate.setErrorHandler(new RestTemplateResponseErrorHandler());
        return restTemplate;
    }
}
