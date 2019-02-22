package network;

public interface tcpConnectionListener {
    void onConnectionReady(tcpConnection tcpConnection);
    void onReceiveString(tcpConnection tcpConnection, String message);
    void onDisconnect(tcpConnection tcpConnection);
    void onException(tcpConnection tcpConnection, Exception e);
}
