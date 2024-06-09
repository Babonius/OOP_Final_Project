import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class LoginPage implements ActionListener, LoginInterface {
    JFrame frame = new JFrame();
    JButton loginButton = new JButton("Login");
    JButton resetButton = new JButton("Reset");
    JButton registerButton = new JButton("Register");
    JTextField userIDField = new JTextField();
    JPasswordField userPasswordField = new JPasswordField();
    JLabel userIDLabel = new JLabel("UserID: ");
    JLabel userPasswordLabel = new JLabel("Password: ");
    JLabel messageLabel = new JLabel();

    JLabel Welcome = new JLabel("Welcome to your Calorie NoteBook! ");

    private IDandPasswordsInterface idAndPasswords;

    public LoginPage(IDandPasswordsInterface idAndPasswords) {
        //to get the methods from ID and passwords
        this.idAndPasswords = idAndPasswords;

        frame.setName("Calorie Count Login");

        userIDLabel.setBounds(50, 100, 75, 25);
        userPasswordLabel.setBounds(50, 150, 75, 25);
        messageLabel.setBounds(125, 250, 250, 35);
        userIDField.setBounds(125, 100, 200, 25);
        userPasswordField.setBounds(125, 150, 200, 25);
        loginButton.setBounds(125, 200, 100, 25);
        resetButton.setBounds(250, 200, 100, 25);
        registerButton.setBounds(375, 200, 100, 25);
        Welcome.setBounds(50, 15, 400,100);

        frame.add(userIDLabel);
        frame.add(userPasswordLabel);
        frame.add(messageLabel);
        frame.add(userIDField);
        frame.add(userPasswordField);
        frame.add(loginButton);
        frame.add(resetButton);
        frame.add(registerButton);
        frame.add(Welcome);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setBounds(730, 350, 500, 400);

        loginButton.addActionListener(this);
        resetButton.addActionListener(this);
        registerButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            login(userIDField.getText(), String.valueOf(userPasswordField.getPassword()));
        }
        if (e.getSource() == resetButton) {
            reset();
        }
        if (e.getSource() == registerButton) {
            openRegisterPage();
        }
    }

    @Override
    public void login(String userID, String password) {
        if (idAndPasswords.getLoginInfo().containsKey(userID)) {
            if (idAndPasswords.verifyPassword(userID, password)) {
                messageLabel.setText("Login successful");
                frame.dispose();
                new Welcome(userID);
            } else {
                messageLabel.setText("Wrong password");
            }
        } else {
            messageLabel.setText("Username not found");
        }
    }

    @Override
    public void reset() {
        userIDField.setText("");
        userPasswordField.setText("");
    }

    @Override
    public void openRegisterPage() {
        frame.dispose();
        new RegisterPage(idAndPasswords);
    }
}
