package com.ethanpritchard.example.dagger.modules;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.qbusiness.QBusinessClient;

public class QBusinessModule {
    private static final QBusinessClient qBusinessClient = QBusinessClient.builder()
            .region(Region.of(EnvironmentModule.getAWS_DEFAULT_REGION()))
            .build();
    public static QBusinessClient getQBusinessClient() {
        return qBusinessClient;
    }
}
