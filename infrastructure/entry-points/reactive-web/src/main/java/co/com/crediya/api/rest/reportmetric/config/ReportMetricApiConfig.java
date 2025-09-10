package co.com.crediya.api.rest.reportmetric.config;


import co.com.crediya.api.exception.ErrorResponse;
import co.com.crediya.api.rest.reportmetric.constant.ReportMetricEndpoint;
import co.com.crediya.api.rest.reportmetric.dto.LoanApprovalsMetricResponseDto;
import org.springdoc.core.fn.builders.operation.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.function.Consumer;

import static org.springdoc.core.fn.builders.apiresponse.Builder.responseBuilder;
import static org.springdoc.core.fn.builders.content.Builder.contentBuilder;
import static org.springdoc.core.fn.builders.schema.Builder.schemaBuilder;

public class ReportMetricApiConfig {

    public static Consumer<Builder> getLoanApprovalMetricsDocsConsumer() {
        return builder -> builder
                .summary(ReportMetricEndpoint.GET_LOAN_APPROVALS.getSummary())
                .description(ReportMetricEndpoint.GET_LOAN_APPROVALS.getDescription())
                .operationId(ReportMetricEndpoint.GET_LOAN_APPROVALS.getOperationId())
                .response(responseBuilder()
                        .responseCode(HttpStatus.OK.name())
                        .content(contentBuilder()
                                .mediaType(MediaType.APPLICATION_JSON_VALUE)
                                .schema(schemaBuilder()
                                        .implementation(LoanApprovalsMetricResponseDto.class)
                                )
                        )
                )
                .response(responseBuilder()
                        .responseCode(HttpStatus.BAD_REQUEST.name())
                        .content(contentBuilder()
                                .mediaType(MediaType.APPLICATION_JSON_VALUE)
                                .schema(schemaBuilder()
                                        .implementation(ErrorResponse.class)
                                )
                        )
                );
    }


}
