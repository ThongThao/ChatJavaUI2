package Controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.io.ByteArrayInputStream;
import Service.UserService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.User;

public class ProjectController {
    @FXML
    private Label nameLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private RadioButton maleRadio;
    @FXML
    private RadioButton femaleRadio;
    @FXML
    private DatePicker birthDatePicker;
    @FXML
    private PasswordField passwordField; 
    @FXML
    private ImageView avatarImageView;

    private UserService userService = new UserService();

    @FXML
    public void initialize() {
        int currentUserId = 1;
        User user = userService.getUserById(currentUserId);

        if (user != null) {
            updateUserInfo(user);

            byte[] userAvatar = userService.getUserAvatarById(currentUserId);
            if (userAvatar != null) {
                avatarImageView.setImage(new Image(new ByteArrayInputStream(userAvatar)));
            } else {
                System.out.println("Avatar not found for user ID: " + currentUserId);
            }
        } else {
            System.out.println("User not found.");
        }
    }

    @FXML
    private void handleAvatarClick(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Chọn Ảnh Đại Diện");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        
        File selectedFile = fileChooser.showOpenDialog(avatarImageView.getScene().getWindow());
        if (selectedFile != null) {
            Image avatarImage = new Image(selectedFile.toURI().toString());
            avatarImageView.setImage(avatarImage);
        }
    }

    private void updateUserInfo(User user) {
        nameLabel.setText(user.getUsername());
        phoneLabel.setText(user.getPhone());
        maleRadio.setSelected("Nam".equalsIgnoreCase(user.getGender()));
        femaleRadio.setSelected("Nữ".equalsIgnoreCase(user.getGender()));
        passwordField.setText("");

        String birthDateStr = user.getBirthDate();
        if (birthDateStr != null && birthDateStr.length() >= 10) {
            try {
                birthDatePicker.setValue(LocalDate.parse(birthDateStr));
            } catch (DateTimeParseException e) {
                System.out.println("Invalid birth date format: " + e.getMessage());
            }
        }
    }

    @FXML
    private void handleBackButtonAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/MainChat.fxml"));
            Stage stage = (Stage) nameLabel.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load previous screen.");
        }
    }

    @FXML
    private void handleSaveButtonAction() {
        if (isInputValid()) {
            String username = nameLabel.getText();
            String phone = phoneLabel.getText();
            String gender = maleRadio.isSelected() ? "Nam" : "Nữ";
            String birthDate = birthDatePicker.getValue() != null ? birthDatePicker.getValue().toString() : "";
            String password = passwordField.getText();

            User user = new User(username, password, phone, gender, birthDate);
            boolean isSaved = userService.saveUser(user);
            if (isSaved) {
                System.out.println("User information saved successfully.");
            } else {
                System.out.println("Failed to save user information.");
            }
        } else {
            System.out.println("Please correct invalid inputs.");
        }
    }

    private boolean isInputValid() {
        String errorMessage = "";
        
        if (nameLabel.getText() == null || nameLabel.getText().isEmpty()) {
            errorMessage += "Username cannot be empty.\n";
        }
        if (phoneLabel.getText() == null || !phoneLabel.getText().matches("\\d{10}")) {
            errorMessage += "Invalid phone number format. Must be 10 digits.\n";
        }
        if (passwordField.getText().isEmpty() || passwordField.getText().length() < 6) {
            errorMessage += "Password must be at least 6 characters.\n";
        }

        if (!errorMessage.isEmpty()) {
            System.out.println("Input validation errors:\n" + errorMessage);
            return false;
        }
        
        return true;
    }
}
