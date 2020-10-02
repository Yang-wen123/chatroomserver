package com.company;

import com.company.thread.LoginThread;
import com.company.utils.ConstantUtil;
import com.company.thread.ServerThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @author WEN
 */
public class Main {

    public static void main(String[] args){
        new Thread(() -> {
            try {
                ServerSocket serverSocket = new ServerSocket(ConstantUtil.LOGIN_PORT);
                while (true){
                    Socket socket = serverSocket.accept();
                    System.out.println(socket);
                    System.out.println("welcome login"+socket.getInetAddress().getHostAddress());
                    new LoginThread(socket).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                ServerSocket serverSocket = new ServerSocket(ConstantUtil.CHAT_PORT);
                List<Socket> socketList = new ArrayList<>();
                while (true){
                    Socket socket = serverSocket.accept();
                    System.out.println(socket);
                    System.out.println("welcome chat"+socket.getInetAddress().getHostAddress());
                    socketList.add(socket);
                    System.out.println(socketList.size());
                    new ServerThread(socket,socketList).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
