package org.example.ui;

import org.example.common.ValidationObserver;
import org.example.common.ValidationViolation;
import org.example.constraintbuilder.ConstraintBuilder;
import org.example.constraints.definition.*;
import org.example.entities.Product;
import org.example.providers.ProgrammaticClassValidatorProvider;
import org.example.validators.Validator;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.List;

public class ProgrammaticValidationDemo extends JFrame {
    private ProgrammaticClassValidatorProvider provider;
    private Validator validator;
    private Product product = new Product();
    
    private JTextField nameField;
    private JSpinner minLengthSpinner;
    private JSpinner maxLengthSpinner;
    private JTextField quantityField;
    private JSpinner minQuantitySpinner;
    private JSpinner maxQuantitySpinner;
    private JCheckBox nameRequiredCheck;
    private JCheckBox quantityRequiredCheck;
    private JTextArea resultArea;
    private JButton applyRulesButton;
    private JButton validateButton;
    
    public ProgrammaticValidationDemo() {
        provider = new ProgrammaticClassValidatorProvider();
        validator = new Validator(List.of(provider));
        validator.addObserver(new ValidationResultObserver());
        setupUI();
        applyDefaultRules();
    }
    
    private void setupUI() {
        setTitle("Programmatic Validation Demo - Dynamic Rule Configuration");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel titleLabel = new JLabel("Dynamic Validation Configuration");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setDividerLocation(300);
        
        splitPane.setTopComponent(createConfigurationPanel());
        
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.add(createDataInputPanel(), BorderLayout.NORTH);
        bottomPanel.add(createResultPanel(), BorderLayout.CENTER);
        splitPane.setBottomComponent(bottomPanel);
        
        mainPanel.add(splitPane, BorderLayout.CENTER);
        
        add(mainPanel);
        
        pack();
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(700, 650));
    }
    
    private JPanel createConfigurationPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        TitledBorder border = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(100, 149, 237), 2),
            "Validation Rules Configuration"
        );
        border.setTitleFont(new Font("Arial", Font.BOLD, 14));
        panel.setBorder(border);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        JLabel nameLabel = new JLabel("Product Name Rules:");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 13));
        panel.add(nameLabel, gbc);
        
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        nameRequiredCheck = new JCheckBox("Required", true);
        panel.add(nameRequiredCheck, gbc);
        
        gbc.gridx = 1;
        panel.add(new JLabel("Min Length:"), gbc);
        
        gbc.gridx = 2;
        minLengthSpinner = new JSpinner(new SpinnerNumberModel(3, 0, 100, 1));
        panel.add(minLengthSpinner, gbc);
        
        gbc.gridx = 3;
        panel.add(new JLabel("Max Length:"), gbc);
        
        gbc.gridx = 4;
        maxLengthSpinner = new JSpinner(new SpinnerNumberModel(50, 0, 200, 1));
        panel.add(maxLengthSpinner, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 5;
        panel.add(new JSeparator(), gbc);
        
        gbc.gridy = 3;
        JLabel quantityLabel = new JLabel("Quantity Rules:");
        quantityLabel.setFont(new Font("Arial", Font.BOLD, 13));
        panel.add(quantityLabel, gbc);
        
        gbc.gridwidth = 1;
        gbc.gridy = 4;
        gbc.gridx = 0;
        quantityRequiredCheck = new JCheckBox("Required", true);
        panel.add(quantityRequiredCheck, gbc);
        
        gbc.gridx = 1;
        panel.add(new JLabel("Min Value:"), gbc);
        
        gbc.gridx = 2;
        minQuantitySpinner = new JSpinner(new SpinnerNumberModel(0, 0, 100000, 10));
        panel.add(minQuantitySpinner, gbc);
        
        gbc.gridx = 3;
        panel.add(new JLabel("Max Value:"), gbc);
        
        gbc.gridx = 4;
        maxQuantitySpinner = new JSpinner(new SpinnerNumberModel(10000, 0, 100000, 100));
        panel.add(maxQuantitySpinner, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 5;
        gbc.insets = new Insets(15, 5, 5, 5);
        applyRulesButton = new JButton("Apply Rules");
        applyRulesButton.setFont(new Font("Arial", Font.BOLD, 14));
        applyRulesButton.setBackground(new Color(100, 149, 237));
        applyRulesButton.setForeground(Color.BLACK);
        applyRulesButton.setPreferredSize(new Dimension(200, 35));
        applyRulesButton.addActionListener(e -> applyRules());
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(applyRulesButton);
        panel.add(buttonPanel, gbc);
        
        return panel;
    }
    
    private JPanel createDataInputPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        TitledBorder border = BorderFactory.createTitledBorder("Product Data");
        border.setTitleFont(new Font("Arial", Font.BOLD, 14));
        panel.setBorder(border);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Product Name:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        nameField = new JTextField(30);
        panel.add(nameField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        panel.add(new JLabel("Quantity:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        quantityField = new JTextField(30);
        panel.add(quantityField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        validateButton = new JButton("Validate Product");
        validateButton.setFont(new Font("Arial", Font.BOLD, 13));
        validateButton.setBackground(new Color(76, 175, 80));
        validateButton.setForeground(Color.BLACK);
        validateButton.setPreferredSize(new Dimension(180, 35));
        validateButton.addActionListener(e -> validateProduct());
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(validateButton);
        panel.add(buttonPanel, gbc);
        
        return panel;
    }
    
    private JPanel createResultPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        TitledBorder border = BorderFactory.createTitledBorder("Validation Result");
        border.setTitleFont(new Font("Arial", Font.BOLD, 14));
        panel.setBorder(border);
        
        resultArea = new JTextArea(8, 50);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        resultArea.setMargin(new Insets(10, 10, 10, 10));
        
        JScrollPane scrollPane = new JScrollPane(resultArea);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void applyDefaultRules() {
        resultArea.setText("Default rules applied.\n\n" +
                          "Name: Required, 3-50 characters\n" +
                          "Quantity: Required, 0-10000\n\n" +
                          "Enter product data and click 'Validate Product'");
        applyRules();
    }
    
    private void applyRules() {
        ConstraintBuilder builder = new ConstraintBuilder(provider);
        
        java.util.ArrayList<ConstraintDefinition> nameConstraints = new java.util.ArrayList<>();
        
        if (nameRequiredCheck.isSelected()) {
            nameConstraints.add(new NotBlankDefinition().message("Product name is required"));
        }
        
        int minLen = (Integer) minLengthSpinner.getValue();
        int maxLen = (Integer) maxLengthSpinner.getValue();
        nameConstraints.add(new SizeDefinition().min(minLen).max(maxLen).message(
            "Product name must be between " + minLen + " and " + maxLen + " characters"));
        
        java.util.ArrayList<ConstraintDefinition> quantityConstraints = new java.util.ArrayList<>();
        
        if (quantityRequiredCheck.isSelected()) {
            quantityConstraints.add(new NotNullDefinition().message("Quantity is required"));
        }
        
        int minQty = (Integer) minQuantitySpinner.getValue();
        int maxQty = (Integer) maxQuantitySpinner.getValue();
        quantityConstraints.add(new MinDefinition().value(minQty).message(
            "Quantity must be at least " + minQty));
        quantityConstraints.add(new MaxDefinition().value(maxQty).message(
            "Quantity must not exceed " + maxQty));
        
        builder.on(Product.class)
            .constraints("name", nameConstraints.toArray(new ConstraintDefinition[0]))
            .constraints("quantity", quantityConstraints.toArray(new ConstraintDefinition[0]))
            .build();
        
        resultArea.setText("✓ Validation rules updated!\n\n" +
                          "Current Configuration:\n" +
                          "─────────────────────\n" +
                          "Name:\n" +
                          "  • Required: " + nameRequiredCheck.isSelected() + "\n" +
                          "  • Length: " + minLen + "-" + maxLen + " characters\n\n" +
                          "Quantity:\n" +
                          "  • Required: " + quantityRequiredCheck.isSelected() + "\n" +
                          "  • Range: " + minQty + "-" + maxQty + "\n\n" +
                          "Ready to validate!");
        resultArea.setForeground(new Color(0, 100, 0));
    }
    
    private void validateProduct() {
        product.setName(nameField.getText());
        
        try {
            product.setQuantity(Integer.parseInt(quantityField.getText()));
        } catch (NumberFormatException e) {
            product.setQuantity(0);
        }
        
        validator.validate(product);
    }
    
    private class ValidationResultObserver implements ValidationObserver {
        @Override
        public void onValidationSuccess() {
            resultArea.setForeground(new Color(0, 128, 0));
            resultArea.setText("✓ VALIDATION SUCCESSFUL\n\n" +
                             "Product is valid:\n" +
                             "─────────────────\n" +
                             "Name: " + product.getName() + "\n" +
                             "Quantity: " + product.getQuantity() + "\n\n" +
                             "All validation rules passed!");
        }
        
        @Override
        public void onValidationFailure(List<ValidationViolation> violations) {
            resultArea.setForeground(Color.RED);
            StringBuilder sb = new StringBuilder();
            sb.append("✗ VALIDATION FAILED\n\n");
            sb.append("Found ").append(violations.size()).append(" violation(s):\n");
            sb.append("─────────────────────\n\n");
            
            for (ValidationViolation v : violations) {
                sb.append("Field: ").append(v.getPath()).append("\n");
                for (String msg : v.getMessages()) {
                    sb.append("  ✗ ").append(msg).append("\n");
                }
                sb.append("\n");
            }
            
            resultArea.setText(sb.toString());
        }
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            ProgrammaticValidationDemo demo = new ProgrammaticValidationDemo();
            demo.setVisible(true);
        });
    }
}
