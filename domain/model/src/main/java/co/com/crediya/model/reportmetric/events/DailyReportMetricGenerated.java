package co.com.crediya.model.reportmetric.events;

import java.time.Instant;

public record DailyReportMetricGenerated(
        long loanApplicationApprovedCount,
        Double loanApplicationTotalApproved,
        Instant reportGeneratedAt
) {
}
