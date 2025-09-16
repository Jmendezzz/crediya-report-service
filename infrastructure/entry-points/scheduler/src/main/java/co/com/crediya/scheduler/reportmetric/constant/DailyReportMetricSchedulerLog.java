package co.com.crediya.scheduler.reportmetric.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DailyReportMetricSchedulerLog {
    DAILY_REPORT_STARTED("Generating daily report at: {}"),
    DAILY_REPORT_METRICS_FETCHED("Fetched metrics: approvedCount={}, totalApproved={}"),
    DAILY_REPORT_EVENT_BUILT("Built DailyReportMetricGenerated event at: {}"),
    DAILY_REPORT_EVENT_PUBLISHED("Daily report event published successfully at: {}"),
    DAILY_REPORT_ERROR("Error while generating daily report: {}");

    private final String message;
}