package com.demo.dto.base;

import com.demo.constant.ResponseStatus;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true, chain = true)
public class ResponseDto<T> {
    public String requestDateTime;
    public ResultDto result;
    public T data;

    public static <T> ResponseDto<T> buildResponse(RequestDto req, T body, ResponseStatus status) {
        return new ResponseDto<T>()
                .requestDateTime(req.requestDateTime)
                .data(body)
                .result(ResultDto.builder()
                        .responseCode(status.getCode())
                        .description(status.getDetail())
                        .build());
    }
}
