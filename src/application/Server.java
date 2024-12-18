package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Server extends Application {
	@Override
	public void start(Stage primaryStage) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("server.fxml"));
        Parent root = loader.load();
			primaryStage.setTitle("ChatJavaUI");
			primaryStage.setScene(new Scene(root));
			primaryStage.show();
		}
	
	public static void main(String[] args) {
		launch(args);
	}
}
