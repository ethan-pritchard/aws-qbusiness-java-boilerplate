package com.ethanpritchard.example.handlers;

import com.ethanpritchard.example.UnitTest;
import com.ethanpritchard.example.dagger.modules.EnvironmentModule;
import com.ethanpritchard.example.dagger.modules.QBusinessModule;
import com.ethanpritchard.example.models.requests.StartQBusinessExecutionRequest;
import com.ethanpritchard.example.models.responses.StartQBusinessExecutionResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.services.qbusiness.QBusinessClient;
import software.amazon.awssdk.services.qbusiness.model.StartDataSourceSyncJobRequest;
import software.amazon.awssdk.services.qbusiness.model.StartDataSourceSyncJobResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StartQBusinessExecutionHandlerTest extends UnitTest {
    private final StartQBusinessExecutionHandler handler = new StartQBusinessExecutionHandler();

    @Mock private QBusinessClient qBusinessClient;

    @Test
    public final void test_happyPath() {
        try (MockedStatic<EnvironmentModule> environmentModule = mockStatic(EnvironmentModule.class)) {
            environmentModule.when(EnvironmentModule::getAWS_DEFAULT_REGION).thenReturn(AWS_DEFAULT_REGION);

            try (MockedStatic<QBusinessModule> qBusinessModule = mockStatic(QBusinessModule.class)) {
                qBusinessModule.when(QBusinessModule::getQBusinessClient).thenReturn(qBusinessClient);

                when(qBusinessClient.startDataSourceSyncJob(any(StartDataSourceSyncJobRequest.class)))
                        .thenReturn(
                                StartDataSourceSyncJobResponse.builder()
                                        .executionId("happyPath")
                                        .build()
                        );
                StartQBusinessExecutionRequest request = createRequest();
                StartQBusinessExecutionResponse response = handler.handleRequest(request, null);
                assertEquals("happyPath", response.getQBusinessExecutionId());
            }
        }
    }

    private StartQBusinessExecutionRequest createRequest() {
        return StartQBusinessExecutionRequest.builder()
                .applicationId(APPLICATION_ID)
                .dataSourceId(DATA_SOURCE_ID)
                .indexId(INDEX_ID)
                .build();
    }
}
