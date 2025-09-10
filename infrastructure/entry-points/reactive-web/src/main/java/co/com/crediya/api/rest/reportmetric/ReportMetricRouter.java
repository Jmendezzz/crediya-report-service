package co.com.crediya.api.rest.reportmetric;

import co.com.crediya.api.rest.reportmetric.config.ReportMetricApiConfig;
import co.com.crediya.api.rest.reportmetric.constant.ReportMetricEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springdoc.webflux.core.fn.SpringdocRouteBuilder;

@Configuration
public class ReportMetricRouter {

    @Bean
    public RouterFunction<ServerResponse> reportMetricRoutes(ReportMetricHandler reportMetricHandler){
        return SpringdocRouteBuilder.route()
                .GET(ReportMetricEndpoint.GET_LOAN_APPROVALS.getPath(),
                        request ->
                                reportMetricHandler.getLoanApprovalsMetric().flatMap(dto ->
                                ServerResponse
                                        .status(HttpStatus.OK)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(dto)
                        ),
                        ops -> ReportMetricApiConfig.getLoanApprovalMetricsDocsConsumer().accept(ops)
                )
                .build();
    }

}
