package net.ow.movie.theatre.config;

import net.ow.shared.errorutils.util.LocaleMessageSource;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ExceptionConfig {
    @Bean
    @Primary
    public MessageSource messageSource() {
        LocaleMessageSource messageSource = new LocaleMessageSource();
        messageSource.setBasenames("error-message/tmdb-exception-messages");
        return messageSource;
    }
}
