import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class Main extends JFrame {

    // =========================
    // USER DETAILS
    // =========================
    private String username = "student";
    private String password = "1234";
    private String displayName = "Student";

    // =========================
    // GUI
    // =========================
    private CardLayout cardLayout;
    private JPanel mainPanel;

    // =========================
    // LOGIN
    // =========================
    private JTextField usernameField;
    private JPasswordField passwordField;

    // =========================
    // PROFILE
    // =========================
    private JTextField displayNameField;
    private JPasswordField newPasswordField;

    // =========================
    // EXAM
    // =========================
    private List<Question> questions;
    private int currentQuestion = 0;

    private int[] selectedAnswers;

    private JLabel questionNumberLabel;
    private JLabel questionLabel;
    private JLabel timerLabel;

    private JRadioButton optionA;
    private JRadioButton optionB;
    private JRadioButton optionC;
    private JRadioButton optionD;

    private ButtonGroup optionGroup;

    private Timer timer;

    // 30 minutes = 1800 seconds
    private int remainingSeconds = 30 * 60;

    private long examStartTime;

    // =========================
    // RESULT
    // =========================
    private JTextArea resultArea;

    // =========================
    // SCREEN NAMES
    // =========================
    private static final String LOGIN = "LOGIN";
    private static final String PROFILE = "PROFILE";
    private static final String EXAM = "EXAM";
    private static final String RESULT = "RESULT";

    // =========================
    // CONSTRUCTOR
    // =========================
    public Main() {

        setTitle("Online Examination System");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        questions = createQuestions();
        selectedAnswers = new int[questions.size()];

        for (int i = 0; i < selectedAnswers.length; i++) {
            selectedAnswers[i] = -1;
        }

        mainPanel.add(createLoginPanel(), LOGIN);
        mainPanel.add(createProfilePanel(), PROFILE);
        mainPanel.add(createExamPanel(), EXAM);
        mainPanel.add(createResultPanel(), RESULT);

        add(mainPanel);

        // Window close handling
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {

                String currentCard = getCurrentCard();

                if (EXAM.equals(currentCard)) {

                    int choice = JOptionPane.showConfirmDialog(
                            Main.this,
                            "Are you sure you want to quit the exam?",
                            "Confirm Exit",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE
                    );

                    if (choice == JOptionPane.YES_OPTION) {
                        stopTimer();
                        System.exit(0);
                    }

                } else {

                    System.exit(0);
                }
            }
        });

        cardLayout.show(mainPanel, LOGIN);
    }

    // =========================
    // LOGIN PANEL
    // =========================
    private JPanel createLoginPanel() {

        JPanel panel = new JPanel(new GridBagLayout());

        JPanel box = new JPanel(new GridBagLayout());
        box.setBorder(BorderFactory.createTitledBorder("Student Login"));

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("ONLINE EXAMINATION SYSTEM");
        title.setFont(new Font("Arial", Font.BOLD, 24));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        box.add(title, gbc);

        // Username
        gbc.gridwidth = 1;
        gbc.gridy++;

        box.add(new JLabel("Username:"), gbc);

        usernameField = new JTextField(15);

        gbc.gridx = 1;
        box.add(usernameField, gbc);

        // Password
        gbc.gridx = 0;
        gbc.gridy++;

        box.add(new JLabel("Password:"), gbc);

        passwordField = new JPasswordField(15);

        gbc.gridx = 1;
        box.add(passwordField, gbc);

        // Login button
        JButton loginButton = new JButton("Login");

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;

        box.add(loginButton, gbc);

        // Demo credentials
        JLabel demo = new JLabel(
                "Demo Login: student / 1234"
        );

        demo.setForeground(Color.GRAY);

        gbc.gridy++;

        box.add(demo, gbc);

        loginButton.addActionListener(e -> login());

        passwordField.addActionListener(e -> login());

        panel.add(box);

        return panel;
    }

    // =========================
    // LOGIN METHOD
    // =========================
    private void login() {

        String enteredUsername = usernameField.getText();
        String enteredPassword =
                new String(passwordField.getPassword());

        if (enteredUsername.equals(username)
                && enteredPassword.equals(password)) {

            displayNameField.setText(displayName);
            newPasswordField.setText("");

            cardLayout.show(mainPanel, PROFILE);

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid username or password!",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================
    // PROFILE PANEL
    // =========================
    private JPanel createProfilePanel() {

        JPanel panel = new JPanel(new GridBagLayout());

        JPanel box = new JPanel(new GridBagLayout());

        box.setBorder(
                BorderFactory.createTitledBorder(
                        "Update Profile"
                )
        );

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title =
                new JLabel("Update Your Profile");

        title.setFont(
                new Font("Arial", Font.BOLD, 22)
        );

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        box.add(title, gbc);

        // Display name
        gbc.gridwidth = 1;
        gbc.gridy++;

        box.add(
                new JLabel("Display Name:"),
                gbc
        );

        displayNameField = new JTextField(15);

        gbc.gridx = 1;

        box.add(
                displayNameField,
                gbc
        );

        // Password
        gbc.gridx = 0;
        gbc.gridy++;

        box.add(
                new JLabel("New Password:"),
                gbc
        );

        newPasswordField =
                new JPasswordField(15);

        gbc.gridx = 1;

        box.add(
                newPasswordField,
                gbc
        );

        // Start exam
        JButton startButton =
                new JButton("Start Exam");

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;

        box.add(
                startButton,
                gbc
        );

        startButton.addActionListener(
                e -> startExam()
        );

        panel.add(box);

        return panel;
    }

    // =========================
    // START EXAM
    // =========================
    private void startExam() {

        String newName =
                displayNameField.getText().trim();

        String newPassword =
                new String(
                        newPasswordField.getPassword()
                );

        if (!newName.isEmpty()) {
            displayName = newName;
        }

        if (!newPassword.isEmpty()) {
            password = newPassword;
        }

        currentQuestion = 0;

        for (int i = 0; i < selectedAnswers.length; i++) {
            selectedAnswers[i] = -1;
        }

        remainingSeconds = 30 * 60;

        examStartTime =
                System.currentTimeMillis();

        loadQuestion();

        startTimer();

        cardLayout.show(mainPanel, EXAM);
    }

    // =========================
    // EXAM PANEL
    // =========================
    private JPanel createExamPanel() {

        JPanel panel =
                new JPanel(new BorderLayout(10, 10));

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        15, 15, 15, 15
                )
        );

        // Top section
        JPanel topPanel =
                new JPanel(new BorderLayout());

        questionNumberLabel =
                new JLabel("Question 1");

        questionNumberLabel.setFont(
                new Font("Arial", Font.BOLD, 18)
        );

        timerLabel =
                new JLabel("Time Remaining: 30:00");

        timerLabel.setFont(
                new Font("Arial", Font.BOLD, 18)
        );

        topPanel.add(
                questionNumberLabel,
                BorderLayout.WEST
        );

        topPanel.add(
                timerLabel,
                BorderLayout.EAST
        );

        panel.add(
                topPanel,
                BorderLayout.NORTH
        );

        // Center section
        JPanel questionPanel =
                new JPanel();

        questionPanel.setLayout(
                new BoxLayout(
                        questionPanel,
                        BoxLayout.Y_AXIS
                )
        );

        questionLabel =
                new JLabel();

        questionLabel.setFont(
                new Font("Arial", Font.BOLD, 18)
        );

        questionLabel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        questionPanel.add(questionLabel);

        questionPanel.add(
                Box.createVerticalStrut(25)
        );

        optionA =
                new JRadioButton();

        optionB =
                new JRadioButton();

        optionC =
                new JRadioButton();

        optionD =
                new JRadioButton();

        optionA.setFont(
                new Font("Arial", Font.PLAIN, 16)
        );

        optionB.setFont(
                new Font("Arial", Font.PLAIN, 16)
        );

        optionC.setFont(
                new Font("Arial", Font.PLAIN, 16)
        );

        optionD.setFont(
                new Font("Arial", Font.PLAIN, 16)
        );

        optionGroup = new ButtonGroup();

        optionGroup.add(optionA);
        optionGroup.add(optionB);
        optionGroup.add(optionC);
        optionGroup.add(optionD);

        questionPanel.add(optionA);
        questionPanel.add(Box.createVerticalStrut(10));

        questionPanel.add(optionB);
        questionPanel.add(Box.createVerticalStrut(10));

        questionPanel.add(optionC);
        questionPanel.add(Box.createVerticalStrut(10));

        questionPanel.add(optionD);

        panel.add(
                questionPanel,
                BorderLayout.CENTER
        );

        // Bottom buttons
        JPanel bottomPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                15,
                                10
                        )
                );

        JButton previousButton =
                new JButton("Previous");

        JButton nextButton =
                new JButton("Next");

        JButton submitButton =
                new JButton("Submit Exam");

        bottomPanel.add(previousButton);
        bottomPanel.add(nextButton);
        bottomPanel.add(submitButton);

        panel.add(
                bottomPanel,
                BorderLayout.SOUTH
        );

        previousButton.addActionListener(
                e -> {

                    saveCurrentAnswer();

                    if (currentQuestion > 0) {
                        currentQuestion--;
                        loadQuestion();
                    }
                }
        );

        nextButton.addActionListener(
                e -> {

                    saveCurrentAnswer();

                    if (currentQuestion <
                            questions.size() - 1) {

                        currentQuestion++;

                        loadQuestion();

                    } else {

                        JOptionPane.showMessageDialog(
                                this,
                                "This is the last question."
                        );
                    }
                }
        );

        submitButton.addActionListener(
                e -> confirmSubmit()
        );

        return panel;
    }

    // =========================
    // LOAD QUESTION
    // =========================
    private void loadQuestion() {

        Question q =
                questions.get(currentQuestion);

        questionNumberLabel.setText(
                "Question "
                        + (currentQuestion + 1)
                        + " of "
                        + questions.size()
        );

        questionLabel.setText(
                "<html><body style='width:650px'>"
                        + q.question
                        + "</body></html>"
        );

        optionA.setText("A. " + q.options[0]);
        optionB.setText("B. " + q.options[1]);
        optionC.setText("C. " + q.options[2]);
        optionD.setText("D. " + q.options[3]);

        optionGroup.clearSelection();

        int savedAnswer =
                selectedAnswers[currentQuestion];

        if (savedAnswer == 0) {
            optionA.setSelected(true);
        } else if (savedAnswer == 1) {
            optionB.setSelected(true);
        } else if (savedAnswer == 2) {
            optionC.setSelected(true);
        } else if (savedAnswer == 3) {
            optionD.setSelected(true);
        }
    }

    // =========================
    // SAVE ANSWER
    // =========================
    private void saveCurrentAnswer() {

        if (optionA.isSelected()) {
            selectedAnswers[currentQuestion] = 0;

        } else if (optionB.isSelected()) {
            selectedAnswers[currentQuestion] = 1;

        } else if (optionC.isSelected()) {
            selectedAnswers[currentQuestion] = 2;

        } else if (optionD.isSelected()) {
            selectedAnswers[currentQuestion] = 3;

        } else {
            selectedAnswers[currentQuestion] = -1;
        }
    }

    // =========================
    // TIMER
    // =========================
    private void startTimer() {

        stopTimer();

        timer =
                new Timer(
                        1000,
                        e -> updateTimer()
                );

        timer.start();

        updateTimer();
    }

    private void updateTimer() {

        int minutes =
                remainingSeconds / 60;

        int seconds =
                remainingSeconds % 60;

        timerLabel.setText(
                String.format(
                        "Time Remaining: %02d:%02d",
                        minutes,
                        seconds
                )
        );

        if (remainingSeconds <= 0) {

            stopTimer();

            JOptionPane.showMessageDialog(
                    this,
                    "Time is over! Your exam will be submitted automatically.",
                    "Time Up",
                    JOptionPane.INFORMATION_MESSAGE
            );

            submitExam();

        } else {

            remainingSeconds--;
        }
    }

    private void stopTimer() {

        if (timer != null) {
            timer.stop();
        }
    }

    // =========================
    // SUBMIT CONFIRMATION
    // =========================
    private void confirmSubmit() {

        saveCurrentAnswer();

        int unanswered = 0;

        for (int answer : selectedAnswers) {

            if (answer == -1) {
                unanswered++;
            }
        }

        String message =
                "Are you sure you want to submit the exam?\n\n"
                        + "Unanswered questions: "
                        + unanswered;

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        message,
                        "Confirm Submission",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

        if (choice == JOptionPane.YES_OPTION) {
            submitExam();
        }
    }

    // =========================
    // SUBMIT EXAM
    // =========================
    private void submitExam() {

        saveCurrentAnswer();

        stopTimer();

        int score = 0;

        int correct = 0;
        int incorrect = 0;
        int unanswered = 0;

        StringBuilder breakdown =
                new StringBuilder();

        for (int i = 0; i < questions.size(); i++) {

            Question q = questions.get(i);

            int selected =
                    selectedAnswers[i];

            if (selected == -1) {

                unanswered++;

                breakdown.append(
                        "Question "
                                + (i + 1)
                                + " - Not Answered\n"
                );

            } else if (
                    selected == q.correctAnswer
            ) {

                score++;
                correct++;

                breakdown.append(
                        "Question "
                                + (i + 1)
                                + " - Correct\n"
                );

            } else {

                incorrect++;

                breakdown.append(
                        "Question "
                                + (i + 1)
                                + " - Incorrect\n"
                );
            }
        }

        long elapsedMillis =
                System.currentTimeMillis()
                        - examStartTime;

        long elapsedSeconds =
                elapsedMillis / 1000;

        long minutes =
                elapsedSeconds / 60;

        long seconds =
                elapsedSeconds % 60;

        String resultText =
                "ONLINE EXAMINATION RESULT\n"
                        + "============================\n\n"
                        + "Student: "
                        + displayName
                        + "\n\n"
                        + "Score: "
                        + score
                        + " out of "
                        + questions.size()
                        + "\n\n"
                        + "Correct Answers: "
                        + correct
                        + "\n"
                        + "Incorrect Answers: "
                        + incorrect
                        + "\n"
                        + "Not Answered: "
                        + unanswered
                        + "\n\n"
                        + "Time Taken: "
                        + minutes
                        + " minutes "
                        + seconds
                        + " seconds\n\n"
                        + "BREAKDOWN\n"
                        + "----------------------------\n"
                        + breakdown;

        resultArea.setText(resultText);

        cardLayout.show(
                mainPanel,
                RESULT
        );
    }

    // =========================
    // RESULT PANEL
    // =========================
    private JPanel createResultPanel() {

        JPanel panel =
                new JPanel(new BorderLayout(10, 10));

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 20, 20, 20
                )
        );

        JLabel title =
                new JLabel(
                        "EXAM RESULT",
                        SwingConstants.CENTER
                );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        26
                )
        );

        panel.add(
                title,
                BorderLayout.NORTH
        );

        resultArea =
                new JTextArea();

        resultArea.setEditable(false);

        resultArea.setFont(
                new Font(
                        "Monospaced",
                        Font.PLAIN,
                        15
                )
        );

        resultArea.setMargin(
                new Insets(10, 10, 10, 10)
        );

        JScrollPane scrollPane =
                new JScrollPane(resultArea);

        panel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        JButton logoutButton =
                new JButton("Logout");

        JPanel bottom =
                new JPanel();

        bottom.add(logoutButton);

        panel.add(
                bottom,
                BorderLayout.SOUTH
        );

        logoutButton.addActionListener(
                e -> logout()
        );

        return panel;
    }

    // =========================
    // LOGOUT
    // =========================
    private void logout() {

        stopTimer();

        usernameField.setText("");
        passwordField.setText("");

        currentQuestion = 0;

        cardLayout.show(
                mainPanel,
                LOGIN
        );
    }

    // =========================
    // GET CURRENT CARD
    // =========================
    private String getCurrentCard() {

        // Determine current screen from visible component

        for (Component component :
                mainPanel.getComponents()) {

            if (component.isVisible()) {

                if (component == mainPanel.getComponent(0))
                    return LOGIN;

                if (component == mainPanel.getComponent(1))
                    return PROFILE;

                if (component == mainPanel.getComponent(2))
                    return EXAM;

                if (component == mainPanel.getComponent(3))
                    return RESULT;
            }
        }

        return LOGIN;
    }

    // =========================
    // CREATE QUESTIONS
    // =========================
    private List<Question> createQuestions() {

        List<Question> list =
                new ArrayList<>();

        list.add(
                new Question(
                        "Which language is mainly used to build this application?",
                        new String[]{
                                "Python",
                                "Java",
                                "HTML",
                                "SQL"
                        },
                        1
                )
        );

        list.add(
                new Question(
                        "Which Swing component is used for a single-choice option?",
                        new String[]{
                                "JButton",
                                "JLabel",
                                "JRadioButton",
                                "JTextField"
                        },
                        2
                )
        );

        list.add(
                new Question(
                        "Which class groups radio buttons so only one can be selected?",
                        new String[]{
                                "ButtonGroup",
                                "JPanel",
                                "JFrame",
                                "Timer"
                        },
                        0
                )
        );

        list.add(
                new Question(
                        "Which Swing class is useful for a repeating GUI timer?",
                        new String[]{
                                "Scanner",
                                "Timer",
                                "ThreadGroup",
                                "Calendar"
                        },
                        1
                )
        );

        list.add(
                new Question(
                        "Which keyword is used to create an object in Java?",
                        new String[]{
                                "class",
                                "this",
                                "new",
                                "object"
                        },
                        2
                )
        );

        list.add(
                new Question(
                        "Which method is the starting point of a Java application?",
                        new String[]{
                                "start()",
                                "run()",
                                "main()",
                                "execute()"
                        },
                        2
                )
        );

        list.add(
                new Question(
                        "Which component is normally used to display non-editable text?",
                        new String[]{
                                "JLabel",
                                "JTextField",
                                "JButton",
                                "JPasswordField"
                        },
                        0
                )
        );

        list.add(
                new Question(
                        "Which layout is useful for switching between different screens?",
                        new String[]{
                                "FlowLayout",
                                "CardLayout",
                                "GridLayout",
                                "BorderLayout"
                        },
                        1
                )
        );

        list.add(
                new Question(
                        "Which component is commonly used to enter a password?",
                        new String[]{
                                "JLabel",
                                "JPasswordField",
                                "JTextArea",
                                "JList"
                        },
                        1
                )
        );

        list.add(
                new Question(
                        "Which data structure can store multiple Question objects?",
                        new String[]{
                                "ArrayList",
                                "boolean",
                                "char",
                                "double"
                        },
                        0
                )
        );

        return list;
    }

    // =========================
    // QUESTION CLASS
    // =========================
    static class Question {

        String question;
        String[] options;
        int correctAnswer;

        Question(
                String question,
                String[] options,
                int correctAnswer
        ) {

            this.question = question;
            this.options = options;
            this.correctAnswer = correctAnswer;
        }
    }

    // =========================
    // MAIN METHOD
    // =========================
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            Main app = new Main();

            app.setVisible(true);
        });
    }
}


