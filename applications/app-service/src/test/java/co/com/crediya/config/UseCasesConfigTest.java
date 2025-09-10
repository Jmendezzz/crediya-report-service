package co.com.crediya.config;

import co.com.crediya.model.reportmetric.gateways.ReportMetricRepository;
import co.com.crediya.usecase.reportmetric.ReportMetricUseCase;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UseCasesConfigTest {
    @Test
    void shouldCreateBeans(){
        ReportMetricRepository reportMetricRepository = Mockito.mock(ReportMetricRepository.class);

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

        context.registerBean(ReportMetricRepository.class, () -> reportMetricRepository);

        context.register(UseCasesConfig.class);
        context.refresh();

        ReportMetricUseCase reportMetricUseCase = context.getBean(ReportMetricUseCase.class);

        assertThat(reportMetricUseCase).isNotNull();

        context.close();
    }


}