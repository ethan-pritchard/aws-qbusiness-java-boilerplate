package com.ethanpritchard.example.dagger.modules;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sfn.SfnClient;

public class StepFunctionsModule {
    private static final String BATCH_PROCESSING_STATE_MACHINE_ARN = System.getenv("BATCH_PROCESSING_STATE_MACHINE_ARN");
    public static String getBATCH_PROCESSING_STATE_MACHINE_ARN() {
        return BATCH_PROCESSING_STATE_MACHINE_ARN;
    }
    private static final SfnClient sfnClient = SfnClient.builder()
            .region(Region.of(EnvironmentModule.getAWS_DEFAULT_REGION()))
            .build();
    public static SfnClient getSfnClient() {
        return sfnClient;
    }
}
