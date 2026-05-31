# Calculator

A desktop calculator built in Java with a Swing GUI. Supports the four basic operations, decimal input, backspace, and logs every completed calculation to a persistent history file.

## 📁 Project Structure

```
Calculadora/
├── src/calculadora/
│   └── Calculadora.java   → All logic: UI, event handling, calculation, file I/O
├── history.txt            → Auto-generated log of every completed operation
└── nbproject/             → NetBeans project config (build system, compiler settings)
```

## 🚀 How It Works

The UI is built with **Java Swing** — `JFrame` as the window, a `JTextField` as the display (non-editable, user can't type directly), and a `JPanel` with a `GridLayout` that arranges the buttons in a 5×4 grid. All buttons are generated from a single `String[]` array in a loop, so there's no repeated code per button.

### State management with arrays

Because the event listeners are lambdas (anonymous functions defined inside a loop), Java requires that any variable they reference be *effectively final* — meaning it can't be reassigned. The workaround is wrapping the mutable state in single-element arrays:

```java
double[] firstNumber = {0};
String[] operation = {""};
```

Modifying `firstNumber[0]` is allowed because the *array reference* never changes, only its contents. It's a common Java lambda workaround.

### Calculation logic

`calcular()` is a separate static method that takes two numbers and an operator string and returns the result. Keeping it separate from the event handling makes it independently testable and reusable:

```java
public static double calcular(double a, double b, String op) {
    switch (op) {
        case "+": return a + b;
        case "-": return a - b;
        case "x": return a * b;
        case "÷": return a / b;
    }
    return 0;
}
```

### Chained operations

Pressing an operator before `=` doesn't just store the operator — it also resolves any pending operation first. So `3 + 4 x` correctly computes `7` and then sets `x` as the next operator. This is what makes the calculator feel like a real one.

### History file with `FileWriter` + `PrintWriter`

Every time `=` is pressed and a valid result is produced, the operation is appended to `history.txt`:

```java
PrintWriter writer = new PrintWriter(new FileWriter("history.txt", true));
writer.println(firstNumber[0] + " " + operation[0] + " " + secondNumber + " = " + result);
writer.close();
```

The `true` in `FileWriter("history.txt", true)` is the append flag — without it, the file would be overwritten every time.

### Edge cases handled

- Pressing an operator with an empty display is ignored (no crash)
- Pressing `=` with no operator or no second number is ignored
- The decimal point `.` can only be added once per number
- Pressing `.` on an empty display inserts `0.` first

## 🧠 Key Concepts

- **Swing GUI** — `JFrame`, `JTextField`, `JPanel`, `GridLayout`, `BorderLayout`
- **ActionListener via lambda** — `button.addActionListener(e -> { ... })`
- **Effectively final workaround** — single-element arrays to hold mutable state in lambdas
- **Static utility method** — `calcular()` separated from UI logic
- **File I/O** — `FileWriter` in append mode + `PrintWriter` to log history
- **Chained operations** — resolving pending operations before storing the next operator

## 🛠️ How To Run

Built with **NetBeans** using **Java 25**. Open the project in NetBeans and run it, or build from the command line:

```bash
cd Calculadora
ant run
```

The `history.txt` file is created automatically in the project root on the first calculation.
