package com.ethanpritchard.example.handlers;

import com.ethanpritchard.example.UnitTest;
import com.ethanpritchard.example.dagger.modules.EnvironmentModule;
import com.ethanpritchard.example.dagger.modules.StepFunctionsModule;
import com.ethanpritchard.example.models.requests.TriggerBatchProcessingStateMachineRequest;
import com.ethanpritchard.example.models.responses.TriggerBatchProcessingStateMachineResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.services.sfn.SfnClient;
import software.amazon.awssdk.services.sfn.model.StartExecutionRequest;
import software.amazon.awssdk.services.sfn.model.StartExecutionResponse;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TriggerBatchProcessingStateMachineHandlerTest extends UnitTest {
    private final TriggerBatchProcessingStateMachineHandler handler = new TriggerBatchProcessingStateMachineHandler();

    @Mock private SfnClient sfnClient;

    @Test
    public final void test_happyPath() {
        try (MockedStatic<EnvironmentModule> environmentModule = mockStatic(EnvironmentModule.class)) {
            environmentModule.when(EnvironmentModule::getAWS_DEFAULT_REGION).thenReturn(AWS_DEFAULT_REGION);

            try (MockedStatic<StepFunctionsModule> stepFunctionsModule = mockStatic(StepFunctionsModule.class)) {
                stepFunctionsModule.when(StepFunctionsModule::getSfnClient).thenReturn(sfnClient);

                when(sfnClient.startExecution(any(StartExecutionRequest.class)))
                        .thenReturn(
                                StartExecutionResponse.builder()
                                        .executionArn("happyPath")
                                        .build()
                        );
                TriggerBatchProcessingStateMachineRequest request = createRequest();
                TriggerBatchProcessingStateMachineResponse response = handler.handleRequest(request, null);
                assertEquals("happyPath", response.getExecutionArn());
            }
        }
    }

    private TriggerBatchProcessingStateMachineRequest createRequest() {
        return TriggerBatchProcessingStateMachineRequest.builder()
                .applicationId(APPLICATION_ID)
                .dataSourceId(DATA_SOURCE_ID)
                .indexId(INDEX_ID)
                .maxPages(RANDOM.nextInt(100))
                .nextToken(UUID.randomUUID().toString())
                .startDate((double) RANDOM.nextInt(100))
                .endDate((double) RANDOM.nextInt(100) + 100)
                .build();
    }
}
