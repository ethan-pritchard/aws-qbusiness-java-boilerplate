package com.ethanpritchard.example.models.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
@Setter
public class StopQBusinessExecutionRequest {
    private String applicationId;
    private String dataSourceId;
    private String indexId;
}
