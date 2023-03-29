package com.demo.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServiceRequest {
    private String lmid;
    private String workflowId;
    private String activityId;
    private String functionName;
    private String jsonData;
}
