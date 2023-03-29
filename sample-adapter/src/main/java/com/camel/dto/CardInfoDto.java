package com.camel.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.experimental.Accessors;

@Accessors(chain = true, fluent = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CardInfoDto {

    @JsonAlias("CARDCODE")
    public String cardCode;

    @JsonAlias("CARDNUMBER")
    public String cardNumber;
}
