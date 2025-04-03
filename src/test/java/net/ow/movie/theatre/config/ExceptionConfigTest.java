package net.ow.movie.theatre.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import net.ow.shared.errorutils.util.LocaleMessageSource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

@ExtendWith(MockitoExtension.class)
class ExceptionConfigTest {
    @InjectMocks private ExceptionConfig exceptionConfig;

    @Test
    void messageSourceTest_OK() {
        MessageSource messageSource = exceptionConfig.messageSource();
        LocaleMessageSource localeMessageSource = (LocaleMessageSource) messageSource;

        assertEquals(LocaleMessageSource.class, messageSource.getClass());
        assertTrue(
                localeMessageSource
                        .getBasenameSet()
                        .contains("error-message/tmdb-exception-messages"));
    }
}
