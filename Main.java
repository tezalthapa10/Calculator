import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Abstract class for calculator buttons
abstract class CalculatorButton extends JButton {
    public CalculatorButton(String label) {
        super(label);
        setPreferredSize(new Dimension(70,70));
    }

    // Abstract method to handle button click
    public abstract void onClick(Calculator calculator);
}

// Digit button implementation
class DigitButton extends CalculatorButton {
    private int digit;

    public DigitButton(int digit) {
        super(Integer.toString(digit));
        this.digit = digit;
    }

    public void onClick(Calculator calculator) {
        calculator.appendDigit(digit);
    }
}

// Arithmetic operation button implementation
class OperationButton extends CalculatorButton {
    private char operation;

    public OperationButton(char operation) {
        super(Character.toString(operation));
        this.operation = operation;
    }

    public void onClick(Calculator calculator) {
        calculator.setOperation(operation);
    }
}

// Equality button implementation
class EqualityButton extends CalculatorButton {
    public EqualityButton() {
        super("=");
    }

    public void onClick(Calculator calculator) {
        calculator.calculate();
    }
}

// Clear button implementation
class ClearButton extends CalculatorButton {
    public ClearButton() {
        super("C");
    }

    public void onClick(Calculator calculator) {
        calculator.clear();
    }
}

// Main calculator class
class Calculator {
    private JFrame frame;
    private JTextField display;
    private int result;
    private int currentNumber;
    private char currentOperation;

    public Calculator() {
        // Create the calculator frame
        frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Create the display text field
        display = new JTextField();
        display.setEditable(false);
        frame.add(display, BorderLayout.NORTH);

        // Create the buttons panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(4, 4));

        // Add buttons to the panel
        addButton(new DigitButton(7), buttonsPanel);
        addButton(new DigitButton(8), buttonsPanel);
        addButton(new DigitButton(9), buttonsPanel);
        addButton(new OperationButton('/'), buttonsPanel);
        addButton(new DigitButton(4), buttonsPanel);
        addButton(new DigitButton(5), buttonsPanel);
        addButton(new DigitButton(6), buttonsPanel);
        addButton(new OperationButton('*'), buttonsPanel);
        addButton(new DigitButton(1), buttonsPanel);
        addButton(new DigitButton(2), buttonsPanel);
        addButton(new DigitButton(3), buttonsPanel);
        addButton(new OperationButton('-'), buttonsPanel);
        addButton(new DigitButton(0), buttonsPanel);
        addButton(new OperationButton('+'), buttonsPanel);
        addButton(new EqualityButton(), buttonsPanel);
        addButton(new ClearButton(), buttonsPanel);

        // Add the buttons panel to the frame
        frame.add(buttonsPanel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }

    private void addButton(CalculatorButton button, JPanel panel) {
        // Attach ActionListener to handle button clicks
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                button.onClick(Calculator.this);
            }
        });
        panel.add(button);
    }

    public void appendDigit(int digit) {
        // Append the pressed digit to the current number and update the display
        currentNumber = currentNumber * 10 + digit;
        display.setText(Integer.toString(currentNumber));
    }

    public void setOperation(char operation) {
        // Set the current operation, store the current number as the result,
        // reset the current number, and update the display with the operation symbol
        result = currentNumber;
        currentNumber = 0;
        currentOperation = operation;
        display.setText(Character.toString(operation));
    }

    public void calculate() {
        // Perform the calculation based on the current operation
        switch (currentOperation) {
            case '+':
                result += currentNumber;
                break;
            case '-':
                result -= currentNumber;
                break;
            case '*':
                result *= currentNumber;
                break;
            case '/':
                if (currentNumber != 0) {
                    result /= currentNumber;
                }
                break;
        }
        currentNumber = result;
        display.setText(Integer.toString(result));
    }

    public void clear() {
        // Clear the calculator's state and update the display
        result = 0;
        currentNumber = 0;
        currentOperation = '\u0000';
        display.setText("");
    }
}

public class Main {
    public static void main(String[] args) {
        // Create an instance of the calculator
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Calculator();
            }
        });
    }
}
