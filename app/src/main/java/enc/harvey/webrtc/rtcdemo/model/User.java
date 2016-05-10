package enc.harvey.webrtc.rtcdemo.model;

public class User {

    private static User mInstance = null;

    private int userId;
    private String userName;
    private String password;
    private String registrationId;


    public User() {
    }

    public static User getInstance() {
        if (mInstance == null) {
            mInstance = new User();
        }
        return mInstance;
    }

    public User(int userId, String userName, String password, String registrationId) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.registrationId = registrationId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }
}
