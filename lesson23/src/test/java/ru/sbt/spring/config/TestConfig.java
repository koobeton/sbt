package ru.sbt.spring.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.*;
import ru.sbt.spring.domain.Service;
import ru.sbt.spring.domain.ServiceImpl;
import ru.sbt.spring.proxy.CacheProxy;
import ru.sbt.spring.proxy.persist.PersistStrategiesContext;

@SpringBootConfiguration
@ComponentScan("ru.sbt")    //needed for run postprocessor
public class TestConfig {

    private final static String ROOT_DIR = "./src/test/resources/";

    @Bean
    PersistStrategiesContext persistStrategiesContext() {
        return new PersistStrategiesContext();
    }

    @Bean
    CacheProxy cacheProxy() {
        return new CacheProxy(ROOT_DIR, persistStrategiesContext());
    }

    @Bean
    @Scope("prototype")
    Service service() {
        return new ServiceImpl();
    }
}
