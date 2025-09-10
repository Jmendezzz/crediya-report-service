package co.com.crediya.model.reportmetric.gateways;

import co.com.crediya.model.reportmetric.LoanApprovalsMetric;
import reactor.core.publisher.Mono;

import java.time.Instant;


public interface ReportMetricRepository {
    Mono<Void> incrementGlobalLoanApprovalsMetrics(Double amount, Instant at);
    Mono<LoanApprovalsMetric> getGlobalLoanApprovalsMetrics();
}
