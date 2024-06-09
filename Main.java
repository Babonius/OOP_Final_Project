public class Main {
    public static void main(String[] args) {
        IDandPasswordsInterface idAndPasswords = new IDandPasswords();
        new LoginPage(idAndPasswords);
    }
}
