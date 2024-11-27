package model;

import java.util.Arrays;

public class User {
    private int id;
    private String username;
    private String phone;
    private String gender;
    private String birthDate;
    private byte[] avatar;
    private String password;

    // Constructor mặc định
    public User() {}

    // Constructor đầy đủ
    public User(int id, String username, String password, String phone, String gender, String birthDate, byte[] avatar) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.gender = gender;
        this.birthDate = birthDate;
        this.avatar = avatar;
    }

    // Constructor ngắn gọn
    public User(String username, String password, String phone, String gender, String birthDate) {
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.gender = gender;
        this.birthDate = birthDate;
    }

    // Getters và setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    // Phương thức kiểm tra avatar
    public boolean hasAvatar() {
        return avatar != null && avatar.length > 0;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", phone='" + phone + '\'' +
                ", gender='" + gender + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", avatar=" + (avatar != null ? "Yes" : "No") +
                '}';
    }
}
