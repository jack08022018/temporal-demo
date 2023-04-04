package com.demo.service;

import com.demo.constant.AllFunction;
import com.demo.constant.ResponseStatus;
import com.demo.dto.CardInfoData;
import com.demo.dto.CardInfoDto;
import com.demo.dto.base.RequestDto;
import com.demo.dto.base.ResponseDto;
import com.demo.utils.WorkflowUtils;
import com.demo.workflow.CardInfoWorkflow;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiServiceImpl implements ApiService {
    final ObjectMapper customObjectMapper;
    final WorkflowUtils workflowUtils;

    @Override
    public ResponseDto getCardInfo(RequestDto<CardInfoDto> dto) throws Exception {
        log.info("lmid={} start service getCardInfo", dto.lmid);
        var workflowOptions = workflowUtils.getWorkflowOptions(AllFunction.GET_CARD_INFO);
        var workflow = workflowUtils.buildWorkflow(CardInfoWorkflow.class, workflowOptions);
        var response = workflow.getCardInfo(dto);
        var data = customObjectMapper.readValue(response.getJsonData(), CardInfoData.class);
        return ResponseDto.buildResponse(dto, data, ResponseStatus.SUCCESS);
    }

}
