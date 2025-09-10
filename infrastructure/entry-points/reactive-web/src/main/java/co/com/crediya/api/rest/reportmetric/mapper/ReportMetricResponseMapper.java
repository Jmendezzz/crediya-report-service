package co.com.crediya.api.rest.reportmetric.mapper;

import co.com.crediya.api.rest.reportmetric.dto.LoanApprovalsMetricResponseDto;
import co.com.crediya.model.reportmetric.LoanApprovalsMetric;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReportMetricResponseMapper {
    LoanApprovalsMetricResponseDto toDto(LoanApprovalsMetric loanApprovalsMetric);
}
