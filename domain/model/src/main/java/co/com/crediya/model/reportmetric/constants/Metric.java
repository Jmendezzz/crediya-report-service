package co.com.crediya.model.reportmetric.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Metric {
    APPROVED_COUNT("approved_count"),
    TOTAL_APPROVED_AMOUNT("total_amount");
    private final String name;
}
