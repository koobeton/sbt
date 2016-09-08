package ru.sbt.sockets;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class CalculatorTest {

    private static Calculator client;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void setUp() throws Exception {

        String host = "localhost";
        int port = 5000;

        Runnable server = () -> ServerRegistrator.listen(port, new CalculatorImpl());
        new Thread(server).start();

        NetClientFactory factory = new NetClientFactory(host, port);
        client = factory.createClient(Calculator.class);
    }

    @Test
    public void calculate() throws Exception {
        double calculate = client.calculate(1, 2);
        assertEquals(-31, calculate, 0);
    }

    @Test
    public void throwMeException() throws Exception {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("Unable to calculate");

        client.throwMeException();
    }
}