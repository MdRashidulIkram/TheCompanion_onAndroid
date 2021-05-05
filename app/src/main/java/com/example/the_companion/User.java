package com.example.the_companion;

public class User {
    private String username;
    private String password;
    private String profilePic;
    private String userId;

    public String getUsername() {
        return username;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, String userId) {
        this.username = username;
        this.password = password;
        this.userId = userId;
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

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public User(String username, String password, String profilePic, String userId) {
        this.username = username;
        this.password = password;
        this.profilePic = profilePic;
        this.userId = userId;
    }
}
