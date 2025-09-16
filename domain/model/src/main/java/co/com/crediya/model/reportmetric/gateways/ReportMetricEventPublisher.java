package co.com.crediya.model.reportmetric.gateways;

import co.com.crediya.model.reportmetric.events.DailyReportMetricGenerated;
import reactor.core.publisher.Mono;

public interface ReportMetricEventPublisher {
    Mono<Void> publish(DailyReportMetricGenerated dailyReportMetricGenerated);
}
