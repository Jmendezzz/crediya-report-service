package co.com.crediya.model.reportmetric;

import lombok.*;

import java.time.Instant;

@RequiredArgsConstructor
@Getter
public class LoanApprovalsMetric {
    private final long approvedCount;
    private final Double totalApproved;
    private final Instant updatedAt;
}
