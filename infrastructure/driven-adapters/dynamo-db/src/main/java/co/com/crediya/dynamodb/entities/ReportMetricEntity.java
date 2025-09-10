package co.com.crediya.dynamodb.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.time.Instant;


@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamoDbBean
public class ReportMetricEntity {

    private String metricId;
    private Double metricValue;
    private Instant updatedAt;

    @DynamoDbPartitionKey
    public String getMetricId() { return metricId; }
}