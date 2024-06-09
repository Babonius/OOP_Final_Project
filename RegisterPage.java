import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class RegisterPage implements ActionListener, RegisterInterface {
    JFrame frame = new JFrame();
    JButton registerButton = new JButton("Register");
    JButton backButton = new JButton("Back");
    JTextField userIDField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JLabel userIDLabel = new JLabel("UserID: ");
    JLabel passwordLabel = new JLabel("Password: ");
    JLabel messageLabel = new JLabel();

    private IDandPasswordsInterface idAndPasswords;

    public RegisterPage(IDandPasswordsInterface idAndPasswords) {
        this.idAndPasswords = idAndPasswords;

        userIDLabel.setBounds(50, 100, 75, 25);
        passwordLabel.setBounds(50, 150, 75, 25);
        messageLabel.setBounds(125, 250, 250, 35);
        userIDField.setBounds(125, 100, 200, 25);
        passwordField.setBounds(125, 150, 200, 25);
        registerButton.setBounds(125, 200, 100, 25);
        backButton.setBounds(225, 200, 100, 25);

        frame.add(userIDLabel);
        frame.add(passwordLabel);
        frame.add(messageLabel);
        frame.add(userIDField);
        frame.add(passwordField);
        frame.add(registerButton);
        frame.add(backButton);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(420, 420);
        //null is used so that we set the GUI manually
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setBounds(730, 350, 500, 400);


        registerButton.addActionListener(this);
        backButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registerButton) {
            register(userIDField.getText(), String.valueOf(passwordField.getPassword()));
        }
        if (e.getSource() == backButton) {
            back();
        }
    }

    @Override
    public void register(String userID, String password) {
        if (!idAndPasswords.getLoginInfo().containsKey(userID)) {
            idAndPasswords.addUser(userID, password);
            messageLabel.setText("User registered successfully");
        } else {
            messageLabel.setText("UserID already exists");
        }
    }

    @Override
    public void back() {
        frame.dispose();
        new LoginPage(idAndPasswords);
    }
}
