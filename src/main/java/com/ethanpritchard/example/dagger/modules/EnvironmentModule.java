package com.ethanpritchard.example.dagger.modules;

public class EnvironmentModule {
    private static final String AWS_DEFAULT_REGION = System.getenv("AWS_DEFAULT_REGION");
    public static String getAWS_DEFAULT_REGION(){
        return AWS_DEFAULT_REGION;
    }
}
