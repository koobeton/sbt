package ru.sbt.spring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import ru.sbt.spring.domain.Service;
import ru.sbt.spring.domain.ServiceImpl;
import ru.sbt.spring.proxy.CacheProxy;
import ru.sbt.spring.proxy.persist.PersistStrategiesContext;

@SpringBootConfiguration
@ComponentScan("ru.sbt")    //needed for run postprocessor
public class TestConfig {

    @Bean
    private static PropertySourcesPlaceholderConfigurer pspc() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Value("${rootDir}")
    private String rootDir;

    @Bean
    PersistStrategiesContext persistStrategiesContext() {
        return new PersistStrategiesContext();
    }

    @Bean
    CacheProxy cacheProxy() {
        return new CacheProxy(rootDir, persistStrategiesContext());
    }

    @Bean
    @Scope("prototype")
    Service service() {
        return new ServiceImpl();
    }
}
