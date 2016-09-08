package ru.sbt.sockets;

import static java.lang.ClassLoader.getSystemClassLoader;
import static java.lang.reflect.Proxy.newProxyInstance;

public class NetClientFactory {

    private final String host;
    private final int port;

    public NetClientFactory(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @SuppressWarnings("unchecked")
    public <T> T createClient(Class<T> interfaceClass) {
        return (T) newProxyInstance(getSystemClassLoader(),
                new Class[]{interfaceClass},
                new ClientInvocationHandler(host, port));
    }
}
