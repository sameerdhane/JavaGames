import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GuessTheNumberGame extends JFrame {
    private int randomNumber;
    private int guessCount;

    private JTextField guessField;
    private JTextArea resultArea;

    public GuessTheNumberGame() {
        setTitle("Guess the Number Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        randomNumber = (int) (Math.random() * 100) + 1;
        guessCount = 0;

        JLabel guessLabel = new JLabel("Enter your guess (1-100): ");
        guessField = new JTextField(10);
        guessField.addActionListener(new GuessListener());

        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);

        add(guessLabel);
        add(guessField);
        add(resultArea);

        pack();
        setVisible(true);
    }

    class GuessListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int guess = Integer.parseInt(guessField.getText());
                guessCount++;

                if (guess == randomNumber) {
                    resultArea.append("Congratulations! You've guessed the number " + randomNumber +
                            " in " + guessCount + " attempts.\n");
                    guessField.setEditable(false);
                } else if (guess < randomNumber) {
                    resultArea.append("Too low. Try again.\n");
                } else {
                    resultArea.append("Too high. Try again.\n");
                }

                guessField.setText("");
            } catch (NumberFormatException ex) {
                resultArea.append("Please enter a valid number.\n");
            }

            guessField.requestFocus();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GuessTheNumberGame::new);
    }
}
