package signUpView;

import dbConnection.DBConnection;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import sample.User;

public class SignUpController {
    @FXML
    private TextField Country;
    @FXML
    private TextField Name;
    @FXML
    private TextField Surname;
    @FXML
    private CheckBox checkBoxFemale;
    @FXML
    private CheckBox checkBoxMale;
    @FXML
    private TextField login_field;
    @FXML
    private TextField password_field;
    @FXML
    private Button sign_up_button;
    @FXML
    void initialize(){
        sign_up_button.setOnAction(event -> {
            signUpNewUser();
        });
    }

    private void signUpNewUser() {
        DBConnection dbConnection = new DBConnection();
        String firstName = Name.getText();
        String lastName = Surname.getText();
        String loginName = login_field.getText();
        String passwordName = password_field.getText();
        String countryName = Country.getText();
        String genderName;
        if(checkBoxMale.isSelected()){
            genderName = "Male";
        }else
            genderName = "Female";
        User user = new User(firstName, lastName, genderName, countryName, loginName, passwordName);
        dbConnection.signUpNewUser(user);
    }

}
