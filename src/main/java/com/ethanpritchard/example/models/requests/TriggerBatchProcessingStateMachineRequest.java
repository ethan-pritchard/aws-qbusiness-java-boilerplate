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
public class TriggerBatchProcessingStateMachineRequest {
    private String applicationId;
    private String dataSourceId;
    private String indexId;
    private Integer maxPages;
    private String nextToken;
    private Double startDate;
    private Double endDate;
}
