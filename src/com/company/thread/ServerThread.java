package com.company.thread;

import com.company.Main;
import org.json.JSONObject;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author WEN
 */
public class ServerThread extends Thread{
    Socket socket;
    List<Socket> socketList;
    public ServerThread(Socket socket, List<Socket> socketList) {
        this.socket = socket;
        this.socketList = socketList;
    }
    @Override
    public void run() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
            while (true){
                String readLine = bufferedReader.readLine();
                for(Socket so:socketList){
                    if(so.equals(socket)){
                        System.out.println(so);
                        continue;
                    }
                    PrintWriter printWriter = new PrintWriter(so.getOutputStream(),true);
                    printWriter.println(readLine);
                }
            }
        } catch (IOException e) {
        }
    }
}