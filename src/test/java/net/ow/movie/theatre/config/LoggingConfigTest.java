package net.ow.movie.theatre.config;

import static org.junit.jupiter.api.Assertions.*;

import net.ow.shared.commonlog.filter.TraceIdRequestLoggingFilter;
import net.ow.shared.commonlog.provider.trace.HeaderBasedTraceIdProvider;
import net.ow.shared.commonlog.provider.trace.TraceIdProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class LoggingConfigTest {
    @InjectMocks private LoggingConfig loggingConfig;

    private final String traceIdHeader = "Trace-Id";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(loggingConfig, "traceIdHeader", traceIdHeader);
    }

    @Test
    public void traceIdRequestLoggingFilter_ShouldReturnConfiguredFilter() {
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
}
