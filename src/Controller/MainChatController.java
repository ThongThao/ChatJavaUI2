package Controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MainChatController {
    
    @FXML
    private ImageView avatarImageView;
    @FXML
    private ImageView group;
    public void initialize() {
        // Gán sự kiện click cho avatarImageView
        avatarImageView.setOnMouseClicked(this::handleAvatarClick);
        group.setOnMouseClicked(this::handleGroupClick);
    }

    private void handleAvatarClick(MouseEvent event) {
        try {
            // Tải trang mới
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/project.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) avatarImageView.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void handleGroupClick(MouseEvent event) {
        try {
            // Tải trang mới
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Group.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) group.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

