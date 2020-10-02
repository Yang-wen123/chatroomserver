package com.company.utils;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @author WEN
 */
public class ConstantUtil {
    public static List<Socket> socketList = new ArrayList<>();
    public static final int LOGIN_PORT = 8888;
    public static final int CHAT_PORT = 6666;
    public static final int LOGIN_LIMIT = 2;
    public static final String ADDRESS = "192.168.43.120";
}
