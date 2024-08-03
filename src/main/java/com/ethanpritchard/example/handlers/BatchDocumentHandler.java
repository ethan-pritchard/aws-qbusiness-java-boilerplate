package com.ethanpritchard.example.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.ethanpritchard.example.models.requests.BatchDocumentRequest;
import com.ethanpritchard.example.models.responses.BatchDocumentResponse;
import com.google.common.collect.Iterators;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.qbusiness.model.BatchPutDocumentRequest;
import software.amazon.awssdk.services.qbusiness.model.BatchPutDocumentResponse;
import software.amazon.awssdk.services.qbusiness.model.Document;

import java.util.Iterator;
import java.util.Objects;

import static com.ethanpritchard.example.dagger.modules.QBusinessModule.getQBusinessClient;

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
        if (request.getEndDate() < request.getStartDate()) throw new IllegalArgumentException("endDate < startDate");

        // todo: Fetch persistence w/ nextToken (optional) and startTime (if not -1)
        String newNextToken = request.getNextToken().equalsIgnoreCase("123") ? "" : "123";

        // todo: Convert persistence -> Q Business Document
        Iterator<Document> documents = null;

        if (!Objects.isNull(documents)) {
            Iterators
                    .partition(documents, 10)
                    .forEachRemaining(batch -> {
                        BatchPutDocumentRequest batchPutDocumentRequest = BatchPutDocumentRequest.builder()
                                .applicationId(request.getApplicationId())
                                .dataSourceSyncId(request.getQBusinessExecutionId())
                                .documents(batch)
                                .indexId(request.getIndexId())
                                .build();
                        BatchPutDocumentResponse batchPutDocumentResponse = getQBusinessClient().batchPutDocument(batchPutDocumentRequest);
                        if (batchPutDocumentResponse.hasFailedDocuments()) {
                            log.warn("qbusiness:BatchPutDocument failed document count: {} | Request ID: {}",
                                    batchPutDocumentResponse.failedDocuments().size(),
                                    batchPutDocumentResponse.responseMetadata().requestId());
                        }
                    });
        }

        return BatchDocumentResponse.builder()
                .nextToken(newNextToken)
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .build();
    }
}
