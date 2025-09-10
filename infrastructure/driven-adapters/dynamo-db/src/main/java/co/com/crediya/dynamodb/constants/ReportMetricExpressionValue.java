package co.com.crediya.dynamodb.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReportMetricExpressionValue {
    INC(":inc"),
    ZERO(":zero"),
    WHEN(":when");

    private final String placeholder;
}
