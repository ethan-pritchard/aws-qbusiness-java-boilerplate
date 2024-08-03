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
public class BatchDocumentRequest {
    private String applicationId;
    private String indexId;
    private String nextToken;
    private String qBusinessExecutionId;
    private Double startDate;
    private Double endDate;
}
