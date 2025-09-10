package co.com.crediya.usecase.reportmetric;

import co.com.crediya.model.reportmetric.LoanApprovalsMetric;
import co.com.crediya.model.reportmetric.events.LoanApplicationApproved;
import co.com.crediya.model.reportmetric.gateways.ReportMetricRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ReportMetricUseCase {
    private final ReportMetricRepository reportMetricRepository;

    public Mono<Void> onLoanApplicationApproved(LoanApplicationApproved loanApplicationApproved) {
        return reportMetricRepository.incrementGlobalLoanApprovalsMetrics(loanApplicationApproved.loanApplicationAmount(), loanApplicationApproved.approvedAt());
    }

    public Mono<LoanApprovalsMetric> getLoanApplicationMetricsReport() {
        return reportMetricRepository.getGlobalLoanApprovalsMetrics();
    }
}
