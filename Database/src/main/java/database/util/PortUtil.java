package database.util;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;

public class PortUtil {
    public static int getRandomPort() {
        int port = 0;
        try {
            ServerSocket serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(0));
            port = serverSocket.getLocalPort();//得到那个随机端口
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return port;
    }
}
