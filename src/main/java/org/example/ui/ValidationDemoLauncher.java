package org.example.ui;

import javax.swing.*;
import java.awt.*;

public class ValidationDemoLauncher extends JFrame {
    
    public ValidationDemoLauncher() {
        setupUI();
    }
    
    private void setupUI() {
        setTitle("Validation Framework - Demo Launcher");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));
        
        JLabel titleLabel = new JLabel("Validation Framework Demo");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        add(titleLabel, BorderLayout.NORTH);
        
        JPanel mainPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 20, 30));
        
        JPanel demo1Panel = createDemoPanel(
            "1. User Registration Form",
            "Complete registration form with real-time validation\n" +
            "Features: Multiple fields, nested validation, visual feedback",
            "Launch Registration Form",
            e -> launchRegistrationForm()
        );
        mainPanel.add(demo1Panel);
        
        JPanel demo2Panel = createDemoPanel(
            "2. Simple Product Form",
            "Minimal example showing basic validation\n" +
            "Features: Multiple observers, console + UI feedback",
            "Launch Product Form",
            e -> launchSimpleProductForm()
        );
        mainPanel.add(demo2Panel);
        
        JPanel demo3Panel = createDemoPanel(
            "3. Programmatic Validation Demo",
            "Dynamic rule configuration using ConstraintBuilder\n" +
            "Features: Runtime rule changes, fluent API demonstration",
            "Launch Programmatic Demo",
            e -> launchProgrammaticDemo()
        );
        mainPanel.add(demo3Panel);
        
        JPanel demo4Panel = createDemoPanel(
            "4. Console Validation Demo",
            "Command-line validation examples\n" +
            "Features: Annotation and programmatic validation",
            "Run Console Demo",
            e -> runConsoleDemo()
        );
        mainPanel.add(demo4Panel);
        
        add(mainPanel, BorderLayout.CENTER);
        
        JLabel footerLabel = new JLabel("Demonstrates Observer Pattern for UI Binding");
        footerLabel.setFont(new Font("Arial", Font.ITALIC, 11));
        footerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        footerLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 15, 20));
        add(footerLabel, BorderLayout.SOUTH);
        
        pack();
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(600, 550));
    }
    
    private JPanel createDemoPanel(String title, String description, 
                                   String buttonText, java.awt.event.ActionListener action) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        panel.setBackground(new Color(250, 250, 250));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titleLabel, BorderLayout.NORTH);
        
        JTextArea descArea = new JTextArea(description);
        descArea.setEditable(false);
        descArea.setBackground(new Color(250, 250, 250));
        descArea.setFont(new Font("Arial", Font.PLAIN, 12));
        descArea.setWrapStyleWord(true);
        descArea.setLineWrap(true);
        panel.add(descArea, BorderLayout.CENTER);
        
        JButton launchButton = new JButton(buttonText);
        launchButton.setFont(new Font("Arial", Font.BOLD, 13));
        launchButton.setPreferredSize(new Dimension(200, 35));
        launchButton.addActionListener(action);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(250, 250, 250));
        buttonPanel.add(launchButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void launchRegistrationForm() {
        SwingUtilities.invokeLater(() -> {
            RegistrationForm form = new RegistrationForm();
            form.setVisible(true);
        });
    }
    
    private void launchSimpleProductForm() {
        SwingUtilities.invokeLater(() -> {
            SimpleProductForm form = new SimpleProductForm();
            form.setVisible(true);
        });
    }
    
    private void launchProgrammaticDemo() {
        SwingUtilities.invokeLater(() -> {
            ProgrammaticValidationDemo demo = new ProgrammaticValidationDemo();
            demo.setVisible(true);
        });
    }
    
    private void runConsoleDemo() {
        new Thread(() -> {
            try {
                org.example.Main.main(new String[]{});
                
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(
                        ValidationDemoLauncher.this,
                        "Console demo completed.\nCheck the console output for results.",
                        "Console Demo",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                });
            } catch (Exception e) {
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(
                        ValidationDemoLauncher.this,
                        "Error running console demo:\n" + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                });
            }
        }).start();
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            ValidationDemoLauncher launcher = new ValidationDemoLauncher();
            launcher.setVisible(true);
        });
    }
}
