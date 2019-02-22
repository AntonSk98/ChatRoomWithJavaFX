package client;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import network.tcpConnection;
import network.tcpConnectionListener;

import java.io.IOException;

public class ClientController implements tcpConnectionListener {
    @FXML
    private TextArea NameField;
    @FXML
    private TextArea CodeField;
    @FXML
    private TextField MessageField;
    @FXML
    private Button SendButton;
    private static final String IP_ADDRESS= "127.0.0.1";
    private static final int PORT = 1234;
    private tcpConnection tcpConnection;
    public ClientController(){

        try{
            tcpConnection = new tcpConnection(this, IP_ADDRESS, PORT);
        }catch (IOException e){
            printMessage("Connection exception "+e);
        }
    }

    private synchronized void printMessage(String msg) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                CodeField.appendText(msg+"\r\n");
                CodeField.positionCaret(CodeField.getLength());

            }
        });

    }
    @FXML
    void initialize(){
        SendButton.setOnAction(event -> {
            String msg = MessageField.getText();
            if(msg.equals("")) return;
            MessageField.setText(null);
            tcpConnection.sendString(msg);
            System.out.println(msg);

        });


    }



    @Override
    public void onConnectionReady(tcpConnection tcpConnection) {
        printMessage("Connection ready!!");
    }

    @Override
    public void onReceiveString(tcpConnection tcpConnection, String message) {
        printMessage(message);
    }

    @Override
    public void onDisconnect(tcpConnection tcpConnection) {
        printMessage("Connection close...");
    }

    @Override
    public void onException(tcpConnection tcpConnection, Exception e) {
        printMessage("Connection exception "+e);
    }
}
