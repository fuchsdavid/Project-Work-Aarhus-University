package com.example.habittracker;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class CreateAccountController {
    @FXML
    private TextField name;
    @FXML
    private TextField login;
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField repeatPassword;
    @FXML
    private Label feedback;
    enum ErrorCode{NO_ERROR, PASSWORD, REPEAT_PASSWORD, EMAIL, EMPTY_FIELD, EMAIL_TAKEN, USERNAME_TAKEN}

    @FXML
    protected void onRegisterButtonClick() {
        processRegisterForm();
        System.out.println(name.getText() + " " + login.getText() + " " + email.getText() + " " + password.getText() + " " + repeatPassword.getText());
    }

    @FXML
    protected void onResetButtonClick() {
        name.setText("");
        login.setText("");
        email.setText("");
        password.setText("");
        repeatPassword.setText("");
    }

    private void processRegisterForm(){
        ErrorCode result = validateRegisterForm();
        if(result == ErrorCode.EMPTY_FIELD){
            feedback.setText("All fields are required");
        }
        else if(result == ErrorCode.REPEAT_PASSWORD){
            feedback.setText("Passwords do not match");
        }
        else if(result == ErrorCode.PASSWORD){
            feedback.setText("Your password needs to be between 8 and 30 characters long");
        }
        else if(result == ErrorCode.EMAIL){
            feedback.setText("Your email address is incorrect");
        }
        else if(result == ErrorCode.EMAIL_TAKEN){
            feedback.setText("This email address is already in use");
        }
        else if(result == ErrorCode.USERNAME_TAKEN){
            feedback.setText("This username is already in use");
        }
        else{
            feedback.setText("");
            saveInDatabase();
        }
    }

    private ErrorCode validateRegisterForm(){
        TextField[] allFields = {name,login,email,password,repeatPassword};
        for (TextField field : allFields) {
            if (field.getText().equals("")) {
                return ErrorCode.EMPTY_FIELD;
            }
        }
        if(!password.getText().equals(repeatPassword.getText())){
            return ErrorCode.REPEAT_PASSWORD;
        }
        if(!email.getText().contains("@") || !email.getText().contains(".")){
            return ErrorCode.EMAIL;
        }
        if(!(password.getText().length() >= 8 && password.getText().length() <= 30)){
            return ErrorCode.PASSWORD;
        }
        if(checkIfEmailTaken(email.getText())){
            return ErrorCode.EMAIL_TAKEN;
        }
        if(checkIfUserNameTaken(login.getText())){
            return ErrorCode.USERNAME_TAKEN;
        }
        return ErrorCode.NO_ERROR;
    }
    private Boolean checkIfUserNameTaken(String username){
        /*TODO
        checks if the username is already in the database
         */
        return false;
    }
    private Boolean checkIfEmailTaken(String email){
        /*TODO
        checks if email is already in the database
         */
        return false;
    }
    private void saveInDatabase(){

    }

}