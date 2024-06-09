import java.util.HashMap;

public interface IDandPasswordsInterface {
    HashMap<String, String> getLoginInfo();
    void addUser(String userID, String password);
    boolean verifyPassword(String userID, String password);
}
