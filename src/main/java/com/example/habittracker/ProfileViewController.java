package com.example.habittracker;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
}
