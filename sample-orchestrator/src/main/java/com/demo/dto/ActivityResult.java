package com.demo.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActivityResult {
    private String responseCode;
    private String description;
    private String jsonData;
}
