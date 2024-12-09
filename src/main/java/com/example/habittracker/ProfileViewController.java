package com.example.habittracker;

import com.example.habittracker.utils.DomainUser;
import javafx.application.Platform;
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
import org.database.User;
import org.database.services.UserService;

import java.io.*;

public class ProfileViewController {
    @FXML
    private Button changeProfilePictureButton;

    @FXML
    ImageView profilePictureImageView;

    @FXML
    Button editName;

    @FXML
    Button editSurame;

    @FXML
    Button okName;

    @FXML
    Button okEmail;

    @FXML
    Button okSurname;

    @FXML
    Button editEmail;

    @FXML
    TextField newName;

    @FXML
    TextField newEmail;

    @FXML
    TextField newSurname;

    @FXML
    Label nameLabel;

    @FXML
    Label emailLabel;

    @FXML
    Label surnameLabel;
    @FXML
    Label usernameLabel;
    @FXML
    Label ageLabel;

    Stage stage;
    Scene scene;
    DomainUser user;

    UserService userService;

    @FunctionalInterface
    interface UserStringFunction {
        void apply(User user, String x);
    }

    public void initialize() {
        Platform.runLater(() -> {
            receiveData();
            userService = new UserService();
            try {
                User userDB = getUserFromDatabase();
                fillInUserData(userDB);
            } catch (Exception e) {
                userService.stopConnection();
                return;
            }
            userService.stopConnection();
        });
    }

    private void receiveData() {
        stage = (Stage) (editName.getScene().getWindow());
        user = (DomainUser) stage.getUserData();
    }

    private User getUserFromDatabase() throws Exception {
        return userService.getUser(user.GetUserName());
    }

    private void fillInUserData(User userDB) {
        usernameLabel.setText(userDB.getUserName());
        emailLabel.setText(userDB.getEmail());
        surnameLabel.setText(userDB.getSurname());
        nameLabel.setText(userDB.getName());
        ageLabel.setText(userDB.getAge().toString());
        fillInProfilePicture(userDB.getImageID());
    }

    private void fillInProfilePicture(String imageID) {
        if (imageID == null) {
            System.out.println("Image ID is null");
            setDefaultProfilePicture();
        } else {
            setProfilePicture(imageID);
        }
    }

    private void setDefaultProfilePicture() {
        String defaultPath = "profile-pictures/default.png";
        setProfilePicture(defaultPath);
    }

    public void setProfilePicture(String path) {
        InputStream input = getClass().getResourceAsStream(path);
        Image image = null;

        if (input != null) {
            image = new Image(input);
        }

        if (!path.equals("profile_pictures/") && image != null) {
            profilePictureImageView.setImage(image);
        }
    }

    public void changeProfilePicture() {
        String picture = "profile-pictures/" + openFileChooser();
        updateProfilePictureInDatabase(picture);
        this.initialize();
    }

    private void updateProfilePictureInDatabase(String newImageID) {
        userService = new UserService();
        try {
            User userDB = getUserFromDatabase();
            userService.updateImage(userDB, newImageID);
        } catch (Exception e) {
            userService.stopConnection();
            return;
        }
        userService.stopConnection();
    }

    public String openFileChooser() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open File");
        File newPicture = chooser.showOpenDialog(changeProfilePictureButton.getScene().getWindow());
        if (newPicture != null) {
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

    public void switchToDashBoardView() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Dashboard-view.fxml"));
            stage = (Stage) profilePictureImageView.getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onEditNameClicked() {
        onEditClicked(nameLabel, newName, editName, okName);
    }

    public void onSaveNameClicked() {
        String newData = onSaveClicked(nameLabel, newName, editName, okName);
        updateDatabase(newData, (u, d) -> userService.updateName(u, d));
    }

    public void onEditEmailClicked() {
        onEditClicked(emailLabel, newEmail, editEmail, okEmail);
    }

    public void onSaveEmailClicked() {
        String newData = onSaveClicked(emailLabel, newEmail, editEmail, okEmail);
        updateDatabase(newData, (u, d) -> userService.updateEmail(u, d));
    }

    public void onEditSurnameClicked() {
        onEditClicked(surnameLabel, newSurname, editSurame, okSurname);
    }

    public void onSaveSurnameClicked() {
        String newData = onSaveClicked(surnameLabel, newSurname, editSurame, okSurname);
        updateDatabase(newData, (u, d) -> userService.updateSurname(u, d));
    }

    private String onSaveClicked(Label label, TextField newData, Button editButton, Button okButton) {
        String updatedData = newData.getText();
        label.setText(updatedData);
        label.setVisible(true);
        newData.setVisible(false);
        editButton.setVisible(true);
        okButton.setVisible(false);
        return updatedData;
    }

    private void onEditClicked(Label label, TextField newData, Button editButton, Button okButton) {
        label.setText("  ");
        label.setVisible(false);
        newData.clear();
        newData.setVisible(true);
        editButton.setVisible(false);
        okButton.setVisible(true);
    }

    private void updateDatabase(String newData, UserStringFunction action) {
        userService = new UserService();
        User userDB = userService.getUser(user.GetUserName());
        action.apply(userDB, newData);
        userService.stopConnection();
    }
}