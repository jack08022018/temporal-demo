package com.demo.dto.base;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkflowResponse {
    private String responseCode;
    private String description;
    private String jsonData;
}
