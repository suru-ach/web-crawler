package core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Whois {

    Whois() {
        try {
            int c;
            Socket s = new Socket("internic.net", 43);
            InputStream in = s.getInputStream();
            OutputStream out = s.getOutputStream();

            String str = "osborne.com";
            byte buf[] = str.getBytes();
            out.write(buf);
            while ((c = in.read()) != -1) {
                System.out.print((char) c);
            }
            s.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
