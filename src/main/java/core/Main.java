package core;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //ThreadPoolBlockingQueue threadPoolBlockingQueue = new ThreadPoolBlockingQueue();

        /*
        UDPClient client;
        try {
            new UDPServer().start();
            client = new UDPClient();
            String string = client.sendEcho("hello");
            System.out.println(string);
        } catch (SocketException | UnknownHostException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }
        */

        /*
        try {
            //InetAddress address = InetAddress.getByName("www.x.com");
            byte[] ipaddr = new byte[]{127,0,0,1};
            InetAddress address = InetAddress.getByAddress(ipaddr);
            System.out.println(address);
            System.out.println(address.getHostAddress());
            System.out.println(address.getHostName());
            System.out.println(address.getCanonicalHostName());
        } catch(UnknownHostException e) {
            System.out.println(e);
        }
        */

        //BoundedBuffer boundedBuffer = new BoundedBuffer();

    }
}