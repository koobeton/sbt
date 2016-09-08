package ru.sbt.sockets;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Client:
 * createProxy
 * send method name + args to server
 * receive return value from server and return it
 * <p>
 * Server:
 * listen host + port
 * read methodName + args
 * invoke method via reflection
 * send return value to client
 */
public class ServerRegistrator {

    public static void listen(int port, Object impl) {

        ServerSocket serverSocket = createServerSocket(port);

        while (true) {
            try (Socket client = serverSocket.accept();
                 ObjectInputStream in = new ObjectInputStream(client.getInputStream());
                 ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream())) {

                out.writeObject(handleRequest(impl, in));
                out.flush();
            } catch (IOException e) {
                throw new RuntimeException("I/O exception: " + e.getMessage(), e);
            }
        }
    }

    private static ServerSocket createServerSocket(int port) {
        try {
            return new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException("Unable to create server socket: " + e.getMessage(), e);
        }
    }

    private static Object handleRequest(Object impl, ObjectInputStream in) {

        Object result;

        try {
            String methodName = (String) in.readObject();
            Object[] args = (Object[]) in.readObject();
            Class<?>[] parameterTypes = null;
            if (args != null) {
                parameterTypes = new Class<?>[args.length];
                for (int i = 0; i < args.length; i++) {
                    parameterTypes[i] = args[i].getClass();
                }
            }

            result = impl.getClass()
                    .getMethod(methodName, parameterTypes)
                    .invoke(impl, args);
        } catch (Exception e) {
            result = e instanceof InvocationTargetException
                    ? e.getCause()
                    : e;
        }

        return result;
    }
}