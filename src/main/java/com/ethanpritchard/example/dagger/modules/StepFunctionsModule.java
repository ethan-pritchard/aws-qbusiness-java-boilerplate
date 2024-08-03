package com.ethanpritchard.example.dagger.modules;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sfn.SfnClient;

import static com.ethanpritchard.example.dagger.modules.EnvironmentModule.AWS_DEFAULT_REGION;

public class StepFunctionsModule {
    public static final String BATCH_PROCESSING_STATE_MACHINE_ARN = System.getenv("BATCH_PROCESSING_STATE_MACHINE_ARN");
    public static final SfnClient sfnClient = SfnClient.builder()
            .region(Region.of(AWS_DEFAULT_REGION))
            .build();
}
