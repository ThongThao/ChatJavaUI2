package Service;

import model.User;

public class UserService {
    
    // Phương thức lưu thông tin người dùng
    public boolean saveUser(User user) {


        return true;
    }

    // Phương thức giả lập để lấy thông tin người dùng từ ID
    public User getUserById(int userId) {
        // Trả về một user giả lập với các thông tin mẫu
        return new User(userId, "testUser", "password", "0123456789", "Nam", "2000-01-01", null);
    }

    // Phương thức giả lập để lấy avatar từ ID
    public byte[] getUserAvatarById(int userId) {
        // Trả về null vì không có avatar mẫu
        // Trong thực tế, bạn sẽ thêm logic để lấy dữ liệu avatar từ cơ sở dữ liệu
        return null;
    }
}
