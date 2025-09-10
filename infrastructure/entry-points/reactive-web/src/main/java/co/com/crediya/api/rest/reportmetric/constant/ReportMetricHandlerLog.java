package co.com.crediya.api.rest.reportmetric.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReportMetricHandlerLog {
    GET_LOAN_APPLICATION_APPROVALS_START("Starting getLoanApprovalsMetrics"),
    GET_LOAN_APPLICATION_APPROVALS_SUCCESS("Success retrieving loanApprovalsMetrics: {}"),
    GET_LOAN_APPLICATION_APPROVALS_ERROR("Error  retrieving loanApprovalsMetrics: {}");

    private final String message;
}
