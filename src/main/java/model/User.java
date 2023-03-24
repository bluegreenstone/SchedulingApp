package model;

public class User {

    private static int userId;

    public User(int userId) {
        this.userId = userId;
    }
    public static int getUserId() {
        return userId;
    }
}
