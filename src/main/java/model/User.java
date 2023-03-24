package model;

public class User {

    private static int userId;

    /**
     * This is the constructor.
     * @param userId
     */
    public User(int userId) {
        this.userId = userId;
    }
    public static int getUserId() {
        return userId;
    }
}
