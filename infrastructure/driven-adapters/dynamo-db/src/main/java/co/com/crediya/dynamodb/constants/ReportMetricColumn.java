package co.com.crediya.dynamodb.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReportMetricColumn {
    METRIC_ID("metricId"),
    METRIC_VALUE("metricValue"),
    UPDATED_AT("updatedAt");

    private final String column;
}