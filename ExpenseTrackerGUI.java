// cd "Expense Tracker"
// javac ExpenseTrackerGUI.java
// java ExpenseTrackerGUI

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Expense {
    private double amount;
    private String category;
    private String date;

    public Expense(double amount, String category, String date) {
        this.amount = amount;
        this.category = category;
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

public class ExpenseTrackerGUI extends JFrame implements ActionListener {
    private List<Expense> expenses;
    private Map<String, Double> categoryTotals;
    private JTextField amountField, categoryField, dateField;
    private JTextArea outputArea;

    public ExpenseTrackerGUI() {
        expenses = new ArrayList<>();
        categoryTotals = new HashMap<>();

        setTitle("Expense Tracker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        JLabel amountLabel = new JLabel("Amount: ");
        amountField = new JTextField(10);

        JLabel categoryLabel = new JLabel("Category: ");
        categoryField = new JTextField(10);

        JLabel dateLabel = new JLabel("Date: ");
        dateField = new JTextField(10);

        JButton addExpenseButton = new JButton("Add Expense");
        addExpenseButton.addActionListener(this);

        JButton categorizeButton = new JButton("Categorize");
        categorizeButton.addActionListener(this);

        JButton visualizeButton = new JButton("Visualize");
        visualizeButton.addActionListener(this);

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(this);

        outputArea = new JTextArea(20, 30);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        add(amountLabel);
        add(amountField);
        add(categoryLabel);
        add(categoryField);
        add(dateLabel);
        add(dateField);
        add(addExpenseButton);
        add(categorizeButton);
        add(visualizeButton);
        add(clearButton);
        add(scrollPane);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        JPanel mainPanel = (JPanel) getContentPane();
        mainPanel.setBackground(new Color(0xADD8E6));

    }

    public void addExpense(Expense expense) {
        expenses.add(expense);
    }

    public void categorizeExpenses() {
        categoryTotals.clear();
        for (Expense expense : expenses) {
            String category = expense.getCategory();
            double amount = expense.getAmount();

            categoryTotals.put(category, categoryTotals.getOrDefault(category, 0.0) + amount);
        }

        outputArea.append("Expense categorization:\n");
        for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
            outputArea.append(entry.getKey() + ": $" + entry.getValue() + "\n");
        }
        outputArea.append("\n");
    }

    public void visualizeSpendingHabits() {
        outputArea.append("Spending habits visualization:\n");
        for (Expense expense : expenses) {
            outputArea.append(expense.getDate() + " - " + expense.getCategory() + ": $" + expense.getAmount() + "\n");
        }
        outputArea.append("\n");
    }

    public void clearOutputArea() {
        outputArea.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Add Expense")) {
            try {
                double amount = Double.parseDouble(amountField.getText());
                String category = categoryField.getText();
                String date = dateField.getText();
                Expense expense = new Expense(amount, category, date);
                addExpense(expense);
                outputArea.append("Expense added successfully.\n");
            } catch (NumberFormatException ex) {
                outputArea.append("Invalid amount.\n");
            }

            amountField.setText("");
            categoryField.setText("");
            dateField.setText("");
        } else if (e.getActionCommand().equals("Categorize")) {
            categorizeExpenses();
        } else if (e.getActionCommand().equals("Visualize")) {
            visualizeSpendingHabits();
        } else if (e.getActionCommand().equals("Clear")) {
            clearOutputArea();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ExpenseTrackerGUI();
            }
        });
    }
}