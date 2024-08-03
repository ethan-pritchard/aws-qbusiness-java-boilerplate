package com.ethanpritchard.example.dagger.modules;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.qbusiness.QBusinessClient;

public class QBusinessModule {
    public static final QBusinessClient qBusinessClient = QBusinessClient.builder()
            .region(Region.of(EnvironmentModule.AWS_DEFAULT_REGION))
            .build();
}
