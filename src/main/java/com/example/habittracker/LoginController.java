package com.example.habittracker;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML
    private TextField login;
    @FXML
    private PasswordField password;
    @FXML
    private Label feedback;
    enum ErrorCode{NO_ERROR, ERROR}

    @FXML
    protected void onSignInButtonClick() {
        processLoginForm();
        System.out.println(login.getText() + " " + password.getText() );
    }


    private void processLoginForm(){
        ErrorCode result = validateLoginForm();
        if(result == ErrorCode.ERROR){
            feedback.setText("Username or password is incorrect");
        }
        if(result == ErrorCode.NO_ERROR){
            feedback.setText("");
        }

    }

    private ErrorCode validateLoginForm(){
        int userID = checkInDatabase();
        if(userID == -1){
            return ErrorCode.ERROR;
        }
        return ErrorCode.NO_ERROR;
    }

    private int checkInDatabase(){
        /*TODO
        check if the account is registered in a database
        and if password is correct
         */
        return 0;
    }
}