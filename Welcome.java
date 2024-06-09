import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Welcome implements ActionListener {

    private JFrame frame;
    private JLabel welcomeLabel;
    private JComboBox<String> dayComboBox;
    private JTextField foodField;
    private JTextField calorieField;
    private JComboBox<String> mealComboBox;
    private DefaultTableModel tableModel;
    private String userID;

    private static final String FILE_PREFIX = "C:\\Users\\Abyan\\Documents\\University_Files\\CalorieIntake";


    public Welcome(String userID) {
        this.userID = userID;
        initialize();
        loadMealData();
    }

    JLabel FoodLabel = new JLabel("Food: ");
    JLabel CalorieLabel = new JLabel("Calorie: ");
    JLabel MealLabel = new JLabel("Meal: ");

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(470, 100, 1000, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        int frameWidth = 1000;
        int componentWidth = 400;
        int componentHeight = 40;
        int labelWidth = 75;
        int labelHeight = 25;
        int padding = 10;
        int startX = (frameWidth - componentWidth) / 2;

        // Day dropdown (JComboBox)
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        dayComboBox = new JComboBox<>(days);
        dayComboBox.setBounds(startX, 50, componentWidth, componentHeight);
        dayComboBox.setFont(new Font("Tahoma", Font.PLAIN, 18));
        frame.add(dayComboBox);

        // Food Label and TextField
        FoodLabel.setBounds(startX - labelWidth - padding, 110, labelWidth, labelHeight);
        FoodLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
        frame.add(FoodLabel);

        foodField = new JTextField();
        foodField.setBounds(startX, 110, componentWidth, componentHeight);
        frame.add(foodField);

        // Calorie Label and TextField
        CalorieLabel.setBounds(startX - labelWidth - padding, 170, labelWidth, labelHeight);
        CalorieLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
        frame.add(CalorieLabel);

        calorieField = new JTextField();
        calorieField.setBounds(startX, 170, componentWidth, componentHeight);
        frame.add(calorieField);

        // Meal Label and ComboBox
        MealLabel.setBounds(startX - labelWidth - padding, 230, labelWidth, labelHeight);
        MealLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
        frame.add(MealLabel);

        String[] meals = {"Breakfast", "Lunch", "Dinner"};
        mealComboBox = new JComboBox<>(meals);
        mealComboBox.setBounds(startX, 230, componentWidth, componentHeight);
        mealComboBox.setFont(new Font("Tahoma", Font.PLAIN, 18));
        frame.add(mealComboBox);

        // Add button to submit food and calorie
        JButton addButton = new JButton("Add");
        addButton.setBounds(startX, 290, componentWidth, componentHeight);
        addButton.addActionListener(this);
        frame.add(addButton);

        // Welcome label
        welcomeLabel = new JLabel("Count your daily intakes for today!");
        welcomeLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
        welcomeLabel.setBounds(startX - 50, 5, componentWidth + 100, 40);
        frame.getContentPane().add(welcomeLabel);

        // Table to display added meals
        String[] columnNames = {"Day", "Meal", "Food", "Calories"};
        tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 350, 900, 400);
        frame.getContentPane().add(scrollPane);

        // Frame settings
        frame.setVisible(true);
        frame.setName("Calorie Count");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String day = (String) dayComboBox.getSelectedItem();
        String meal = (String) mealComboBox.getSelectedItem();
        String food = foodField.getText();
        String calories = calorieField.getText();

        if (food.isEmpty() || calories.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter both food and calories.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Add the input data to the table
        tableModel.addRow(new Object[]{day, meal, food, calories});

        // Save the input data to a file
        saveMealData(day, meal, food, calories);

        // Clear input fields
        foodField.setText("");
        calorieField.setText("");
    }

    private void saveMealData(String day, String meal, String food, String calories) {
        try (FileWriter writer = new FileWriter(FILE_PREFIX + userID + ".txt", true)) {
            writer.write(day + "," + meal + "," + food + "," + calories + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadMealData() {
        File file = new File(FILE_PREFIX + userID + ".txt");
        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    tableModel.addRow(new Object[]{parts[0], parts[1], parts[2], parts[3]});
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
