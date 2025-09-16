package co.com.crediya.sqs.sender.reportmetric.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReportMetricSQSQueueName {
    DAILY_REPORT_GENERATED("dailyReportMetricGenerated");
    private final String key;
}
