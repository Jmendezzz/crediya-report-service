package co.com.crediya.sqs.listener.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReportMetricProcessorLog {
    RECEIVED_MESSAGE("Received message from SQS: {}"),
    PARSE_SUCCESS("Successfully parsed LoanApplicationApproved event. loanApplicationId={}, amount={}"),
    PARSE_ERROR("Error parsing message body={} cause={}"),
    PROCESS_START("Starting metric update for loanApplicationId={}"),
    PROCESS_SUCCESS("Successfully updated metrics for loanApplicationId={}"),
    PROCESS_ERROR("Error updating metrics for loanApplicationId={}, cause={}");

    private final String message;
}
