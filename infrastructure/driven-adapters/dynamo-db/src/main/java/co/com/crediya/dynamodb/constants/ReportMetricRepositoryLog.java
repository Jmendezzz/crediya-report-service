package co.com.crediya.dynamodb.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReportMetricRepositoryLog {

    INCREMENT_START("Incrementing metric {} with delta={}"),
    INCREMENT_SUCCESS("Metric {} incremented successfully"),
    INCREMENT_ERROR("Error incrementing metric {}: {}"),

    GET_METRICS_START("Fetching global loan approval metrics"),
    GET_METRICS_SUCCESS("Successfully fetched global loan approval metrics: count={}, total={}"),
    GET_METRICS_ERROR("Error fetching global loan approval metrics: {}");

    private final String message;
}