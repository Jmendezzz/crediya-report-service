package co.com.crediya.usecase.reportmetric;

import co.com.crediya.model.reportmetric.LoanApprovalsMetric;
import co.com.crediya.model.reportmetric.events.LoanApplicationApproved;
import co.com.crediya.model.reportmetric.gateways.ReportMetricRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ReportMetricUseCaseTest {

    @Mock
    private ReportMetricRepository reportMetricRepository;

    @InjectMocks
    private ReportMetricUseCase reportMetricUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void onLoanApplicationApproved_ShouldIncrementMetrics() {
        Instant approvedAt = Instant.now();
        LoanApplicationApproved event = new LoanApplicationApproved(
                1L,
                1_200_000.0,
                "123456789",
                approvedAt
        );

        when(reportMetricRepository.incrementGlobalLoanApprovalsMetrics(1_200_000.0, approvedAt))
                .thenReturn(Mono.empty());

        StepVerifier.create(reportMetricUseCase.onLoanApplicationApproved(event))
                .verifyComplete();

        verify(reportMetricRepository, times(1))
                .incrementGlobalLoanApprovalsMetrics(1_200_000.0, approvedAt);
    }

    @Test
    void getLoanApplicationMetricsReport_ShouldReturnMetricsFromRepository() {
        LoanApprovalsMetric metric = new LoanApprovalsMetric(
                5L,
                20_000_000.0,
                Instant.now()
        );

        when(reportMetricRepository.getGlobalLoanApprovalsMetrics())
                .thenReturn(Mono.just(metric));

        StepVerifier.create(reportMetricUseCase.getLoanApplicationMetricsReport())
                .assertNext(result -> {
                    assertThat(result.getApprovedCount()).isEqualTo(5L);
                    assertThat(result.getTotalApproved()).isEqualTo(20_000_000.0);
                })
                .verifyComplete();

        verify(reportMetricRepository, times(1)).getGlobalLoanApprovalsMetrics();
    }
}
