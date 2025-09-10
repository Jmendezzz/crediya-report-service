package co.com.crediya.api.rest.reportmetric.dto;

import java.time.Instant;

public record LoanApprovalsMetricResponseDto(
        Long approvedCount,
        Double totalApproved,
        Instant updatedAt
) {
}
