package com.ethanpritchard.example.dagger.modules;

public class EnvironmentModule {
    public static final String AWS_DEFAULT_REGION = System.getenv("AWS_DEFAULT_REGION");
}
