package signInView;

import animation.Animation;
import client.ClientController;
import dbConnection.DBConnection;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import sample.User;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.ResourceBundle;

public class SignInController {
    @FXML
    private TextField login_field;
    @FXML
    private TextField password_field;
    @FXML
    private Button sign_in_button;
    @FXML
    private Button sign_up_button;
    private Alert alert;

    @FXML
    void initialize(){
        sign_in_button.setOnAction(event -> {
            String loginText = login_field.getText().trim();
            String loginPassword = password_field.getText().trim();
            if(!loginText.isEmpty() && !loginPassword.isEmpty()) {
                try {
                    loginUser(loginText, loginPassword);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            else{
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR!");
                alert.setHeaderText(null);
                alert.setContentText("Login or password are empty");
                alert.showAndWait();
            }


        });
        sign_up_button.setOnAction(event -> {
            openNewScene("../signUpView/SignUpView.fxml");
        });
    }

    private void loginUser(String loginText, String loginPassword) throws SQLException {
        DBConnection connection = new DBConnection();
        User user = new User();
        user.setLogin(loginText);
        user.setPassword(loginPassword);
        ResultSet resultSet = connection.getUsers(user);
        int counter=0;
        while(resultSet.next())
            counter++;
        if (counter>=1){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    openNewScene("../client/clientView.fxml");
                }
            });




        }
        else{
            Animation animationLogin = new Animation(login_field);
            Animation animationPassword = new Animation(password_field);
            animationLogin.playAnimation();
            animationPassword.playAnimation();

        }
    }

    private void openNewScene(String window) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(window));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        sign_up_button.getScene().getWindow().hide();
        stage.initOwner(sign_up_button.getScene().getWindow());
        stage.setScene(new Scene(root));
        stage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });
        stage.show();



    }

}
