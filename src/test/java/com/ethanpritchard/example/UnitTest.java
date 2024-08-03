package com.ethanpritchard.example;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Random;

@ExtendWith(MockitoExtension.class)
public abstract class UnitTest {
    public static final String APPLICATION_ID = "APPLICATION_ID";
    public static final String AWS_DEFAULT_REGION = "unit-test";
    public static final String BATCH_PROCESSING_STATE_MACHINE_ARN = "BATCH_PROCESSING_STATE_MACHINE_ARN";
    public static final String DATA_SOURCE_ID = "DATA_SOURCE_ID";
    public static final String INDEX_ID = "INDEX_ID";
    public static final String Q_BUSINESS_EXECUTION_ID = "Q_BUSINESS_EXECUTION_ID";

    public static final Random RANDOM = new Random();
}
