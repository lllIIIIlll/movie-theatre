package net.ow.movie.theatre.config;

import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import net.ow.shared.commonlog.filter.RequestInformationRequestLoggingFilter;
import net.ow.shared.commonlog.filter.TraceIdRequestLoggingFilter;
import net.ow.shared.commonlog.provider.client.information.ClientInformationProvider;
import net.ow.shared.commonlog.provider.client.information.JWTClientInformationProvider;
import net.ow.shared.commonlog.provider.trace.HeaderBasedTraceIdProvider;
import net.ow.shared.commonlog.provider.trace.TraceIdProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class LoggingConfig {
    @Value("${movie-theatre.trace-id.header}")
    private String traceIdHeader;

    @Value("${movie-theatre.user-id.jwt-claim}")
    private String userIdJwtClaim;

    @Bean
    public TraceIdRequestLoggingFilter traceIdRequestLoggingFilter() {
        TraceIdRequestLoggingFilter traceIdRequestLoggingFilter = new TraceIdRequestLoggingFilter();

        TraceIdProvider traceIdProvider = new HeaderBasedTraceIdProvider(traceIdHeader);
        traceIdRequestLoggingFilter.setTraceIdProvider(traceIdProvider);

        return traceIdRequestLoggingFilter;
    }

    @Bean
    public RequestInformationRequestLoggingFilter requestInformationRequestLoggingFilter() {
        RequestInformationRequestLoggingFilter requestInformationRequestLoggingFilter =
                new RequestInformationRequestLoggingFilter();

        Set<String> clientInformationJwtClaims = new HashSet<>();
        clientInformationJwtClaims.add(userIdJwtClaim);
        ClientInformationProvider clientInformationProvider =
                new JWTClientInformationProvider(clientInformationJwtClaims);

        requestInformationRequestLoggingFilter.setClientInformationProvider(
                clientInformationProvider);
        requestInformationRequestLoggingFilter.setIncludeClientInfo(true);
        requestInformationRequestLoggingFilter.setIncludeQueryString(true);
        requestInformationRequestLoggingFilter.setIncludePayload(true);

        return requestInformationRequestLoggingFilter;
    }
}
