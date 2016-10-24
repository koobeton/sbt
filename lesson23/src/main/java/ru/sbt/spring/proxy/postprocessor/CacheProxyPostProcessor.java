package ru.sbt.spring.proxy.postprocessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Service;
import ru.sbt.spring.proxy.CacheProxy;
import ru.sbt.spring.proxy.annotation.Cache;

import java.util.stream.Stream;

@Service
public class CacheProxyPostProcessor implements BeanPostProcessor {

    @Autowired
    private CacheProxy cacheProxy;

    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        return o;
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        if (Stream.of(o.getClass().getInterfaces())
                .flatMap(i -> Stream.of(i.getDeclaredMethods()))
                .anyMatch(m -> m.isAnnotationPresent(Cache.class))) {
            return cacheProxy.cache(o);
        } else return o;
    }
}
