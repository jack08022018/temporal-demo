package com.camel.routes;

import lombok.experimental.Accessors;

@Accessors(chain = true, fluent = true)
public class ConsumeFilterDto {
    public String adapterName;
    public String lmid;
}
