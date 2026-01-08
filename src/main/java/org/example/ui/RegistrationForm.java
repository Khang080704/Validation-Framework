package org.example.ui;

import org.example.common.ValidationObserver;
import org.example.common.ValidationViolation;
import org.example.entities.User;
import org.example.providers.AnnotationClassValidatorProvider;
import org.example.validators.Validator;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistrationForm extends JFrame {
    private Validator validator;
    private User user = new User();

    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JTextField ageField;
    private JCheckBox activeCheckbox;
    private JButton submitButton;
    private JButton clearButton;
    
    private Map<String, JLabel> errorLabels = new HashMap<>();
    private JLabel statusLabel;
    
    public RegistrationForm() {
        this.validator = new Validator(List.of(
            new AnnotationClassValidatorProvider()
        ));
        
        this.validator.addObserver(new FormValidationObserver());
        
        setupUI();
        attachEventHandlers();
    }
    
    private void setupUI() {
        setTitle("User Registration - Validation Framework Demo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel titleLabel = new JLabel("User Registration Form");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        mainPanel.add(titleLabel, gbc);
        
        gbc.gridwidth = 1;
        
        addFieldRow(mainPanel, gbc, 1, "First Name:", 
                    firstNameField = new JTextField(20), "firstName");
        
        addFieldRow(mainPanel, gbc, 2, "Last Name:", 
                    lastNameField = new JTextField(20), "lastName");
        
        addFieldRow(mainPanel, gbc, 3, "Email:", 
                    emailField = new JTextField(20), "email");
        
        addFieldRow(mainPanel, gbc, 4, "Age:", 
                    ageField = new JTextField(20), "age");
        
        gbc.gridx = 0;
        gbc.gridy = 5;
        JLabel activeLabel = new JLabel("Active:");
        activeLabel.setFont(new Font("Arial", Font.BOLD, 12));
        mainPanel.add(activeLabel, gbc);
        
        gbc.gridx = 1;
        activeCheckbox = new JCheckBox();
        activeCheckbox.setSelected(true);
        mainPanel.add(activeCheckbox, gbc);
        
        gbc.gridx = 2;
        JLabel activeError = new JLabel();
        errorLabels.put("active", activeError);
        styleErrorLabel(activeError);
        mainPanel.add(activeError, gbc);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        submitButton = new JButton("Submit");
        submitButton.setPreferredSize(new Dimension(120, 35));
        submitButton.setFont(new Font("Arial", Font.BOLD, 13));
        submitButton.setBackground(new Color(76, 175, 80));
        submitButton.setForeground(Color.BLACK);
        submitButton.setFocusPainted(false);
        
        clearButton = new JButton("Clear");
        clearButton.setPreferredSize(new Dimension(120, 35));
        clearButton.setFont(new Font("Arial", Font.BOLD, 13));
        clearButton.setBackground(new Color(158, 158, 158));
        clearButton.setForeground(Color.BLACK);
        clearButton.setFocusPainted(false);
        
        buttonPanel.add(submitButton);
        buttonPanel.add(clearButton);
        
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 3;
        mainPanel.add(buttonPanel, gbc);
        
        statusLabel = new JLabel(" ");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 14));
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        gbc.gridy = 7;
        gbc.insets = new Insets(15, 8, 8, 8);
        mainPanel.add(statusLabel, gbc);
        
        add(mainPanel, BorderLayout.CENTER);
        
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        JTextArea infoText = new JTextArea(
            "Validation Rules:\n" +
            "• First Name & Last Name: Required (not blank)\n" +
            "• Email: Valid email format required\n" +
            "• Age: Must be between 1 and 100\n" +
            "• Active: Must be checked (true)\n\n" +
            "Try entering invalid data to see real-time validation!"
        );
        infoText.setEditable(false);
        infoText.setBackground(new Color(240, 248, 255));
        infoText.setFont(new Font("Arial", Font.PLAIN, 11));
        infoText.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        infoPanel.add(infoText, BorderLayout.CENTER);
        
        add(infoPanel, BorderLayout.SOUTH);
        
        pack();
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(600, 550));
    }
    
    private void addFieldRow(JPanel panel, GridBagConstraints gbc, int row, 
                            String labelText, JTextField field, String propertyName) {
        gbc.gridx = 0;
        gbc.gridy = row;
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(label, gbc);
        
        gbc.gridx = 1;
        field.setPreferredSize(new Dimension(200, 25));
        panel.add(field, gbc);
        
        gbc.gridx = 2;
        JLabel errorLabel = new JLabel();
        errorLabels.put(propertyName, errorLabel);
        styleErrorLabel(errorLabel);
        panel.add(errorLabel, gbc);
    }
    
    private void styleErrorLabel(JLabel label) {
        label.setForeground(Color.RED);
        label.setFont(new Font("Arial", Font.ITALIC, 10));
        label.setPreferredSize(new Dimension(250, 25));
    }
    
    private void attachEventHandlers() {
        submitButton.addActionListener(e -> handleSubmit());
        
        clearButton.addActionListener(e -> clearForm());
    }
    
    private void validateField(String fieldName, Object value, Runnable setter) {
        setter.run();
        validator.validateProperty(user, fieldName);
    }
    
    private void handleSubmit() {
        user.setFirstName(firstNameField.getText());
        user.setLastName(lastNameField.getText());
        user.setEmail(emailField.getText());
        
        try {
            user.setAge(Integer.parseInt(ageField.getText()));
        } catch (NumberFormatException e) {
            user.setAge(0);
        }
        
        user.setActive(activeCheckbox.isSelected());
        validator.validate(user);
    }
    
    private void clearForm() {
        firstNameField.setText("");
        lastNameField.setText("");
        emailField.setText("");
        ageField.setText("");
        activeCheckbox.setSelected(true);
        
        errorLabels.values().forEach(label -> label.setText(""));
        
        setFieldState(firstNameField, null);
        setFieldState(lastNameField, null);
        setFieldState(emailField, null);
        setFieldState(ageField, null);
        
        statusLabel.setText(" ");
        statusLabel.setForeground(Color.BLACK);
        
        user = new User();
    }
    
    private void setFieldState(JTextField field, Boolean isValid) {
        if (isValid == null) {
            field.setBackground(Color.WHITE);
            field.setBorder(UIManager.getBorder("TextField.border"));
        } else if (isValid) {
            field.setBackground(new Color(220, 255, 220));
            field.setBorder(BorderFactory.createLineBorder(new Color(76, 175, 80), 2));
        } else {
            field.setBackground(new Color(255, 220, 220));
            field.setBorder(BorderFactory.createLineBorder(new Color(244, 67, 54), 2));
        }
    }
    
    private class FormValidationObserver implements ValidationObserver {
        @Override
        public void onValidationSuccess() {
            System.out.println("DEBUG: onValidationSuccess called");
            errorLabels.values().forEach(label -> label.setText(""));
            
            setFieldState(firstNameField, true);
            setFieldState(lastNameField, true);
            setFieldState(emailField, true);
            setFieldState(ageField, true);
            
            statusLabel.setText("✓ Validation Successful! User data is valid.");
            statusLabel.setForeground(new Color(76, 175, 80));
            statusLabel.setBackground(new Color(220, 255, 220));
            statusLabel.setOpaque(true);
            
            JOptionPane.showMessageDialog(
                RegistrationForm.this,
                "User registered successfully!\n\n" +
                "Name: " + user.getFirstName() + " " + user.getLastName() + "\n" +
                "Email: " + user.getEmail() + "\n" +
                "Age: " + user.getAge() + "\n" +
                "Active: " + user.isActive(),
                "Registration Success",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
        
        @Override
        public void onValidationFailure(List<ValidationViolation> violations) {
            System.out.println("DEBUG: onValidationFailure called with " + violations.size() + " violations");
            errorLabels.values().forEach(label -> label.setText(""));
            setFieldState(firstNameField, null);
            setFieldState(lastNameField, null);
            setFieldState(emailField, null);
            setFieldState(ageField, null);
            
            int errorCount = 0;
            for (ValidationViolation violation : violations) {
                String path = violation.getPath();
                JLabel errorLabel = errorLabels.get(path);
                
                if (errorLabel != null) {
                    String errorText = "✗ " + String.join(", ", violation.getMessages());
                    errorLabel.setText(errorText);
                    errorLabel.setToolTipText(errorText);
                    errorCount += violation.getMessages().size();
                    
                    switch (path) {
                        case "firstName":
                            setFieldState(firstNameField, false);
                            break;
                        case "lastName":
                            setFieldState(lastNameField, false);
                            break;
                        case "email":
                            setFieldState(emailField, false);
                            break;
                        case "age":
                            setFieldState(ageField, false);
                            break;
                    }
                }
            }
            
            statusLabel.setText("✗ Validation Failed! Found " + errorCount + 
                              " error(s). Please fix the highlighted fields.");
            statusLabel.setForeground(new Color(244, 67, 54));
            statusLabel.setBackground(new Color(255, 220, 220));
            statusLabel.setOpaque(true);
        }
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            RegistrationForm form = new RegistrationForm();
            form.setVisible(true);
        });
    }
}
