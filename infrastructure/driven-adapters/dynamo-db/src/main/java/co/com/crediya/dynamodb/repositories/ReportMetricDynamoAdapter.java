package co.com.crediya.dynamodb.repositories;

import co.com.crediya.dynamodb.constants.ReportMetricColumn;
import co.com.crediya.dynamodb.constants.ReportMetricExpressionValue;
import co.com.crediya.dynamodb.constants.ReportMetricRepositoryLog;
import co.com.crediya.dynamodb.entities.ReportMetricEntity;
import co.com.crediya.model.reportmetric.LoanApprovalsMetric;
import co.com.crediya.model.reportmetric.constants.Metric;
import co.com.crediya.model.reportmetric.gateways.ReportMetricRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemRequest;

import java.time.Instant;
import java.util.Map;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ReportMetricDynamoAdapter implements ReportMetricRepository {

    private final DynamoDbEnhancedAsyncClient enhancedClient;
    private final DynamoDbAsyncClient asyncClient;

    @Value("${aws.dynamodb.tables.approved-loans}")
    private String tableName;

    private DynamoDbAsyncTable<ReportMetricEntity> table() {
        return enhancedClient.table(tableName, TableSchema.fromBean(ReportMetricEntity.class));
    }

    @Override
    public Mono<Void> incrementGlobalLoanApprovalsMetrics(Double amount, Instant at) {
        Instant when = at == null ? Instant.now() : at;
        log.info(ReportMetricRepositoryLog.INCREMENT_START.getMessage(), Metric.APPROVED_COUNT.getName(), 1.0);
        log.info(ReportMetricRepositoryLog.INCREMENT_START.getMessage(), Metric.TOTAL_APPROVED_AMOUNT.getName(), amount);

        Mono<Void> incCount = updateNumber(Metric.APPROVED_COUNT.getName(), 1.0, when);
        Mono<Void> incAmount = updateNumber(Metric.TOTAL_APPROVED_AMOUNT.getName(), amount, when);

        return Mono.when(incCount, incAmount)
                .doOnSuccess(v -> log.info(ReportMetricRepositoryLog.INCREMENT_SUCCESS.getMessage(), "ALL"))
                .doOnError(e -> log.error(ReportMetricRepositoryLog.INCREMENT_ERROR.getMessage(), "ALL", e.getMessage(), e))
                .then();
    }

    private Mono<Void> updateNumber(String key, Double delta, Instant when) {
        String expr = "SET " + ReportMetricColumn.METRIC_VALUE.getColumn() +
                " = if_not_exists(" + ReportMetricColumn.METRIC_VALUE.getColumn() + ", " +
                ReportMetricExpressionValue.ZERO.getPlaceholder() + ") + " +
                ReportMetricExpressionValue.INC.getPlaceholder() +
                ", " + ReportMetricColumn.UPDATED_AT.getColumn() + " = " +
                ReportMetricExpressionValue.WHEN.getPlaceholder();

        Map<String, AttributeValue> values = Map.of(
                ReportMetricExpressionValue.INC.getPlaceholder(),
                AttributeValue.builder().n(delta.toString()).build(),
                ReportMetricExpressionValue.ZERO.getPlaceholder(),
                AttributeValue.builder().n("0").build(),
                ReportMetricExpressionValue.WHEN.getPlaceholder(),
                AttributeValue.builder().s(when.toString()).build()
        );

        Map<String, AttributeValue> keyMap = Map.of(
                ReportMetricColumn.METRIC_ID.getColumn(),
                AttributeValue.builder().s(key).build()
        );

        UpdateItemRequest request = UpdateItemRequest.builder()
                .tableName(tableName)
                .key(keyMap)
                .updateExpression(expr)
                .expressionAttributeValues(values)
                .build();

        return Mono.fromFuture(() -> asyncClient.updateItem(request))
                .doOnSuccess(r -> log.info(ReportMetricRepositoryLog.INCREMENT_SUCCESS.getMessage(), key))
                .doOnError(e -> log.error(ReportMetricRepositoryLog.INCREMENT_ERROR.getMessage(), key, e.getMessage(), e))
                .then();
    }


    @Override
    public Mono<LoanApprovalsMetric> getGlobalLoanApprovalsMetrics() {
        log.info(ReportMetricRepositoryLog.GET_METRICS_START.getMessage());

        Mono<ReportMetricEntity> approvedCount = Mono.fromFuture(() ->
                        table().getItem(r -> r.key(k -> k.partitionValue(Metric.APPROVED_COUNT.getName()))))
                .switchIfEmpty(Mono.just(new ReportMetricEntity(Metric.APPROVED_COUNT.getName(), 0.0, Instant.now())));

        Mono<ReportMetricEntity> totalAmount = Mono.fromFuture(() ->
                        table().getItem(r -> r.key(k -> k.partitionValue(Metric.TOTAL_APPROVED_AMOUNT.getName()))))
                .switchIfEmpty(Mono.just(new ReportMetricEntity(Metric.TOTAL_APPROVED_AMOUNT.getName(), 0.0, Instant.now())));

        return Mono.zip(approvedCount, totalAmount)
                .map(tuple -> {
                    LoanApprovalsMetric metrics = new LoanApprovalsMetric(
                            tuple.getT1().getMetricValue().longValue(),
                            tuple.getT2().getMetricValue(),
                            tuple.getT1().getUpdatedAt()
                    );
                    log.info(ReportMetricRepositoryLog.GET_METRICS_SUCCESS.getMessage(),
                            metrics.getApprovedCount(),
                            metrics.getTotalApproved());
                    return metrics;
                })
                .doOnError(e -> log.error(ReportMetricRepositoryLog.GET_METRICS_ERROR.getMessage(), e.getMessage(), e));
    }

}