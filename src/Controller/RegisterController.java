package Controller;



import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;
    
    @FXML
    private TextField confirmpasswordField;

    @FXML
    private Button submitButton;

    @FXML
    private Label loginLabel;

 // Kết nối với cơ sở dữ liệu
    private Connection connect() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Nạp lớp driver MySQL
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Driver MySQL không tìm thấy.");
        }
        
        String url = "jdbc:mysql://localhost:3306/chatjava?useSSL=false&serverTimezone=UTC"; // Đường dẫn tới cơ sở dữ liệu
        String user = "root"; // Tài khoản MySQL
        String password = ""; // Mật khẩu MySQL
        return DriverManager.getConnection(url, user, password);
    }

    
    @FXML
    private void navigateToLogin() {
        try {
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSubmitButtonAction() {
        // Lấy dữ liệu từ các trường nhập liệu
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmpasswordField.getText();
        
        // Kiểm tra đầu vào
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            System.out.println("Username và Password không được để trống!");
            return;
        }
        
        // Kiểm tra mật khẩu và xác nhận mật khẩu
        if (!password.equals(confirmPassword)) {
            System.out.println("Mật khẩu xác nhận không khớp!");
            return;
        }

        // Lưu thông tin đăng ký vào cơ sở dữ liệu
        try (Connection conn = connect()) {
            String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password); // Bạn nên mã hóa mật khẩu trước khi lưu
            pstmt.executeUpdate();
            System.out.println("Đăng ký thành công cho người dùng: " + username);
        } catch (SQLException e) {
            System.out.println("Lỗi khi lưu thông tin người dùng!");
            e.printStackTrace();
        }

        // Reset các trường nhập liệu
        usernameField.clear();
        passwordField.clear();
        confirmpasswordField.clear();
    }
}
