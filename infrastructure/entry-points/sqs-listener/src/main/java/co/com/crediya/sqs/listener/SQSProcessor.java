package co.com.crediya.sqs.listener;

import co.com.crediya.model.reportmetric.events.LoanApplicationApproved;
import co.com.crediya.sqs.listener.constant.ReportMetricProcessorLog;
import co.com.crediya.usecase.reportmetric.ReportMetricUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.sqs.model.Message;

import java.util.function.Function;

@Service
@Slf4j
@RequiredArgsConstructor
public class SQSProcessor implements Function<Message, Mono<Void>> {

    private final ReportMetricUseCase reportMetricUseCase;
    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> apply(Message message) {
        log.info(ReportMetricProcessorLog.RECEIVED_MESSAGE.getMessage(), message.body());

        return Mono.fromCallable(() -> parseEvent(message.body()))
                .doOnNext(event -> log.info(
                        ReportMetricProcessorLog.PARSE_SUCCESS.getMessage(),
                        event.loanApplicationId(),
                        event.loanApplicationAmount()
                ))
                .flatMap(event -> reportMetricUseCase.onLoanApplicationApproved(event)
                        .doOnSubscribe(sub -> log.info(
                                ReportMetricProcessorLog.PROCESS_START.getMessage(),
                                event.loanApplicationId()
                        ))
                        .doOnSuccess(v -> log.info(
                                ReportMetricProcessorLog.PROCESS_SUCCESS.getMessage(),
                                event.loanApplicationId()
                        ))
                        .doOnError(err -> log.error(
                                ReportMetricProcessorLog.PROCESS_ERROR.getMessage(),
                                event.loanApplicationId(),
                                err.getMessage(),
                                err
                        ))
                )
                .onErrorResume(e -> {
                    log.error(
                            ReportMetricProcessorLog.PARSE_ERROR.getMessage(),
                            message.body(),
                            e.getMessage(),
                            e
                    );
                    return Mono.empty();
                })
                .then();
    }

    private LoanApplicationApproved parseEvent(String body) {
        try {
            return objectMapper.readValue(body, LoanApplicationApproved.class);
        } catch (Exception e) {
            throw new RuntimeException("Error parsing LoanApplicationApproved event: " + body, e);
        }
    }
}
