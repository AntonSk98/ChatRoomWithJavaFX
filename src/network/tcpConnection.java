package network;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;

public class tcpConnection {
    private final Socket socket;
    private final Thread rxThread;
    private final BufferedReader in;
    private final tcpConnectionListener eventListener;
    private final BufferedWriter out;
    public tcpConnection(tcpConnectionListener eventListener, String ipAddres, int port) throws IOException{
        this(new Socket(ipAddres, port), eventListener);

    }

    public tcpConnection(Socket socket, tcpConnectionListener eventListener) throws IOException {
        this.eventListener=eventListener;
        this.socket=socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream(), Charset.forName("UTF-8")));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), Charset.forName("UTF-8")));
        rxThread = new Thread(new Runnable() { //anonymous class
            @Override
            public void run() {
                try {
                    eventListener.onConnectionReady(tcpConnection.this);
                    while (!rxThread.isInterrupted()){
                        String msg =in.readLine();
                        eventListener.onReceiveString(tcpConnection.this, msg);
                    }
                } catch (IOException e) {
                    eventListener.onException(tcpConnection.this, e);
                }finally {//below I will use universal way, according to what will work either server or client this block
                    //will react different, to realise it I would prefer to use interface
                    eventListener.onDisconnect(tcpConnection.this);

                }

            }
        });
        rxThread.start();
    }
    public synchronized void sendString(String value){
        try {
            out.write(value +"\r\n");
            out.flush();
        } catch (IOException e) {
            eventListener.onException(tcpConnection.this, e);
            disconnext();
        }
    }
    public synchronized void disconnext(){
        rxThread.interrupt();
        try {
            socket.close();
        } catch (IOException e) {
            eventListener.onException(tcpConnection.this, e);
        }

    }

    @Override
    public String toString() {
        return "ru.chat.network.TCPConnection "+socket.getInetAddress()+" : "+socket.getPort() ;
    }
}
