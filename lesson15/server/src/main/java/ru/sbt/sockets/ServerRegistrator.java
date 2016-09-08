package ru.sbt.sockets;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;

/*
* Client:
* createProxy
*     send method name + args to server
 *    receive return value from server and return it
* */

/*
* server:
* listen host + port
* read methodName + args
* invoke method via reflection
* send return value to client
*
* */

public class ServerRegistrator {

    public static void listen(int port, Object impl) throws IOException {

        ServerSocket serverSocket = new ServerSocket(port);
        while (true) {
            try (Socket client = serverSocket.accept();
                 ObjectInputStream in = new ObjectInputStream(client.getInputStream());
                 ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream())) {

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

                out.writeObject(result);
                out.flush();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        ServerRegistrator.listen(5000, new CalculatorImpl());
    }
}