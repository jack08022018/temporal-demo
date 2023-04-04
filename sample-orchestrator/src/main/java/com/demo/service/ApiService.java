package com.demo.service;

import com.demo.dto.CardInfoDto;
import com.demo.dto.base.RequestDto;
import com.demo.dto.base.ResponseDto;

public interface ApiService {
    ResponseDto getCardInfo(RequestDto<CardInfoDto> dto) throws Exception;
}
