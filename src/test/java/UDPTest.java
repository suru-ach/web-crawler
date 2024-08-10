import core.UDPClient;
import core.UDPServer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDPTest {
    UDPClient client;

    @Before
    public void setup() throws SocketException, UnknownHostException {
        new UDPServer().start();
        client = new UDPClient();
    }

    @Test
    public void whenCanSendAndReceivePacket_thenCorrect() throws IOException {
        String echo = client.sendEcho("hello server");
        Assert.assertEquals("hello server", echo);
        echo = client.sendEcho("server is working");
        Assert.assertFalse(echo.equals("hello server"));
    }

    @After
    public void tearDown() throws IOException {
        client.sendEcho("end");
    }
}
