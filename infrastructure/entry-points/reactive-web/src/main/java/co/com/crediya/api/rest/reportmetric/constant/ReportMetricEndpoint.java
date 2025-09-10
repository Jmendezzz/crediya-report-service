package co.com.crediya.api.rest.reportmetric.constant;

import co.com.crediya.api.constant.ApiResource;
import co.com.crediya.api.constant.ApiVersion;
import lombok.Getter;

@Getter
public enum ReportMetricEndpoint {
    GET_LOAN_APPROVALS(
             "/loan-approvals",
            "geLoanApprovalsMetrics",
            "Obtener las metricas de las solicitudes aprobadas",
            "Obtiene las metricas de las solicitudes aprobadas"
    );

    private final String path;
    private final String operationId;
    private final String summary;
    private final String description;

    ReportMetricEndpoint(String path, String operationId, String summary, String description) {
        this.path = ApiVersion.V1.getVersion() + ApiResource.REPORT_METRICS.getResource() + path;
        this.operationId = operationId;
        this.summary = summary;
        this.description = description;
    }
}

