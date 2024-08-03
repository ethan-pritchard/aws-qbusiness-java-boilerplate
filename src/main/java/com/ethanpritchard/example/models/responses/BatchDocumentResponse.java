package com.ethanpritchard.example.models.responses;

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
public class BatchDocumentResponse {
    private String nextToken;
    private Double startDate;
    private Double endDate;
}
