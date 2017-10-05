package com.noroc.impl.network;

import com.noroc.api.network.IServer;
import com.noroc.api.network.RunnableState;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Server implements IServer {
    private int port;
    private int connectionInitThreadCount = 5;
    private RunnableState state = RunnableState.CREATED;

    private ServerSocket socket = null;
    private ThreadPoolExecutor executor;

    @Override
    public RunnableState init() {
        if(this.state != RunnableState.CREATED){
            return this.state;
        }
        try {
            socket = new ServerSocket(port);
        } catch (IOException e) {
            state = RunnableState.INITIALIZATION_FAILED;
        }
        executor = new ThreadPoolExecutor(1, connectionInitThreadCount, 5, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        return this.state;
    }

    @Override
    public void run() {
        try {
            Socket s = socket.accept();

        } catch (IOException e) {
            state = RunnableState.RUN_ERROR;
        }
    }

    @Override
    public RunnableState stop() {
        return null;
    }

    @Override
    public RunnableState getState() {
        return state;
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public String getIp() {
        return socket.getInetAddress().getHostAddress();
    }
}
