package ru.sbt.sockets;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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

    private static final ExecutorService THREAD_POOL = Executors.newFixedThreadPool(3);

    public static void listen(int port, Object impl) {

        ServerSocket serverSocket = createServerSocket(port);

        while (true) {
            try (Socket client = serverSocket.accept();
                 ObjectInputStream in = new ObjectInputStream(client.getInputStream());
                 ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream())) {

                out.writeObject(executeTask(in, impl));
                out.flush();
            } catch (IOException e) {
                throw new RuntimeException("Internal server error: " +
                        "Unable to send answer because I/O exception occurred: " +
                        e.getMessage(), e);
            }
        }
    }

    private static ServerSocket createServerSocket(int port) {
        try {
            return new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException("Internal server error: " +
                    "Unable to create server socket: " + e.getMessage(), e);
        }
    }

    private static Object executeTask(ObjectInputStream in, Object impl) {

        Object result;

        Future<Object> task = THREAD_POOL.submit(() -> handleRequest(in, impl));
        try {
            result = task.get();
        } catch (ExecutionException e) {
            result = e.getCause();
        } catch (InterruptedException e) {
            result = e;
        }

        return result;
    }

    private static Object handleRequest(ObjectInputStream in, Object impl) {

        Object result;

        try {
            String methodName = (String) in.readObject();
            Class<?>[] parameterTypes = (Class<?>[]) in.readObject();
            Object[] args = (Object[]) in.readObject();

            result = impl.getClass()
                    .getMethod(methodName, parameterTypes)
                    .invoke(impl, args);
        } catch (InvocationTargetException e) {
            result = e.getCause();
        } catch (Exception e) {
            result = e;
        }

        return result;
    }
}