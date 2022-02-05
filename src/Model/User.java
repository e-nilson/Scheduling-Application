package Model;

/**
 * Class for the user model.
 *
 * @author Erik Nilson
 */
public class User {
    int userId;
    String username;
    String password;

    /**
     * User constructors.
     */
    public User(int userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }

    /**
     * Getter for User ID.
     *
     * @return the userId.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Setter for User ID.
     *
     * @param userId to set.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Getter for username.
     *
     * @return the username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter for username.
     *
     * @param username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter for password.
     *
     * @return the password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for password.
     *
     * @param password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString(){
        return ("Username: " +(username )+ " User ID: " + userId);
    }
}
