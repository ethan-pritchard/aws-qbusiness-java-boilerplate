package com.ethanpritchard.example.handlers;

import com.ethanpritchard.example.UnitTest;
import com.ethanpritchard.example.dagger.modules.EnvironmentModule;
import com.ethanpritchard.example.dagger.modules.QBusinessModule;
import com.ethanpritchard.example.models.requests.StopQBusinessExecutionRequest;
import com.ethanpritchard.example.models.responses.StopQBusinessExecutionResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.services.qbusiness.QBusinessClient;
import software.amazon.awssdk.services.qbusiness.model.StopDataSourceSyncJobRequest;
import software.amazon.awssdk.services.qbusiness.model.StopDataSourceSyncJobResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StopQBusinessExecutionHandlerTest extends UnitTest {
    private final StopQBusinessExecutionHandler handler = new StopQBusinessExecutionHandler();

    @Mock private QBusinessClient qBusinessClient;

    @Test
    public final void test_happyPath() {
        try (MockedStatic<EnvironmentModule> environmentModule = mockStatic(EnvironmentModule.class)) {
            environmentModule.when(EnvironmentModule::getAWS_DEFAULT_REGION).thenReturn(AWS_DEFAULT_REGION);

            try (MockedStatic<QBusinessModule> qBusinessModule = mockStatic(QBusinessModule.class)) {
                qBusinessModule.when(QBusinessModule::getQBusinessClient).thenReturn(qBusinessClient);

                when(qBusinessClient.stopDataSourceSyncJob(any(StopDataSourceSyncJobRequest.class)))
                        .thenReturn(
                                StopDataSourceSyncJobResponse.builder().build()
                        );
                StopQBusinessExecutionRequest request = createRequest();
                StopQBusinessExecutionResponse response = handler.handleRequest(request, null);
            }
        }
    }

    private StopQBusinessExecutionRequest createRequest() {
        return StopQBusinessExecutionRequest.builder()
                .applicationId(APPLICATION_ID)
                .dataSourceId(DATA_SOURCE_ID)
                .indexId(INDEX_ID)
                .build();
    }
}
