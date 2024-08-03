package com.ethanpritchard.example.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.ethanpritchard.example.models.requests.TriggerBatchProcessingStateMachineRequest;
import com.ethanpritchard.example.models.responses.TriggerBatchProcessingStateMachineResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.sfn.model.StartExecutionRequest;
import software.amazon.awssdk.services.sfn.model.StartExecutionResponse;

import java.util.Objects;

import static com.ethanpritchard.example.dagger.modules.JacksonModule.OBJECT_MAPPER;
import static com.ethanpritchard.example.dagger.modules.StepFunctionsModule.BATCH_PROCESSING_STATE_MACHINE_ARN;
import static com.ethanpritchard.example.dagger.modules.StepFunctionsModule.sfnClient;

@Slf4j
public class TriggerBatchProcessingStateMachineHandler implements RequestHandler<TriggerBatchProcessingStateMachineRequest, TriggerBatchProcessingStateMachineResponse> {
    @Override
    public TriggerBatchProcessingStateMachineResponse handleRequest(TriggerBatchProcessingStateMachineRequest request, Context context) {
        if (Objects.isNull(request.getApplicationId())) throw new IllegalArgumentException("applicationId null");
        if (Objects.isNull(request.getDataSourceId())) throw new IllegalArgumentException("dataSourceId null");
        if (Objects.isNull(request.getIndexId())) throw new IllegalArgumentException("indexId null");
        if (Objects.isNull(request.getMaxPages())) throw new IllegalArgumentException("maxPages null");
        if (Objects.isNull(request.getNextToken())) throw new IllegalArgumentException("nextToken null");
        if (Objects.isNull(request.getStartDate())) throw new IllegalArgumentException("startDate null");
        if (Objects.isNull(request.getEndDate())) throw new IllegalArgumentException("endDate null");

        StartExecutionResponse apiResponse;
        try {
            StartExecutionRequest apiRequest = StartExecutionRequest.builder()
                    .input(OBJECT_MAPPER.writeValueAsString(request)) // json String
                    .stateMachineArn(BATCH_PROCESSING_STATE_MACHINE_ARN)
                    .build();
            apiResponse = sfnClient.startExecution(apiRequest);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return TriggerBatchProcessingStateMachineResponse.builder()
                .executionArn(apiResponse.executionArn())
                .build();
    }
}
