package server;

import network.tcpConnection;
import network.tcpConnectionListener;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class server implements tcpConnectionListener {
    public static void main(String[] args) {
        new server();
    }
    private final ArrayList<tcpConnection> connections = new ArrayList<>();
    private server(){
        System.out.println("server running");
        try(ServerSocket serverSocket = new ServerSocket(1234)){
            while (true){
                new tcpConnection(serverSocket.accept(), this);
            }
        }catch (IOException e){
            throw new RuntimeException();
        }
    }

    @Override
    public void onConnectionReady(tcpConnection tcpConnection) {
        connections.add(tcpConnection);
        sendToAllConnections("Client connected "+ tcpConnection);
    }

    @Override
    public void onReceiveString(tcpConnection tcpConnection, String message) {
        sendToAllConnections(message);
    }

    @Override
    public void onDisconnect(tcpConnection tcpConnection) {
        connections.remove(tcpConnection);
        sendToAllConnections("Client disconnected "+ tcpConnection);

    }

    @Override
    public void onException(tcpConnection tcpConnection, Exception e) {
        System.out.println("TCPException: "+e);
    }
    private void sendToAllConnections(String value){
        for (int i=0; i<connections.size(); i++)
            connections.get(i).sendString(value);

    }
}
