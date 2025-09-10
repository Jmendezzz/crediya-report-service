package co.com.crediya.api.rest.reportmetric;


import co.com.crediya.api.rest.reportmetric.constant.ReportMetricHandlerLog;
import co.com.crediya.api.rest.reportmetric.dto.LoanApprovalsMetricResponseDto;
import co.com.crediya.api.rest.reportmetric.mapper.ReportMetricResponseMapper;
import co.com.crediya.usecase.reportmetric.ReportMetricUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReportMetricHandler {
    private final ReportMetricUseCase reportMetricUseCase;
    private final ReportMetricResponseMapper reportMetricResponseMapper;

    public Mono<LoanApprovalsMetricResponseDto> getLoanApprovalsMetric(){
        return reportMetricUseCase
                .getLoanApplicationMetricsReport()
                .doOnSubscribe(s -> log.info(ReportMetricHandlerLog.GET_LOAN_APPLICATION_APPROVALS_START.getMessage()))
                .doOnSuccess(metrics -> log.info(ReportMetricHandlerLog.GET_LOAN_APPLICATION_APPROVALS_SUCCESS.getMessage(), metrics.toString() ))
                .doOnError(e -> log.error(ReportMetricHandlerLog.GET_LOAN_APPLICATION_APPROVALS_ERROR.getMessage(), e.getMessage()))
                .map(reportMetricResponseMapper::toDto);
    }
}
