package com.ethanpritchard.example.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.ethanpritchard.example.models.requests.StopQBusinessExecutionRequest;
import com.ethanpritchard.example.models.responses.StopQBusinessExecutionResponse;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.qbusiness.model.StopDataSourceSyncJobRequest;

import java.util.Objects;

import static com.ethanpritchard.example.dagger.modules.QBusinessModule.getQBusinessClient;

@Slf4j
public class StopQBusinessExecutionHandler implements RequestHandler<StopQBusinessExecutionRequest, StopQBusinessExecutionResponse> {
    @Override
    public StopQBusinessExecutionResponse handleRequest(StopQBusinessExecutionRequest request, Context context) {
        if (Objects.isNull(request.getApplicationId())) throw new IllegalArgumentException("applicationId null");
        if (Objects.isNull(request.getDataSourceId())) throw new IllegalArgumentException("dataSourceId null");
        if (Objects.isNull(request.getIndexId())) throw new IllegalArgumentException("indexId null");

        StopDataSourceSyncJobRequest apiRequest = StopDataSourceSyncJobRequest.builder()
                .applicationId(request.getApplicationId())
                .dataSourceId(request.getDataSourceId())
                .indexId(request.getIndexId())
                .build();
        getQBusinessClient().stopDataSourceSyncJob(apiRequest);

        return StopQBusinessExecutionResponse.builder().build();
    }
}
