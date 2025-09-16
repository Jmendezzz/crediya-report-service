package co.com.crediya.sqs.sender.reportmetric;

import co.com.crediya.model.reportmetric.events.DailyReportMetricGenerated;
import co.com.crediya.model.reportmetric.gateways.ReportMetricEventPublisher;
import co.com.crediya.sqs.sender.SQSSender;
import co.com.crediya.sqs.sender.config.SQSSenderProperties;
import co.com.crediya.sqs.sender.reportmetric.constant.ReportMetricSQSQueueName;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReportMetricEventPublisherSQSAdapter implements ReportMetricEventPublisher {
    private final SQSSenderProperties properties;
    private final SQSSender sqsSender;
    @Override
    public Mono<Void> publish(DailyReportMetricGenerated dailyReportMetricGenerated) {
        return sqsSender
                .publish(
                        dailyReportMetricGenerated,
                        properties.queues().get(ReportMetricSQSQueueName.DAILY_REPORT_GENERATED.getKey())
                ).then();
    }
}
