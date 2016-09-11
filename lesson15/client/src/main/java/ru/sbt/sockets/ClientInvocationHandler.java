package ru.sbt.sockets;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.Socket;

public class ClientInvocationHandler implements InvocationHandler {

    private final String host;
    private final int port;

    public ClientInvocationHandler(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        try (Socket client = new Socket(host, port);
             ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(client.getInputStream())) {

            out.writeObject(method.getName());
            out.writeObject(method.getParameterTypes());
            out.writeObject(args);
            out.flush();

            Object result = in.readObject();

            if (result instanceof Exception) throw ((Exception) result);
            else return result;
        }
    }
}