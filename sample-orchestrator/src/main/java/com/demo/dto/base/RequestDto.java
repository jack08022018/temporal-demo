package com.demo.dto.base;

import lombok.*;
import lombok.experimental.Accessors;

@Accessors(fluent = true, chain = true)
public class RequestDto<T> {
    public String lmid;
    public String requestDateTime;
    public T data;
}
