package co.com.crediya.api.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ApiResource {
    REPORT_METRICS ("/report-metrics");

    final String resource;
}
