import java.io.*;
import java.util.Base64;
import java.util.HashMap;

public class IDandPasswords implements IDandPasswordsInterface {
    private HashMap<String, String> logininfo = new HashMap<>();
    private String fileName = "C:\\Users\\Abyan\\Documents\\University_Files\\OOPLoginInfo.txt";


    // once the class is run its gonna check for the accounts that has been inside the system so there is less error
    public IDandPasswords() {

        loadAccounts();
    }

    private void loadAccounts() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                logininfo.put(parts[0], parts[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveAccounts() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (String userID : logininfo.keySet()) {
                writer.write(userID + "," + logininfo.get(userID));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public HashMap<String, String> getLoginInfo() {
        return logininfo;
    } // so this code is to make it easier to implement in other files

    @Override
    public void addUser(String userID, String password) {
        logininfo.put(userID, encryptPassword(password));
        saveAccounts();
    }

    private String encryptPassword(String password) {
        return Base64.getEncoder().encodeToString(password.getBytes());
    }

    private String decryptPassword(String encryptedPassword) {
        return new String(Base64.getDecoder().decode(encryptedPassword));
    }

    @Override
    //used in the login page to check if the ID and passwords match
    public boolean verifyPassword(String userID, String password) {
        if (logininfo.containsKey(userID)) {
            String encryptedPassword = logininfo.get(userID);
            String decryptedPassword = decryptPassword(encryptedPassword);
            return password.equals(decryptedPassword);
        }
        return false;
    }
}
