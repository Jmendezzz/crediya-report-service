package co.com.crediya.config;

import co.com.crediya.model.reportmetric.gateways.ReportMetricRepository;
import co.com.crediya.usecase.reportmetric.ReportMetricUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(basePackages = "co.com.crediya.usecase",
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "^.+UseCase$")
        },
        useDefaultFilters = false)
public class UseCasesConfig {

        @Bean
        public ReportMetricUseCase reportMetricUseCase(ReportMetricRepository reportMetricRepository){
                return  new ReportMetricUseCase(reportMetricRepository);
        }
}
