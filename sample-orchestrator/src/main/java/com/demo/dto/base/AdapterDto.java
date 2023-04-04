package com.demo.dto.base;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdapterDto {
    private String lmid;
    private String workflowId;
    private String activityId;
    private String functionName;
    private String jsonData;
}
