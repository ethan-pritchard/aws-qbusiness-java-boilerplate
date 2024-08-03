package com.ethanpritchard.example.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.ethanpritchard.example.models.requests.StartQBusinessExecutionRequest;
import com.ethanpritchard.example.models.responses.StartQBusinessExecutionResponse;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.qbusiness.model.StartDataSourceSyncJobRequest;
import software.amazon.awssdk.services.qbusiness.model.StartDataSourceSyncJobResponse;

import java.util.Objects;

import static com.ethanpritchard.example.dagger.modules.QBusinessModule.getQBusinessClient;

@Slf4j
public class StartQBusinessExecutionHandler implements RequestHandler<StartQBusinessExecutionRequest, StartQBusinessExecutionResponse> {
    @Override
    public StartQBusinessExecutionResponse handleRequest(StartQBusinessExecutionRequest request, Context context) {
        if (Objects.isNull(request.getApplicationId())) throw new IllegalArgumentException("applicationId null");
        if (Objects.isNull(request.getDataSourceId())) throw new IllegalArgumentException("dataSourceId null");
        if (Objects.isNull(request.getIndexId())) throw new IllegalArgumentException("indexId null");

        StartDataSourceSyncJobRequest apiRequest = StartDataSourceSyncJobRequest.builder()
                .applicationId(request.getApplicationId())
                .dataSourceId(request.getDataSourceId())
                .indexId(request.getIndexId())
                .build();
        StartDataSourceSyncJobResponse apiResponse = getQBusinessClient().startDataSourceSyncJob(apiRequest);

        return StartQBusinessExecutionResponse.builder()
                .qBusinessExecutionId(apiResponse.executionId())
                .build();
    }
}
