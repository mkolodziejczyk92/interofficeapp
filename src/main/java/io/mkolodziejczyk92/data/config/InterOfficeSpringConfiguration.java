package io.mkolodziejczyk92.data.config;

import io.mkolodziejczyk92.utils.BeanProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@Configuration
@EnableScheduling
public class InterOfficeSpringConfiguration {

    @Bean
    public BeanProvider getBeanProvider() {
        return new BeanProvider();
    }
}
