package com.demo.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompleteActivityDto {
    private String lmid;
    private String workflowId;
    private String activityId;
    private String jsonData;
}
