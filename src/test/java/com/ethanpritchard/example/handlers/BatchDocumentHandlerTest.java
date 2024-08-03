package com.ethanpritchard.example.handlers;

import com.ethanpritchard.example.UnitTest;
import com.ethanpritchard.example.models.requests.BatchDocumentRequest;
import com.ethanpritchard.example.models.responses.BatchDocumentResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class BatchDocumentHandlerTest extends UnitTest {
    private final BatchDocumentHandler handler = new BatchDocumentHandler();

    @Test
    public final void test_happyPath() {
        BatchDocumentRequest request = createRequest();
        BatchDocumentResponse response = handler.handleRequest(request, null);
        assertEquals("123", response.getNextToken());
        assertEquals(request.getStartDate(), response.getStartDate());
        assertEquals(request.getEndDate(), response.getEndDate());
    }

    @Test
    public final void test_errorPath_badRequest_endDate_lt_startDate() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            BatchDocumentRequest request = createRequest();
            request.setStartDate(2d);
            request.setEndDate(1d);
            handler.handleRequest(request, null);
        });

        assertEquals(exception.getMessage(), "endDate < startDate");
    }

    private BatchDocumentRequest createRequest() {
        return BatchDocumentRequest.builder()
                .applicationId(APPLICATION_ID)
                .indexId(INDEX_ID)
                .nextToken(UUID.randomUUID().toString())
                .qBusinessExecutionId(Q_BUSINESS_EXECUTION_ID)
                .startDate((double) RANDOM.nextInt(100))
                .endDate((double) RANDOM.nextInt(100) + 100)
                .build();
    }
}
