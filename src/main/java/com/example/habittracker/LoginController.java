package com.example.habittracker;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.database.User;
import org.database.services.UserService;

import java.io.Console;
import java.io.IOException;

public class LoginController {
    @FXML
    private TextField login;
    @FXML
    private PasswordField password;
    @FXML
    private Label feedback;
    enum ErrorCode{NO_ERROR, ERROR}

    private Stage stage;
    private Scene scene;
    public Parent parent;

    @FXML
    protected void onSignInButtonClick() {
        processLoginForm();
        System.out.println(login.getText() + " " + password.getText() );
    }
    @FXML
    public void switchToCreateAccountScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("create-account-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchToNewScene(String sceneName) {
        //create an object to store the data about current user
        DomainUser user = new DomainUser(login.getText());

        try{
        Parent root = FXMLLoader.load(getClass().getResource(sceneName));

        stage = (Stage)login.getScene().getWindow();

        //attach this object to the stage
        stage.setUserData(user);

        scene = new Scene(root);

        //FXMLLoader loader = new FXMLLoader(getClass().getResource(sceneName));
        //loader.setController(new ProfileViewController());
        //ProfileViewController controller = loader.getController();

        //controller.postInitialize();

        stage.setScene(scene);
        stage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }


    private void processLoginForm(){
        ErrorCode result = validateLoginForm();
        if(result == ErrorCode.ERROR){
            feedback.setText("Username or password is incorrect");
        }
        if(result == ErrorCode.NO_ERROR){
            feedback.setText("");
            //switchToNewScene("Dashboard-view.fxml");
            switchToNewScene("profile-view.fxml");
        }

    }

    private ErrorCode validateLoginForm(){
        boolean databaseResult  = checkInDatabase();
        if(databaseResult == false){
            return ErrorCode.ERROR;
        }
        return ErrorCode.NO_ERROR;
    }

    //checks if the account is registered in a database
    //and if password is correct
    private boolean checkInDatabase(){
        UserService userService = new UserService();
        User user = null;
        try {
            user = userService.getUser(login.getText()); // get a user by it's username
        } catch (Exception e) {
            userService.stopConnection();
            return false;
        }
        if(user != null){
            if(!user.getPassword().equals(password.getText())) {
                userService.stopConnection();
                return false;
            }
        }
        userService.stopConnection();
        return true;
    }
    /*
    private void sendData(){
        DomainUser user = new DomainUser(login.getText());
        try {
            Parent root = FXMLLoader.load(getClass().getResource("login-view.fxml"));
            stage = (Stage) name.getScene().getWindow();
            stage.setUserData(user);
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    private void receiveData(){
        stage = (Stage)(login.getScene().getWindow());
        DomainUser user = (DomainUser) stage.getUserData();
        user.GetUserName();
        login.setText(user.GetUserName());
    }
*/
}