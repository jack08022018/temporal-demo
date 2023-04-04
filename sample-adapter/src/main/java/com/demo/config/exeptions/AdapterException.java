package com.demo.config.exeptions;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class AdapterException extends Exception {
    private String responseCode;
    private String description;

    public AdapterException(String responseCode, String description) {
        this.responseCode = responseCode;
        this.description = description;
    }
}
