package com.example.habittracker;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;


public class ProfileViewController {
    @FXML
    private Button changeProfilePictureButton;

    @FXML
    private ImageView profilePictureImageView;

    @FXML
    private Button editName;

    @FXML
    private Button editSurame;

    @FXML
    private Button okName;

    @FXML
    private Button okEmail;

    @FXML
    private Button okSurname;

    @FXML
    private Button editEmail;

    @FXML
    private TextField newName;

    @FXML
    private TextField newEmail;

    @FXML
    private TextField newSurname;

    @FXML
    private Label nameLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label surnameLabel;


    Stage stage;
    Scene scene;
    public void changeProfilePicture(){

        String picture = "profile-pictures/"+openFileChooser();
        System.out.println("Here: + " + picture);
        InputStream input = getClass().getResourceAsStream(picture);
        Image image = null;

        if(input != null){
            image = new Image(input);
        }

        if(picture != "profile_pictures/" && image != null){
            profilePictureImageView.setImage(image);
        }
    }

    public String openFileChooser(){
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open File");
        File newPicture = chooser.showOpenDialog(changeProfilePictureButton.getScene().getWindow());
        if(newPicture != null){
            File targetLocation = new File("src/main/resources/com/example/habittracker/profile-pictures", newPicture.getName());
            try (FileInputStream fis = new FileInputStream(newPicture);
                 FileOutputStream fos = new FileOutputStream(targetLocation)) {

                byte[] buffer = new byte[1024];
                int bytesRead;

                while ((bytesRead = fis.read(buffer)) > 0) {
                    fos.write(buffer, 0, bytesRead);
                }

                System.out.println("File saved to " + targetLocation.getAbsolutePath());

                return targetLocation.getName();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Failed to save the file.");
            }
        }
        return "";
    }
    public void switchToDashBoardView(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Dashboard-view.fxml"));
            stage = (Stage) profilePictureImageView.getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void onEditNameClicked(){
        nameLabel.setText("  ");
        nameLabel.setVisible(false);
        newName.clear();
        newName.setVisible(true);
        editName.setVisible(false);
        okName.setVisible(true);

    }
    public void onSaveNameClicked(){
        nameLabel.setText("your name");
        nameLabel.setVisible(true);
        newName.setVisible(false);
        editName.setVisible(true);
        okName.setVisible(false);

    }

    public void onEditEmailClicked(){
        emailLabel.setText("  ");
        emailLabel.setVisible(false);
        newEmail.clear();
        newEmail.setVisible(true);
        editEmail.setVisible(false);
        okEmail.setVisible(true);

    }
    public void onSaveEmailClicked(){
        emailLabel.setText("your e-mail");
        emailLabel.setVisible(true);
        newEmail.setVisible(false);
        editEmail.setVisible(true);
        okEmail.setVisible(false);
    }
    public void onEditSurnameClicked(){
        surnameLabel.setText("  ");
        surnameLabel.setVisible(false);
        newSurname.clear();
        newSurname.setVisible(true);
        editSurame.setVisible(false);
        okSurname.setVisible(true);

    }
    public void onSaveSurnameClicked(){
        surnameLabel.setText("Your last name");
        surnameLabel.setVisible(true);
        newSurname.setVisible(false);
        editSurame.setVisible(true);
        okSurname.setVisible(false);
    }
}
