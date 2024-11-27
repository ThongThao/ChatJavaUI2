package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import Service.ClientSocketHandler;

public class LoginController {

    private ClientSocketHandler client;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button submitButton;

    @FXML
    private Label loginLabel;

    public void initialize() {
        try {
            // Connect to the server when the application starts
            client = new ClientSocketHandler("127.0.0.1", 12345);
        } catch (IOException e) {
            showAlert("Error", "Cannot connect to server!");
        }
    }

    @FXML
    private void handleLoginButtonAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Send login credentials to the server
        client.sendMessage("LOGIN " + username + " " + password);

        try {
            String response = client.receiveMessage();
            if (response.equals("SUCCESS")) {
                showAlert("Success", "Login successful!");
                switchToChatScene();
            } else {
                showAlert("Error", "Invalid username or password!");
            }
        } catch (IOException e) {
            showAlert("Error", "Failed to receive response from server.");
        }
    }

    private void switchToChatScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/MainChat.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) submitButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Chat");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Could not load chat interface.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void navigateToRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Register.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Register");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}