package co.com.crediya.sqs.listener.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProviderChain;
import software.amazon.awssdk.auth.credentials.ContainerCredentialsProvider;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.auth.credentials.InstanceProfileCredentialsProvider;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.auth.credentials.SystemPropertyCredentialsProvider;
import software.amazon.awssdk.auth.credentials.WebIdentityTokenFileCredentialsProvider;
import software.amazon.awssdk.metrics.MetricPublisher;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

import java.net.URI;

@Configuration
public class SQSListenerConfig {

    @Bean(name = "sqsListenerClient")
    public SqsAsyncClient sqsListenerClient(SQSProperties properties, MetricPublisher publisher) {
        return SqsAsyncClient.builder()
                .endpointOverride(resolveEndpoint(properties))
                .region(Region.of(properties.region()))
                .overrideConfiguration(o -> o.addMetricPublisher(publisher))
                .credentialsProvider(getProviderChain())
                .build();
    }

    private AwsCredentialsProviderChain getProviderChain() {
        return AwsCredentialsProviderChain.builder()
                .addCredentialsProvider(EnvironmentVariableCredentialsProvider.create())
                .addCredentialsProvider(SystemPropertyCredentialsProvider.create())
                .addCredentialsProvider(WebIdentityTokenFileCredentialsProvider.create())
                .addCredentialsProvider(ProfileCredentialsProvider.create())
                .addCredentialsProvider(ContainerCredentialsProvider.builder().build())
                .addCredentialsProvider(InstanceProfileCredentialsProvider.create())
                .build();
    }

    protected URI resolveEndpoint(SQSProperties properties) {
        if (properties.endpoint() != null) {
            return URI.create(properties.endpoint());
        }
        return null;
    }
}
