package net.ow.movie.theatre.config;

import lombok.Getter;
import net.ow.shared.commonlog.filter.TraceIdRequestLoggingFilter;
import net.ow.shared.commonlog.provider.trace.HeaderBasedTraceIdProvider;
import net.ow.shared.commonlog.provider.trace.TraceIdProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class LoggingConfig {
    @Value("${logging.trace-id.header}")
    private String traceIdHeader;

    @Bean
    public TraceIdRequestLoggingFilter traceIdRequestLoggingFilter() {
        TraceIdRequestLoggingFilter traceIdRequestLoggingFilter = new TraceIdRequestLoggingFilter();

        TraceIdProvider traceIdProvider = new HeaderBasedTraceIdProvider(traceIdHeader);
        traceIdRequestLoggingFilter.setTraceIdProvider(traceIdProvider);

        return traceIdRequestLoggingFilter;
    }
}
