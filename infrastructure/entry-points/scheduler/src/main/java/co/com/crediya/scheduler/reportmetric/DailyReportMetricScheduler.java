package co.com.crediya.scheduler.reportmetric;

import co.com.crediya.model.reportmetric.LoanApprovalsMetric;
import co.com.crediya.model.reportmetric.events.DailyReportMetricGenerated;
import co.com.crediya.model.reportmetric.gateways.ReportMetricEventPublisher;
import co.com.crediya.scheduler.reportmetric.constant.DailyReportMetricSchedulerLog;
import co.com.crediya.usecase.reportmetric.ReportMetricUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;

import static co.com.crediya.scheduler.constant.CronExpression.EVERY_DAY_AT_3AM;

@Slf4j
@Component
@RequiredArgsConstructor
public class DailyReportMetricScheduler {
    private final ReportMetricUseCase reportMetricUseCase;
    private final ReportMetricEventPublisher reportMetricEventPublisher;

    @Scheduled(cron = EVERY_DAY_AT_3AM)
    public void generateReport() {
        log.info(DailyReportMetricSchedulerLog.DAILY_REPORT_STARTED.getMessage(), Instant.now());

        reportMetricUseCase.getLoanApplicationMetricsReport()
                .doOnNext(metrics ->
                        log.info(DailyReportMetricSchedulerLog.DAILY_REPORT_METRICS_FETCHED.getMessage(),
                                metrics.getApprovedCount(),
                                metrics.getTotalApproved()))
                .map(this::buildDailyReportMetricGenerated)
                .doOnNext(event ->
                        log.info(DailyReportMetricSchedulerLog.DAILY_REPORT_EVENT_BUILT.getMessage(), event.reportGeneratedAt()))
                .flatMap(reportMetricEventPublisher::publish)
                .doOnSuccess(v ->
                        log.info(DailyReportMetricSchedulerLog.DAILY_REPORT_EVENT_PUBLISHED.getMessage(), Instant.now()))
                .doOnError(e ->
                        log.error(DailyReportMetricSchedulerLog.DAILY_REPORT_ERROR.getMessage(), e.getMessage(), e))
                .subscribe();
    }


    private DailyReportMetricGenerated buildDailyReportMetricGenerated(LoanApprovalsMetric loanApprovalsMetric){
        return new DailyReportMetricGenerated(
                loanApprovalsMetric.getApprovedCount(),
                loanApprovalsMetric.getTotalApproved(),
                Instant.now()
        );
    }
}
