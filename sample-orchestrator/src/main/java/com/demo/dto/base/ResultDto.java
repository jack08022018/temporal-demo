package com.demo.dto.base;

import lombok.Builder;
import lombok.experimental.Accessors;

@Builder
@Accessors(fluent = true, chain = true)
public class ResultDto {
    public String responseCode;
    public String description;
}
