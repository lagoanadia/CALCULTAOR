package calculadora;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculadora {

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
        display.setText("Calculate!");

        // ============ Button panel ============
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 4));

        String[] labels = {
            "7", "8", "9", "÷",
            "4", "5", "6", "x",
            "1", "2", "3", "-",
            "C", "0", "<", "+"
        };

        for (String label : labels) {
            buttonPanel.add(new JButton(label));
        }

        // ============ Add components to window ============
        window.add(display, BorderLayout.NORTH);
        window.add(buttonPanel, BorderLayout.CENTER);
        
        // =========== Event Listeners ======================
        for (Component c : buttonPanel.getComponents()) 
        {
         JButton button = (JButton) c;
         button.addActionListener(e -> {
         display.setText(button.getText());
        });
}

        window.setVisible(true);
    }
}