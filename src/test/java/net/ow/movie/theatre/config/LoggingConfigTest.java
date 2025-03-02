package net.ow.movie.theatre.config;

import static org.junit.jupiter.api.Assertions.*;

import net.ow.shared.commonlog.filter.RequestInformationRequestLoggingFilter;
import net.ow.shared.commonlog.filter.TraceIdRequestLoggingFilter;
import net.ow.shared.commonlog.provider.client.information.ClientInformationProvider;
import net.ow.shared.commonlog.provider.client.information.JWTClientInformationProvider;
import net.ow.shared.commonlog.provider.trace.HeaderBasedTraceIdProvider;
import net.ow.shared.commonlog.provider.trace.TraceIdProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@SuppressWarnings("all")
@ExtendWith(MockitoExtension.class)
class LoggingConfigTest {
    @InjectMocks private LoggingConfig loggingConfig;

    private final String traceIdHeader = "Trace-Id";

    private final String userIdJwtClaim = "userId";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(loggingConfig, "traceIdHeader", traceIdHeader);
        ReflectionTestUtils.setField(loggingConfig, "userIdJwtClaim", userIdJwtClaim);
    }

    @Test
    void traceIdRequestLoggingFilterTest_OK() {
        TraceIdRequestLoggingFilter filter = loggingConfig.traceIdRequestLoggingFilter();
        assertInstanceOf(
                TraceIdRequestLoggingFilter.class,
                filter,
                "Expected an instance of TraceIdRequestLoggingFilter");

        TraceIdProvider traceIdProvider =
                (TraceIdProvider) ReflectionTestUtils.getField(filter, "traceIdProvider");
        assertInstanceOf(
                HeaderBasedTraceIdProvider.class,
                traceIdProvider,
                "Expected an instance of HeaderBasedTraceIdProvider");

        HeaderBasedTraceIdProvider headerBasedTraceIdProvider =
                (HeaderBasedTraceIdProvider) traceIdProvider;
        assertEquals(
                traceIdHeader,
                ReflectionTestUtils.getField(headerBasedTraceIdProvider, "traceIdHeader"),
                "Trace ID header should match the configured value");
    }

    @Test
    void requestInformationRequestLoggingFilterTest_OK() {
        RequestInformationRequestLoggingFilter filter =
                loggingConfig.requestInformationRequestLoggingFilter();
        assertInstanceOf(
                RequestInformationRequestLoggingFilter.class,
                filter,
                "Expected an instance of RequestInformationRequestLoggingFilter");

        ClientInformationProvider clientInformationProvider =
                (ClientInformationProvider)
                        ReflectionTestUtils.getField(filter, "clientInformationProvider");
        assertInstanceOf(
                JWTClientInformationProvider.class,
                clientInformationProvider,
                "Expected an instance of JWTClientInformationProvider");

        assertTrue(
                (boolean) ReflectionTestUtils.getField(filter, "includeClientInfo"),
                "includeClientInfo should be true");
        assertTrue(
                (boolean) ReflectionTestUtils.getField(filter, "includeQueryString"),
                "includeQueryString should be true");
        assertTrue(
                (boolean) ReflectionTestUtils.getField(filter, "includePayload"),
                "includePayload should be true");
    }
}
