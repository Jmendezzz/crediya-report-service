package co.com.crediya.model.reportmetric.events;

import java.time.Instant;

public record LoanApplicationApproved(
        Long loanApplicationId,
        Double loanApplicationAmount,
        String customerIdentityNumber,
        Instant approvedAt
){
}
