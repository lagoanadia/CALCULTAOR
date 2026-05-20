package calculadora;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Calculadora {
    public static double calcular(double a, double b, String op) {
    switch (op) {
        case "+": return a + b;
        case "-": return a - b;
        case "x": return a * b;
        case "÷": return a / b;
    }
    return 0;
}

    public static void main(String[] args) {

        // ============ Window configuration ============
        JFrame window = new JFrame("Calculator");
        window.setSize(300, 400);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(new BorderLayout());

        // ============ Display ============
        JTextField display = new JTextField();
        display.setEditable(false);
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setFont(new Font("Arial", Font.BOLD, 24));
        display.setText("");

        // ============ Button panel ============
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4));

        String[] labels = {
            "7", "8", "9", "÷",
            "4", "5", "6", "x",
            "1", "2", "3", "-",
            "C", "0", "<", "+",
            ".", "="
        };

        for (String label : labels) {
            buttonPanel.add(new JButton(label));
        }

        // ============ Add components to window ============
        window.add(display, BorderLayout.NORTH);
        window.add(buttonPanel, BorderLayout.CENTER);

        // ============ State variables ============
        double[] firstNumber = {0};
        String[] operation = {""};

        // ============ Event Listeners ============
        for (Component c : buttonPanel.getComponents()) {
            JButton button = (JButton) c;
            button.addActionListener(e -> {
                 

                switch (button.getText()) {

                    case "+": case "-": case "x": case "÷":
//              ========= Shield in case user presses operator after start or wants to change operator =========
                        if (display.getText().equals("")) {
                            operation[0] = button.getText();
                            break;
                        }
                        double currentNumber = Double.parseDouble(display.getText());

                        if (!operation[0].equals("")) {
                            firstNumber[0] = calcular(firstNumber[0], currentNumber, operation[0]);
                        } else {
                            firstNumber[0] = currentNumber;
                        }

                        operation[0] = button.getText();
                        display.setText("");
                        break;

                    // === Equals: final calculation + write to history ===
                    case "=":
                        if (operation[0].equals("") || display.getText().equals("")) break;
                        double secondNumber = Double.parseDouble(display.getText());
                        double result = calcular(firstNumber[0], secondNumber, operation[0]);
                           
                        // ===== Write the operation to history.txt =====
                        try 
                        {
                            PrintWriter writer = new PrintWriter(new FileWriter("history.txt", true));
                            writer.println(firstNumber[0] + " " + operation[0] + " " + secondNumber + " = " + result);
                            writer.close();
                        }
                        catch (IOException ex) 
                        {
                            ex.printStackTrace();
                        }
                        // ===============================================

                        display.setText(String.valueOf(result));
                        firstNumber[0] = result;
                        operation[0] = "";
                        break;

                    // === Clear everything ===
                    case "C":
                        display.setText("");
                        firstNumber[0] = 0;
                        operation[0] = "";
                        break;

                    // === Backspace ===
                    case "<":
                        String text = display.getText();
                        if (text.length() > 0) {
                             display.setText(text.substring(0, text.length() - 1));
                        }
                        break;

                    // === Decimal point (only one allowed) ===
                    case ".":
                        if (!display.getText().contains(".")) {
                            if (display.getText().equals("")) {
                                display.setText("0.");
                            } else {
                                display.setText(display.getText() + ".");
                            }
                        }
                        break;

                    // === Numbers 0-9 ===
                    default:
                        display.setText(display.getText() + button.getText());
                        break;
                }
            });
        }

        // ============ Show the window LAST ============
        window.setVisible(true);
    }
}