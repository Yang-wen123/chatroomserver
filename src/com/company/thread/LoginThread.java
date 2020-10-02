package com.company.thread;

import com.company.utils.TokenUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author WEN
 */
public class LoginThread extends Thread{
    private Socket socket;
    PrintWriter printWriter;
    public LoginThread (Socket socket){
        this.socket = socket;
    }
    @Override
    public void run(){
        JSONObject choke = new JSONObject();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
            while (true){
                String readLine = bufferedReader.readLine();
                //System.out.println(readLine);
                if(readLine!=null){
                    JSONObject login = new JSONObject(readLine);
                    String username = login.getString("username");
                    String token = login.getString("token");
                    if(TokenUtil.checkToken(token)){
                        choke.put("code",200);
                        choke.put("username",username);
                        choke.put("result","check succeed!");
                    }else {
                        choke.put("code",202);
                        choke.put("username",username);
                        choke.put("result","check failed! illegal login");
                    }
                    printWriter = new PrintWriter(socket.getOutputStream(),true);
                    printWriter.println(choke.toString());
                }

            }
        } catch (IOException e) {
            System.out.println("客户端退出");
        } catch (JSONException exception){
            choke.put("code",201);
            choke.put("username","unknown");
            choke.put("result","cannot get token!");
            try {
                printWriter = new PrintWriter(socket.getOutputStream(),true);
                printWriter.println(choke.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
