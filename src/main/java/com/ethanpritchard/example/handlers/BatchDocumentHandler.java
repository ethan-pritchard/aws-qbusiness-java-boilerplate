package com.ethanpritchard.example.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.ethanpritchard.example.models.requests.BatchDocumentRequest;
import com.ethanpritchard.example.models.responses.BatchDocumentResponse;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.qbusiness.model.BatchPutDocumentRequest;
import software.amazon.awssdk.services.qbusiness.model.BatchPutDocumentResponse;

import java.util.Objects;

import static com.ethanpritchard.example.dagger.modules.QBusinessModule.qBusinessClient;

@Slf4j
public class BatchDocumentHandler implements RequestHandler<BatchDocumentRequest, BatchDocumentResponse> {
    @Override
    public BatchDocumentResponse handleRequest(BatchDocumentRequest request, Context context) {
        if (Objects.isNull(request.getApplicationId())) throw new IllegalArgumentException("applicationId null");
        if (Objects.isNull(request.getIndexId())) throw new IllegalArgumentException("indexId null");
        if (Objects.isNull(request.getNextToken())) throw new IllegalArgumentException("nextToken null");
        if (Objects.isNull(request.getQBusinessExecutionId())) throw new IllegalArgumentException("qBusinessExecutionId null");
        if (Objects.isNull(request.getStartDate())) throw new IllegalArgumentException("startDate null");
        if (Objects.isNull(request.getEndDate())) throw new IllegalArgumentException("endDate null");

        // todo: Fetch documents w/ nextToken (optional) and startTime (if not -1)
        String newNextToken = request.getNextToken().equalsIgnoreCase("123") ? "" : "123";
        // todo: Convert documents -> Q Business Document

        // todo: Q Business Documents + Batch size 10 and iterate this API
        if (false) {
            BatchPutDocumentRequest batchPutDocumentRequest = BatchPutDocumentRequest.builder()
                    .applicationId(request.getApplicationId())
                    .dataSourceSyncId(request.getQBusinessExecutionId())
                    //.documents(null)
                    .indexId(request.getIndexId())
                    .build();
            BatchPutDocumentResponse batchPutDocumentResponse = qBusinessClient.batchPutDocument(batchPutDocumentRequest);
            if (batchPutDocumentResponse.hasFailedDocuments()) {
                log.warn("qbusiness:BatchPutDocument failed document count: {}", batchPutDocumentResponse.failedDocuments().size());
            }
        }

        return BatchDocumentResponse.builder()
                .nextToken(newNextToken) // todo: Get new nextToken from "Fetch Documents"
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .build();
    }
}
